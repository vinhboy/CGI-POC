'use strict';

describe('ProfileController', function() {
  var profileController;
  var $scope;
  var profileService;
  var authenticationService;
  var $state;
  var $sessionStorage;
  var $q;
  var deferred;
  var authDeferred;

  beforeEach(module('cgi-web-app'));

  beforeEach(inject(function(_$rootScope_, _$controller_, _ProfileService_, _Authenticator_, _$state_, _$sessionStorage_, _$q_) {
    $q = _$q_;
    $scope = _$rootScope_.$new();
    profileService = _ProfileService_;
    authenticationService = _Authenticator_;
    $state = _$state_;
    $sessionStorage = _$sessionStorage_;

    deferred = _$q_.defer();
    authDeferred = _$q_.defer();

    spyOn(profileService, 'register').and.returnValue(deferred.promise);
    spyOn(profileService, 'getProfile').and.returnValue(deferred.promise);
    spyOn(profileService, 'update').and.returnValue(deferred.promise);
    spyOn(authenticationService, 'authenticate').and.returnValue(authDeferred.promise);

    profileController = _$controller_('ProfileController', {
      $scope: $scope,
      ProfileService: profileService,
      $state: $state,
      $sessionStorage: _$sessionStorage_,
      Authenticator: authenticationService
    });
  }));

  describe('init', function() {
    it('initializes a profile object', function() {
      expect($scope.profile.firstName).toBe('');
      expect($scope.profile.lastName).toBe('');
      expect($scope.profile.email).toBe('');
      expect($scope.profile.password).toBe('');
      expect($scope.profile.passwordConfirmation).toBe('');
      expect($scope.profile.phone).toBe('');
      expect($scope.profile.address1).toBe('');
      expect($scope.profile.address2).toBe('');
      expect($scope.profile.city).toBe('');
      expect($scope.profile.state).toBe('');
      expect($scope.profile.zipCode).toBe('');
      expect($scope.profile.notificationType.length).toBe(0);
      expect($scope.profile.emailNotification).toBe(false);
      expect($scope.profile.pushNotification).toBe(false);
      expect($scope.profile.smsNotification).toBe(false);
    });

    it('initializes the apiErrors', function() {
      expect($scope.apiErrors.length).toBe(0);
    });

    it('should have these pattern validations', function() {
      expect('12345').toMatch($scope.regexZip);
      expect('abcABC123').toMatch($scope.regexPassword);
      expect('1234567890').toMatch($scope.regexPhone);
      expect('123-456-7890').toMatch($scope.regexPhone);
    });

    it('should get the current profile from the API if managing profile', function() {
      $state.current.name = 'manageProfile';
      var retrievedProfile = { notificationType: [] };
      $scope.init();
      deferred.resolve({ data: retrievedProfile });
      $scope.$apply();
      expect(profileService.getProfile).toHaveBeenCalled();
      expect($scope.profile).toBe(retrievedProfile);
    });

    it('should parse the current profile notification types if managing profile', function() {
      $state.current.name = 'manageProfile';
      var retrievedProfile = {
        notificationType: [
          { notificationId: 1 },
          { notificationId: 2 },
          { notificationId: 3 }
        ]
      };
      $scope.init();
      deferred.resolve({ data: retrievedProfile });
      $scope.$apply();
      expect($scope.profile.emailNotification).toBe(true);
      expect($scope.profile.pushNotification).toBe(true);
      expect($scope.profile.smsNotification).toBe(true);
    });
  });

  describe('updateProfile', function() {
    beforeEach(function () {
      $state.current.name = 'manageProfile';
    });

    it('should call the ProfileService.update', function() {
      $scope.updateProfile();
      deferred.resolve({ status: 200, data: {} });
      $scope.$apply();
      expect(profileService.update).toHaveBeenCalledWith($scope.toSend);
    });

    it('should transform the notificationTypes', function() {
      $scope.profile.smsNotification = true;
      spyOn($scope, 'processNotificationTypes').and.callThrough();
      $scope.updateProfile();
      deferred.resolve({ status: 200, data: {} });
      $scope.$apply();
      expect($scope.processNotificationTypes).toHaveBeenCalled();
    });

    it('should construct the phoneNumber', function() {
      $scope.profile.phone = '313-252-7456';
      spyOn($scope, 'generatePhoneNumber').and.callThrough();
      $scope.updateProfile();
      deferred.resolve({ status: 200, data: {} });
      $scope.$apply();
      expect($scope.generatePhoneNumber).toHaveBeenCalled();
    });

    it('should redirect if successful', function() {
      spyOn($state, 'go');
      $scope.updateProfile();
      deferred.resolve({ status: 200, data: {} });
      $scope.$apply();
      expect($state.go).toHaveBeenCalledWith('landing');
    });

    it('should construct apiErrors if failed', function() {
      spyOn($scope, 'processApiErrors');
      $scope.updateProfile();
      var response = { status: 404, data: {} };
      deferred.reject(response);
      $scope.$apply();
      expect($scope.processApiErrors).toHaveBeenCalledWith(response);
    });

    it('should use undefined for password value if it is not populated', function() {
      $scope.profile.password = '';
      $scope.updateProfile();
      deferred.resolve({ status: 200, data: {} });
      $scope.$apply();
      expect($scope.toSend.password).toBeUndefined();
    });

    it('should use provided value for password value if it is populated', function() {
      $scope.profile.password = 'abcABC123';
      $scope.updateProfile();
      deferred.resolve({ status: 200, data: {} });
      $scope.$apply();
      expect($scope.toSend.password).toBe('abcABC123');
    });
  });

  describe('registerProfile', function() {
    beforeEach(function () {
      $state.current.name = 'register';
    });

    it('should call the ProfileService.register', function() {
      $scope.registerProfile();
      deferred.resolve({ status: 200, data: {} });
      $scope.$apply();
      expect(profileService.register).toHaveBeenCalledWith($scope.toSend);
    });

    it('should transform the notificationTypes', function() {
      $scope.profile.smsNotification = true;
      spyOn($scope, 'processNotificationTypes').and.callThrough();
      $scope.registerProfile();
      deferred.resolve({ status: 200, data: {} });
      $scope.$apply();
      expect($scope.processNotificationTypes).toHaveBeenCalled();
    });

    it('should construct the phoneNumber', function() {
      $scope.profile.phone = '313-252-7456';
      spyOn($scope, 'generatePhoneNumber').and.callThrough();
      $scope.registerProfile();
      deferred.resolve({ status: 200, data: {} });
      $scope.$apply();
      expect($scope.generatePhoneNumber).toHaveBeenCalled();
    });

    it('should redirect if successful', function() {
      spyOn($state, 'go');
      $scope.registerProfile();
      deferred.resolve({ status: 200, data: {} });
      $scope.$apply();
      expect($state.go).toHaveBeenCalledWith('landing');
    });

    it('should construct apiErrors if failed', function() {
      spyOn($scope, 'processApiErrors');
      $scope.registerProfile();
      var response = { status: 404, data: {} };
      deferred.reject(response);
      $scope.$apply();
      expect($scope.processApiErrors).toHaveBeenCalledWith(response);
    });

    it('should get the auth token', function() {
      spyOn($sessionStorage, 'put');
      $scope.registerProfile();
      deferred.resolve({ status: 200, data: {} });
      authDeferred.resolve({ status: 200, data: { authToken: 'the auth token' } });
      $scope.$apply();
      expect(authenticationService.authenticate).toHaveBeenCalled();
      expect($sessionStorage.put).toHaveBeenCalledWith('jwt', 'the auth token');
    });

    it('should process these optional fields for null', function() {
      spyOn($scope, 'processForNull');
      $scope.registerProfile();
      deferred.resolve({ status: 200, data: {} });
      $scope.$apply();
      expect($scope.processForNull).toHaveBeenCalledWith($scope.toSend, 'firstName');
      expect($scope.processForNull).toHaveBeenCalledWith($scope.toSend, 'lastName');
      expect($scope.processForNull).toHaveBeenCalledWith($scope.toSend, 'phone');
      expect($scope.processForNull).toHaveBeenCalledWith($scope.toSend, 'address1');
      expect($scope.processForNull).toHaveBeenCalledWith($scope.toSend, 'address2');
      expect($scope.processForNull).toHaveBeenCalledWith($scope.toSend, 'city');
      expect($scope.processForNull).toHaveBeenCalledWith($scope.toSend, 'state');
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
      $scope.profile.phone = '313-252-7456';
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

    it('should construct the apiErrors for unauthorized', function() {
      var response = {
        status: 401,
        statusText: 'Unauthorized',
        data: 'Credentials are required to access this resource.'
      };
      $scope.processApiErrors(response);
      expect($scope.apiErrors[0]).toBe('Credentials are required to access this resource.');
    });

    it('should default to generic error message', function() {
      var response = {
        status: -1,
        statusText: '',
        data: null
      };
      $scope.processApiErrors(response);
      expect($scope.apiErrors[0]).toBe('Server error occurred. Please try again later.');
    });
  });

  describe('isNew', function() {
    it('should be considered new', function() {
      $state.current.name = 'register';
      expect($scope.isNew()).toBe(true);
    });
  });

  describe('isEdit', function() {
    it('should be considered edit', function() {
      $state.current.name = 'manageProfile';
      expect($scope.isEdit()).toBe(true);
    });
  });

  describe('isPasswordValid', function() {
    it('should be invalid if new and empty', function() {
      $state.current.name = 'register';
      $scope.profile.password = '';
      expect($scope.isPasswordValid()).toBe(false);
    });

    it('should be invalid if new, populated, and does not meet pattern', function() {
      $state.current.name = 'register';
      $scope.profile.password = 'a';
      expect($scope.isPasswordValid()).toBe(false);
    });

    it('should be invalid if edit, populated and does not meet pattern', function() {
      $state.current.name = 'manageProfile';
      $scope.profile.password = 'a';
      expect($scope.isPasswordValid()).toBe(false);
    });

    it('should be valid if edit and empty', function() {
      $state.current.name = 'manageProfile';
      $scope.profile.password = '';
      expect($scope.isPasswordValid()).toBe(true);
    });

    it('should be valid if new, populated and meets pattern', function() {
      $state.current.name = 'register';
      $scope.profile.password = 'abcABC123';
      expect($scope.isPasswordValid()).toBe(true);
    });
  });

  describe('processForNull', function() {
    it('should null out any falsey value', function() {
      var obj = { something: '' };
      $scope.processForNull(obj, 'something');
      expect(obj.something).toBeNull();
    });

    it('should NOT null out any populated string', function() {
      var obj = { something: 'not empty' };
      $scope.processForNull(obj, 'something');
      expect(obj.something).not.toBeNull();
    });
  });
});
