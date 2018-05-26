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

import javax.validation.Valid;

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
import services.EventService;
import services.ItemDesignService;
import controllers.AbstractController;
import domain.Actor;
import domain.Configuration;
import domain.Designer;
import domain.Event;

@Controller
@RequestMapping("/event/designer")
public class EventDesignerController extends AbstractController {

	@Autowired
	private ActorService			actorService;
	@Autowired
	private EventService			eventService;
	@Autowired
	private ConfigurationService	configurationService;
	@Autowired
	private ItemDesignService itemDesignService;


	// Constructors -----------------------------------------------------------

	public EventDesignerController() {
		super();
	}

	@RequestMapping(value = "/list-final")
	public ModelAndView list(@RequestParam(defaultValue = "0") final int page) {
		ModelAndView result;
		Page<Event> eventsFinal;
		Actor actor;
		Pageable pageable;
		Configuration configuration;

		try {

			configuration = this.configurationService.findConfiguration();

			pageable = new PageRequest(page, configuration.getPageSize());
			result = new ModelAndView("event/list");
			actor = this.actorService.findActorByPrincipal();
			// Checking that the user trying to list draft mode products is a manager.
			Assert.isTrue(actor instanceof Designer);
			eventsFinal = this.eventService.findFinal(pageable);
			result.addObject("designerDraftModeView", false);
			result.addObject("events", eventsFinal.getContent());
			result.addObject("page", page);
			result.addObject("requestURI", "event/designer/list.do");
			result.addObject("pageNum", eventsFinal.getTotalPages());

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}

		return result;
	}

	@RequestMapping(value = "/list")
	public ModelAndView listDraftMode(@RequestParam(defaultValue = "0") final int page) {
		ModelAndView result;
		Page<Event> eventsNotFinal;
		Actor actor;
		Pageable pageable;
		Configuration configuration;
		try {
			configuration = this.configurationService.findConfiguration();
			pageable = new PageRequest(page, configuration.getPageSize());
			result = new ModelAndView("event/list");
			actor = this.actorService.findActorByPrincipal();
			// Checking that the user trying to list draft mode products is a manager.
			Assert.isTrue(actor instanceof Designer);
			eventsNotFinal = this.eventService.findNotFinal(pageable);
			result.addObject("designerDraftModeView", true);
			result.addObject("events", eventsNotFinal.getContent());
			result.addObject("page", page);
			result.addObject("requestURI", "event/designer/list.do");
			result.addObject("pageNum", eventsNotFinal.getTotalPages());

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
	public ModelAndView editEvent(@RequestParam final Integer eventId) {
		ModelAndView result;
		Designer designer;
		Event event;
		try {
			designer = (Designer) this.actorService.findActorByPrincipal();
			Assert.notNull(designer);

			event = this.eventService.findOne(eventId);
			Assert.notNull(event);
			Assert.isTrue(!event.getFinalMode());
			

			result = this.createEditModelAndView(event);
			
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}
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

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(event, "event.params.error");
		} else {
			try {
				this.eventService.save(event);
				result = new ModelAndView("redirect:/event/designer/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(event, "event.commit.error");
			}
		}

		return result;
	}

	/**
	 * This method deletes a Event
	 * 
	 * @param event
	 * @param binding
	 * @return a model depending on the error/success on the operation
	 * 
	 * @author Alejandro
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Event event, final BindingResult binding) {
		ModelAndView result;

		try {
			Assert.isTrue(!event.getFinalMode());
			this.eventService.delete(event);
			result = new ModelAndView("redirect:list.do");

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
		result.addObject("items", itemDesignService.findFinal());

		return result;

	}

}
