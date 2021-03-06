function randomPointInZone(r) {
	var p = {};
	var r1 = Math.random();
	var r2 = Math.random();
	if (Math.random(0, 1) > 0.5) {
		p.x = (1 - Math.sqrt(r1)) * r.A.x + (Math.sqrt(r1) * (1 - r2)) * r.B.x + (Math.sqrt(r1) * r2) * r.C.x;
		p.y = (1 - Math.sqrt(r1)) * r.A.y + (Math.sqrt(r1) * (1 - r2)) * r.B.y + (Math.sqrt(r1) * r2) * r.C.y;
	} else {
		p.x = (1 - Math.sqrt(r1)) * r.A.x + (Math.sqrt(r1) * (1 - r2)) * r.D.x + (Math.sqrt(r1) * r2) * r.C.x;
		p.y = (1 - Math.sqrt(r1)) * r.A.y + (Math.sqrt(r1) * (1 - r2)) * r.D.y + (Math.sqrt(r1) * r2) * r.C.y;
	}
	return p;
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

function pointInZone2(m, r) {
	var P = [
			m.x, m.y
	];
	var A = [
			r.A.x, r.A.y
	];
	var B = [
			r.B.x, r.B.y
	];
	var C = [
			r.C.x, r.C.y
	];
	var D = [
			r.D.x, r.D.y
	];
	return pointInTriange(P, A, B, C) || pointInTriange(P, A, D, C);
};

function pointInTriange(P, A, B, C) {
	// Compute vectors
	function vec(from, to) {
		return [
				to[0] - from[0], to[1] - from[1]
		];
	}
	var v0 = vec(A, C);
	var v1 = vec(A, B);
	var v2 = vec(A, P);
	// Compute dot products
	function dot(u, v) {
		return u[0] * v[0] + u[1] * v[1];
	}
	var dot00 = dot(v0, v0);
	var dot01 = dot(v0, v1);
	var dot02 = dot(v0, v2);
	var dot11 = dot(v1, v1);
	var dot12 = dot(v1, v2);
	// Compute barycentric coordinates
	var invDenom = 1.0 / (dot00 * dot11 - dot01 * dot01);
	var u = (dot11 * dot02 - dot01 * dot12) * invDenom;
	var v = (dot00 * dot12 - dot01 * dot02) * invDenom;
	// Check if point is in triangle
	return (u >= 0) && (v >= 0) && (u + v < 1);
}

function getRemainingTime(time) {
	var language = getLanguageToUse();
	var timeInMin = Math.floor((time / 1000) / 60);
	if (time > 0) {
		if (timeInMin < 1) {
			return mapTranslations.attack.secondsRemaining[language];
		} else {
			return timeInMin + ' ' + mapTranslations.attack.minutes[language];
		}
	} else {
		return mapTranslations.attack.done[language];
	}
};

function getZoneCenter(p) {
	return {
		x : (p.A.x + p.B.x + p.C.x + p.D.x) / 4,
		y : (p.A.y + p.B.y + p.C.y + p.D.y) / 4
	};
}
