/**
 * @ngdoc overview
 * @name pocsacApp
 * @description # pocsacApp
 *
 * Landing Controller.
 * Will be responsible for loading data for notifications and displaying a map component
 * This component will be re-used for admin functionality and end user
 * Header will have different options for an end user
 * Admin will be able to filter data and publish notifications
 */

'use strict';

cgiWebApp.controller('landingController',
  ['$scope','$filter','$timeout','EventNotificationService' ,'uiGmapGoogleMapApi','$sessionStorage','ProfileService',
  function ($scope,$filter,$timeout,EventNotificationService,uiGmapGoogleMapApi,$sessionStorage, ProfileService ) {
  $scope.apiErrors = [];
  $scope.map = undefined;
  $scope.googleMaps = undefined;
  $scope.showMapOrDetails='MAP';
  $scope.activeItem = {item: -1};
  $scope.isMobile = false;
  $scope.role = $sessionStorage.get('role');
  if (/Mobi/.test(navigator.userAgent)) {
     $scope.isMobile=true;

  }
    $scope.eventIcons = {};
    $scope.eventIcons.ADMIN_E ='/images/admin-emergency.svg';
    $scope.eventIcons.ADMIN_I = '/images/admin-non-emergency.svg';
    $scope.eventIcons.Weather = '/images/weather.svg';
    $scope.eventIcons.Flood = '/images/flood.svg';
    $scope.eventIcons.Fire = '/images/fire.svg';


  $scope.currentSelectedEvent=null;
    $scope.eventTypes = [
        { name: 'All', id: undefined},
        { name: 'Emergency', id: 'ADMIN_E'},
        { name: 'Non-Emergency', id: 'ADMIN_I'},
        { name: 'Weather', id: 'Weather'},
        { name: 'Flood', id: 'Flood'},
        { name: 'Fire', id: 'Fire'}];
    $scope.eventTimeFrames = [
        { name: '30 Days', id: 30},
        { name: '60 Days', id: 60},
        { name: '90 Days', id: 90},
        { name: '180 Days', id: 180},
        { name: '1 year', id: 365}];
    $scope.model = {
      notifications: []
    };
    $scope.eventTypeFilter=undefined;
    $scope.changeFilters = function(){
        $scope.model.filteredNotifications = angular.copy( $scope.model.notifications);
        $scope.model.filteredNotifications  =  $filter('filter')($scope.model.filteredNotifications, {type: $scope.eventTypeFilter}, true);
        $scope.currentSelectedEvent = $scope.model.filteredNotifications[0];
        $scope.activeItem.item = 0;
        $scope.backToDefault($scope.model.filteredNotifications[0]);

    };
   uiGmapGoogleMapApi.then(function(maps) {
          $scope.maps =maps;

   });
   $scope.backToDefault = function(currentSelectedEvent) {
       if ($scope.isMobile){
            $scope.showMapOrDetails='LIST';
       }else {
            $scope.showMapOrDetails='MAP';
            $scope.loadMap(currentSelectedEvent);

       }

   };


   $scope.processApiErrors = function(response) {
    $scope.apiErrors = [];
    if (response.data && response.data.errors) {
      for (var i = 0; i < response.data.errors.length; i++) {
        if (response.data.errors[i].message) {
          $scope.apiErrors.push(response.data.errors[i].message);
        }
      }
    }
   };
    $scope.convertApiData = function(data){
        $scope.model.notifications = data ;
        // need to convert date string into a proper date.
        angular.forEach($scope.model.notifications,function(value){
           value.generationDate = Date.parse(value.generationDate);
           if (value.geometry !== '' && value.geometry!==null &&
               value.geometry!== undefined){
                value.geometry = JSON.parse(value.geometry);
           }
        });
        $scope.changeFilters();


    };

    $scope.handlePushNotifications = function(){
      //setup firebase messaging
      // Retrieve Firebase Messaging object.


     const messaging = firebase.messaging();

      messaging.requestPermission()
     .then(function() {
       //granted permission
       // Retrieve a Instance ID token for use with FCM.
       // Get Instance ID token. Initially this makes a network call, once retrieved
        // subsequent calls to getToken will return from cache.
        messaging.getToken()
        .then(function(currentToken) {
          if (currentToken) {

            console.log('PUSH token, ',currentToken);
             ProfileService.updateFcmtoken({fcmtoken: currentToken}).then(function() {
               //NOOP

             }).catch( function(err){
               //error handling
               console.error('Unable to set web push, will try again later',err);

             });

          } else {
            // Show permission request.
            console.log('No Instance ID token available. Request permission to generate one.');
            // Show permission UI.

          }
        })
        .catch(function(err) {
          console.log('An error occurred while retrieving token. ', err);

        } );
     })
     .catch(function(err) {
       console.log('Unable to get permission to notify. ', err);
     });

   };

    $scope.initLoad = function(){
        if ($scope.role === 'ADMIN'){
             EventNotificationService.allNotifications().then(function(response) {
                 $scope.convertApiData(response.data);
             }).catch(function(response) {
                        $scope.processApiErrors(response);

             });
        } else {
             EventNotificationService.userNotifications().then(function(response) {
                 $scope.convertApiData(response.data);
             }).catch(function(response) {
                    // omce implemented...this changes to report an error
                        $scope.processApiErrors(response);

             });


             try{
               $scope.handlePushNotifications();
             }catch(err){
               //this should not prevent the user from doing anything else
             }


        }


    };
    $scope.eventSelected = function(selectedEvent, index){
          $scope.activeItem.item = index;
          if (!$scope.isMobile){
               $scope.loadMap(selectedEvent);

          }
    };

    $scope.loadEventDetails = function(selectedEvent, event,index){
          $scope.activeItem.item = index;
       $scope.currentSelectedEvent = selectedEvent;
       $scope.showMapOrDetails='DETAILS';
       if(event){
         event.stopPropagation();
         event.preventDefault();
       }
    };

    $scope.mapLoadASimplePoint = function (xValue, yValue) {
                    // just a map point
                    var myLatLng = {lat: yValue, lng: xValue};
                    $scope.map.setCenter({lat: myLatLng.lat, lng: myLatLng.lng});
                    $scope.mapMarker = new $scope.maps.Marker({
                        position: myLatLng,
                        map: $scope.map
                    });
    };
    $scope.mapLoadFromRings = function (listOfRings) {
        var coordPoints = [];
        angular.forEach(listOfRings, function (ring) {
            angular.forEach(ring, function (ringEntry) {
                var singlePoint = {};
                singlePoint.lat = ringEntry[1];
                singlePoint.lng = ringEntry[0];
                coordPoints.push(singlePoint);
            });
        });
        /// take the first one in the list so we set the area of the map
        $scope.map.setCenter({lat: coordPoints[0].lat, lng: coordPoints[0].lng});

        // Construct the polygon.
        var ring = new $scope.maps.Polygon({
            paths: coordPoints,
            strokeColor: '#FF0000',
            strokeOpacity: 0.8,
            strokeWeight: 2,
            fillColor: '#FF0000',
            fillOpacity: 0.35
        });
        ring.setMap($scope.map);
    };
    $scope.mapLoadFromForZipCodes = function (listOfZips) {
                     var geocoder = new $scope.maps.Geocoder();
                    var markers = [];
                    var bounds = new $scope.maps.LatLngBounds();
                    angular.forEach(listOfZips, function (zipCode) {
                        geocoder.geocode({'address': zipCode.zipCode}, function (results, status) {
                            if (status === 'OK') {
                                $scope.map.setCenter(results[0].geometry.location);
                                var marker = new $scope.maps.Marker({
                                    map: $scope.map,
                                    position: results[0].geometry.location
                                });
                                bounds.extend(results[0].geometry.location);
                                markers.push(marker);
                            }
                        });
                    });
                    $scope.map.fitBounds(bounds);
    };


    $scope.loadMap = function (selectedEvent) {
        $scope.currentSelectedEvent = selectedEvent;
        $scope.showMapOrDetails = 'MAP';
        // the 2 variables above hide/show the map.
        // because of the way google mas renders, we need to make sure
        //that the map is set up AFTER the UI render. So we
        // have this timeout whic forces the render to complete first.
        $timeout(function () {
            var ele = angular.element(document.querySelector('#map'));
            var mapIngfo = {center: {lat: -34.397, lng: 150.644}, zoom: 8};
            $scope.map = new $scope.maps.Map(ele[0], mapIngfo);
            if ($scope.currentSelectedEvent !== undefined) {
                 if (selectedEvent.eventNotificationZipcodes.length > 0) {
                    $scope.mapLoadFromForZipCodes(selectedEvent.eventNotificationZipcodes);
                } else if (selectedEvent.geometry.y !== undefined && selectedEvent.geometry.x !== undefined &&
                        selectedEvent.geometry.y !== '' && selectedEvent.geometry.x !== '') {
                    $scope.mapLoadASimplePoint(selectedEvent.geometry.x ,selectedEvent.geometry.y  );
                } else if ($scope.currentSelectedEvent.geometry.rings !== undefined) {
                     $scope.mapLoadFromRings($scope.currentSelectedEvent.geometry.rings);
                }
            }
        }, 0, false);
    };
    $scope.initLoad();
}]);
