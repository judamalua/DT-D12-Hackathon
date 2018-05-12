/*
 * RendezvousController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.designer;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ConfigurationService;
import services.DesignerService;
import services.EventService;
import controllers.AbstractController;
import domain.Actor;
import domain.Configuration;
import domain.Designer;
import domain.Event;
import domain.Manager;
import domain.Product;
import forms.ActorForm;

@Controller
@RequestMapping("/event/designer")
public class EventDesignerController extends AbstractController {

	@Autowired
	private ActorService	actorService;
	@Autowired
	private EventService	eventService;
	@Autowired
	private ConfigurationService configurationService;


	// Constructors -----------------------------------------------------------

	public EventDesignerController() {
		super();
	}

	
	@RequestMapping(value = "/list")
	public ModelAndView listEvents(@RequestParam(defaultValue = "0") final int page) {
		ModelAndView result;
		//Page<Event> events;
		Collection<Event> events;
		Actor actor;
		Pageable pageable;
		Configuration configuration;

		try {

			configuration = this.configurationService.findConfiguration();

			pageable = new PageRequest(page, configuration.getPageSize());
			result = new ModelAndView("event/list");
			actor = this.actorService.findActorByPrincipal();
			

			events = this.eventService.findAll();

			result.addObject("managerDraftModeView", true);
			result.addObject("events", events);
		//	result.addObject("page", page);
			result.addObject("requestURI", "event/designer/list.do");
		//	result.addObject("pageNum", draftModeProducts.getTotalPages());

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}

		return result;
	}
	
	// Creating ---------------------------------------------------------
	/**
	 * 
	 * Gets the form to create a new Event
	 * 
	 * @param eventId
	 * @return a ModelAndView object with a form to create a new Event
	 * @author Alejandro
	 */
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Event event;
		Designer designer;

		try {
			designer = (Designer) this.actorService.findActorByPrincipal();
			event = this.eventService.create();
			result = this.createEditModelAndView(event);


		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}

		return result;
	}
	
	
	//Edit an Event
	/**
	 * That method edits a event
	 * 
	 * @param
	 * @return ModelandView
	 * @author Alejandro
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editEvent(@RequestParam Integer eventId) {
		ModelAndView result;
		Designer designer;
		Event event;

		designer = (Designer) this.actorService.findActorByPrincipal();
		Assert.notNull(designer);
		
		
		event = eventService.findOne(eventId);
		Assert.notNull(event);

		result = this.createEditModelAndView(event);

		return result;
	}

	//Updating profile of a designer ---------------------------------------------------------------------
	/**
	 * That method update the profile of a designer.
	 * 
	 * @param save
	 * @return ModelandView
	 * @author Luis
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView updateEvent(@ModelAttribute("event") Event event, final BindingResult binding) {
		ModelAndView result;
		event = this.eventService.reconstruct(event, binding);
	
		if (binding.hasErrors())
			result = this.createEditModelAndView(event, "event.params.error");
		else
			try {
				this.eventService.save(event);
				result = new ModelAndView("redirect:/event/designer/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(event, "event.commit.error");
			}

		return result;
	}

	// Ancillary methods --------------------------------------------------

	protected ModelAndView createEditModelAndView(final Event event) {
		ModelAndView result;

		result = this.createEditModelAndView(event, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Event event, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("event/edit");
		result.addObject("message", messageCode);
		result.addObject("event", event);

		return result;

	}

}