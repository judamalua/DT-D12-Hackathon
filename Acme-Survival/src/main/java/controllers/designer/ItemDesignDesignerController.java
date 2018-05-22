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
import services.ItemDesignService;
import services.ResourceService;
import services.ToolService;
import controllers.AbstractController;
import domain.Actor;
import domain.Configuration;
import domain.Designer;
import domain.ItemDesign;
import domain.Resource;
import domain.Tool;

@Controller
@RequestMapping("/itemDesign/designer")
public class ItemDesignDesignerController extends AbstractController {

	@Autowired
	private ItemDesignService		itemDesignService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ToolService				toolService;

	@Autowired
	private ResourceService			resourceService;

	@Autowired
	private ConfigurationService	configurationService;


	// Constructors -----------------------------------------------------------

	public ItemDesignDesignerController() {
		super();
	}

	// Listing  ---------------------------------------------------------------		

	/**
	 * That method returns a model and view with the system item design list of the principal
	 * 
	 * @param page
	 * 
	 * @return ModelandView
	 * @author MJ
	 */
	@RequestMapping("/list")
	public ModelAndView list(@RequestParam(required = false, defaultValue = "0") final int page, @RequestParam final Boolean tool, @RequestParam final Boolean finalMode) {
		ModelAndView result;
		Page<Tool> tools = null;
		Page<Resource> resources = null;
		Pageable pageable;
		Configuration configuration;

		try {
			result = new ModelAndView("itemDesign/list");
			configuration = this.configurationService.findConfiguration();
			pageable = new PageRequest(page, configuration.getPageSize());

			if (tool && finalMode)
				tools = this.toolService.findFinal(pageable);
			else if (tool && !finalMode)
				tools = this.toolService.findNotFinal(pageable);
			else if (!tool && finalMode)
				resources = this.resourceService.findFinal(pageable);
			else
				resources = this.resourceService.findNotFinal(pageable);

			if (tool) {
				result.addObject("itemDesigns", tools.getContent());
				result.addObject("pageNum", tools.getTotalPages());
			} else {
				result.addObject("itemDesigns", resources.getContent());
				result.addObject("pageNum", resources.getTotalPages());
			}

			result.addObject("page", page);
			result.addObject("tool", tool);
			result.addObject("finalMode", finalMode);

			result.addObject("requestURI", "itemDesign/designer/list.do?finalMode=" + finalMode + "&tool=" + tool + "&");

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final Integer itemDesignId, @RequestParam final Boolean tool) {
		ModelAndView result;
		final ItemDesign itemDesign;

		try {
			Assert.isTrue(this.actorService.findActorByPrincipal() instanceof Designer);

			itemDesign = this.itemDesignService.findOne(itemDesignId);
			Assert.isTrue(!itemDesign.getFinalMode());

			result = this.createEditModelAndView(itemDesign);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final Boolean tool) {
		ModelAndView result;
		final Tool toolItem;
		Resource resource;
		Actor actor;

		try {
			actor = this.actorService.findActorByPrincipal();
			Assert.isTrue(actor instanceof Designer);

			if (tool) {
				toolItem = this.toolService.create();
				result = this.createEditModelAndView(toolItem);
			} else {
				resource = this.resourceService.create();
				result = this.createEditModelAndView(resource);
			}

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}
		return result;
	}

	//Updating forum ---------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@ModelAttribute("itemDesign") ItemDesign itemDesign, final BindingResult binding, @RequestParam final Boolean tool) {
		ModelAndView result;
		final ItemDesign sendedRefuge = null;
		try {
			if (tool)
				itemDesign = this.toolService.reconstruct((Tool) itemDesign, binding);
			else
				itemDesign = this.resourceService.reconstruct((Resource) itemDesign, binding);
		} catch (final Throwable oops) {//Not delete
		}
		if (binding.hasErrors())
			result = this.createEditModelAndView(sendedRefuge, "refuge.params.error");
		else
			try {
				Assert.isTrue(!itemDesign.getFinalMode());

				if (tool)
					this.toolService.save((Tool) itemDesign);
				else
					this.resourceService.save((Resource) itemDesign);

				result = new ModelAndView("redirect:/itemDesign/designer/list.do?tool=" + tool + "&");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(itemDesign, "itemDesign.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(ItemDesign itemDesign, final BindingResult binding, @RequestParam final Boolean tool) {
		ModelAndView result;

		try {
			if (tool)
				itemDesign = this.toolService.findOne(itemDesign.getId());
			else
				itemDesign = this.resourceService.findOne(itemDesign.getId());

			Assert.isTrue(!itemDesign.getFinalMode());

			if (tool)
				this.toolService.delete((Tool) itemDesign);
			else
				this.resourceService.delete((Resource) itemDesign);

			result = new ModelAndView("redirect:/itemDesign/designer/list.do");

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(itemDesign, "refuge.commit.error");
		}

		return result;
	}

	// Ancillary methods --------------------------------------------------

	protected ModelAndView createEditModelAndView(final ItemDesign itemDesign) {
		ModelAndView result;

		result = this.createEditModelAndView(itemDesign, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final ItemDesign itemDesign, final String message) {
		ModelAndView result;
		Configuration configuration;

		configuration = this.configurationService.findConfiguration();

		result = new ModelAndView("itemDesign/edit");
		result.addObject("itemDesign", itemDesign);
		result.addObject("tool", itemDesign instanceof Tool);
		result.addObject("message", message);
		result.addObject("languages", configuration.getLanguages());

		if (itemDesign instanceof Tool)
			result.addObject("requestURI", "tool/designer/edit.do");
		else
			result.addObject("requestURI", "resource/designer/edit.do");

		return result;

	}

}
