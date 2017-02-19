'use strict';

/**
 * @ngdoc overview
 * @name pocsacApp
 * @description
 * # pocsacApp
 *
 * profile service.
 */
cgiWebApp.service('EventNotificationService',
  ['$http', 'urls',
  function($http, urls) {

  this.notifications = function() {
    var endpoint = urls.BASE + '/notifications';
    return $http.get(endpoint);
  };
}]);
