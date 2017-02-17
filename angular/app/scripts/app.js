'use strict';

var cgiWebApp = angular.module('cgi-web-app', [ 'pascalprecht.translate','ngSessionStorage', 'ui.router', 'ngWebSocket', 'ngMessages' ]);

var HOST = 'localhost';

cgiWebApp.constant('urls', {
  // have to be change depending of the environment
  BASE: 'http://' + HOST + ':8080',
  HOSTNAME: HOST
}).config([ '$translateProvider', '$urlRouterProvider', '$stateProvider', function($translateProvider, $urlRouterProvider, $stateProvider) {

  $translateProvider.useStaticFilesLoader({
    prefix : 'language/locale-', // path to translations files
    suffix : '.json' // suffix, currently- extension of the translations
  });
  $translateProvider.preferredLanguage('en');

  //if no url is specified redirect to login
  $urlRouterProvider.otherwise('login');

  $stateProvider.state('login', {
    url: '/login',
    views: {
      'pageContent': {
        templateUrl: '/views/login.html',
        controller: 'loginController'
      }
    }
  }).state('register', {
    url: '/register',
    views: {
      'pageContent': {
        templateUrl: '/views/register.html',
        controller: 'ProfileController'
      }
    }
  }).state('landing', {
    url: '/landing',
    views: {
      'header': {
        templateUrl: '/views/userHeader.html',
        controller: 'LandingController'
      },
      'pageContent': {
        templateUrl: '/views/landing.html',
        controller: 'LandingController'
      },
      'footer': {
        templateUrl: '/views/userFooter.html',
        controller: 'LandingController'
      }
    }
  }).state('profile', {
    url: '/profile',
    views: {
      'header': {
        templateUrl: '/views/userHeader.html',
        controller: 'profileController'
      },
      'pageContent': {
        templateUrl: '/views/userProfile.html',
        controller: 'profileController'
      },
      'footer': {
        templateUrl: '/views/userFooter.html'
      }
    }
  }).state('restricted', {
    url: '/restricted',
    views: {
      'pageContent': {
        templateUrl: '/views/restricted.html',
        controller: 'loginController'
      }
    }
  });
}]);
