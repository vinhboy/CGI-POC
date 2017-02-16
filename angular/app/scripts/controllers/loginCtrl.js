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
    errorMessage: '',
    successMessage: ''
  };

  $scope.popUp = function(code, message) {
    $scope.model.errorNotif = false;
    $scope.model.successNotif = false;

    if (code === 'error') {
      $scope.model.errorNotif = true;
      $scope.model.errorMessage = message;
    } else if (code === 'success') {
      $scope.model.successNotif = true;
      $scope.model.successMessage = message;
    }
  };

  $scope.submitForm = function() {
    $scope.popUp();
    var credentials = {
      email: $scope.user.username,
      password: $scope.user.password
    };

    Authenticator.authenticate(credentials).then(function(response) {
      if (response.status === 200) {
        $scope.popUp('success', 'LOGIN.MESSAGE.LOGGEDIN');
        $sessionStorage.put('jwt', response.data.authToken);
      }
      clearFields();
    }).catch(function(response){
      $scope.popUp('error', 'LOGIN.MESSAGE.INVALID');
    });
  };

  var clearFields = function() {
    $scope.user.username = '';
    $scope.user.password = '';
  };
}]);
