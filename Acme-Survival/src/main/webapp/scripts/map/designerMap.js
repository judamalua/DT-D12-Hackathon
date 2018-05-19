var map;
function initMap() {
	document.getElementsByTagName("h1")[0].innerHTML = "";
	map = new google.maps.Map(document.getElementById('map'), {
		center : {
			lat : 0,
			lng : 0
		},
		zoom : 3,
		disableDefaultUI : true,
		styles : [
				{
					elementType : "geometry",
					stylers : [
						{
							color : "#ebe3cd"
						}
					]
				}, {
					elementType : "labels",
					stylers : [
						{
							visibility : "off"
						}
					]
				}, {
					elementType : "labels.text.fill",
					stylers : [
						{
							color : "#523735"
						}
					]
				}, {
					elementType : "labels.text.stroke",
					stylers : [
						{
							color : "#f5f1e6"
						}
					]
				}, {
					featureType : "administrative",
					elementType : "geometry.stroke",
					stylers : [
						{
							color : "#c9b2a6"
						}
					]
				}, {
					featureType : "administrative.country",
					elementType : "geometry.stroke",
					stylers : [
						{
							visibility : "off"
						}
					]
				}, {
					featureType : "administrative.land_parcel",
					stylers : [
						{
							visibility : "off"
						}
					]
				}, {
					featureType : "administrative.land_parcel",
					elementType : "geometry.stroke",
					stylers : [
						{
							color : "#dcd2be"
						}
					]
				}, {
					featureType : "administrative.land_parcel",
					elementType : "labels.text.fill",
					stylers : [
						{
							color : "#ae9e90"
						}
					]
				}, {
					featureType : "administrative.locality",
					elementType : "geometry.fill",
					stylers : [
						{
							visibility : "off"
						}
					]
				}, {
					featureType : "administrative.neighborhood",
					stylers : [
						{
							visibility : "off"
						}
					]
				}, {
					featureType : "administrative.province",
					elementType : "geometry.stroke",
					stylers : [
						{
							visibility : "off"
						}
					]
				}, {
					featureType : "landscape.natural",
					elementType : "geometry",
					stylers : [
						{
							color : "#dfd2ae"
						}
					]
				}, {
					featureType : "poi",
					elementType : "geometry",
					stylers : [
						{
							color : "#dfd2ae"
						}
					]
				}, {
					featureType : "poi",
					elementType : "labels.text.fill",
					stylers : [
						{
							color : "#93817c"
						}
					]
				}, {
					featureType : "poi.park",
					elementType : "geometry.fill",
					stylers : [
						{
							color : "#a5b076"
						}
					]
				}, {
					featureType : "poi.park",
					elementType : "labels.text.fill",
					stylers : [
						{
							color : "#447530"
						}
					]
				}, {
					featureType : "road",
					stylers : [
						{
							visibility : "off"
						}
					]
				}, {
					featureType : "road",
					elementType : "geometry",
					stylers : [
						{
							color : "#f5f1e6"
						}
					]
				}, {
					featureType : "road.arterial",
					elementType : "geometry",
					stylers : [
						{
							color : "#fdfcf8"
						}
					]
				}, {
					featureType : "road.highway",
					elementType : "geometry",
					stylers : [
						{
							color : "#f8c967"
						}
					]
				}, {
					featureType : "road.highway",
					elementType : "geometry.stroke",
					stylers : [
						{
							color : "#e9bc62"
						}
					]
				}, {
					featureType : "road.highway.controlled_access",
					elementType : "geometry",
					stylers : [
						{
							color : "#e98d58"
						}
					]
				}, {
					featureType : "road.highway.controlled_access",
					elementType : "geometry.stroke",
					stylers : [
						{
							color : "#db8555"
						}
					]
				}, {
					featureType : "road.local",
					elementType : "labels.text.fill",
					stylers : [
						{
							color : "#806b63"
						}
					]
				}, {
					featureType : "transit",
					elementType : "geometry.fill",
					stylers : [
						{
							visibility : "off"
						}
					]
				}, {
					featureType : "transit.line",
					elementType : "geometry",
					stylers : [
						{
							color : "#dfd2ae"
						}
					]
				}, {
					featureType : "transit.line",
					elementType : "labels.text.fill",
					stylers : [
						{
							color : "#8f7d77"
						}
					]
				}, {
					featureType : "transit.line",
					elementType : "labels.text.stroke",
					stylers : [
						{
							color : "#ebe3cd"
						}
					]
				}, {
					featureType : "transit.station",
					elementType : "geometry",
					stylers : [
						{
							color : "#dfd2ae"
						}
					]
				}, {
					featureType : "water",
					elementType : "geometry.fill",
					stylers : [
						{
							color : "#b9d3c2"
						}
					]
				}, {
					featureType : "water",
					elementType : "labels.text.fill",
					stylers : [
						{
							color : "#92998d"
						}
					]
				}
		]
	});
	infoWindow = new google.maps.InfoWindow;
	createElementsMain();
}

function generateMap() {
	var mapElements = JSON.parse(document.getElementById("mapElements").innerHTML);
	// LOCATION HANDLING -----------------------------------
	var zone = [];
	for ( var int = 0; int < mapElements.locations.length; int++) {
		zone[int] = new google.maps.Polygon({
			paths : [
					{
						lat : Number(mapElements.locations[int].point_a.split(",")[0]),
						lng : Number(mapElements.locations[int].point_a.split(",")[1])
					}, {
						lat : Number(mapElements.locations[int].point_b.split(",")[0]),
						lng : Number(mapElements.locations[int].point_b.split(",")[1])
					}, {
						lat : Number(mapElements.locations[int].point_c.split(",")[0]),
						lng : Number(mapElements.locations[int].point_c.split(",")[1])
					}, {
						lat : Number(mapElements.locations[int].point_d.split(",")[0]),
						lng : Number(mapElements.locations[int].point_d.split(",")[1])
					}
			],
			strokeColor : '#FF0000',
			strokeOpacity : 0.8,
			strokeWeight : 2,
			fillColor : '#FF0000',
			fillOpacity : 0.35,
			map : map
		});
		zone[int].addListener('click', function(event) {
			var mapElements = JSON.parse(document.getElementById("mapElements").innerHTML);
			var currentInt = -1;
			for ( var int2 = 0; int2 < mapElements.locations.length; int2++) {
				var m = {
					x : event.latLng.lat(),
					y : event.latLng.lng()
				};
				var r = {
					A : {
						x : Number(mapElements.locations[int2].point_a.split(",")[0]),
						y : Number(mapElements.locations[int2].point_a.split(",")[1])
					},
					B : {
						x : Number(mapElements.locations[int2].point_b.split(",")[0]),
						y : Number(mapElements.locations[int2].point_b.split(",")[1])
					},
					C : {
						x : Number(mapElements.locations[int2].point_c.split(",")[0]),
						y : Number(mapElements.locations[int2].point_c.split(",")[1])
					},
					D : {
						x : Number(mapElements.locations[int2].point_d.split(",")[0]),
						y : Number(mapElements.locations[int2].point_d.split(",")[1])
					}
				};
				if (pointInZone2(m, r)) {
					currentInt = int2;
				}
			}
			if (currentInt != -1) {
				var language = getLanguageToUse();
				var contentString = '<b>' + mapTranslations.location.location[language] + '</b><br/><b>' + mapTranslations.location.name[language] + ": </b>"
						+ mapElements.locations[currentInt].name[language];

				infoWindow.setContent(contentString);
				infoWindow.setPosition(event.latLng);

				infoWindow.open(map);
			}
		});
	}
	// LOCATION NOT FINAL HANDLING -----------------------------------
	var zoneNotFinal = [];
	for ( var int = 0; int < mapElements.locationsNotFinal.length; int++) {
		zoneNotFinal[int] = new google.maps.Polygon({
			paths : [
					{
						lat : Number(mapElements.locationsNotFinal[int].point_a.split(",")[0]),
						lng : Number(mapElements.locationsNotFinal[int].point_a.split(",")[1])
					}, {
						lat : Number(mapElements.locationsNotFinal[int].point_b.split(",")[0]),
						lng : Number(mapElements.locationsNotFinal[int].point_b.split(",")[1])
					}, {
						lat : Number(mapElements.locationsNotFinal[int].point_c.split(",")[0]),
						lng : Number(mapElements.locationsNotFinal[int].point_c.split(",")[1])
					}, {
						lat : Number(mapElements.locationsNotFinal[int].point_d.split(",")[0]),
						lng : Number(mapElements.locationsNotFinal[int].point_d.split(",")[1])
					}
			],
			strokeColor : '#0000FF',
			strokeOpacity : 0.8,
			strokeWeight : 2,
			fillColor : '#0000FF',
			fillOpacity : 0.35,
			map : map
		});
		zone[int].addListener('click', function(event) {
			var mapElements = JSON.parse(document.getElementById("mapElements").innerHTML);
			var currentInt = -1;
			for ( var int2 = 0; int2 < mapElements.locationsNotFinal.length; int2++) {
				var m = {
					x : event.latLng.lat(),
					y : event.latLng.lng()
				};
				var r = {
					A : {
						x : Number(mapElements.locationsNotFinal[int2].point_a.split(",")[0]),
						y : Number(mapElements.locationsNotFinal[int2].point_a.split(",")[1])
					},
					B : {
						x : Number(mapElements.locationsNotFinal[int2].point_b.split(",")[0]),
						y : Number(mapElements.locationsNotFinal[int2].point_b.split(",")[1])
					},
					C : {
						x : Number(mapElements.locationsNotFinal[int2].point_c.split(",")[0]),
						y : Number(mapElements.locationsNotFinal[int2].point_c.split(",")[1])
					},
					D : {
						x : Number(mapElements.locationsNotFinal[int2].point_d.split(",")[0]),
						y : Number(mapElements.locationsNotFinal[int2].point_d.split(",")[1])
					}
				};
				if (pointInZone2(m, r)) {
					currentInt = int2;
				}
			}
			if (currentInt != -1) {
				var language = getLanguageToUse();
				var contentString = '<b>' + mapTranslations.location.location[language] + '</b><br/><b>' + mapTranslations.location.name[language] + ": </b>"
						+ mapElements.locationsNotFinal[currentInt].name[language] + '<br/><br/><a href="' + getMainDomain() + 'gather/player/create.do?locationId='
						+ mapElements.locationsNotFinal[currentInt].id + '">' + mapTranslations.location.gatherMissionStartLink[language] + '</a><br/><a href="' + getMainDomain()
						+ 'move/player/create.do?locationId=' + mapElements.locationsNotFinal[currentInt].id + '">' + mapTranslations.location.moveStartLink[language] + '</a>';

				infoWindow.setContent(contentString);
				infoWindow.setPosition(event.latLng);

				infoWindow.open(map);
			}
		});
	}

}
function createElementsMain() {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			if (this.responseText != "null") {
				document.getElementById("mapElements").innerHTML = this.responseText;
				generateMap();
			}

		}
	};
	xhttp.open("GET", getMainDomain() + "location/designer/info.do", true);
	xhttp.send();

}
