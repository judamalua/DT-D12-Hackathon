/*
 * RendezvousController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ConfigurationService;
import services.ForumService;
import services.ThreadService;
import domain.Actor;
import domain.Configuration;
import domain.Forum;
import domain.Player;
import domain.Thread;

@Controller
@RequestMapping("/user")
public class ForumController extends AbstractController {

	@Autowired
	private ForumService			forumService;

	@Autowired
	private ThreadService			threadsService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ConfigurationService	configurationService;


	// Constructors -----------------------------------------------------------

	public ForumController() {
		super();
	}

	// Listing  ---------------------------------------------------------------		

	/**
	 * That method returns a model and view with the system forum list
	 * 
	 * @param page
	 * @param anonymous
	 * 
	 * @return ModelandView
	 * @author MJ
	 */
	@RequestMapping("/list")
	public ModelAndView list(@RequestParam(required = false, defaultValue = "false") final Boolean staff, @RequestParam(required = false) final Integer forumId, @RequestParam(defaultValue = "0") final int page,
		@RequestParam(defaultValue = "0") final int pageThread) {
		ModelAndView result;
		Page<Forum> forums;
		Forum forum;
		Pageable pageable, threadPageable;
		Actor actor;
		Configuration configuration;
		final Page<Thread> threads;
		final Collection<Boolean> ownForums, ownThreads;

		try {

			result = new ModelAndView("forum/list");
			configuration = this.configurationService.findConfiguration();
			pageable = new PageRequest(page, configuration.getPageSize());
			threadPageable = new PageRequest(pageThread, configuration.getPageSize());

			ownForums = new ArrayList<>();
			ownThreads = new ArrayList<>();

			if (staff) {
				actor = this.actorService.findActorByPrincipal();
				Assert.isTrue(!(actor instanceof Player));
			}

			if (forumId == null)
				forums = this.forumService.findForums(staff, pageable);
			else
				forums = this.forumService.findSubForums(forumId, staff, pageable);

			threads = this.threadsService.findThreadsByForum(forumId, threadPageable);
			forum = this.forumService.findOne(forumId);

			if (this.actorService.getLogged()) {
				actor = this.actorService.findActorByPrincipal();
				for (int i = 0; i < forums.getContent().size(); i++)
					ownForums.add(actor.equals(forums.getContent().get(i).getOwner()));

				for (int i = 0; i < threads.getContent().size(); i++)
					ownThreads.add(actor.equals(threads.getContent().get(i).getActor()));

				result.addObject("ownForums", threads.getTotalPages());
				result.addObject("ownThreads", threads.getTotalPages());
			}

			result.addObject("fatherForum", forum);
			result.addObject("forums", forums.getContent());
			result.addObject("threads", threads.getContent());
			result.addObject("page", page);
			result.addObject("pageNum", forums.getTotalPages());
			result.addObject("pageThread", pageThread);
			result.addObject("pageNumThread", threads.getTotalPages());
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}
		return result;
	}
}
