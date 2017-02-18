/**
 * @ngdoc overview
 * @name pocsacApp
 * @description # pocsacApp
 *
 * Profile Controller.
 */

'use strict';

cgiWebApp.controller('ProfileController',
  ['$scope', 'ProfileService', '$state',
  function ($scope, ProfileService, $state) {

  $scope.restApiErrors = [];

  $scope.profile = {
    firstName: '',
    lastName: '',
    email: '',
    password: '',
    passwordConfirmation: '',
    phoneNumber: {
      areaCode: '',
      centralOfficeCode: '',
      lineNumber: ''
    },
    phone: '',
    zipCode: '',
    emailNotification: false,
    pushNotification: false,
    smsNotification: false,
    notificationType: [],
    latitude: 0,
    longitude: 0,
    allowNotificationsByLocation: false
  };

  $scope.regexZip = /^\d{5}$/;
  $scope.regexPassword = /^(?=.{8,})((?=.*\d)(?=.*[a-z])(?=.*[A-Z])|(?=.*\d)(?=.*[a-zA-Z])(?=.*[\W_])|(?=.*[a-z])(?=.*[A-Z])(?=.*[\W_])).*/;
  $scope.regexPhoneAreaCode = /^\d{3}$/;
  $scope.regexPhoneCentralOfficeCode = /^\d{3}$/;
  $scope.regexPhoneLineNumber = /^\d{4}$/;

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
    $scope.profile.phone = $scope.profile.phoneNumber.areaCode +
      $scope.profile.phoneNumber.centralOfficeCode +
      $scope.profile.phoneNumber.lineNumber;
  };

  $scope.registerProfile = function() {
    $scope.processNotificationTypes();
    $scope.generatePhoneNumber();

    var toPost = {
      email: $scope.profile.email,
      password: $scope.profile.password,
      firstName: $scope.profile.firstName,
      lastName: $scope.profile.lastName,
      phone: $scope.profile.phone,
      zipCode: $scope.profile.zipCode,
      latitude: 0,
      longitude: 0,
      notificationType: $scope.profile.notificationType
    };

    ProfileService.register(toPost).then(function(response) {
      if (response.status === 200) {
        $state.go('landing');
      }
    }).catch(function(response){
      window.alert('something went very wrong.');
    });
  };

  $scope.someSelected = function() {
    return $scope.profile.emailNotification || $scope.profile.pushNotification || $scope.profile.smsNotification;
  };
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
