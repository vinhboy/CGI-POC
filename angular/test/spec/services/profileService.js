'use strict';

describe('ProfileService', function() {
  var profileService;
  var $httpBackend;
  var urls;
  var profile;

  beforeEach(module('cgi-web-app'));

  beforeEach(inject(function(_ProfileService_, _urls_, _$httpBackend_) {
    profileService = _ProfileService_;
    urls = _urls_;
    $httpBackend = _$httpBackend_;
  }));

  describe('register', function() {
    it('should post to the expected registration endpoint', function() {
      var profile = { email: 'user@example.com', password: 'pw', name: 'jonny depp' };
      $httpBackend.expectPOST('http://localhost:8080/register', profile)
        .respond(200, {});

      profileService.register(profile);
      $httpBackend.flush();

      $httpBackend.verifyNoOutstandingExpectation();
      $httpBackend.verifyNoOutstandingRequest();
    });

    it('should construct the endpoint URL', function() {
      var profile = { email: 'user@example.com', password: 'pw', name: 'jonny depp' };
      $httpBackend.expectPOST(urls.BASE + '/register', profile)
        .respond(200, {});

      profileService.register(profile);
      $httpBackend.flush();

      $httpBackend.verifyNoOutstandingExpectation();
      $httpBackend.verifyNoOutstandingRequest();
    });
  });
});
