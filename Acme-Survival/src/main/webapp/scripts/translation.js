function getLanguageToUse() {
	var supportedLanguages = [
			"en", "es"
	];
	var language = getCookie("language");
	for ( var int = 0; int < supportedLanguages.length; int++) {
		if (language === supportedLanguages[int]) { return supportedLanguages[int]; }
	}
	return "en";
};

var mapTranslations = {
	attack : {
		attack : {
			es : "Ataque en proceso",
			en : "On going attack"
		},
		refuge : {
			es : "Refugio al que atacas",
			en : "Attacked refuge"
		},
		time : {
			es : "Tiempo restante",
			en : "Remaining time"
		},
		minutes : {
			es : "min",
			en : "min"
		},
		done : {
			es : "Misión cumplida, vaya a notificaciones para ver el resultado",
			en : "Mission acomplished, go to notifications to see the results"
		}
	},
	gather : {
		gather : {
			es : "Recolección en proceso",
			en : "On going gather"
		},
		character : {
			es : "Personaje en misión",
			en : "Character in the mission"
		}
	},
	move : {
		move : {
			es : "Mudanza en proceso",
			en : "On going move"
		},
		location : {
			es : "Zona a la que te mueves",
			en : "Location that you are moving to"
		}
	},
	location : {
		location : {
			es : "Zona",
			en : "Location"
		},
		name : {
			es : "Nombre de zona",
			en : "Location name"
		},
		gatherMissionStartLink : {
			es : "Iniciar misión de recolección en esta zona",
			en : "Start gather mission in this location"
		},
		moveStartLink : {
			es : "Mover tu refugio a esta zona",
			en : "Move your refuge to this location"
		},
		editLocation : {
			es : "Editar esta zona",
			en : "Edit this location"
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
		playerName : {
			es : "Dueño del refugio",
			en : "Refuge owner"
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
var cookiesTranslation = {
	title : {
		es : "Sobre las cookies",
		en : "About the cookies"
	},
	message : {
		es : "Esta pagina usa cookies para realizar su correcto funcionamiento, si continúa navegando, asumiremos que acepta estas cookies.",
		en : "This page uses cookies for its internal operations, by continuing, we will assume that you accept these cookies."
	},
	cookiePolicy : {
		es : "Política de cookies",
		en : "Cookie policy"
	},
	acceptCookies : {
		es : "Aceptar las cookies",
		en : "Accept the cookies"
	}
};

var creditCardTranslation = {
	newCreditCard : {
		es : "Usar una nueva tarjeta de crédito",
		en : "Use a new credit card"
	},
	title : {
		es : "Usar una tarjeta de crédito anterior",
		en : "Use a previous credit card"
	},
	useNumber : {
		es : "Con el número XXXX XXXX XXXX ",
		en : "With the number XXXX XXXX XXXX "
	},
	useCard : {
		es : "Usar esta tarjeta",
		en : "Use this card"
	}
};

var widgEditorTranslation = {
	bold : {
		es : "Negrita",
		en : "Bold"
	},
	italic : {
		es : "Cursiva",
		en : "Italic"
	},
	hyperlink : {
		es : "Hipervínculo",
		en : "Hyperlink"
	},
	unorderedlist : {
		es : "Lista No Ordenada",
		en : "Unordered List"
	},
	orderedlist : {
		es : "Lista Ordenada",
		en : "Ordered List"
	},
	image : {
		es : "Insertar Imagen",
		en : "Insert Image"
	},
	htmlsource : {
		es : "Código Fuente",
		en : "HTML Source"
	},
	linkSelect : {
		es : "Por favor selecciona el texto para generar el enlace.",
		en : "Please select the text you wish to hyperlink."
	},
	linkInsert : {
		es : "Inserta la URL para este link:",
		en : "Enter the URL for this link:"
	},
	imageLocation : {
		es : "Introduce la localización de esta imagen:",
		en : "Enter the location for this image:"
	},
	imageText : {
		es : "Introduce un texto alternativo a esta imagen:",
		en : "Enter the alternate text for this image:"
	},
	imageHeight : {
		es : "Introduce la altura de esta imagen:",
		en : "Enter height of this image:"
	},
	imageWidth : {
		es : "Introduce la anchura de esta imagen:",
		en : "Enter width of this image:"
	}
};

var imageUploadTranslation = {
	error : {
		nonUrl : {
			es : "Error, la imagen introducida no es una url",
			en : "Error, this image is not an url"
		},
		notSupported : {
			es : "Error, formato no soportado",
			en : "Error, format not supported"
		},
		tooLarge : {
			es : "Error, archivo demasiado grande",
			en : "Error, file too large"
		}
	}
}
