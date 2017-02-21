'use strict';

/**
 * @ngdoc overview
 * @name pocsacApp
 * @description
 * # pocsacApp
 *
 * geo localization setter service.
 */
cgiWebApp.service('Localizator',
  ['$http', 'urls',
  function($http, urls) {

  this.localize = function(user) {
    var authToken = $sessionStorage.get('jwt');
    var endpoint = urls.BASE + '/user/geoLocation';
    return $http.put(endpoint, user, {
      headers: { 'Authorization': 'Bearer ' + authToken }
    });
  };
}]);