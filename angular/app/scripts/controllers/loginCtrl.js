/**
 * @ngdoc overview
 * @name pocsacApp
 * @description # pocsacApp
 *
 * Login Controller.
 */

'use strict';

cgiWebApp.controller('loginController',
  ['$scope', 'Authenticator', '$sessionStorage', '$state','$http',
  function ($scope, Authenticator, $sessionStorage, $state,$http) {

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

    Authenticator.authenticate(credentials).then(function(response) {
      if (response.status === 200) {
        $sessionStorage.put('jwt', response.data.authToken);
        $sessionStorage.put('role', response.data.role);
        
        $http.defaults.headers.common['Content-Type'] = 'application/json';
        $http.defaults.headers.common.Authorization =  'Bearer ' + response.data.authToken;
        
        $state.go('landing');
      }
    }).catch(function(){
      $scope.popUp('error', 'LOGIN.MESSAGE.INVALID');
    });
  };

}]);
