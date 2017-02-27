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
});
