'use strict';

var cgiWebApp = angular.module('cgi-web-app', [ 'pascalprecht.translate','ngSessionStorage', 'ui.router', 'ngWebSocket', 'ngMessages' ]);

cgiWebApp.constant('urls', {
  // have to be change depending of the environment
  BASE: 'http://localhost:8080',
  WS_BASE: 'ws://localhost:8080'
}).config([ '$translateProvider', '$urlRouterProvider', '$stateProvider', function($translateProvider, $urlRouterProvider, $stateProvider) {

  $translateProvider.useStaticFilesLoader({
    prefix : 'language/locale-', // path to translations files
    suffix : '.json' // suffix, currently- extension of the translations
  });
  $translateProvider.preferredLanguage('en');

  //if no url is specified redirect to login
  $urlRouterProvider.otherwise('login');

  $stateProvider.state('login', {
    url : '/login',
    views:{
      'pageContent':{
        templateUrl: '/views/login.html',
        controller: 'loginController'
  }
    }
  }).state('register', {
    url: '/register',
    views:{
      'pageContent':{
        templateUrl: '/views/profile.html',
        controller: 'ProfileController'
    }
    }
  }).state('landing', {
       url : '/landing',
    views:{
      'pageContent':{
       templateUrl: 'views/landing.html',
       controller: 'landingController'
    }
    }
  }).state('manageProfile', {
    url: '/manageProfile',
    views: {
      'pageContent': {
        templateUrl: '/views/profile.html',
        controller: 'ProfileController'
      }
    }
  });
}]);

//get geo localization
//TODO verify if the user is sign in and if he accept the geoloclization
navigator.geolocation.getCurrentPosition(function(position) {
    
}
