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
  describe('isAdminUser', function() {
    it('true if seesion value is ADMIN ', function() {
      spyOn($sessionStorage, 'get').and.returnValue('ADMIN');
      expect($scope.isAdminUser()).toBe(true);
    });

    it('false if seesion value is not ADMIN or not set', function() {
      spyOn($sessionStorage, 'get').and.returnValue(null);
      expect($scope.isAdminUser()).toBe(false);
    });
    it('false if seesion value is not ADMIN or not set', function() {
      spyOn($sessionStorage, 'get').and.returnValue("RESIDENT");
      expect($scope.isAdminUser()).toBe(false);
    });
  });

 
});
