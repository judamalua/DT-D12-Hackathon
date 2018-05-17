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
