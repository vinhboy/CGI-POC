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

  beforeEach(function() {
    spyOn($sessionStorage, 'get').and.returnValue('magical auth token here');
  });

  describe('register', function() {
    it('should post to the expected registration endpoint', function() {
      var profile = { email: 'user@example.com', password: 'pw', name: 'jonny depp' };
      $httpBackend.expectPOST('http://localhost:8080/user', profile)
        .respond(200, {});

      profileService.register(profile);
      $httpBackend.flush();

      $httpBackend.verifyNoOutstandingExpectation();
      $httpBackend.verifyNoOutstandingRequest();
    });

    it('should construct the endpoint URL', function() {
      var profile = { email: 'user@example.com', password: 'pw', name: 'jonny depp' };
      $httpBackend.expectPOST(urls.BASE + '/user', profile)
        .respond(200, {});

      profileService.register(profile);
      $httpBackend.flush();

      $httpBackend.verifyNoOutstandingExpectation();
      $httpBackend.verifyNoOutstandingRequest();
    });
  });

  describe('update', function() {
    it('should post to the expected registration endpoint', function() {
      var profile = { email: 'user@example.com', password: 'pw', name: 'jonny depp' };
      $httpBackend.expectPUT('http://localhost:8080/user', profile)
        .respond(200, {});

      profileService.update(profile);
      $httpBackend.flush();

      $httpBackend.verifyNoOutstandingExpectation();
      $httpBackend.verifyNoOutstandingRequest();
    });

    it('should construct the endpoint URL', function() {
      var profile = { email: 'user@example.com', password: 'pw', name: 'jonny depp' };
      $httpBackend.expectPUT(urls.BASE + '/user', profile)
        .respond(200, {});

      profileService.update(profile);
      $httpBackend.flush();

      $httpBackend.verifyNoOutstandingExpectation();
      $httpBackend.verifyNoOutstandingRequest();
    });

    it('should send the JWT auth token during udpate', function() {
      var profile = { email: 'user@example.com', password: 'pw', name: 'jonny depp' };
      $httpBackend.expectPUT(urls.BASE + '/user', profile, {
        'Authorization': 'Bearer magical auth token here',
        'Accept': 'application/json, text/plain, */*',
        'Content-Type': 'application/json;charset=utf-8'
      }).respond(200, {});

      profileService.update(profile);
      $httpBackend.flush();

      expect($sessionStorage.get).toHaveBeenCalledWith('jwt');
      $httpBackend.verifyNoOutstandingExpectation();
      $httpBackend.verifyNoOutstandingRequest();
    });
  });

  describe('getProfile', function() {
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
