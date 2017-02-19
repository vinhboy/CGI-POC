'use strict';

describe('ProfileController', function() {
  var profileController;
  var $scope;
  var profileService;
  var $state;
  var $q;
  var deferred;

  beforeEach(module('cgi-web-app'));

  beforeEach(inject(function(_$rootScope_, _$controller_, _ProfileService_, _$state_, _$q_) {
    $q = _$q_;
    $scope = _$rootScope_.$new();
    profileService = _ProfileService_;
    $state = _$state_;

    deferred = _$q_.defer();
    spyOn(profileService, 'register').and.returnValue(deferred.promise);

    profileController = _$controller_('ProfileController', {
      $scope: $scope,
      ProfileService: profileService,
      $state: $state
    });
  }));

  it('initializes a profile object', function() {
    expect($scope.profile.firstName).toBe('');
    expect($scope.profile.lastName).toBe('');
    expect($scope.profile.email).toBe('');
    expect($scope.profile.password).toBe('');
    expect($scope.profile.passwordConfirmation).toBe('');
    expect($scope.profile.phone).toBe('');
    expect($scope.profile.phoneNumber.areaCode).toBe('');
    expect($scope.profile.phoneNumber.centralOfficeCode).toBe('');
    expect($scope.profile.phoneNumber.lineNumber).toBe('');
    expect($scope.profile.zipCode).toBe('');
    expect($scope.profile.notificationType.length).toBe(0);
    expect($scope.profile.emailNotification).toBe(false);
    expect($scope.profile.pushNotification).toBe(false);
    expect($scope.profile.smsNotification).toBe(false);
    expect($scope.profile.allowNotificationsByLocation).toBe(false);
  });

  it('initializes the apiErrors', function() {
    expect($scope.apiErrors.length).toBe(0);
  });

  it('should have these pattern validations', function() {
    expect('12345').toMatch($scope.regexZip);
    expect('abcABC123').toMatch($scope.regexPassword);
    expect('123').toMatch($scope.regexPhoneAreaCode);
    expect('123').toMatch($scope.regexPhoneCentralOfficeCode);
    expect('1234').toMatch($scope.regexPhoneLineNumber);
  });

  describe('register', function() {
    it('should call the ProfileService.register', function() {
      $scope.registerProfile($scope.profile);
      deferred.resolve({ status: 200, data: {} });
      $scope.$apply();
      expect(profileService.register).toHaveBeenCalled();
    });

    it('should transform the notificationTypes', function() {
      $scope.profile.smsNotification = true;
      spyOn($scope, 'processNotificationTypes').and.callThrough();
      $scope.registerProfile($scope.profile);
      deferred.resolve({ status: 200, data: {} });
      $scope.$apply();
      expect($scope.processNotificationTypes).toHaveBeenCalled();
    });

    it('should construct the phoneNumber', function() {
      $scope.profile.phoneNumber.areaCode = '313';
      $scope.profile.phoneNumber.centralOfficeCode = '252';
      $scope.profile.phoneNumber.lineNumber = '7456';
      spyOn($scope, 'generatePhoneNumber').and.callThrough();
      $scope.registerProfile($scope.profile);
      deferred.resolve({ status: 200, data: {} });
      $scope.$apply();
      expect($scope.generatePhoneNumber).toHaveBeenCalled();
    });

    it('should redirect if successful', function() {
      spyOn($state, 'go');
      $scope.registerProfile($scope.profile);
      deferred.resolve({ status: 200, data: {} });
      $scope.$apply();
      expect($state.go).toHaveBeenCalledWith('landing');
    });

    it('should construct apiErrors if failed', function() {
      spyOn($scope, 'processApiErrors');
      $scope.registerProfile($scope.profile);
      var response = { status: 404, data: {} };
      deferred.reject(response);
      $scope.$apply();
      expect($scope.processApiErrors).toHaveBeenCalledWith(response);
    });
  });

  describe('someSelected', function() {
    it('should be false if all are unchecked', function() {
      $scope.profile.emailNotification = false;
      $scope.profile.pushNotification = false;
      $scope.profile.smsNotification = false;
      expect($scope.someSelected()).toBe(false);
    });

    it('should be true if all are checked', function() {
      $scope.profile.emailNotification = true;
      $scope.profile.pushNotification = true;
      $scope.profile.smsNotification = true;
      expect($scope.someSelected()).toBe(true);
    });

    it('should be true if some are checked', function() {
      $scope.profile.emailNotification = false;
      $scope.profile.pushNotification = false;
      $scope.profile.smsNotification = true;
      expect($scope.someSelected()).toBe(true);
    });
  });

  describe('processNotificationTypes', function() {
    it('notificationType should be empty if nothing is checked', function() {
      $scope.profile.emailNotification = false; // id: 1
      $scope.profile.pushNotification = false; // id: 3
      $scope.profile.smsNotification = false; // id: 2
      $scope.processNotificationTypes();
      expect($scope.profile.notificationType.length).toBe(0);
    });

    it('notificationType should include everything if everything is checked', function() {
      $scope.profile.emailNotification = true; // id: 1
      $scope.profile.pushNotification = true; // id: 3
      $scope.profile.smsNotification = true; // id: 2
      $scope.processNotificationTypes();
      expect($scope.profile.notificationType[0].notificationId).toBe(1);
      expect($scope.profile.notificationType[1].notificationId).toBe(2);
      expect($scope.profile.notificationType[2].notificationId).toBe(3);
    });

    it('notificationType should only include checked', function() {
      $scope.profile.emailNotification = true; // id: 1
      $scope.profile.pushNotification = false; // id: 3
      $scope.profile.smsNotification = true; // id: 2
      $scope.processNotificationTypes();
      expect($scope.profile.notificationType[0].notificationId).toBe(1);
      expect($scope.profile.notificationType[1].notificationId).toBe(2);
    });
  });

  describe('generatePhoneNumber', function() {
    it('generates the phone number', function() {
      $scope.profile = {
        phoneNumber: {
          areaCode: '313',
          centralOfficeCode: '252',
          lineNumber: '7456'
        }
      };
      $scope.generatePhoneNumber();
      expect($scope.profile.phone).toBe('3132527456');
    });
  });

  describe('processApiErrors', function() {
    it('should construct the apiErrors', function() {
      var response = {
        status: 404,
        data: {
          errors: [
            { code: 'ERR3', message: 'Stuff cannot be blank' },
            { code: 'ERR4', message: 'You should be blank' },
            { code: 'ERR3', message: 'Please do not be blank' }
          ]
        }
      };
      $scope.processApiErrors(response);
      expect($scope.apiErrors[0]).toBe('Stuff cannot be blank');
      expect($scope.apiErrors[1]).toBe('You should be blank');
      expect($scope.apiErrors[2]).toBe('Please do not be blank');
    });

    it('should ignore other pieces of data', function() {
      var response = {
        status: 404,
        data: {
          err: [
            { code: 'ERR3', message: 'Stuff cannot be blank' }
          ],
          errors: [
            { code: 'ERR4', error: 'You should be blank' },
            { code: 'ERR3', errorMessage: 'Please do not be blank' }
          ]
        },
        datas: {
          errors: [
            { code: 'ERR3', message: 'Stuff cannot be blank' },
            { code: 'ERR4', message: 'You should be blank' },
            { code: 'ERR3', message: 'Please do not be blank' }
          ]
        }
      };
      $scope.processApiErrors(response);
      expect($scope.apiErrors.length).toBe(0);
    });
  });
});
