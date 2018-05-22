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
		var language = getLanguageToUse();
		var contentString = '<b>' + mapTranslations.refuge.refugeMine[language] + '</b><br/><b>' + mapTranslations.refuge.name[language] + ": </b>" + mapElements.refuge.name
				+ '<br/><b>' + mapTranslations.refuge.playerName[language] + ": </b>" + mapElements.refuge.playerName + '<br/><br/><a href="' + getMainDomain()
				+ 'refuge/player/display.do">' + mapTranslations.refuge.enterInRefuge[language] + '</a>';
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
				if (pointInZone2(m, r)) {
					currentInt = int2;
				}
			}
			if (currentInt != -1) {
				var language = getLanguageToUse();
				var contentString = '<b>' + mapTranslations.location.location[language] + '</b><br/><b>' + mapTranslations.location.name[language] + ": </b>"
						+ mapElements.locations[currentInt].name[language] + '<br/><br/><a href="' + getMainDomain() + 'gather/player/create.do?locationId='
						+ mapElements.locations[currentInt].id + '">' + mapTranslations.location.gatherMissionStartLink[language] + '</a><br/><a href="' + getMainDomain()
						+ 'move/player/create.do?locationId=' + mapElements.locations[currentInt].id + '">' + mapTranslations.location.moveStartLink[language] + '</a>';

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
				var language = getLanguageToUse();
				var contentString = '<b>' + mapTranslations.refuge.refugeEnemy[language] + '</b><br/><b>' + mapTranslations.refuge.name[language] + ": </b>"
						+ mapElements.knownRefuges[currentInt].name + '<br/><b>' + mapTranslations.refuge.playerName[language] + ": </b>"
						+ mapElements.knownRefuges[currentInt].playerName + '<br/><br/><a href="' + getMainDomain() + 'attack/player/create.do?refugeId='
						+ mapElements.knownRefuges[currentInt].id + '">' + mapTranslations.refuge.attackRefuge[language] + '</a>';
				infoWindow.setContent(contentString);
				infoWindow.setPosition(event.latLng);

				infoWindow.open(map);
			}
		});

	}
	// ATTACK HANDLING -------------------------------------
	if (mapElements.onGoingAttack.refuge !== undefined) {
		var attackCoordinates = [
				{
					lat : Number(mapElements.refuge.gpsCoordinates.split(",")[0]),
					lng : Number(mapElements.refuge.gpsCoordinates.split(",")[1])
				}, {
					lat : Number(mapElements.onGoingAttack.refuge.gpsCoordinates.split(",")[0]),
					lng : Number(mapElements.onGoingAttack.refuge.gpsCoordinates.split(",")[1])
				}
		];
		var attack = new google.maps.Polyline({
			path : attackCoordinates,
			geodesic : true,
			strokeColor : '#FF0000',
			strokeOpacity : 1.0,
			strokeWeight : 5,
			map : map
		});
		attack.addListener('click', function(event) {
			var mapElements = JSON.parse(document.getElementById("mapElements").innerHTML);
			var language = getLanguageToUse();
			var contentString = '<b>' + mapTranslations.attack.attack[language] + '</b><br/><b>' + mapTranslations.attack.refuge[language] + ": </b>"
					+ mapElements.onGoingAttack.refuge.name + '<br/><b>' + mapTranslations.refuge.playerName[language] + ": </b>" + mapElements.onGoingAttack.refuge.playerName
					+ '<br/>' + '<b>' + mapTranslations.attack.time[language] + ': </b>' + getRemainingTime(mapElements.onGoingAttack.endMoment);
			infoWindow.setContent(contentString);
			infoWindow.setPosition(event.latLng);

			infoWindow.open(map);
		});
	}

	// MOVE HANDLING -------------------------------------
	if (mapElements.onGoingMove.location !== undefined) {
		var locationToMove = {
			A : {
				x : Number(mapElements.onGoingMove.location.point_a.split(",")[0]),
				y : Number(mapElements.onGoingMove.location.point_a.split(",")[1])
			},
			B : {
				x : Number(mapElements.onGoingMove.location.point_b.split(",")[0]),
				y : Number(mapElements.onGoingMove.location.point_b.split(",")[1])
			},
			C : {
				x : Number(mapElements.onGoingMove.location.point_c.split(",")[0]),
				y : Number(mapElements.onGoingMove.location.point_c.split(",")[1])
			},
			D : {
				x : Number(mapElements.onGoingMove.location.point_d.split(",")[0]),
				y : Number(mapElements.onGoingMove.location.point_d.split(",")[1])
			}
		};
		var locationCenter = getZoneCenter(locationToMove);
		var moveCoordinates = [
				{
					lat : Number(mapElements.refuge.gpsCoordinates.split(",")[0]),
					lng : Number(mapElements.refuge.gpsCoordinates.split(",")[1])
				}, {
					lat : Number(locationCenter.x),
					lng : Number(locationCenter.y)
				}
		];
		var move = new google.maps.Polyline({
			path : moveCoordinates,
			geodesic : true,
			strokeColor : '#000000',
			strokeOpacity : 1.0,
			strokeWeight : 5,
			map : map
		});
		move.addListener('click', function(event) {
			var mapElements = JSON.parse(document.getElementById("mapElements").innerHTML);
			var language = getLanguageToUse();
			var contentString = '<b>' + mapTranslations.move.move[language] + '</b><br/><b>' + mapTranslations.move.location[language] + ": </b>"
					+ mapElements.onGoingMove.location.name[language] + '<br/><b>' + mapTranslations.attack.time[language] + ': </b>'
					+ getRemainingTime(mapElements.onGoingMove.endMoment);
			infoWindow.setContent(contentString);
			infoWindow.setPosition(event.latLng);

			infoWindow.open(map);
		});
	}

	// GATHER HANDLING -------------------------------------
	var gatherLineCoordinates = [];
	var gatherLine = [];
	var gatherCharacter = [];
	var randomCoordinates = [];
	var r = [];
	for ( var int20 = 0; int20 < mapElements.onGoingGathers.length; int20++) {
		r[int20] = {
			A : {
				x : Number(mapElements.onGoingGathers[int20].location.point_a.split(",")[0]),
				y : Number(mapElements.onGoingGathers[int20].location.point_a.split(",")[1])
			},
			B : {
				x : Number(mapElements.onGoingGathers[int20].location.point_b.split(",")[0]),
				y : Number(mapElements.onGoingGathers[int20].location.point_b.split(",")[1])
			},
			C : {
				x : Number(mapElements.onGoingGathers[int20].location.point_c.split(",")[0]),
				y : Number(mapElements.onGoingGathers[int20].location.point_c.split(",")[1])
			},
			D : {
				x : Number(mapElements.onGoingGathers[int20].location.point_d.split(",")[0]),
				y : Number(mapElements.onGoingGathers[int20].location.point_d.split(",")[1])
			}
		};
		randomCoordinates[int20] = randomPointInZone(r[int20]);
		gatherCharacter[int20] = new google.maps.Marker({
			position : {
				lat : randomCoordinates[int20].x,
				lng : randomCoordinates[int20].y
			},
			map : map,
			icon : imageCharacter
		});
		gatherLineCoordinates[int20] = [
				{
					lat : Number(mapElements.refuge.gpsCoordinates.split(",")[0]),
					lng : Number(mapElements.refuge.gpsCoordinates.split(",")[1])
				}, {
					lat : randomCoordinates[int20].x,
					lng : randomCoordinates[int20].y
				}
		];
		gatherLine[int20] = new google.maps.Polyline({
			path : gatherLineCoordinates[int20],
			geodesic : true,
			strokeColor : '#0000FF',
			strokeOpacity : 1.0,
			strokeWeight : 5,
			map : map
		});
		gatherCharacter[int20].addListener('click', function(event) {
			var mapElements = JSON.parse(document.getElementById("mapElements").innerHTML);
			var randomPoints = JSON.parse(document.getElementById("randomPoints").innerHTML);
			var currentInt = -1;
			var currentLow = null;
			for ( var int2 = 0; int2 < randomPoints.length; int2++) {
				var m = {
					x : event.latLng.lat(),
					y : event.latLng.lng()
				};
				var r = {
					x : randomPoints[int2].x,
					y : randomPoints[int2].y
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
				var language = getLanguageToUse();
				var contentString = '<b>' + mapTranslations.gather.gather[language] + '</b><br/><b>' + mapTranslations.gather.character[language] + ": </b>"
						+ mapElements.onGoingGathers[currentInt].character.fullName + '<br/><div style="height: 100px; width: 100px;">'
						+ getCharacters(mapElements.onGoingGathers[currentInt].character.fullName, mapElements.onGoingGathers[currentInt].character.isMale) + '</div><b>'
						+ mapTranslations.attack.time[language] + ': </b>' + getRemainingTime(mapElements.onGoingGathers[currentInt].endMoment);
				infoWindow.setContent(contentString);
				infoWindow.setPosition(event.latLng);

				infoWindow.open(map);
			}
		});
	}
	document.getElementById("randomPoints").innerHTML = JSON.stringify(randomCoordinates);
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
