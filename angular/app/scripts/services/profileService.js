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
  ['$http', 'urls',
  function($http, urls) {

  this.register = function(profile) {
    var endpoint = urls.BASE + '/register';
    return $http.post(endpoint, profile);
  };
}]);
