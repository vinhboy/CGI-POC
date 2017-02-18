'use strict';

describe('Service: Localization', function() {

  var window,
    service;

  beforeEach(module('cgi-web-app'));

  beforeEach(inject(function(geolocationSvc, $window, $rootScope) {
    scope = $rootScope.$new();
    service = geolocationSvc;
    $window.navigator = {
      userAgent: 'Mozilla/5.0 (NodeJS; Karma 0.0.0) (Cucumber/0.0 like virtualisation) Fantasy/0.0',
      appVersion: '5.0 (NodeJS; Karma 0.0.0) (Cucumber/0.0 like virtualisation) Fantasy/0.0',
      platform: 'nodeKarma'
    };

    scope.$digest();

    window = $window;
  }));

  describe('current position', function() {
    it('should connect to socket api', function() {
      expect(service.getCurrentPosition(), "");
    });
  });
});
