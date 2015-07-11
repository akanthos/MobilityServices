/**
 * Created by Thanasis on 28/6/2015.
 */

function geolocate() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function(position) {
            var geolocation = new google.maps.LatLng(
                position.coords.latitude, position.coords.longitude);
            var circle = new google.maps.Circle({
                center: geolocation,
                radius: position.coords.accuracy
            });
            autocomplete.setBounds(circle.getBounds());
        });
    }
}
// [END region_geolocation]

function addMarker( map, latitude, longitude, label ){
    // Create the marker - this will automatically place it
    // on the existing Google map (that we pass-in).
    var marker = new google.maps.Marker({
        map: map,
        position: new google.maps.LatLng(
            latitude,
            longitude
        ),
        title: (label || "")
    });


    // Return the new marker reference.
    return( marker );
}


// I update the marker's position and label.
function updateMarker( marker, latitude, longitude, label ){
    // Update the position.
    marker.setPosition(
        new google.maps.LatLng(
            latitude,
            longitude
        )
    );

    // Update the title if it was provided.
    if (label){

        marker.setTitle( label );

    }
}

function pinpointMeNoMap(document, startLabel) {
    alert('in');
    // Check to see if this browser supports geolocation.
    if (navigator.geolocation) {

        navigator.geolocation.getCurrentPosition(
            function( position ){
                var latlng = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
                var geocoder;

                geocoder = new google.maps.Geocoder();

                geocoder.geocode({'latLng': latlng}, function(results, status) {
                    if (status == google.maps.GeocoderStatus.OK) {
                        if (results[0]) {
                            document.getElementById(startLabel).value = results[0].formatted_address;
                        } else {
                            alert('No results found');
                        }
                    } else {
                        alert('Geocoder failed due to: ' + status);
                    }
                });

            },
            function( error ){
                console.log( "Something went wrong: ", error );
            },
            {
                timeout: (5 * 1000),
                maximumAge: (1000 * 60 * 15),
                enableHighAccuracy: true
            }
        );

    }

}

function pinpointMe(document, mapContainer, theLabel) {

    // Get the map container node.
    //var mapContainer = $( "#mapContainer" );
    var map = new google.maps.Map(
        mapContainer[ 0 ],
        {
            zoom: 15,
            center: new google.maps.LatLng(
                40.700683,
                -73.925972
            ),
            mapTypeId: google.maps.MapTypeId.ROADMAP
        }
    );

    var bounds = new google.maps.LatLngBounds();

    // Check to see if this browser supports geolocation.
    if (navigator.geolocation) {

        // This is the location marker that we will be using
        // on the map. Let's store a reference to it here so
        // that it can be updated in several places.
        var locationMarker = null;


        // Get the location of the user's browser using the
        // native geolocation service. When we invoke this method
        // only the first callback is requied. The second
        // callback - the error handler - and the third
        // argument - our configuration options - are optional.
        navigator.geolocation.getCurrentPosition(
            function( position ){
                var latlng = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
                var geocoder;

                geocoder = new google.maps.Geocoder();

                geocoder.geocode({'latLng': latlng}, function(results, status) {
                    if (status == google.maps.GeocoderStatus.OK) {
                        if (results[0]) {
                            document.getElementById(theLabel).value = results[0].formatted_address;
                        } else {
                            alert('No results found');
                        }
                    } else {
                        alert('Geocoder failed due to: ' + status);
                    }
                });


                // Check to see if there is already a location.
                // There is a bug in FireFox where this gets
                // invoked more than once with a cahced result.
                if (locationMarker){
                    return;
                }

                // Log that this is the initial position.
                console.log( "Initial Position Found" );

                // Add a marker to the map using the position.
                locationMarker = addMarker(
                    map,
                    position.coords.latitude,
                    position.coords.longitude,
                    "Initial Position"
                );
                map.setCenter(locationMarker.getPosition());
                bounds.extend(locationMarker);
                map.setCenter(locationMarker);
                map.fitBounds(bounds);
                map.setZoom(15);
            },
            function( error ){
                console.log( "Something went wrong: ", error );
            },
            {
                timeout: (5 * 1000),
                maximumAge: (1000 * 60 * 15),
                enableHighAccuracy: true
            }
        );


        // Now tha twe have asked for the position of the user,
        // let's watch the position to see if it updates. This
        // can happen if the user physically moves, of if more
        // accurate location information has been found (ex.
        // GPS vs. IP address).
        //
        // NOTE: This acts much like the native setInterval(),
        // invoking the given callback a number of times to
        // monitor the position. As such, it returns a "timer ID"
        // that can be used to later stop the monitoring.
        var positionTimer = navigator.geolocation.watchPosition(
            function( position ){

                // Log that a newer, perhaps more accurate
                // position has been found.
                console.log( "Newer Position Found" );

                // Set the new position of the existing marker.
                updateMarker(
                    locationMarker,
                    position.coords.latitude,
                    position.coords.longitude,
                    "Updated / Accurate Position"
                );
                if ((!map.getBounds().contains(locationMarker.getPosition()))) { //Note the double &
                    map.setCenter(locationMarker.getPosition());
                    //OR map.panTo(marker.getPosition());
                    bounds.extend(locationMarker);
                    map.setCenter(locationMarker);
                    map.fitBounds(bounds);
                    map.setZoom(15);
                }

            }
        );

        // If the position hasn't updated within 5 minutes, stop
        // monitoring the position for changes.
        setTimeout(
            function(){
                // Clear the position watcher.
                navigator.geolocation.clearWatch( positionTimer );
            },
            (1000 * 60 * 5)
        );

    }

}

function showRouteOnMap(document, mapContainer, /*map, */startLat1, startLong1, endLat1, endLong1, startLat2, startLong2, endLat2, endLong2) {

//                var divs = document.getElementsByClassName("hidable");
//                var i;
//                for (i = 0; i < divs.length; i++) {
//                        divs[i].style.visibility = 'hidden';
//                        divs[i].display = 'none';
//                }
//                mapContainer.style.visibility = 'visible';
//                mapContainer.display = 'block';

    var map = new google.maps.Map(
        mapContainer[ 0 ],
        {
            zoom: 15,
            center: new google.maps.LatLng(
                40.700683,
                -73.925972
            ),
            mapTypeId: google.maps.MapTypeId.ROADMAP
        }
    );
    var start1 = new google.maps.LatLng(startLat1, startLong1);
    var end1 = new google.maps.LatLng(endLat1, endLong1);
    var start2 = new google.maps.LatLng(startLat2, startLong2);
    var end2 = new google.maps.LatLng(endLat2, endLong2);

    var directionsDisplay1 = new google.maps.DirectionsRenderer({
        polylineOptions: {
            strokeColor: getRandomColor()
        }
    });// also, constructor can get "DirectionsRendererOptions" object
    directionsDisplay1.setMap(map); // map should be already initialized.\
    var directionsDisplay2 = new google.maps.DirectionsRenderer({
        polylineOptions: {
            strokeColor: getRandomColor()
        }
    });// also, constructor can get "DirectionsRendererOptions" object
    directionsDisplay2.setMap(map); // map should be already initialized.

    var request1 = {
        origin : start1,
        destination : end1,
        travelMode : google.maps.TravelMode.DRIVING
    };
    var request2 = {
        origin : start2,
        destination : end2,
        travelMode : google.maps.TravelMode.DRIVING
    };
    var directionsService = new google.maps.DirectionsService();
    directionsService.route(request1, function(response, status) {
        if (status == google.maps.DirectionsStatus.OK) {
            directionsDisplay1.setDirections(response);
        }
    });
    directionsService.route(request2, function(response, status) {
        if (status == google.maps.DirectionsStatus.OK) {
            directionsDisplay2.setDirections(response);
        }
    });
    mapContainer.scrollIntoView();
}

function showSingleRouteOnMap(document, mapContainer, /*map, */startLat, startLong, endLat, endLong) {

//                var divs = document.getElementsByClassName("hidable");
//                var i;
//                for (i = 0; i < divs.length; i++) {
//                        divs[i].style.visibility = 'hidden';
//                        divs[i].display = 'none';
//                }
//                mapContainer.style.visibility = 'visible';
//                mapContainer.display = 'block';

    var map = new google.maps.Map(
        mapContainer[ 0 ],
        {
            zoom: 15,
            center: new google.maps.LatLng(
                40.700683,
                -73.925972
            ),
            mapTypeId: google.maps.MapTypeId.ROADMAP
        }
    );
    var start = new google.maps.LatLng(startLat, startLong);
    var end = new google.maps.LatLng(endLat, endLong);

    var directionsDisplay = new google.maps.DirectionsRenderer({
        polylineOptions: {
            strokeColor: getRandomColor()
        }
    });// also, constructor can get "DirectionsRendererOptions" object
    directionsDisplay.setMap(map); // map should be already initialized.\


    var request = {
        origin : start,
        destination : end,
        travelMode : google.maps.TravelMode.DRIVING
    };

    var directionsService = new google.maps.DirectionsService();
    directionsService.route(request, function(response, status) {
        if (status == google.maps.DirectionsStatus.OK) {
            directionsDisplay.setDirections(response);
        }
    });

    mapContainer.scrollIntoView();
}

function getRandomColor() {
    var letters = '0123456789ABCDEF'.split('');
    var color = '#';
    for (var i = 0; i < 6; i++ ) {
        color += letters[Math.floor(Math.random() * 16)];
    }
    return color;
}