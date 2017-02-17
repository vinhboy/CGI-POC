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
    notificationType: [],
    emailNotification: false,
    pushNotification: false,
    smsNotification: false,
    geoNotification: false
  };

  $scope.regexZip = /^\d{5}$/;
  $scope.regexPhone = /^\d{3}-?\d{3}-?\d{4}$/;
  $scope.regexPassword = /^(?=.{8,})((?=.*\d)(?=.*[a-z])(?=.*[A-Z])|(?=.*\d)(?=.*[a-zA-Z])(?=.*[\W_])|(?=.*[a-z])(?=.*[A-Z])(?=.*[\W_])).*/;

  $scope.registerProfile = function() {
    console.log('registering: ');
    console.log($scope.profile);

    ProfileService.register($scope.profile).then(function(response) {
      if (response.status === 200) {
        console.log('yay success registering! ');
        console.log(response);
      }
    }).catch(function(response){
      console.log('boo failed registering! ');
      console.log(response);
    });
  };
}]);
