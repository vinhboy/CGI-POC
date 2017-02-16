/**
 * @ngdoc overview
 * @name pocsacApp
 * @description # pocsacApp
 *
 * Login Controller.
 */

'use strict';

cgiWebApp.controller('loginController',
  ['$scope', 'Authenticator', '$sessionStorage',
  function ($scope, Authenticator, $sessionStorage) {

  $scope.user = {
    username: '',
    password: ''
  };

  $scope.model = {
    errorNotif: false,
    successNotif: false,
    errorMessage: 'GENERIC.MESSAGE.ERROR.SERVER',
    successMessage: 'GENERIC.MESSAGE.SUCCESS'
  };

  $scope.popUp = function(code, message, duration) {
    if (code === 'error') {
      $scope.model.errorNotif = true;
      $scope.model.errorMessage = message;
    } else if (code === 'success') {
      $scope.model.successNotif = true;
      $scope.model.successMessage = message;
    }
  };

  $scope.submitForm = function(isValid) {
    if (isValid) {
      var dataObject = {
        email: $scope.user.username,
        password: $scope.user.password
      };

      //call to the authenticate service
      Authenticator.authenticate(dataObject).then(function(response) {
      if (response.status === 200) {
        $scope.model.errorNotif = false;

        //                                          $scope.$parent.USER = data.user;
        //                                          $scope.$parent.template.url = '';
        $scope.model.successNotif = true;
        $scope.model.successMessage = 'LOGIN.MESSAGE.LOGGEDIN';
        //                                            $scope.$parent.navigate('INDEX');
        $sessionStorage.put('jwt', response.data.authToken);

      } else if (response.status === 401) {
        $scope.popUp('error', 'LOGIN.MESSAGE.UNVALID', POP_UP_DURATION); // jshint ignore:line
      } else {
        $scope.popUp('error', 'GENERIC.MESSAGE.ERROR.SERVER', POP_UP_DURATION); // jshint ignore:line
      }

        $scope.authForm.$setPristine();
        $scope.authForm.$setUntouched();

      });
      // Making the fields empty
      clearFields();
    }
  };

  var clearFields = function() {
    $scope.user.username = '';
    $scope.user.password = '';
  }
}]);
