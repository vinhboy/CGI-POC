'use strict';

describe('loginController', function() {
  var loginController;
  var $scope;
  var authenticationService;
  var $sessionStorage;

  beforeEach(module('cgi-web-app'));

  beforeEach(inject(function($rootScope, $controller, _Authenticator_, _$sessionStorage_) {
    $scope = $rootScope.$new();
    authenticationService = _Authenticator_;
    $sessionStorage = _$sessionStorage_;
    loginController = $controller('loginController', {
      $scope: $scope,
      Authenticator: authenticationService,
      $sessionStorage: $sessionStorage
    });
  }));

  it('should initially not have any notifications', function() {
    expect($scope.model.errorNotif).toBe(false);
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
      spyOn(authenticationService, 'authenticate').and.returnValue({ success: true });
      spyOn($scope, 'popUp');
      $scope.submitForm(true);
      expect($scope.model.errorNotif).toBe(false);
      expect($scope.model.successNotif).toBe(true);
      expect($scope.model.successMessage).toBe('LOGIN.MESSAGE.LOGGEDIN');
      expect($scope.popUp).not.toHaveBeenCalled();
    });

    it('should set the error message on unauthorized', function() {
      spyOn(authenticationService, 'authenticate').and.returnValue({ success: false, error_code: 401 });
      spyOn($scope, 'popUp');
      $scope.submitForm(true);
      expect($scope.popUp).toHaveBeenCalledWith('error', 'LOGIN.MESSAGE.UNVALID');
    });

    it('should set the error message on any error', function() {
      spyOn(authenticationService, 'authenticate').and.returnValue({ success: false, error_code: 500 });
      spyOn($scope, 'popUp');
      $scope.submitForm(true);
      expect($scope.popUp).toHaveBeenCalledWith('error', 'GENERIC.MESSAGE.ERROR.SERVER');
    });

    it('should not do anything if the form is invalid', function() {
      spyOn(authenticationService, 'authenticate');
      $scope.submitForm(false);
      expect(authenticationService.authenticate).not.toHaveBeenCalled();
    });
  });
});
