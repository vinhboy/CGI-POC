/**
 * @ngdoc overview
 * @name pocsacApp
 * @description # pocsacApp
 *
 * Profile Controller.
 */

'use strict';

cgiWebApp.controller('ProfileController',
  ['$scope', 'ProfileService', '$state', 'Authenticator', '$anchorScroll',
  function ($scope, ProfileService, $state, Authenticator, $anchorScroll) {

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
      smsNotification: false
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

  $scope.generatePhoneNumber = function() {
    $scope.profile.phone = $scope.profile.phone.replace(/-/g, '');
  };

  // this is meant to null out any empty strings, be careful with false/0
  $scope.processForNull = function(toSend, property) {
    if(!toSend[property]) {
      toSend[property] = null;
    }
  };

  $scope.process = function(beforeNavFunc) {
    $scope.generatePhoneNumber();

    var toSend = {
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
      emailNotification: $scope.profile.emailNotification,
      pushNotification: $scope.profile.pushNotification,
      smsNotification: $scope.profile.smsNotification
    };

    $scope.processForNull(toSend, 'firstName');
    $scope.processForNull(toSend, 'lastName');
    $scope.processForNull(toSend, 'phone');
    $scope.processForNull(toSend, 'address1');
    $scope.processForNull(toSend, 'address2');
    $scope.processForNull(toSend, 'city');
    $scope.processForNull(toSend, 'state');

    //putting this on scope so I can test
    $scope.toSend = toSend;

    var toCall;
    if ($scope.isNew()) {
      toCall = ProfileService.register;
    } else {
      toCall = ProfileService.update;
      if ($scope.profile.password === '') {
        toSend.password = undefined;
      }
    }

    toCall(toSend).then(function() {
      if (beforeNavFunc) {
        beforeNavFunc(toSend);
      }
    }).then(function() {
      $state.go('landing');
    }).catch(function(response) {
      $scope.processApiErrors(response);
    });
  };

  $scope.registerProfile = function() {
    var beforeNavFunc = function(toSend) {
      var credentials = {
        email: toSend.email,
        password: toSend.password
      };

      Authenticator.authenticate(credentials).catch(function(response) {
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
