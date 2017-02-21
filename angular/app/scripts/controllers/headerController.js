/**
 * @ngdoc overview
 * @name pocsacApp
 * @description # pocsacApp
 *
 * Landing Controller.
 * Will be responsible for loading data for notifications and displaying a map component
 * This component will be re-used for admin functionality and end user
 * Header will have different options for an end user
 * Admin will be able to filter data and publish notifications
 */

'use strict';

cgiWebApp.controller('headerController',
  ['$scope', '$sessionStorage', '$state',
  function ($scope, $sessionStorage, $state) {
    $scope.isLoggedIn = function(){
      return $sessionStorage.get('jwt') !== null ;
    };

    $scope.isAuth = function(){
      return $state.current.name === 'login';
    };
}]);
