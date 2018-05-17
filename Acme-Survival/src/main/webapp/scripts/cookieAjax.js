function getCookie(cname) {
	var name = cname + "=";
	var decodedCookie = decodeURIComponent(document.cookie);
	var ca = decodedCookie.split(';');
	for ( var i = 0; i < ca.length; i++) {
		var c = ca[i];
		while (c.charAt(0) == ' ') {
			c = c.substring(1);
		}
		if (c.indexOf(name) == 0) { return c.substring(name.length, c.length); }
	}
	return null;
}
function acceptCookies() {
	document.getElementsByClassName("cookies")[0].innerHTML = "";
	document.cookie = "acceptCookies=true;path=/";
}
function showCookieMessage() {
	var language = getLanguageToUse();
	document.getElementsByClassName("cookies")[0].innerHTML = '<div class="row"><div class="col s12 m6"><div class="card red darken-4"><div class="card-content white-text"><span class="card-title">'
			+ cookiesTranslation.title[language]
			+ '</span><p>'
			+ cookiesTranslation.message[language]
			+ '</p></div><div class="card-action"><a href="'
			+ getMainDomain()
			+ 'cookie/policy.do">'
			+ cookiesTranslation.cookiePolicy[language]
			+ '</a><a href="javascript:void(0);" onClick="acceptCookies();">'
			+ cookiesTranslation.acceptCookies[language] + '</a></div></div></div></div>';

};
function checkCookie() {
	var acceptCookies = getCookie("acceptCookies");
	if (acceptCookies != "" && acceptCookies != null) {
		if (acceptCookies == "false") {
			showCookieMessage();
		}
	} else {
		if (acceptCookies == null) {
			document.cookie = "acceptCookies=false;path=/";
			showCookieMessage();
		}
	}
}
