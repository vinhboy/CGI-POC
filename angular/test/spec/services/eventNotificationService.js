'use strict';

describe('EventSerivce', function() {
  var eventNotificationService;
  var $httpBackend;
  var urls;

  beforeEach(module('cgi-web-app'));

  beforeEach(inject(function(_EventNotificationService_, _urls_, _$httpBackend_) {
    eventNotificationService = _EventNotificationService_;
    urls = _urls_;
    $httpBackend = _$httpBackend_;
  }));
  describe('userNotifications', function() {
    it('should post to the expected publish endpoint', function() {
      $httpBackend.expectGET('http://localhost:8080/notification/user')
        .respond(200, {});

      eventNotificationService.userNotifications();
      $httpBackend.flush();

      $httpBackend.verifyNoOutstandingExpectation();
      $httpBackend.verifyNoOutstandingRequest();
    });

    it('should construct the endpoint URL', function() {
       $httpBackend.expectGET(urls.BASE + '/notification/user' )
        .respond(200, {});

      eventNotificationService.userNotifications();
      $httpBackend.flush();

      $httpBackend.verifyNoOutstandingExpectation();
      $httpBackend.verifyNoOutstandingRequest();
    });
  });
  describe('allNotifications', function() {
    it('should post to the expected publish endpoint', function() {
      $httpBackend.expectGET('http://localhost:8080/notification/admin')
        .respond(200, {});

      eventNotificationService.allNotifications();
      $httpBackend.flush();

      $httpBackend.verifyNoOutstandingExpectation();
      $httpBackend.verifyNoOutstandingRequest();
    });

    it('should construct the endpoint URL', function() {
       $httpBackend.expectGET(urls.BASE + '/notification/admin' )
        .respond(200, {});

      eventNotificationService.allNotifications();
      $httpBackend.flush();

      $httpBackend.verifyNoOutstandingExpectation();
      $httpBackend.verifyNoOutstandingRequest();
    });
  });


  describe('publish', function() {
    it('should post to the expected publish endpoint', function() {
      var notification = {};
      $httpBackend.expectPOST('http://localhost:8080/notification', notification)
        .respond(200, {});

      eventNotificationService.publish(notification);
      $httpBackend.flush();

      $httpBackend.verifyNoOutstandingExpectation();
      $httpBackend.verifyNoOutstandingRequest();
    });

    it('should construct the endpoint URL', function() {
      var notification = {};
      $httpBackend.expectPOST(urls.BASE + '/notification', notification)
        .respond(200, {});

      eventNotificationService.publish(notification);
      $httpBackend.flush();

      $httpBackend.verifyNoOutstandingExpectation();
      $httpBackend.verifyNoOutstandingRequest();
    });
  });
});
