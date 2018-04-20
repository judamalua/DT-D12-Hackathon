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
import services.MessageService;
import controllers.AbstractController;
import domain.Message;

@Controller
@RequestMapping("/message/actor")
public class MessageActorController extends AbstractController {

	@Autowired
	private ActorService	actorService;

	@Autowired
	private MessageService	messageService;


	// Constructors -----------------------------------------------------------

	public MessageActorController() {
		super();
	}

	//Edit an forum
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int messageId) {
		ModelAndView result;
		Message message;

		this.actorService.checkActorLogin();

		message = this.messageService.findOne(messageId);
		result = this.createEditModelAndView(message);

		result.addObject("thread", message.getThread());

		return result;
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final Message message;

		this.actorService.checkActorLogin();
		message = this.messageService.create();

		result = this.createEditModelAndView(message);

		return result;
	}

	//Updating forum ---------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView updateUser(@ModelAttribute("messageForm") Message messageForm, final BindingResult binding) {
		ModelAndView result;
		Message savedMessage, sendedMessage = null;
		try {
			sendedMessage = messageForm;
			messageForm = this.messageService.reconstruct(messageForm, binding);
		} catch (final Throwable oops) {//Not delete
		}
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(sendedMessage, "message.params.error");
			result.addObject("thread", messageForm.getThread());
		} else
			try {
				savedMessage = this.messageService.save(messageForm);
				result = new ModelAndView("redirect:/message/list.do?threadId=" + savedMessage.getThread().getId());

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(messageForm, "message.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Message messageForm, final BindingResult binding) {
		ModelAndView result;

		try {
			messageForm = this.messageService.reconstruct(messageForm, binding);
		} catch (final Throwable oops) {
		}
		try {
			this.messageService.delete(messageForm);

			result = new ModelAndView("redirect:/message/list.do?threadId=" + messageForm.getThread().getId());

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(messageForm, "forum.commit.error");
		}

		return result;
	}

	// Ancillary methods --------------------------------------------------

	protected ModelAndView createEditModelAndView(final Message message) {
		ModelAndView result;

		result = this.createEditModelAndView(message, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Message message, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("message/edit");
		result.addObject("message", messageCode);
		result.addObject("messageForm", message);

		return result;

	}

}
