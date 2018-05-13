var map;
function initMap() {
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
	debugger;
	var mapElements = JSON.parse(document.getElementById("mapElements").innerHTML);
	var imageRefuge = {
		url : 'data:image/svg+xml;base64,PHN2ZyB2ZXJzaW9uPSIxLjIiIGJhc2VQcm9maWxlPSJ0aW55IiB4bWxucz0iaHR0cDovL3d3dy53%0D%0AMy5vcmcvMjAwMC9zdmciIHdpZHRoPSI1MCIgaGVpZ2h0PSI1MCIgdmlld0JveD0iMCAwIDUwIDUw%0D%0AIiBvdmVyZmxvdz0iaW5oZXJpdCI+PHBhdGggZD0iTTI1IDZjLTEuMjYzIDAtMS44MzQuNjE2LTIu%0D%0AMjEgMS4yODZsLTE1LjcxIDI5LjU3MmgtMy4zOTVjLTEuNDgyIDAtMi42ODUgMS4xNDUtMi42ODUg%0D%0AMi41NzEgMCAxLjQyMyAxLjIwMyAyLjU3IDIuNjg1IDIuNTdoNDIuNjMyYzEuNDgxIDAgMi42ODUt%0D%0AMS4xNDcgMi42ODUtMi41NyAwLTEuNDI3LTEuMjAzLTIuNTcxLTIuNjg1LTIuNTcxaC0zLjM5N2wt%0D%0AMTUuNzA5LTI5LjU3M2MtLjM3Ny0uNjY5LS45NDctMS4yODUtMi4yMTEtMS4yODV6bTAgMTIuODU3%0D%0AbDguODQyIDE4LjAwMWgtMTcuNjg0bDguODQyLTE4LjAwMXoiLz48L3N2Zz4=',
		size : new google.maps.Size(64, 64),
		origin : new google.maps.Point(0, 0),
		anchor : new google.maps.Point(26, 40)
	};
	console.log(mapElements.refuge.gpsCoordinates.split(",")[0]);
	refuge = new google.maps.Marker({
		position : {
			lat : Number(mapElements.refuge.gpsCoordinates.split(",")[0]),
			lng : Number(mapElements.refuge.gpsCoordinates.split(",")[1])
		},
		map : map,
		icon : imageRefuge
	});
	refuge.addListener('click', function(event) {
		var contentString;
		var usedLanguage = false;
		for ( var int4 = 0; int4 < mapElements.languages.length; int4++) {
			if (mapElements.languages[int4] === getCookie("language")) {
				contentString = '<b>' + mapTranslations.refuge.refugeMine[mapElements.languages[int4]] + '</b><br/><b>' + mapTranslations.refuge.name[mapElements.languages[int4]]
						+ ": " + mapElements.refuge.name + '</b><br><br><a href="' + getMainDomain() + 'refuge/player/display.do">'
						+ mapTranslations.refuge.enterInRefuge[mapElements.languages[int4]] + '</a>';
				usedLanguage = true;
				break;
			}
		}
		if (usedLanguage == false) {
			contentString = '<b>' + mapTranslations.refuge.refugeMine["en"] + '</b><br/><b>' + mapTranslations.refuge.name["en"] + ": " + mapElements.refuge.name
					+ '</b><br><br><a href="' + getMainDomain() + 'refuge/player/display.do">' + mapTranslations.refuge.enterInRefuge["en"] + '</a>';
		}
		// Replace the info window's content and position.
		infoWindow.setContent(contentString);
		infoWindow.setPosition(event.latLng);

		infoWindow.open(map);
	});
	for ( var int = 0; int < mapElements.locations.length; int++) {
		var zone = new google.maps.Polygon({
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
		zone.addListener('click', function(event) {
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
				if (pointInZone(m, r)) {
					currentInt = int2;
				}
			}
			if (currentInt != -1) {
				var contentString;
				var usedLanguage = false;
				for ( var int3 = 0; int3 < mapElements.languages.length; int3++) {
					if (mapElements.languages[int3] === getCookie("language")) {
						contentString = '<b>' + mapTranslations.location.name[mapElements.languages[int3]] + ": </b>"
								+ mapElements.locations[currentInt].name[mapElements.languages[int3]] + '<br><a href="' + getMainDomain()
								+ 'recolection/player/start.do?locationId=' + mapElements.locations[currentInt].id + '">'
								+ mapTranslations.location.recolectionMissionStartLink[mapElements.languages[int3]] + '</a>';
						usedLanguage = true;
						break;
					}
				}
				if (usedLanguage == false) {
					contentString = '<b>' + mapTranslations.location.name["en"] + ": " + mapElements.locations[currentInt].name["en"] + '</b><br><br><a href="' + getMainDomain()
							+ 'recolection/player/start.do?locationId=' + mapElements.locations[currentInt].id + '">' + mapTranslations.location.recolectionMissionStartLink["en"]
							+ '</a>';
				}
				// Replace the info window's content and position.
				infoWindow.setContent(contentString);
				infoWindow.setPosition(event.latLng);

				infoWindow.open(map);
			}
		});
	}
}

function pointInZone(m, r) {
	var AB = vector(r.A, r.B);
	var AM = vector(r.A, m);
	var BC = vector(r.B, r.C);
	var BM = vector(r.B, m);
	var dotABAM = dot(AB, AM);
	var dotABAB = dot(AB, AB);
	var dotBCBM = dot(BC, BM);
	var dotBCBC = dot(BC, BC);
	return 0 <= dotABAM && dotABAM <= dotABAB && 0 <= dotBCBM && dotBCBM <= dotBCBC;
}

function vector(p1, p2) {
	return {
		x : (p2.x - p1.x),
		y : (p2.y - p1.y)
	};
}

function dot(u, v) {
	return u.x * v.x + u.y * v.y;
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
	xhttp.open("GET", getMainDomain() + "map/player/info.do", true);
	xhttp.send();

}

var mapTranslations = {
	location : {
		name : {
			es : "Nombre de zona",
			en : "Location name"
		},
		recolectionMissionStartLink : {
			es : "Iniciar misión de recolección en esta zona",
			en : "Start recolection mission in this location"
		}
	},
	refuge : {
		refugeMine : {
			es : "Mi refugio",
			en : "My refuge"
		},
		refugeEnemy : {
			es : "Refugio enemigo",
			en : "Enemy refuge"
		},
		name : {
			es : "Nombre del refugio",
			en : "Refuge name"
		},
		enterInRefuge : {
			es : "Entrar en el refugio",
			en : "Enter in the refuge"
		},
		attackRefuge : {
			es : "Atacar este refugio",
			en : "Attack this refuge"
		}
	}
};
