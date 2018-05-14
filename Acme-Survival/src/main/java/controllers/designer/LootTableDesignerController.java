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
import services.LootTableService;
import controllers.AbstractController;
import domain.Actor;
import domain.Configuration;
import domain.Designer;
import domain.LootTable;
import domain.Manager;
import domain.Product;
import forms.ActorForm;

@Controller
@RequestMapping("/lootTable/designer")
public class LootTableDesignerController extends AbstractController {

	@Autowired
	private ActorService	actorService;
	@Autowired
	private LootTableService	lootTableService;
	@Autowired
	private ConfigurationService configurationService;


	// Constructors -----------------------------------------------------------

	public LootTableDesignerController() {
		super();
	}

	
	@RequestMapping(value = "/list")
	public ModelAndView listLootTables(@RequestParam(defaultValue = "0") final int page) {
		ModelAndView result;
		//Page<LootTable> lootTables;
		Collection<LootTable> lootTables;
		Actor actor;
		Pageable pageable;
		Configuration configuration;

		try {

			configuration = this.configurationService.findConfiguration();

			pageable = new PageRequest(page, configuration.getPageSize());
			result = new ModelAndView("lootTable/list");
			actor = this.actorService.findActorByPrincipal();
			

			lootTables = this.lootTableService.findAll();

			result.addObject("managerDraftModeView", true);
			result.addObject("lootTables", lootTables);
		//	result.addObject("page", page);
			result.addObject("requestURI", "lootTable/designer/list.do");
		//	result.addObject("pageNum", draftModeProducts.getTotalPages());

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}

		return result;
	}
	
	// Creating ---------------------------------------------------------
	/**
	 * 
	 * Gets the form to create a new LootTable
	 * 
	 * @param lootTableId
	 * @return a ModelAndView object with a form to create a new LootTable
	 * @author Alejandro
	 */
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		LootTable lootTable;
		Designer designer;
		try {
			designer = (Designer) this.actorService.findActorByPrincipal();
			lootTable = this.lootTableService.create();
			result = this.createEditModelAndView(lootTable);


		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}

		return result;
	}
	
	
	//Edit an LootTable
	/**
	 * That method edits a lootTable
	 * 
	 * @param
	 * @return ModelandView
	 * @author Alejandro
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editLootTable(@RequestParam Integer lootTableId) {
		ModelAndView result;
		Designer designer;
		LootTable lootTable;

		designer = (Designer) this.actorService.findActorByPrincipal();
		Assert.notNull(designer);
		
		
		lootTable = lootTableService.findOne(lootTableId);
		Assert.notNull(lootTable);

		result = this.createEditModelAndView(lootTable);

		return result;
	}

	//Updating loottable ( designer ) ---------------------------------------------------------------------
	/**
	 * That method update the loottable
	 * 
	 * @param save
	 * @return ModelandView
	 * @author Alejandro
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView updateLootTable(@ModelAttribute("lootTable") LootTable lootTable, final BindingResult binding) {
		ModelAndView result;
		lootTable = this.lootTableService.reconstruct(lootTable, binding);
	
		if (binding.hasErrors())
			result = this.createEditModelAndView(lootTable, "lootTable.params.error");
		else
			try {
				this.lootTableService.save(lootTable);
				result = new ModelAndView("redirect:/lootTable/designer/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(lootTable, "lootTable.commit.error");
			}

		return result;
	}

	// Ancillary methods --------------------------------------------------

	protected ModelAndView createEditModelAndView(final LootTable lootTable) {
		ModelAndView result;

		result = this.createEditModelAndView(lootTable, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final LootTable lootTable, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("lootTable/edit");
		result.addObject("message", messageCode);
		result.addObject("lootTable", lootTable);

		return result;

	}

}
