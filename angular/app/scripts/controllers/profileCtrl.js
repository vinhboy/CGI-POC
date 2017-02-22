/**
 * @ngdoc overview
 * @name pocsacApp
 * @description # pocsacApp
 *
 * Profile Controller.
 */

'use strict';

cgiWebApp.controller('ProfileController',
  ['$scope', 'ProfileService', '$state', '$sessionStorage', 'Authenticator', '$anchorScroll',
  function ($scope, ProfileService, $state, $sessionStorage, Authenticator,$anchorScroll) {

  $scope.init = function() {
    $scope.apiErrors = [];

    $scope.profile = {
      firstName: '',
      lastName: '',
      email: '',
      password: '',
      passwordConfirmation: '',
      phone: '',
      address1: '',
      address2: '',
      city: '',
      state: '',
      zipCode: '',
      emailNotification: false,
      pushNotification: false,
      smsNotification: false,
      notificationType: []
    };

    if ($scope.isEdit()) {
      ProfileService.getProfile().then(function(response) {
        $scope.profile = response.data;
      });
    }

    $scope.regexZip = /^\d{5}$/;
    $scope.regexPassword = /^(?=.{8,})((?=.*\d)(?=.*[a-z])(?=.*[A-Z])|(?=.*\d)(?=.*[a-zA-Z])(?=.*[\W_])|(?=.*[a-z])(?=.*[A-Z])(?=.*[\W_])).*/;
    $scope.regexPhone = /^\d{3}-?\d{3}-?\d{4}$/;
  };

  $scope.processApiErrors = function(response) {
    $scope.apiErrors = [];
    if (response.data && response.data.errors) {
      for (var i = 0; i < response.data.errors.length; i++) {
        if (response.data.errors[i].message) {
          $scope.apiErrors.push(response.data.errors[i].message);
        }
      }
    } else if (response.status === 401 && response.data) {
      $scope.apiErrors.push(response.data);
    } else {
      $scope.apiErrors.push('Server error occurred. Please try again later.');
    }

    if ($scope.apiErrors.length > 0) {
      $anchorScroll();
    }
  };

  $scope.processNotificationTypes = function() {
    var notificationTypes = [];
    if ($scope.profile.emailNotification) {
      notificationTypes.push({ notificationId: 1 });
    }
    if ($scope.profile.smsNotification) {
      notificationTypes.push({ notificationId: 2 });
    }
    if ($scope.profile.pushNotification) {
      notificationTypes.push({ notificationId: 3 });
    }
    $scope.profile.notificationType = notificationTypes;
  };

  $scope.generatePhoneNumber = function() {
    $scope.profile.phone = $scope.profile.phone.replace(/-/g, '');
  };

  $scope.process = function(beforeNavFunc){
    $scope.processNotificationTypes();
    $scope.generatePhoneNumber();

    var toPost = {
      email: $scope.profile.email,
      password: $scope.profile.password,
      firstName: $scope.profile.firstName,
      lastName: $scope.profile.lastName,
      phone: $scope.profile.phone,
      address1: $scope.profile.address1,
      address2: $scope.profile.address2,
      city: $scope.profile.city,
      state: $scope.profile.state,
      zipCode: $scope.profile.zipCode,
      notificationType: $scope.profile.notificationType
    };

    //putting this on scope so I can test
    $scope.toSend = toPost;

    var toCall;
    if ($scope.isNew()) {
      toCall = ProfileService.register;
    } else {
      toCall = ProfileService.update;
      if ($scope.profile.password === '') {
        toPost.password = undefined;
      }
    }

    toCall(toPost).then(function() {
      if (beforeNavFunc) {
        beforeNavFunc(toPost);
      }
      $state.go('landing');
    }).catch(function(response) {
      $scope.processApiErrors(response);
    });
  };

  $scope.registerProfile = function() {
    var beforeNavFunc = function(toPost) {
      var credentials = {
        email: toPost.email,
        password: toPost.password
      };

      Authenticator.authenticate(credentials).then(function(response) {
        if (response.status === 200) {
          $sessionStorage.put('jwt', response.data.authToken);
        }
      }).catch(function(response) {
        console.log('we should never get this b/c we registered the user first, then turned right around and authenticated with the same info.');
        console.log(response);
      });
    };
    $scope.process(beforeNavFunc);
  };

  $scope.updateProfile = function() {
    $scope.process();
  };

  $scope.someSelected = function() {
    return $scope.profile.emailNotification || $scope.profile.pushNotification || $scope.profile.smsNotification;
  };

  $scope.isNew = function() {
    return $state.current.name === 'register';
  };

  $scope.isEdit = function() {
    return $state.current.name === 'manageProfile';
  };

  $scope.isPasswordValid = function() {
    if ($scope.isNew()) {
      return $scope.profile.password !== '' && $scope.regexPassword.test($scope.profile.password);
    }
    else {
      return $scope.profile.password === '' || $scope.regexPassword.test($scope.profile.password);
    }
  };

  $scope.init();
}]);

cgiWebApp.directive('compareTo', function() {
  return {
    require: 'ngModel',
    scope: {
      otherModelValue: '=compareTo'
    },
    link: function(scope, element, attributes, ngModel) {
      ngModel.$validators.compareTo = function(modelValue) {
        return modelValue === scope.otherModelValue;
      };

      scope.$watch('otherModelValue', function() {
        ngModel.$validate();
      });
    }
  };
});
