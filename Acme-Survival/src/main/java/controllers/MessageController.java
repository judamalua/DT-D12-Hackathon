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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ConfigurationService;
import services.MessageService;
import services.ThreadService;
import domain.Actor;
import domain.Configuration;
import domain.Message;
import domain.Thread;

@Controller
@RequestMapping("/message")
public class MessageController extends AbstractController {

	@Autowired
	private MessageService			messageService;

	@Autowired
	private ThreadService			threadService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ConfigurationService	configurationService;


	// Constructors -----------------------------------------------------------

	public MessageController() {
		super();
	}

	// Listing  ---------------------------------------------------------------		

	/**
	 * That method returns a model and view with the system messages list by a thread
	 * 
	 * @param page
	 * @param threadId
	 * 
	 * @return ModelandView
	 * @author MJ
	 */
	@RequestMapping("/list")
	public ModelAndView list(@RequestParam final Integer threadId, @RequestParam(defaultValue = "0") final int page) {
		ModelAndView result;
		Thread thread;
		Pageable pageable;
		Actor actor;
		Configuration configuration;
		final Page<Message> messages;
		final Collection<Boolean> ownMessages;

		try {

			result = new ModelAndView("message/list");
			configuration = this.configurationService.findConfiguration();
			pageable = new PageRequest(page, configuration.getPageSize());

			ownMessages = new ArrayList<>();

			messages = this.messageService.findMessagesByThread(threadId, pageable);

			thread = this.threadService.findOne(threadId);

			if (this.actorService.getLogged()) {
				actor = this.actorService.findActorByPrincipal();
				for (int i = 0; i < messages.getContent().size(); i++)
					ownMessages.add(actor.equals(messages.getContent().get(i).getActor()));

				result.addObject("ownMessages", ownMessages);
			}

			result.addObject("thread", thread);
			result.addObject("messages", messages.getContent());
			result.addObject("page", page);
			result.addObject("pageNum", messages.getTotalPages());
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}
		return result;
	}
}
