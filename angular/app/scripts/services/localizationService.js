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
    var endpoint = urls.BASE + '/localization';
    return $http.post(endpoint, user);
  };
}]);