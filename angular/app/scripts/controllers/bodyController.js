'use strict';

cgiWebApp.controller('bodyController',
  ['$scope', '$state',
  function ($scope, $state) {
    $scope.init = function() {
    };

    $scope.isLightningNeeded = function() {
      var lightningNeeded = false;
      if ($state.current.name === 'login') {
        lightningNeeded = true;
      }
      return lightningNeeded;
    };

    $scope.init();
}]);
