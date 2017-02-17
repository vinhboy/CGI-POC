/**
 * @ngdoc overview
 * @name pocsacApp
 * @description # pocsacApp
 *
 * Profile Controller.
 */

'use strict';

cgiWebApp.controller('ProfileController',
  ['$scope', 'ProfileService',
  function ($scope, ProfileService) {

  $scope.profile = {
    firstName: '',
    lastName: '',
    email: '',
    password: '',
    confirmPassword: '',
    phone: '',
    zipCode: '',
    emailNotification: false,
    pushNotification: false,
    smsNotification: false,
    geoNotification: false,
    notificationType: []
  };

  $scope.regexZip = /^\d{5}$/;
  $scope.regexPhone = /^\d{3}-?\d{3}-?\d{4}$/;
  $scope.regexPassword = /^(?=.{8,})((?=.*\d)(?=.*[a-z])(?=.*[A-Z])|(?=.*\d)(?=.*[a-zA-Z])(?=.*[\W_])|(?=.*[a-z])(?=.*[A-Z])(?=.*[\W_])).*/;

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

  $scope.registerProfile = function() {
    $scope.processNotificationTypes();

    ProfileService.register($scope.profile).then(function(response) {
      if (response.status === 200) {
        console.log('yay success registering! ');
        console.log(response);
      }
    }).catch(function(){
      alert('something went very wrong.');
    });
  };

  $scope.someSelected = function() {
    return $scope.profile.emailNotification || $scope.profile.pushNotification || $scope.profile.smsNotification || $scope.profile.geoNotification;
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
