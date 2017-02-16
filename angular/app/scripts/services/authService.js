'use strict';

/**
 * @ngdoc overview
 * @name pocsacApp
 * @description
 * # pocsacApp
 *
 * authentication service.
 */
cgiWebApp.service('Authenticator',
  ['$http', 'urls',
  function($http, urls) {

  this.authenticate = function(credentials) {
    var endpoint = urls.BASE + '/login';
    return $http.post(endpoint, credentials);
  };
}]);
