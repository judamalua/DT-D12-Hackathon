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

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ForumService;
import services.ThreadService;
import controllers.AbstractController;
import domain.Actor;
import domain.Forum;
import domain.Thread;

@Controller
@RequestMapping("/thread/actor")
public class ThreadActorController extends AbstractController {

	@Autowired
	private ActorService	actorService;

	@Autowired
	private ThreadService	threadService;

	@Autowired
	private ForumService	forumService;


	// Constructors -----------------------------------------------------------

	public ThreadActorController() {
		super();
	}

	//Edit an forum
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final Integer threadId) {
		ModelAndView result;
		Thread thread;
		Actor actor;
		try {
			actor = this.actorService.findActorByPrincipal();

			thread = this.threadService.findOne(threadId);
			Assert.notNull(thread);
			Assert.isTrue(thread.getActor().equals(actor));

			result = this.createEditModelAndView(thread);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(required = false) final Integer forumId) {
		ModelAndView result;
		final Thread thread;
		Forum forum;

		try {
			this.actorService.checkActorLogin();

			thread = this.threadService.create();
			if (forumId != null) {
				forum = this.forumService.findOne(forumId);
				Assert.notNull(forum);
				thread.setForum(forum);
			}

			result = this.createEditModelAndView(thread);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}
		return result;
	}
	//Updating forum ---------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView updateUser(@ModelAttribute("thread") Thread thread, final BindingResult binding) {
		ModelAndView result;
		Thread savedThread, sendedThread = null;
		try {
			sendedThread = thread;
			thread = this.threadService.reconstruct(thread, binding);
		} catch (final Throwable oops) {//Not delete
		}
		if (binding.hasErrors())
			result = this.createEditModelAndView(sendedThread, "thread.params.error");
		else
			try {
				savedThread = this.threadService.save(thread);

				if (savedThread.getForum() != null)
					result = new ModelAndView("redirect:/forum/list.do?forumId=" + savedThread.getForum().getId());
				else
					result = new ModelAndView("redirect:/forum/list.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(thread, "thread.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@ModelAttribute("thread") Thread thread, final BindingResult binding) {
		ModelAndView result;

		try {
			thread = this.threadService.findOne(thread.getId());
			this.threadService.delete(thread);

			if (thread.getForum() != null)
				result = new ModelAndView("redirect:/forum/list.do?forumId=" + thread.getForum().getId());
			else
				result = new ModelAndView("redirect:/forum/list.do");

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(thread, "thread.commit.error");
		}

		return result;
	}

	// Ancillary methods --------------------------------------------------

	protected ModelAndView createEditModelAndView(final Thread thread) {
		ModelAndView result;

		result = this.createEditModelAndView(thread, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Thread thread, final String messageCode) {
		ModelAndView result;
		Collection<Forum> forums;

		result = new ModelAndView("thread/edit");

		if (thread.getForum() != null) {
			forums = this.forumService.findForums(false);
			result.addObject("forums", forums);
		}

		result.addObject("message", messageCode);
		result.addObject("thread", thread);

		return result;

	}

}
