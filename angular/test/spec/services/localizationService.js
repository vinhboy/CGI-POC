'use strict';

describe('Localizator', function() {
  var authenticationService;
  var $httpBackend;
  var urls;
  var LocalizeService;

  beforeEach(module('cgi-web-app'));

  beforeEach(inject(function(_Localizator_, _urls_, _$httpBackend_) {
    LocalizeService = _Localizator_;
    urls = _urls_;
    $httpBackend = _$httpBackend_;
  }));

  describe('localizer', function() {
    it('should post to the expected localize endpoint', function() {
      var user = { username: 'user', password: 'pw' , geoloclatitude : 53.00, geoloclongitude : -121.00};
      $httpBackend.expectPOST('http://localhost:8080/user/localizer', credentials)
        .respond(200, {});

      LocalizeService.localize(user);
      $httpBackend.flush();

      $httpBackend.verifyNoOutstandingExpectation();
      $httpBackend.verifyNoOutstandingRequest();
    });

    it('should construct the endpoint URL', function() {
      var user = { email: 'user@example.com', password: 'pw', name: 'jonny depp'  , geoloclatitude : 53.00, geoloclongitude : -121.00};
      $httpBackend.expectPOST(urls.BASE + '/user/localizer', credentials)
        .respond(200, {});

      LocalizeService.localize(user);
      $httpBackend.flush();

      $httpBackend.verifyNoOutstandingExpectation();
      $httpBackend.verifyNoOutstandingRequest();
    });
  });
});
