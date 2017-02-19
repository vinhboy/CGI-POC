'use strict';

describe('headerController', function() {
  var headerController;
  var $scope;
  var $sessionStorage;
  var $state;

  beforeEach(module('cgi-web-app'));

  beforeEach(inject(function(_$rootScope_, _$controller_, _$sessionStorage_, _$state_) {
    $scope = _$rootScope_.$new();
    $sessionStorage = _$sessionStorage_;
    $state = _$state_;

    headerController = _$controller_('headerController', {
      $scope: $scope,
      $sessionStorage: $sessionStorage,
      $state: $state
    });
  }));

  describe('isLoggedIn', function() {
    it('should consider those with authToken to be logged in', function() {
      spyOn($sessionStorage, 'get').and.returnValue('the jwt token');
      expect($scope.isLoggedIn()).toBe(true);
    });

    it('should consider those without authToken to be logged out', function() {
      spyOn($sessionStorage, 'get').and.returnValue(null);
      expect($scope.isLoggedIn()).toBe(false);
    });
  });

  describe('isAuth', function() {
    it('should be considered isAuth if in the login state', function() {
      $state.current.name = 'login';
      expect($scope.isAuth()).toBe(true);
    });

    it('should be not be considered isAuth if not in the login state', function() {
      $state.current.name = 'somethingElse';
      expect($scope.isAuth()).toBe(false);
    });
  });
});
