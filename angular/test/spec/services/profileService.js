'use strict';

describe('ProfileService', function() {
  var profileService;
  var $httpBackend;
  var urls;
  var $sessionStorage;

  beforeEach(module('cgi-web-app'));

  beforeEach(inject(function(_ProfileService_, _urls_, _$sessionStorage_, _$httpBackend_) {
    profileService = _ProfileService_;
    urls = _urls_;
    $httpBackend = _$httpBackend_;
    $sessionStorage = _$sessionStorage_;
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

  describe('getProfile', function() {

    beforeEach(function() {
      spyOn($sessionStorage, 'get').and.returnValue('magical auth token here');
    });

    it('should get to the expected endpoint', function() {
      $httpBackend.expectGET('http://localhost:8080/getProfile')
        .respond(200, {});

      profileService.getProfile();
      $httpBackend.flush();

      $httpBackend.verifyNoOutstandingExpectation();
      $httpBackend.verifyNoOutstandingRequest();
    });

    it('should construct the endpoint URL', function() {
      $httpBackend.expectGET(urls.BASE + '/getProfile')
        .respond(200, {});

      profileService.getProfile();
      $httpBackend.flush();

      $httpBackend.verifyNoOutstandingExpectation();
      $httpBackend.verifyNoOutstandingRequest();
    });

    it('should send the JWT auth token during the GET', function() {
      $httpBackend.expectGET(urls.BASE + '/getProfile', {
        'Authorization': 'Bearer magical auth token here',
        'Accept': 'application/json, text/plain, */*'
      }).respond(200, {});

      profileService.getProfile();
      $httpBackend.flush();

      expect($sessionStorage.get).toHaveBeenCalledWith('jwt');
      $httpBackend.verifyNoOutstandingExpectation();
      $httpBackend.verifyNoOutstandingRequest();
    });
  });
});
