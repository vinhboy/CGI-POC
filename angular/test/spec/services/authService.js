'use strict';

describe('Authenticator', function() {
  var authenticationService;
  var $httpBackend;
  var urls;

  beforeEach(module('cgi-web-app'));

  beforeEach(inject(function(_Authenticator_, _urls_, _$httpBackend_) {
    authenticationService = _Authenticator_;
    urls = _urls_;
    $httpBackend = _$httpBackend_;
  }));

  describe('authenticate', function() {
    it('should post to the expected login endpoint', function() {
      var credentials = { username: 'user', password: 'pw' };
      $httpBackend.expectPOST('http://localhost:8080/login', credentials)
        .respond(200, {});

      authenticationService.authenticate(credentials);
      $httpBackend.flush();

      $httpBackend.verifyNoOutstandingExpectation();
      $httpBackend.verifyNoOutstandingRequest();
    });

    it('should construct the endpoint URL', function() {
      var credentials = { username: 'user', password: 'pw' };
      $httpBackend.expectPOST(urls.BASE + '/login', credentials)
        .respond(200, {});

      authenticationService.authenticate(credentials);
      $httpBackend.flush();

      $httpBackend.verifyNoOutstandingExpectation();
      $httpBackend.verifyNoOutstandingRequest();
    });
  });
});
