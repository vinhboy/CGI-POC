/**
 * @ngdoc overview
 * @name pocsacApp
 * @description # pocsacApp
 *
 * Login Controller.
 */

'use strict';

cgiWebApp.controller('loginController',
  ['$scope', 'Authenticator', '$sessionStorage',
  function ($scope, Authenticator, $sessionStorage) {

  $scope.user = {
    username: '',
    password: ''
  };

  $scope.model = {
    errorNotif: false,
    successNotif: false,
    errorMessage: 'GENERIC.MESSAGE.ERROR.SERVER',
    successMessage: 'GENERIC.MESSAGE.SUCCESS'
  };

  $scope.popUp = function(code, message) {
    if (code === 'error') {
      $scope.model.errorNotif = true;
      $scope.model.errorMessage = message;
    } else if (code === 'success') {
      $scope.model.successNotif = true;
      $scope.model.successMessage = message;
    }
  };

  $scope.submitForm = function(isValid) {
    if (isValid) {
      var credentials = {
        email: $scope.user.username,
        password: $scope.user.password
      };

      var authenticationResult = Authenticator.authenticate(credentials);
      if (authenticationResult.success) {
        $scope.model.errorNotif = false;
        $scope.model.successNotif = true;
        $scope.model.successMessage = 'LOGIN.MESSAGE.LOGGEDIN';
      } else if (authenticationResult.error_code === 401) {
        $scope.popUp('error', 'LOGIN.MESSAGE.UNVALID');
      } else {
        $scope.popUp('error', 'GENERIC.MESSAGE.ERROR.SERVER');
      }
      clearFields();
    }
  };

  var clearFields = function() {
    $scope.user.username = '';
    $scope.user.password = '';
  }
}]);
