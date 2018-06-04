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
import services.ForumService;
import controllers.AbstractController;
import domain.Actor;
import domain.Forum;
import domain.Moderator;

@Controller
@RequestMapping("/forum/moderator")
public class ForumModeratorController extends AbstractController {

	@Autowired
	private ActorService	actorService;
	@Autowired
	private ForumService	forumService;


	// Constructors -----------------------------------------------------------

	public ForumModeratorController() {
		super();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final Integer forumId) {
		ModelAndView result;
		final Forum forum;
		Actor actor;
		try {

			actor = this.actorService.findActorByPrincipal();
			Assert.isTrue(actor instanceof Moderator);
			forum = this.forumService.findOne(forumId);

			this.forumService.delete(forum);
			if (forum.getForum() == null) {
				result = new ModelAndView("redirect:/forum/list.do");
			} else {
				result = new ModelAndView("redirect:/forum/list.do?forumId=" + forum.getForum().getId());
			}
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}
		return result;
	}

}
