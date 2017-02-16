'use strict';

describe('loginController', function() {
    var loginController;
    var $scope;
    var $rootScope;
    var $controller;


    beforeEach(module('cgi-web-app', function($translateProvider) {
        $translateProvider.translations('en', {});
    }));

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $controller = $injector.get('$controller');
        $scope = $rootScope.$new();
    }));

    beforeEach(inject(function($controller) {
        loginController = $controller('loginController', {
            $scope : $scope
        });
    }));

    it('should not have error notifications', function() {
        expect($scope.model.errorNotif).toBe(false);
    });

    it('should have error notifications', function() {
        $scope.popUp('error', 'errorMessage', 0);
        expect($scope.model.errorNotif).toBe(true);
        expect($scope.model.errorMessage).toBe('errorMessage');
    });

    it('should have success notifications', function() {
        $scope.popUp('success', 'successMessage', 0);
        expect($scope.model.successNotif).toBe(true);
        expect($scope.model.successMessage).toBe('successMessage');
    });

  describe('authentication', function() {
    it('should set the authentication token into the storage', function() {

    });
  });
});
