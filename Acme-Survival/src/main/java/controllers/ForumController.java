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
import java.util.HashSet;

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
@RequestMapping("/forum")
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
	public ModelAndView list(@RequestParam(required = false, defaultValue = "false") final Boolean staff, @RequestParam(required = false, defaultValue = "false") final Boolean support, @RequestParam(required = false) final Integer forumId, @RequestParam(
		required = false, defaultValue = "0") final int page, @RequestParam(required = false, defaultValue = "0") final int pageThread) {
		ModelAndView result;
		Page<Forum> forums;
		Forum forum;
		Pageable pageable, threadPageable;
		Actor actor;
		Configuration configuration;
		Page<Thread> threads = null;
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
			if (support) {
				this.actorService.checkActorLogin();
			}

			if (forumId == null) {
				forums = this.forumService.findRootForums(staff, support, pageable);
			} else {
				forums = this.forumService.findSubForums(forumId, staff, support, pageable);
				threads = this.threadsService.findThreadsByForum(forumId, threadPageable);
				forum = this.forumService.findOne(forumId);
				if (forum.getStaff() || forum.getSupport()) {
					this.actorService.checkActorLogin();
				}
				result.addObject("fatherForum", forum);
			}

			if (this.actorService.getLogged()) {
				actor = this.actorService.findActorByPrincipal();
				for (int i = 0; i < forums.getContent().size(); i++) {
					ownForums.add(actor.equals(forums.getContent().get(i).getOwner()));
				}
				if (forumId != null) {
					for (int i = 0; i < threads.getContent().size(); i++) {
						ownThreads.add(actor.equals(threads.getContent().get(i).getActor()));
					}
				}

				result.addObject("ownForums", ownForums);
				result.addObject("ownThreads", ownThreads);
			}

			if (forumId != null) {
				result.addObject("threads", threads.getContent());
				result.addObject("pageThread", pageThread);
				result.addObject("pageNumThread", threads.getTotalPages());
				result.addObject("requestURI", "forum/list.do?staff=" + staff + "&forumId=" + forumId + "&");
			} else {
				result.addObject("threads", new HashSet<>());
				result.addObject("pageThread", 0);
				result.addObject("pageNumThread", 0);
				result.addObject("requestURI", "forum/list.do?staff=" + staff + "&");
			}

			result.addObject("forums", forums.getContent());
			result.addObject("page", page);
			result.addObject("pageNum", forums.getTotalPages());

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}
		return result;
	}
}
