<%--
 * edit.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
    <style>
      /* Always set the map height explicitly to define the size of the div
       * element that contains the map. */
      #map {
        height: 100%;
      }
      /* Optional: Makes the sample page fill the window. */
      html, body {
        height: 100%;
        margin: 0;
        padding: 0;
      }
    </style>
    <div id="map"></div>
    <script>
		var map;
	    var casa;
		var currentMarker=0;
		var zone0, zone1;
		var enemigo0, enemigo1;
		var character0;
		var trayecto0;
		var infoWindow;
		function placeMarker(location) {
			switch(currentMarker){
				case 0:
					if ( marker0 ) {
						marker0.setPosition(location);
					} else {
						marker0 = new google.maps.Marker({
						position: location,
						map: map,
						icon: 'http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld=1|FE6256|000000'
						});
					}
					currentMarker=1;
					document.getElementById("location0").value=location.lat()+","+location.lng();
					break;
				case 1:
					if ( marker1 ) {
						marker1.setPosition(location);
					} else {
						marker1 = new google.maps.Marker({
						position: location,
						map: map,
						icon: 'http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld=2|FE6256|000000'
						});
					}
					currentMarker=2;
					document.getElementById("location1").value=location.lat()+","+location.lng();
					break;
				case 2:
					if ( marker2 ) {
						marker2.setPosition(location);
					} else {
						marker2 = new google.maps.Marker({
						position: location,
						map: map,
						icon: 'http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld=3|FE6256|000000'
						});
					}
					currentMarker=3;
					document.getElementById("location2").value=location.lat()+","+location.lng();
					break;
				case 3:
					if ( marker3 ) {
						marker3.setPosition(location);
					} else {
						marker3 = new google.maps.Marker({
						position: location,
						map: map,
						icon: 'http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld=4|FE6256|000000'
						});
					}
					currentMarker=0;
					document.getElementById("location3").value=location.lat()+","+location.lng();
					break;
				
			}
			if (marker0&& marker1 && marker2 && marker3) {
				var zoneCoords = [
					marker0.getPosition().toJSON(),
					marker1.getPosition().toJSON(),
					marker2.getPosition().toJSON(),
					marker3.getPosition().toJSON()
				];
				if(zone){
					zone.setPaths(zoneCoords);
				}else{
				zone=new google.maps.Polygon({
					paths: zoneCoords,
					strokeColor: '#FF0000',
					strokeOpacity: 0.8,
					strokeWeight: 2,
					fillColor: '#FF0000',
					fillOpacity: 0.35
				});
				zone.setMap(map);
				zone.addListener('click', showArrays);
				}
			}
			
		}
	function showArrays(event) {
        var vertices = this.getPath();

        var contentString = '<b>'+document.getElementById("name").value+'</b><br><br>';
        for (var i =0; i < vertices.getLength(); i++) {
          var xy = vertices.getAt(i);
          contentString += '<br>' + 'Coordinate ' + i + ':<br>' + xy.lat() + ',' +
              xy.lng();
        }
        // Replace the info window's content and position.
        infoWindow.setContent(contentString);
        infoWindow.setPosition(event.latLng);

        infoWindow.open(map);
    }
	function initMap() {
        map = new google.maps.Map(document.getElementById('map'), {
          center: {lat: 0, lng: 0},
          zoom: 0,
		  disableDefaultUI: true,
		  styles : [
			{elementType: "geometry", stylers: [{color: "#ebe3cd"}]},
			{elementType: "labels", stylers: [{visibility: "off"}]},
			{elementType: "labels.text.fill", stylers: [{color: "#523735"}]},
			{elementType: "labels.text.stroke", stylers: [{color: "#f5f1e6"}]},
			{featureType: "administrative",
				elementType: "geometry.stroke",
				stylers: [{color: "#c9b2a6"}]},
			{featureType: "administrative.country",
				elementType: "geometry.stroke",
				stylers: [{visibility: "off"}]},
			{featureType: "administrative.land_parcel",
				stylers: [{visibility: "off"}]},
			{featureType: "administrative.land_parcel",
				elementType: "geometry.stroke",
				stylers: [{color: "#dcd2be"}]},
			{featureType: "administrative.land_parcel",
				elementType: "labels.text.fill",
				stylers: [{color: "#ae9e90"}]},
			{featureType: "administrative.locality",
				elementType: "geometry.fill",
				stylers: [{visibility: "off"}]},
			{featureType: "administrative.neighborhood",
				stylers: [{visibility: "off"}]},
			{featureType: "administrative.province",
				elementType: "geometry.stroke",
				stylers: [{visibility: "off"}]},
			{featureType: "landscape.natural",
				elementType: "geometry",
				stylers: [{color: "#dfd2ae"}]},
			{featureType: "poi",
				elementType: "geometry",
				stylers: [{color: "#dfd2ae"}]},
			{featureType: "poi",
				elementType: "labels.text.fill",
				stylers: [{color: "#93817c"}]},
			{featureType: "poi.park",
				elementType: "geometry.fill",
				stylers: [{color: "#a5b076"}]},
			{featureType: "poi.park",
				elementType: "labels.text.fill",
				stylers: [{color: "#447530"}]},
			{featureType: "road",
				stylers: [{visibility: "off"}]},
			{featureType: "road",
				elementType: "geometry",
				stylers: [{color: "#f5f1e6"}]},
			{featureType: "road.arterial",
				elementType: "geometry",
				stylers: [{color: "#fdfcf8"}]},
			{featureType: "road.highway",
				elementType: "geometry",
				stylers: [{color: "#f8c967"}]},
			{featureType: "road.highway",
				elementType: "geometry.stroke",
				stylers: [{color: "#e9bc62"}]},
			{featureType: "road.highway.controlled_access",
				elementType: "geometry",
				stylers: [{color: "#e98d58"}]},
			{featureType: "road.highway.controlled_access",
				elementType: "geometry.stroke",
				stylers: [{color: "#db8555"}]},
			{featureType: "road.local",
				elementType: "labels.text.fill",
				stylers: [{color: "#806b63"}]},
			{featureType: "transit",
				elementType: "geometry.fill",
				stylers: [{visibility: "off"}]},
			{featureType: "transit.line",
				elementType: "geometry",
				stylers: [{color: "#dfd2ae"}]},
			{featureType: "transit.line",
				elementType: "labels.text.fill",
				stylers: [{color: "#8f7d77"}]},
			{featureType: "transit.line",
				elementType: "labels.text.stroke",
				stylers: [{color: "#ebe3cd"}]},
			{featureType: "transit.station",
				elementType: "geometry",
				stylers: [{color: "#dfd2ae"}]},
			{featureType: "water",
				elementType: "geometry.fill",
				stylers: [{color: "#b9d3c2"}]},
			{featureType: "water",
				elementType: "labels.text.fill",
				stylers: [{color: "#92998d"}]}
			]
        });
		
      }

    </script>
    <script async defer
    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBe0wmulZvK1IM3-3jIUgbxt2Ax_QOVW6c&callback=initMap">
    </script>