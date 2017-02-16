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
});
