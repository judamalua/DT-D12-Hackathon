function useOldCreditCard() {
	var language = getLanguageToUse();
	var cardId = document.getElementsByClassName("oldCreditCardId")[0].innerHTML;
	document.getElementsByClassName("cardForm")[0].innerHTML = '<input id="creditCard" name="creditCard" type="hidden" value="' + cardId + '">';
	document.getElementsByClassName("creditCardButton")[0].onclick = function() {
		useNewCreditCard();
	};
	document.getElementsByClassName("creditCardButton")[0].innerHTML = creditCardTranslation.newCreditCard[language];
};
function useNewCreditCard() {
	location.reload();
};
function checkCreditCard() {
	var cardCookie = getCookie("cardCookie");
	var language = getLanguageToUse();
	if (cardCookie != null || cardCookie != "") {
		var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {
				if (this.responseText != "null") {
					document.getElementsByClassName("cookieCard")[0].innerHTML = '<div class="row"><div class="col s12 m6"><div class="card red darken-4"><div class="card-content white-text"><span class="card-title">'
							+ creditCardTranslation.title[language]
							+ '</span><p>'
							+ creditCardTranslation.useNumber[language]
							+ this.responseText.substring(0, 4)
							+ '</p></div><div class="card-action"><a onclick="useOldCreditCard()" class="creditCardButton" href="javascript:void(0);">'
							+ creditCardTranslation.useCard[language]
							+ '</a></div></div></div></div><p hidden="true" class="oldCreditCardId">'
							+ this.responseText.substring(4)
							+ '</p>';
				}
			}
		};
		xhttp.open("GET", getMainDomain() + "order/player/ajaxCard.do?cookieToken=" + cardCookie, true);
		xhttp.send();
	}
};
function saveCreditCardCookie() {
	var cardCookie = document.getElementsByClassName("creditCardCookieToken")[0].value;
	document.cookie = "cardCookie=" + cardCookie + ";path=/";
};