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
import services.ThreadService;
import controllers.AbstractController;
import domain.Thread;

@Controller
@RequestMapping("/thread/actor")
public class ThreadActorController extends AbstractController {

	@Autowired
	private ActorService	actorService;

	@Autowired
	private ThreadService	threadService;


	// Constructors -----------------------------------------------------------

	public ThreadActorController() {
		super();
	}

	//Edit an forum
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int threadId) {
		ModelAndView result;
		Thread thread;

		this.actorService.checkUserLogin();

		thread = this.threadService.findOne(threadId);
		result = this.createEditModelAndView(thread);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final Thread thread;

		this.actorService.checkUserLogin();
		thread = this.threadService.create();

		result = this.createEditModelAndView(thread);

		return result;
	}

	//Updating forum ---------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView updateUser(@ModelAttribute("thread") Thread thread, final BindingResult binding) {
		ModelAndView result;
		Thread savedThread;
		try {
			thread = this.threadService.reconstruct(thread, binding);
		} catch (final Throwable oops) {//Not delete
		}
		if (binding.hasErrors())
			result = this.createEditModelAndView(thread, "thread.params.error");
		else
			try {
				savedThread = this.threadService.save(thread);

				if (savedThread.getForum().getForum() != null)
					result = new ModelAndView("redirect:/forum/list.do?forumId=" + savedThread.getForum().getForum().getId());
				else
					result = new ModelAndView("redirect:/forum/list.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(thread, "thread.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Thread thread, final BindingResult binding) {
		ModelAndView result;

		try {
			thread = this.threadService.reconstruct(thread, binding);
		} catch (final Throwable oops) {
		}
		try {
			this.threadService.delete(thread);

			if (thread.getForum().getForum() != null)
				result = new ModelAndView("redirect:/forum/list.do?forumId=" + thread.getForum().getForum().getId());
			else
				result = new ModelAndView("redirect:/forum/list.do");

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(thread, "thread.commit.error");
		}

		return result;
	}

	// Ancillary methods --------------------------------------------------

	protected ModelAndView createEditModelAndView(final Thread message) {
		ModelAndView result;

		result = this.createEditModelAndView(message, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Thread message, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("thread/edit");
		result.addObject("message", messageCode);
		result.addObject("thread", message);

		return result;

	}

}
