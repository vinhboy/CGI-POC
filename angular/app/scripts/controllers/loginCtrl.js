/**
 * @ngdoc overview
 * @name pocsacApp
 * @description # pocsacApp
 *
 * Login Controller.
 */

'use strict';

cgiWebApp.controller('loginController',
  ['$scope', 'Authenticator', '$state',
  function ($scope, Authenticator, $state) {

    $scope.user = {
    email: '',
    password: ''
  };

  $scope.model = {
    errorNotif: false,
    errorMessage: ''
  };

  $scope.popUp = function(code, message) {
    $scope.model.errorNotif = false;

    if (code === 'error') {
      $scope.model.errorNotif = true;
      $scope.model.errorMessage = message;
    }
  };

  $scope.submitForm = function() {
    $scope.popUp();
    var credentials = {
      email: $scope.user.email.toLowerCase(),
      password: $scope.user.password
    };

    Authenticator.authenticate(credentials).then(function() {
      $state.go('landing');
    }).catch(function(){
      $scope.popUp('error', 'LOGIN.MESSAGE.INVALID');
    });
  };

}]);
