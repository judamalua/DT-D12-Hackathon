function generateCharacters() {
	for ( var int = 0; int < document.getElementsByClassName("charaterGenre").length; int++) {
		var genre = document.getElementsByClassName("charaterGenre")[int].innerHTML;
		var name = document.getElementsByClassName("characterName")[int].innerHTML;
		var avatars;
		if (genre === "Male") {
			avatars = new Avatars(Avatars.sprites.male);
		}
		if (genre === "Female") {
			avatars = new Avatars(Avatars.sprites.female);
		}
		if (genre !== "Male" && genre !== "Female") {
			avatars = '<svg><text x="0" y="15" fill="red">Sorry, only two genders are supported</text></svg>';
		}
		var svg = avatars.create(name);
		document.getElementsByByClassName("characterImage")[int].innerHTML = svg;
	}
}
