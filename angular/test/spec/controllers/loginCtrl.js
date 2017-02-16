'use strict';

describe('loginController', function() {
  var loginController;
  var $scope;
  var authenticationService;
  var $sessionStorage;
  var $q;
  var deferred;

  beforeEach(module('cgi-web-app'));

  beforeEach(inject(function(_$rootScope_, _$controller_, _Authenticator_, _$sessionStorage_, _$q_) {
    $q = _$q_;
    $scope = _$rootScope_.$new();
    authenticationService = _Authenticator_;
    $sessionStorage = _$sessionStorage_;

    deferred = _$q_.defer();
    spyOn(authenticationService, 'authenticate').and.returnValue(deferred.promise);

    loginController = _$controller_('loginController', {
      $scope: $scope,
      Authenticator: authenticationService,
      $sessionStorage: $sessionStorage
    });
  }));

  it('should initially not have any notifications', function() {
    expect($scope.model.errorNotif).toBe(false);
    expect($scope.model.successNotif).toBe(false);
  });

  it('should assign error notifications on pop-up', function() {
    $scope.popUp('error', 'errorMessage', 0);
    expect($scope.model.errorNotif).toBe(true);
    expect($scope.model.errorMessage).toBe('errorMessage');
  });

  it('should assign success notifications on pop-up', function() {
    $scope.popUp('success', 'successMessage', 0);
    expect($scope.model.successNotif).toBe(true);
    expect($scope.model.successMessage).toBe('successMessage');
  });

  describe('authentication', function() {
    it('should set the success message', function() {
      spyOn($scope, 'popUp');

      $scope.submitForm();
      deferred.resolve({ status: 200, data: { autToken: 'token' } });
      $scope.$apply();

      expect($scope.popUp).toHaveBeenCalledWith('success', 'LOGIN.MESSAGE.LOGGEDIN');
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
});
