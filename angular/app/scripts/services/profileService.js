'use strict';

/**
 * @ngdoc overview
 * @name pocsacApp
 * @description
 * # pocsacApp
 *
 * profile service.
 */
cgiWebApp.service('ProfileService',
  ['$http', 'urls', '$sessionStorage',
  function($http, urls, $sessionStorage) {

  this.register = function(profile) {
    var endpoint = urls.BASE + '/user';
    return $http.post(endpoint, profile);
  };

  this.update = function(profile) {
    var authToken = $sessionStorage.get('jwt');
    var endpoint = urls.BASE + '/profile/update';
    return $http.put(endpoint, profile, {
      headers: { 'Authorization': 'Bearer ' + authToken }
    });
  };

  this.getProfile = function() {
    var authToken = $sessionStorage.get('jwt');
    var endpoint = urls.BASE + '/getProfile';
    return $http.get(endpoint, {
      headers: { 'Authorization': 'Bearer ' + authToken }
    });
  };
}]);
