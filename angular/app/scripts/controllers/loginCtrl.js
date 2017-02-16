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
      var dataObject = {
        email: $scope.user.username,
        password: $scope.user.password
      };

      Authenticator.authenticate(dataObject).then(function(response) {
      if (response.status === 200) {
        $scope.model.errorNotif = false;
        $scope.model.successNotif = true;
        $scope.model.successMessage = 'LOGIN.MESSAGE.LOGGEDIN';
        $sessionStorage.put('jwt', response.data.authToken);

      } else if (response.status === 401) {
        $scope.popUp('error', 'LOGIN.MESSAGE.UNVALID');
      } else {
        $scope.popUp('error', 'GENERIC.MESSAGE.ERROR.SERVER');
      }
        $scope.authForm.$setPristine();
        $scope.authForm.$setUntouched();
      });
      clearFields();
    }
  };

  var clearFields = function() {
    $scope.user.username = '';
    $scope.user.password = '';
  }
}]);
