
package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/cookie")
public class CookieController {

	@RequestMapping(value = "/policy", method = RequestMethod.GET)
	public ModelAndView policy() {
		return new ModelAndView("cookie/policy");
	}
}
