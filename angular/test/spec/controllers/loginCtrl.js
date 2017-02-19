'use strict';

describe('loginController', function() {
  var loginController;
  var $scope;
  var authenticationService;
  var $sessionStorage;
  var $state;
  var $q;
  var deferred;

  beforeEach(module('cgi-web-app'));

  beforeEach(inject(function(_$rootScope_, _$controller_, _Authenticator_, _$sessionStorage_, _$state_, _$q_) {
    $q = _$q_;
    $scope = _$rootScope_.$new();
    authenticationService = _Authenticator_;
    $sessionStorage = _$sessionStorage_;
    $state = _$state_;

    deferred = _$q_.defer();
    spyOn(authenticationService, 'authenticate').and.returnValue(deferred.promise);

    loginController = _$controller_('loginController', {
      $scope: $scope,
      Authenticator: authenticationService,
      $sessionStorage: $sessionStorage,
      $state: $state
    });
  }));

  it('should initially not have any notifications', function() {
    expect($scope.model.errorNotif).toBe(false);
  });

  it('should assign error notifications on pop-up', function() {
    $scope.popUp('error', 'errorMessage');
    expect($scope.model.errorNotif).toBe(true);
    expect($scope.model.errorMessage).toBe('errorMessage');
  });

  describe('authentication', function() {
    it('should redirect to landing page on success', function() {
      spyOn($state, 'go');

      $scope.submitForm();
      var response = { status: 200, data: { autToken: 'token', role: 'the role' } };
      deferred.resolve(response);
      $scope.$apply();

      expect($state.go).toHaveBeenCalledWith('landing', { role: response.data.role });
    });

    it('should save the JWT auth token', function() {
      spyOn($sessionStorage, 'put');

      $scope.submitForm();
      deferred.resolve({ status: 200, data: { authToken: 'the jwt auth token' } });
      $scope.$apply();

      expect($sessionStorage.put).toHaveBeenCalledWith('jwt', 'the jwt auth token');
    });

    it('should set the error message on unauthorized', function() {
      spyOn($scope, 'popUp');

      $scope.submitForm();
      deferred.reject();
      $scope.$apply();

      expect($scope.popUp).toHaveBeenCalledWith('error', 'LOGIN.MESSAGE.INVALID');
    });
  });

  describe('logout', function() {
    it('should clear the session', function() {
      spyOn($sessionStorage, 'remove');
      $scope.logout();
      expect($sessionStorage.remove).toHaveBeenCalledWith('jwt');
    });

    it('should redirect to login', function() {
      spyOn($state, 'go');
      $scope.logout();
      expect($state.go).toHaveBeenCalledWith('login');
    });
  });
});
