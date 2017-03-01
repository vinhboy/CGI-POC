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
  ['$scope','$filter','$timeout','EventNotificationService' ,'uiGmapGoogleMapApi','$sessionStorage', 
  function ($scope,$filter,$timeout,EventNotificationService,uiGmapGoogleMapApi,$sessionStorage ) {
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
        $scope.model.displayedNotifications =[];
    
    $scope.eventTypeFilter=undefined;
    $scope.changeFilters = function(){
        $scope.model.filteredNotifications  =  $filter('filter')($scope.model.notifications, {type: $scope.eventTypeFilter}, true);
        $scope.model.displayedNotifications =[];
        $scope.loadMore();
        $scope.currentSelectedEvent = $scope.model.displayedNotifications[0];
        $scope.activeItem.item = 0;
        $scope.backToDefault($scope.model.displayedNotifications[0]);

    };
   $scope.moreToLoad = function() {
       var bMoreToLoad = true;
       if ($scope.model.displayedNotifications.length === $scope.model.filteredNotifications.length)
       { 
           bMoreToLoad = false;
       }   
       return bMoreToLoad;
    };
   $scope.loadMore = function() {
       if ($scope.model.filteredNotifications!== undefined && $scope.model.filteredNotifications.length > 0) {
          var last = $scope.model.displayedNotifications.length;
          var iteamsToAdd = 40;
          if ($scope.model.filteredNotifications.length - $scope.model.displayedNotifications.length < iteamsToAdd){
              iteamsToAdd = $scope.model.filteredNotifications.length - $scope.model.displayedNotifications.length;
          }
           for (var i = 0; i < iteamsToAdd; i++) {
            $scope.model.displayedNotifications.push($scope.model.filteredNotifications[last + i]);
          }
       }
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
            value.generationDate  = moment(value.generationDate);
            value.generationDateFromatted = value.generationDate.format('MM-DD-YYYY  hh:mm:ss a');
            
           if (value.geometry !== '' && value.geometry!==null && 
               value.geometry!== undefined){
                value.geometry = JSON.parse(value.geometry);
           }
        });
        $scope.changeFilters();
        
        
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


cgiWebApp.directive('infiniteScroll',   function ( ) {
        return {
            link:function (scope, element, attrs) {
                var offset = parseInt(attrs.threshold) || 0;
                var e = element[0];

                element.bind('scroll', function () {
                    if (scope.$eval(attrs.canLoad) && e.scrollTop + e.offsetHeight >= e.scrollHeight - offset) {
                        scope.$apply(attrs.infiniteScroll);
                    }
                });
            }
        };
    });