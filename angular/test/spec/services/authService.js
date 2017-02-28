'use strict';

describe('Authenticator', function() {
  var authenticationService;
  var $httpBackend;
  var urls;
  var $sessionStorage;

  beforeEach(module('cgi-web-app'));

  beforeEach(inject(function(_Authenticator_, _urls_, _$httpBackend_, _$sessionStorage_) {
    authenticationService = _Authenticator_;
    urls = _urls_;
    $httpBackend = _$httpBackend_;
    $sessionStorage = _$sessionStorage_;
  }));

  afterEach(function () {
    $httpBackend.verifyNoOutstandingExpectation();
    $httpBackend.verifyNoOutstandingRequest();
  });

  describe('authenticate', function() {
    it('should post to the expected login endpoint', function() {
      var credentials = { username: 'user', password: 'pw' };
      $httpBackend.expectPOST('http://localhost:8080/login', credentials)
        .respond(200, {});

      authenticationService.authenticate(credentials);
      $httpBackend.flush();
    });

    it('should construct the endpoint URL', function() {
      var credentials = { username: 'user', password: 'pw' };
      $httpBackend.expectPOST(urls.BASE + '/login', credentials)
        .respond(200, {});

      authenticationService.authenticate(credentials);
      $httpBackend.flush();
    });

    it('should set the token/role information onto the session', function() {
      $sessionStorage.put('jwt', 'junk');
      $sessionStorage.put('role', 'junk');

      var credentials = { username: 'user', password: 'pw' };
      $httpBackend.expectPOST(urls.BASE + '/login', credentials)
        .respond(200, { authToken: 'the token', role: 'the role' });

      authenticationService.authenticate(credentials);
      $httpBackend.flush();

      expect($sessionStorage.get('jwt')).toBe('the token');
      expect($sessionStorage.get('role')).toBe('the role');
    });
  });

  describe('logout', function() {
    it('should clear the persisted auth token', function() {
      spyOn($sessionStorage, 'remove');
      authenticationService.logout();
      expect($sessionStorage.remove).toHaveBeenCalledWith('jwt');
    });

    it('should clear the persisted role', function() {
      spyOn($sessionStorage, 'remove');
      authenticationService.logout();
      expect($sessionStorage.remove).toHaveBeenCalledWith('role');
    });
  });

  describe('isLoggedIn', function() {
    it('should consider those with authToken to be logged in', function() {
      spyOn($sessionStorage, 'get').and.returnValue('the jwt token');
      expect(authenticationService.isLoggedIn()).toBe(true);
    });

    it('should consider those without authToken to be logged out', function() {
      spyOn($sessionStorage, 'get').and.returnValue(null);
      expect(authenticationService.isLoggedIn()).toBe(false);
    });
  });

  describe('isAdminUser', function() {
    it('true if session value is ADMIN ', function() {
      spyOn($sessionStorage, 'get').and.returnValue('ADMIN');
      expect(authenticationService.isAdminUser()).toBe(true);
    });

    it('false if session value is not set', function() {
      spyOn($sessionStorage, 'get').and.returnValue(null);
      expect(authenticationService.isAdminUser()).toBe(false);
    });
    it('false if session value is not ADMIN', function() {
      spyOn($sessionStorage, 'get').and.returnValue('RESIDENT');
      expect(authenticationService.isAdminUser()).toBe(false);
    });
  });
});
