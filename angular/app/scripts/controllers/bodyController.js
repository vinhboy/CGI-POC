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

cgiWebApp.controller('bodyController',
  ['$scope',
  function ($scope ) {
    $scope.initLoad = function(){
       // nothing at the moment. Maybe later
    };
    
      $scope.initLoad();


}]);
