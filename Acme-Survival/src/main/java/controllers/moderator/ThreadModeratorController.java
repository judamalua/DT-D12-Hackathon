/*
 * RendezvousController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.moderator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ThreadService;
import controllers.AbstractController;
import domain.Actor;
import domain.Moderator;
import domain.Thread;

@Controller
@RequestMapping("/thread/moderator")
public class ThreadModeratorController extends AbstractController {

	@Autowired
	private ActorService	actorService;
	@Autowired
	private ThreadService	threadService;


	// Constructors -----------------------------------------------------------

	public ThreadModeratorController() {
		super();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final Integer threadId) {
		ModelAndView result;
		final Thread thread;
		Actor actor;
		try {

			actor = this.actorService.findActorByPrincipal();
			Assert.isTrue(actor instanceof Moderator);
			thread = this.threadService.findOne(threadId);

			this.threadService.delete(thread);
			result = new ModelAndView("redirect:/forum/list.do?forumId=" + thread.getForum().getId());
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}
		return result;
	}

}
