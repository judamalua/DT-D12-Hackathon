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
	// IMAGE HANDLING ------------------------------------
	var imageRefuge = {
		url : 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAMAAABEpIrGAAAABGdBTUEAALGPC/xhBQAAAAFzUkdCAK7OHOkAAAAgY0hSTQAAeiYAAICEAAD6AAAAgOgAAHUwAADqYAAAOpgAABdwnLpRPAAAAPBQTFRFAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA////ZvvSvwAAAE50Uk5TAAEJDGF6BAI2qvqnGX/kDwsGCghSx/zovsCKDlGwkS6g8/ZlFnljFHPev7qWyeNCDaP9HMtBFcpKV4gH/sgTmF6XLyAD+1nwu/hYR1off06j1gAAAAFiS0dET25mQUkAAADjSURBVDjL3ZPHEoIwFEUBOzYsYC8g9t6wgF1QUd//f446iXUUNo4Lzyrv3jOTl0UI4peQFGnYW6w2u0HtcLpot4Hg8foAXH4mgG8Lhp7qUJjlACASjcWZy5xIptLMw3KZLA8YIUdRDjHPA1+49cVSGe5EKtVanT4fGk3ct9odeEu9iwSx974HoY8EafBBoIcmAjcyEWAsmwhCnnwVFAweJ9MXoTObW8/MZ/jhwmK5Wj8KqobW0tRrsNkWDQWAgWgmSP8isIqK4HY6EvQdhyOFlQh5f9Aw+hEJR/2aHPbyN7/mB05qA5w56QRThQAAACV0RVh0ZGF0ZTpjcmVhdGUAMjAxOC0wNS0xM1QyMDozMzo1NSswMjowMHC0eyMAAAAldEVYdGRhdGU6bW9kaWZ5ADIwMTgtMDUtMTNUMjA6MzM6NTUrMDI6MDAB6cOfAAAAAElFTkSuQmCC',
		size : new google.maps.Size(32, 32),
		origin : new google.maps.Point(0, 0),
		anchor : new google.maps.Point(16, 16)
	};
	var imageCharacter = {
		url : 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAQAAADZc7J/AAAABGdBTUEAAYagMeiWXwAAAAJiS0dEAP+Hj8y/AAAB6ElEQVRIx73Uy0uUURjH8c+MOmaI4tgFFeliGkW2CGrTWloULYLathGiRbuKFi0K/4LQXasEW1TUNoLCVdEFKUIiNNJKMfNW2cxINW+L3iYv70wzs+h3Fu/hcJ7vc31PhcKK2WSfdovSylBMq35pKddtLwdQ4aK0QCBwQSzqSvwfEWxREe6TqksHZD2wCGa9lIkOspACY6bUeKfP7WhALK9xTNxGB301KWVKqpTixa3X6bIXZkwa0FqKcUKTQ/pNWJIVCCzplSjevNt9qdD0zxpzvFhA0qzAD288NLcMMaizFMBzXU4aWxHFHXsKlHwV4AbO5mbwk0eyAoNO26W2WEBvzvcT+12TFpjx2E1dKxsWpawGLbn9W0+dc95rDQ7o0rb8auUq08C8hC9aNIcnGa8w7aq7jjqsNhzuPIpp0KjaEe/DBBZdyk1BQp366J9qtU75nqvBuBOqimvi38R6BAIjMuH32JpkC6rJgMBP3a6EzbxnRz5fUdpgGyY9c8s3Z9RKSkYD4nkAWzFs3oI+gwgEpQA2a8KwBaRMFMo2CrAuHJVhnws4KQCo04E5H4qpdxSgXgdGfSwXUKcdI+UDmjVi1HR5gBqdyBiXxe/XmVi+x2TtcaXddsoYCttXZa82s4bMFxPR/9cvP1KtOorzWGYAAAAldEVYdGRhdGU6Y3JlYXRlADIwMTgtMDUtMTNUMjA6Mzc6NDArMDI6MDDnzfRgAAAAJXRFWHRkYXRlOm1vZGlmeQAyMDE4LTA1LTEzVDIwOjM3OjQwKzAyOjAwlpBM3AAAAABJRU5ErkJggg==',
		size : new google.maps.Size(32, 32),
		origin : new google.maps.Point(0, 0),
		anchor : new google.maps.Point(16, 16)
	};
	var imageEnemyRefuge = {
		url : 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAMAAABEpIrGAAAABGdBTUEAALGPC/xhBQAAAAFzUkdCAK7OHOkAAAAgY0hSTQAAeiYAAICEAAD6AAAAgOgAAHUwAADqYAAAOpgAABdwnLpRPAAAAPNQTFRFAAAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA/wAA////Thsz2AAAAE90Uk5TAAABCQxhegQCNqr6pxl/5A8LBgoIUsf86L7Aig5RsJEuoPP2ZRZ5YxRz3r+6lsnjQg2j/RzLQRXKSleIB/7IE5hely8gA/tZ8Lv4WEdaH4OcN4cAAAABYktHRFDjbky8AAAA+ElEQVQ4y93TXVeCQBAGYAY0P9AUNTDLUhC1TEutsAQ/UkFFnf//b0R2KS4E7rzovdqdfc7OmbNnGeaCAZZjIeQYYvGrRDCAZCrNZwIBQPY6h5jOCwUAt1uxBD4MpRtRQsTybeVOcOpwX314FH4BsLW6jDRKg+OSalNGueVdAe2nZ/xLufPS7fHO4vWNiv5giGfTeydA/Th/jsonAdooAPBfEUD6jgA41iEcKE33bfzAoKHbyfQ0qQ8MZ/O4k/mMDq78LJYrPzAttylYpldYb9qhAHGkRgHtvwDRMEmkrU2AvZVoyRA1Rt/tLRr7QMDB9ir7nX6BL3sEQRyeyWFn/PIAAAAldEVYdGRhdGU6Y3JlYXRlADIwMTgtMDUtMTNUMjA6MzQ6NDArMDI6MDAM+k9jAAAAJXRFWHRkYXRlOm1vZGlmeQAyMDE4LTA1LTEzVDIwOjM0OjQwKzAyOjAwfaf33wAAAABJRU5ErkJggg==',
		size : new google.maps.Size(32, 32),
		origin : new google.maps.Point(0, 0),
		anchor : new google.maps.Point(16, 16)
	};
	// REFUGE HANDLING -------------------------------------
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
						+ ": </b>" + mapElements.refuge.name + '<br/><br/><a href="' + getMainDomain() + 'refuge/player/display.do">'
						+ mapTranslations.refuge.enterInRefuge[mapElements.languages[int4]] + '</a>';
				usedLanguage = true;
				break;
			}
		}
		if (usedLanguage == false) {
			contentString = '<b>' + mapTranslations.refuge.refugeMine["en"] + '</b><br/><b>' + mapTranslations.refuge.name["en"] + ": </b>" + mapElements.refuge.name
					+ '<br/><br/><a href="' + getMainDomain() + 'refuge/player/display.do">' + mapTranslations.refuge.enterInRefuge["en"] + '</a>';
		}
		infoWindow.setContent(contentString);
		infoWindow.setPosition(event.latLng);

		infoWindow.open(map);
	});
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
				if (pointInZone(m, r)) {
					currentInt = int2;
				}
			}
			if (currentInt != -1) {
				var contentString;
				var usedLanguage = false;
				for ( var int3 = 0; int3 < mapElements.languages.length; int3++) {
					if (mapElements.languages[int3] === getCookie("language")) {
						contentString = '<b>' + mapTranslations.location.location[mapElements.languages[int3]] + '</b><br/><b>'
								+ mapTranslations.location.name[mapElements.languages[int3]] + ": </b>" + mapElements.locations[currentInt].name[mapElements.languages[int3]]
								+ '<br/><br/><a href="' + getMainDomain() + 'recolection/player/create.do?locationId=' + mapElements.locations[currentInt].id + '">'
								+ mapTranslations.location.recolectionMissionStartLink[mapElements.languages[int3]] + '</a>';
						usedLanguage = true;
						break;
					}
				}
				if (usedLanguage == false) {
					contentString = '<b>' + mapTranslations.location.location[mapElements.languages[int3]] + '</b><br/><b>' + mapTranslations.location.name["en"] + ": </b>"
							+ mapElements.locations[currentInt].name["en"] + '<br/><br/><a href="' + getMainDomain() + 'recolection/player/create.do?locationId='
							+ mapElements.locations[currentInt].id + '">' + mapTranslations.location.recolectionMissionStartLink["en"] + '</a>';
				}
				infoWindow.setContent(contentString);
				infoWindow.setPosition(event.latLng);

				infoWindow.open(map);
			}
		});
	}
	// KNOWN REFUGE HANDLING -------------------------------------
	var refugeEnemy = [];
	for ( var int5 = 0; int5 < mapElements.knownRefuges.length; int5++) {
		refugeEnemy[int5] = new google.maps.Marker({
			position : {
				lat : Number(mapElements.knownRefuges[int5].gpsCoordinates.split(",")[0]),
				lng : Number(mapElements.knownRefuges[int5].gpsCoordinates.split(",")[1])
			},
			map : map,
			icon : imageEnemyRefuge
		});
		refugeEnemy[int5].addListener('click', function(event) {
			var currentInt = -1;
			var currentLow = null;
			var contentString;
			var usedLanguage = false;
			for ( var int2 = 0; int2 < mapElements.knownRefuges.length; int2++) {
				var m = {
					x : event.latLng.lat(),
					y : event.latLng.lng()
				};
				var r = {
					x : Number(mapElements.knownRefuges[int2].gpsCoordinates.split(",")[0]),
					y : Number(mapElements.knownRefuges[int2].gpsCoordinates.split(",")[1])
				};
				if (currentLow == null) {
					currentLow = distanceBetweenPoints(m, r);
					currentInt = int2;
				} else {
					if (distanceBetweenPoints(m, r) < currentLow) {
						currentLow = distanceBetweenPoints(m, r);
						currentInt = int2;
					}
				}
			}
			if (currentInt !== -1) {
				for ( var int5 = 0; int5 < mapElements.languages.length; int5++) {
					if (mapElements.languages[int5] === getCookie("language")) {
						contentString = '<b>' + mapTranslations.refuge.refugeEnemy[mapElements.languages[int5]] + '</b><br/><b>'
								+ mapTranslations.refuge.name[mapElements.languages[int5]] + ": </b>" + mapElements.knownRefuges[currentInt].name + '<br/><br/><a href="'
								+ getMainDomain() + 'attack/player/create.do?refugeId=' + mapElements.knownRefuges[currentInt].id + '">'
								+ mapTranslations.refuge.attackRefuge[mapElements.languages[int5]] + '</a>';
						usedLanguage = true;
						break;
					}
				}
				if (usedLanguage == false) {
					contentString = '<b>' + mapTranslations.refuge.refugeEnemy["en"] + '</b><br/><b>' + mapTranslations.refuge.name["en"] + ": </b>"
							+ mapElements.knownRefuges[currentInt].name + '<br/><br/><a href="' + getMainDomain() + 'attack/player/create.do?refugeId='
							+ mapElements.knownRefuges[currentInt].id + '">' + mapTranslations.refuge.attackRefuge["en"] + '</a>';
				}
				infoWindow.setContent(contentString);
				infoWindow.setPosition(event.latLng);

				infoWindow.open(map);
			}
		});
	}

}
function distanceBetweenPoints(m, r) {
	return Math.sqrt(Math.pow((m.x - r.x), 2) + Math.pow((m.y - r.y), 2));
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
		location : {
			es : "Zona",
			en : "Location"
		},
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
