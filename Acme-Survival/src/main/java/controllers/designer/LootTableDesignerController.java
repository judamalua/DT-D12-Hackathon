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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
import services.ItemDesignService;
import services.LootTableService;
import services.ProbabilityEventService;
import services.ProbabilityItemService;
import controllers.AbstractController;
import domain.Actor;
import domain.Configuration;
import domain.Designer;
import domain.Event;
import domain.ItemDesign;
import domain.LootTable;
import domain.Manager;
import domain.ProbabilityEvent;
import domain.ProbabilityItem;
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
	@Autowired
	private EventService eventService;
	@Autowired
	private ItemDesignService itemDesignService;
	
	@Autowired
	private ProbabilityEventService probabilityEventService;
	@Autowired
	private ProbabilityItemService probabilityItemService;


	// Constructors -----------------------------------------------------------

	public LootTableDesignerController() {
		super();
	}

	
	@RequestMapping(value = "/list")
	public ModelAndView list(@RequestParam(defaultValue = "0") final int page) {
		ModelAndView result;
		Page<LootTable> lootTables;
		Actor actor;
		Pageable pageable;
		Configuration configuration;
		try {
			configuration = this.configurationService.findConfiguration();
			pageable = new PageRequest(page, configuration.getPageSize());
			result = new ModelAndView("lootTable/list");
			actor = this.actorService.findActorByPrincipal();
			// Checking that the user trying to list draft mode products is a manager.
			Assert.isTrue(actor instanceof Designer);
			lootTables = this.lootTableService.findAll(pageable);
			result.addObject("lootTables", lootTables.getContent());
			result.addObject("page", page);
			result.addObject("requestURI", "lootTable/designer/list.do");
			result.addObject("pageNum", lootTables.getTotalPages());

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
	public ModelAndView updateLootTable(@RequestParam Integer tableId, @RequestParam Map<String,String> allRequestParams, ModelMap model) {
		LootTable lootTable;
		if (tableId != 0){
		 lootTable = lootTableService.findOne(tableId);
		}else{
			lootTable = lootTableService.create();
		}
		
		
		BindingResult binding = null;
		ModelAndView result;
		try {
		if (lootTable != null){
		lootTable = this.lootTableService.reconstruct(lootTable, binding);
		allRequestParams.remove("save");
		List<ProbabilityItem> probItems = new ArrayList<ProbabilityItem>();
		List<ProbabilityEvent> probEvents = new ArrayList<ProbabilityEvent>();
		lootTable.setName(allRequestParams.get("name"));
		lootTable.setFinalMode(Boolean.valueOf(allRequestParams.get("finalMode")));
		for (Entry<String, String> entry : allRequestParams.entrySet()){
			if (entry.getKey().contains("item")){
				ItemDesign item = itemDesignService.findOne(Integer.parseInt(entry.getKey().replaceFirst("item", "")));
				ProbabilityItem pItem = new ProbabilityItem();
				pItem.setItemDesign(item);
				pItem.setValue(Double.parseDouble(entry.getValue()));
				probItems.add(pItem);
			}
			else if (entry.getKey().contains("event"))
			{
				Event event = eventService.findOne(Integer.parseInt(entry.getKey().replaceFirst("event", "")));
				ProbabilityEvent pEvent = new ProbabilityEvent();
				pEvent.setEvent(event);
				pEvent.setValue(Double.parseDouble(entry.getValue()));
				probEvents.add(pEvent);
			}
		}
		Collection<ProbabilityItem> delItems = new HashSet<ProbabilityItem>(lootTable.getProbabilityItems());
		Collection<ProbabilityEvent> delEvents = new HashSet<ProbabilityEvent>(lootTable.getProbabilityEvents());
		lootTable.getProbabilityEvents().clear();
		lootTable.getProbabilityItems().clear();
		lootTable.setProbabilityEvents(probEvents);
		lootTable.setProbabilityItems(probItems);
		probabilityEventService.saveAll(probEvents);
		probabilityItemService.saveAll(probItems);
		probabilityItemService.deleteAll(delItems);
		probabilityEventService.deleteAll(delEvents);
		this.lootTableService.save(lootTable);
//		probabilityEventService.saveAll(delEvents);
//		probabilityItemService.saveAll(delItems);
		
		


		result = new ModelAndView("redirect:/lootTable/designer/list.do");
		
		}
		else{
			result = this.createEditModelAndView(lootTable, "lootTable.params.error");
		}
				
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(lootTable, "lootTable.commit.error");
			}

		return result;
	}
	
	/**
	 * This method deletes a LootTable
	 * 
	 * @param lootTable
	 * @param binding
	 * @return a model depending on the error/success on the operation
	 * 
	 * @author Alejandro
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@RequestParam Integer lootTableId) {
		ModelAndView result;
		LootTable lootTable = lootTableService.findOne(lootTableId);
		try {
			Assert.notNull(lootTable);
			Assert.isTrue(!lootTable.getFinalMode());
			this.lootTableService.delete(lootTable);
			result = new ModelAndView("redirect:list.do");

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
		result.addObject("events", eventService.findFinal());
		result.addObject("items", itemDesignService.findFinal());

		return result;

	}

}
