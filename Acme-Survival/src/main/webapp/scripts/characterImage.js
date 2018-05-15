function generateCharacters() {
	var genre = document.getElementsByClassName("charaterGenre")[0].innerHTML;
	var name = document.getElementsByClassName("characterName")[0].innerHTML;
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
	document.getElementsByByClassName("characterImage")[0].innerHTML = svg;
}
