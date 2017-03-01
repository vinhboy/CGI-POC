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
  ['$http', 'urls', '$sessionStorage',
  function($http, urls, $sessionStorage) {

  this.authenticate = function(credentials) {
    var endpoint = urls.BASE + '/login';
    return $http.post(endpoint, credentials).then(function(response) {
      $sessionStorage.put('jwt', response.data.authToken);
      $sessionStorage.put('role', response.data.role);

      $http.defaults.headers.common['Content-Type'] = 'application/json';
      $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.authToken;
    });
  };

  this.logout = function() {
    $sessionStorage.remove('role');
    $sessionStorage.remove('jwt');
    $http.defaults.headers.common.Authorization = 'Bearer';
  };

  this.isLoggedIn = function(){
    return $sessionStorage.get('jwt') !== null ;
  };

  this.isAdminUser = function(){
    return $sessionStorage.get('role') === 'ADMIN';
  };
}]);
