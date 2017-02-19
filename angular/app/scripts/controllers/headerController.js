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
  ['$scope', '$window','$location',
  function ($scope,$window,$location) {
    $scope.isLoggedIn = function(){
      return $window.sessionStorage.getItem('jwt') !== null ;
    };
    
    $scope.isAuth = function(){
      return ($location.url() === '/login');
    };
    

}]);
