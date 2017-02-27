/**
 * @ngdoc overview
 * @name pocsacApp
 * @description # pocsacApp
 */

'use strict';

cgiWebApp.controller('headerController',
  ['$scope', '$state', 'Authenticator',
  function ($scope, $state, Authenticator) {
    $scope.isLoggedIn = function() {
      return Authenticator.isLoggedIn();
    };

    $scope.isAdminUser = function() {
      return Authenticator.isAdminUser();
    };

    $scope.logout = function(){
      Authenticator.logout();
      $state.go('login');
    };
}]);
