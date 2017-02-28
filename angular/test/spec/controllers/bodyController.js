'use strict';

describe('bodyController', function() {
  var bodyController;
  var $scope;
  var $state;

  beforeEach(module('cgi-web-app'));

  beforeEach(inject(function(_$rootScope_, _$controller_, _$state_) {
    $scope = _$rootScope_.$new();
    $state = _$state_;

    bodyController = _$controller_('bodyController', {
      $scope: $scope,
      $state: $state
    });
  }));

  describe('isLightningNeeded', function() {
    it('should set lightningNeeded to true if on login page', function() {
      $state.current.name = 'login';
      expect($scope.isLightningNeeded()).toBe(true);
    });

    it('should set lightningNeeded to false if NOT on login page', function() {
      $state.current.name = 'landing';
      expect($scope.isLightningNeeded()).toBe(false);
    });
  });
});
