function generateCharacters() {
	for ( var int = 0; int < document.getElementsByClassName("characterGenre").length; int++) {
		var genre = document.getElementsByClassName("characterGenre")[int].innerHTML.trim();
		var name = document.getElementsByClassName("characterName")[int].innerHTML;
		var avatars;
		if (genre === "Male") {
			avatars = new Avatars(Avatars.sprites.male);
		}
		if (genre === "Female") {
			avatars = new Avatars(Avatars.sprites.female);
		}
		if (genre !== "Male" && genre !== "Female") {
			svg = '<svg><text x="0" y="15" fill="red">Sorry, gender is stored in a boolean ;)</text></svg>';
		} else {
			var svg = avatars.create(name);
		}
		document.getElementsByClassName("characterImage")[int].innerHTML = svg;
	}
}
