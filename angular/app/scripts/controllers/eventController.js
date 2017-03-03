'use strict';

cgiWebApp.controller('eventController',
  ['$scope', '$sessionStorage', '$state', 'EventNotificationService',
  function ($scope, $sessionStorage, $state, EventNotificationService) {

  $scope.init = function() {
    $scope.apiErrors = [];

    $scope.notification = {
      messageMaxLength: 135,
      notificationType: 'ADMIN_E',
      zipCodes: '',
      zipCodesSplit: [],
      message: '',
      submitting: false,
      defaultSubmitButtonText: 'SEND'
    };

    $scope.notification.submitButtonText = $scope.notification.defaultSubmitButtonText;

    $scope.regexZipCodes = /^\d{5}(?:\s*,\s*\d{5})*$/;
  };

  $scope.generateZipCodes = function() {
    $scope.notification.zipCodesSplit = $scope.notification.zipCodes.match(/\d{5}/g);
  };

  $scope.processApiErrors = function(response) {
    $scope.apiErrors = [];
    if (response.data && response.data.errors) {
      for (var i = 0; i < response.data.errors.length; i++) {
        if (response.data.errors[i].message) {
          $scope.apiErrors.push(response.data.errors[i].message);
        }
      }
    } else if (response.status === 401 && response.data) {
      $scope.apiErrors.push(response.data);
    } else {
      $scope.apiErrors.push('Server error occurred. Please try again later.');
    }
  };

  $scope.publishEvent = function(form) {
    if (!form.$valid) {
      return;
    }
    $scope.setAsSubmitting();
    $scope.generateZipCodes();

    var toPost = {
      type: $scope.notification.notificationType,
      zipCodes: $scope.notification.zipCodesSplit,
      description: $scope.notification.message
    };

    //putting this on scope so I can test
    $scope.toSend = toPost;

    EventNotificationService.publish(toPost).then(function() {
      $state.go('landing');
    }).catch(function(response) {
      $scope.processApiErrors(response);
    }).finally(function() {
      $scope.setAsSubmitted();
    });
  };

  $scope.setAsSubmitting = function() {
    $scope.notification.submitting = true;
    $scope.notification.submitButtonText = 'SUBMITTING...';
  };

  $scope.setAsSubmitted = function() {
    $scope.notification.submitting = false;
    $scope.notification.submitButtonText = $scope.notification.defaultSubmitButtonText;
  };

  $scope.init();
}]);
