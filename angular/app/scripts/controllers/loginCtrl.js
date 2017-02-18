/**
 * @ngdoc overview
 * @name pocsacApp
 * @description # pocsacApp
 *
 * Login Controller.
 */

'use strict';

cgiWebApp.controller('loginController',
  ['$scope', 'Authenticator', '$sessionStorage', '$state',
  function ($scope, Authenticator, $sessionStorage, $state) {

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
      email: $scope.user.email,
      password: $scope.user.password
    };

    Authenticator.authenticate(credentials).then(function(response) {
      if (response.status === 200) {
        $sessionStorage.put('jwt', response.data.authToken);
        $state.go('landing',{role: response.data.role});
      }
    }).catch(function(){
      $scope.popUp('error', 'LOGIN.MESSAGE.INVALID');
    });
  };

  $scope.logout = function() {
    $sessionStorage.remove('jwt');
    $state.go('login');
  };
}]);
