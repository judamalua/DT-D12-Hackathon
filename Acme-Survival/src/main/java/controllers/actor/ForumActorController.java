/*
 * RendezvousController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.actor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ForumService;
import controllers.AbstractController;
import domain.Forum;

@Controller
@RequestMapping("/forum/actor")
public class ForumActorController extends AbstractController {

	@Autowired
	private ActorService	actorService;
	@Autowired
	private ForumService	forumService;


	// Constructors -----------------------------------------------------------

	public ForumActorController() {
		super();
	}

	//Edit an forum
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int forumId) {
		ModelAndView result;
		Forum forum;

		this.actorService.checkUserLogin();

		forum = this.forumService.findOne(forumId);
		result = this.createEditModelAndView(forum);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final Forum forum;

		this.actorService.checkUserLogin();
		forum = this.forumService.create();

		result = this.createEditModelAndView(forum);

		return result;
	}

	//Updating forum ---------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView updateUser(@ModelAttribute("forum") Forum forum, final BindingResult binding) {
		ModelAndView result;
		Forum savedForum;
		try {
			forum = this.forumService.reconstruct(forum, binding);
		} catch (final Throwable oops) {//Not delete
		}
		if (binding.hasErrors())
			result = this.createEditModelAndView(forum, "forum.params.error");
		else
			try {
				savedForum = this.forumService.save(forum);
				if (savedForum.getForum() != null)
					result = new ModelAndView("redirect:/forum/list.do?forumId=" + savedForum.getForum().getId() + "&staff=" + savedForum.getStaff());
				else
					result = new ModelAndView("redirect:/forum/list.do?staff=" + savedForum.getStaff());

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(forum, "forum.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Forum forum, final BindingResult binding) {
		ModelAndView result;

		try {
			forum = this.forumService.reconstruct(forum, binding);
		} catch (final Throwable oops) {
		}
		try {
			this.forumService.delete(forum);

			if (forum.getForum() != null)
				result = new ModelAndView("redirect:/forum/list.do?forumId=" + forum.getForum().getId() + "&staff=" + forum.getStaff());
			else
				result = new ModelAndView("redirect:/forum/list.do?staff=" + forum.getStaff());

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(forum, "forum.commit.error");
		}

		return result;
	}

	// Ancillary methods --------------------------------------------------

	protected ModelAndView createEditModelAndView(final Forum forum) {
		ModelAndView result;

		result = this.createEditModelAndView(forum, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Forum forum, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("forum/edit");
		result.addObject("message", messageCode);
		result.addObject("forum", forum);

		return result;

	}

}
