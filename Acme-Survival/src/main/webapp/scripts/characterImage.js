function generateCharacters() {
	debugger;
	var genre = document.getElementById("charaterGenre").innerHtml;
	var name = document.getElementById("characterName").value;
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
	document.getElementById("characterImage").innerHTML = svg;
}
