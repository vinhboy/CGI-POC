'use strict';

describe('loginController', function() {
  var loginController;
  var $scope;
  var authenticationService;
  var $state;
  var $q;
  var deferred;

  beforeEach(module('cgi-web-app'));

  beforeEach(inject(function(_$rootScope_, _$controller_, _Authenticator_, _$state_, _$q_) {
    $q = _$q_;
    $scope = _$rootScope_.$new();
    authenticationService = _Authenticator_;
    $state = _$state_;

    deferred = _$q_.defer();
    spyOn(authenticationService, 'authenticate').and.returnValue(deferred.promise);

    loginController = _$controller_('loginController', {
      $scope: $scope,
      Authenticator: authenticationService,
      $state: $state
    });
  }));

  describe('init', function() {
    it('should initially not have any notifications', function() {
      expect($scope.model.errorNotif).toBe(false);
    });

    it('should logout any logged-in user', function() {
      spyOn(authenticationService, 'logout');
      $scope.init();
      expect(authenticationService.logout).toHaveBeenCalled();
    });
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

      expect($state.go).toHaveBeenCalledWith('landing');
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
