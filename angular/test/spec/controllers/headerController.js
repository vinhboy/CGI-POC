'use strict';

describe('headerController', function() {
  var headerController;
  var $scope;
  var $state;
  var authenticationService;

  beforeEach(module('cgi-web-app'));

  beforeEach(inject(function(_$rootScope_, _$controller_, _$state_, _Authenticator_) {
    $scope = _$rootScope_.$new();
    $state = _$state_;
    authenticationService = _Authenticator_;

    headerController = _$controller_('headerController', {
      $scope: $scope,
      $state: $state,
      Authenticator: authenticationService
    });
  }));

  describe('isLoggedIn', function() {
    it('should ask the Authenticator', function() {
      spyOn(authenticationService, 'isLoggedIn').and.returnValue(true);
      expect($scope.isLoggedIn()).toBe(true);
      expect(authenticationService.isLoggedIn).toHaveBeenCalled();
    });
  });

  describe('isAdminUser', function() {
    it('should ask the Authenticator', function() {
      spyOn(authenticationService, 'isAdminUser').and.returnValue(true);
      expect($scope.isAdminUser()).toBe(true);
      expect(authenticationService.isAdminUser).toHaveBeenCalled();
    });
  });

  describe('logout', function() {
    it('should request logout to the Authenticator', function() {
      spyOn(authenticationService, 'logout');
      $scope.logout();
      expect(authenticationService.logout).toHaveBeenCalled();
    });

    it('should redirect to login', function() {
      spyOn($state, 'go');
      $scope.logout();
      expect($state.go).toHaveBeenCalledWith('login');
    });
  });
});
