var previewImage = "data:image/png;base64,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";
function uploadImage() {
	var language = getLanguageToUse();
	document.getElementById("imageError").innerHTML = "";
	var file = document.getElementById('myFile').files[0];
	var imageUrl = document.getElementById("imageUrl");
	var reader = new FileReader();
	reader.addEventListener("load", function() {
		imageUrl.value = reader.result;
		updateImage();
	}, false);
	if (file) {
		if (file.type === "image/jpeg" || file.type === "image/png" || file.type === "image/gif") {
			if (file.size < 5242880) {
				reader.readAsDataURL(file);
			} else {
				document.getElementById("imageError").innerHTML = imageUploadTranslation.error.tooLarge[language];
			}
		} else {
			document.getElementById("imageError").innerHTML = imageUploadTranslation.error.notSupported[language];
		}
	}
}
function updateImageRex() {
	var language = getLanguageToUse();
	document.getElementById("imageError").innerHTML = "";
	var expression = /https?:\/\/(www\.)?[-a-zA-Z0-9@:%._\+~#=]{2,256}\.[a-z]{2,6}\b([-a-zA-Z0-9@:%_\+.~#?&\/\/=]*)/;
	var regex = new RegExp(expression);
	if (document.getElementById("imageUrl").value.match(regex)) {
		document.getElementById('preview').src = document.getElementById("imageUrl").value;
	} else {
		document.getElementById("imageError").innerHTML = imageUploadTranslation.error.nonUrl[language];
	}
}
function updateImage() {
	document.getElementById('preview').src = document.getElementById("imageUrl").value;
}
function useImageUpload() {
	document.getElementById("imageUrl").hidden = true;
	document.getElementById("myFile").hidden = false;
	document.getElementById("imageMax").hidden = false;
	document.getElementById("imageUrl").value = "";
	document.getElementById("preview").src = previewImage;
}
function useImageUrl() {
	document.getElementById("imageUrl").hidden = false;
	document.getElementById("myFile").hidden = true;
	document.getElementById("imageMax").hidden = true;
	document.getElementById("imageUrl").value = "";
	document.getElementById('myFile').files = null;
	document.getElementById("preview").src = previewImage;
}
