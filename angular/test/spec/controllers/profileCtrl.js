'use strict';

describe('ProfileController', function() {
  var profileController;
  var $scope;
  var profileService;
  var $q;
  var deferred;

  beforeEach(module('cgi-web-app'));

  beforeEach(inject(function(_$rootScope_, _$controller_, _ProfileService_, _$q_) {
    $q = _$q_;
    $scope = _$rootScope_.$new();
    profileService = _ProfileService_;

    deferred = _$q_.defer();
    spyOn(profileService, 'register').and.returnValue(deferred.promise);

    profileController = _$controller_('ProfileController', {
      $scope: $scope,
      ProfileService: profileService
    });
  }));

  describe('register', function() {
    it('should call the ProfileService.register', function() {
      $scope.profile = {};
      $scope.registerProfile($scope.profile);
      deferred.resolve({ status: 200, data: {} });
      $scope.$apply();
      expect(profileService.register).toHaveBeenCalledWith($scope.profile);
    });
  });
});
