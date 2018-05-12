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
import services.DesignerService;
import services.ItemDesignService;
import services.RoomService;
import controllers.AbstractController;
import domain.Actor;
import domain.Configuration;
import domain.ItemDesign;
import domain.Player;
import domain.Room;

@Controller
@RequestMapping("/itemDesign/designer")
public class ItemDesignDesignerController extends AbstractController {

	@Autowired
	private ItemDesignService		itemDesignService;

	@Autowired
	private DesignerService			designerService;

	@Autowired
	private RoomService				roomService;

	@Autowired
	private ActorService			actorService;

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
	public ModelAndView list(@RequestParam(required = false, defaultValue = "0") final int page) {
		ModelAndView result;
		Page<ItemDesign> itemDesigns;
		Pageable pageable;
		final ItemDesign refuge;
		Configuration configuration;
		Player actor;

		try {
			result = new ModelAndView("refuge/list");
			configuration = this.configurationService.findConfiguration();
			pageable = new PageRequest(page, configuration.getPageSize());
			actor = (Player) this.actorService.findActorByPrincipal();

			itemDesigns = this.itemDesignService.findAll(pageable);

			result.addObject("itemDesigns", itemDesigns.getContent());
			result.addObject("page", page);
			result.addObject("pageNum", itemDesigns.getTotalPages());
			result.addObject("requestURI", "itemDesign/designer/list.do?");

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}
		return result;
	}
	/**
	 * That method returns a model and view with the refuge display
	 * 
	 * @param pageRoom
	 * @param refugeId
	 *            (Optional)
	 * 
	 * @return ModelandView
	 * @author MJ
	 */
	@RequestMapping("/display")
	public ModelAndView display(@RequestParam(required = false) final Integer refugeId, @RequestParam(required = false, defaultValue = "0") final int pageRoom) {
		ModelAndView result;
		final ItemDesign refuge, ownRefuge;
		Configuration configuration;
		Actor actor;
		Pageable pageable;
		Page<Room> rooms;
		boolean owner = false, knowRefuge = false;

		try {
			configuration = this.configurationService.findConfiguration();
			pageable = new PageRequest(pageRoom, configuration.getPageSize());

			result = new ModelAndView("refuge/display");

			actor = this.actorService.findActorByPrincipal();

			Assert.isTrue((actor instanceof Player));

			ownRefuge = this.refugeService.findRefugeByPlayer(actor.getId());

			if (refugeId == null) {
				refuge = ownRefuge;
				Assert.notNull(refuge, "Not have refuge");
			} else
				refuge = this.refugeService.findOne(refugeId);

			knowRefuge = ((Player) actor).getRefuges().contains(refuge);
			owner = ownRefuge != null && ownRefuge.equals(refuge);
			rooms = this.roomService.findRoomsByRefuge(refuge.getId(), pageable);

			//Assert.isTrue(refuge.equals(ownRefuge) || player.getRefuges().contains(refuge));

			result.addObject("refuge", refuge);
			result.addObject("rooms", rooms.getContent());
			result.addObject("pageRoom", pageRoom);
			result.addObject("pageNumRoom", rooms.getTotalPages());
			result.addObject("knowRefuge", knowRefuge);
			result.addObject("owner", owner);

		} catch (final Throwable oops) {
			if (oops.getMessage().contains("Not have refuge"))
				result = new ModelAndView("redirect:/refuge/player/create.do");
			else
				result = new ModelAndView("redirect:/misc/403");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		ItemDesign ownRefuge;
		Player actor;

		try {
			actor = (Player) this.actorService.findActorByPrincipal();
			ownRefuge = this.refugeService.findRefugeByPlayer(actor.getId());
			Assert.notNull(ownRefuge, "Not have refuge");
			result = this.createEditModelAndView(ownRefuge);

		} catch (final Throwable oops) {
			if (oops.getMessage().contains("Not have refuge"))
				result = new ModelAndView("redirect:/refuge/player/create.do");
			else
				result = new ModelAndView("redirect:/misc/403");
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final ItemDesign refuge;
		Actor actor;

		try {
			actor = this.actorService.findActorByPrincipal();
			Assert.isTrue(actor instanceof Player);

			refuge = this.refugeService.create();

			result = this.createEditModelAndView(refuge);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:misc/403");
		}
		return result;
	}

	//Updating forum ---------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@ModelAttribute("refuge") ItemDesign refuge, final BindingResult binding) {
		ModelAndView result;
		ItemDesign sendedRefuge = null;
		Player player;
		ItemDesign ownRefuge;
		try {
			sendedRefuge = refuge;
			refuge = this.refugeService.reconstruct(refuge, binding);
		} catch (final Throwable oops) {//Not delete
		}
		if (binding.hasErrors())
			result = this.createEditModelAndView(sendedRefuge, "refuge.params.error");
		else
			try {
				player = (Player) this.actorService.findActorByPrincipal();
				ownRefuge = this.refugeService.findRefugeByPlayer(player.getId());
				if (refuge.getId() != 0)
					Assert.isTrue(ownRefuge.equals(refuge));
				else
					Assert.isTrue(ownRefuge == null, "Not have refuge");
				this.refugeService.save(refuge);
				result = new ModelAndView("redirect:/refuge/player/display.do");

			} catch (final Throwable oops) {
				if (oops.getMessage().contains("Not have refuge"))
					result = new ModelAndView("redirect:/refuge/player/create.do");
				else
					result = this.createEditModelAndView(refuge, "refuge.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final ItemDesign refuge, final BindingResult binding) {
		ModelAndView result;
		Player player;
		ItemDesign ownRefuge;

		try {
			player = (Player) this.actorService.findActorByPrincipal();
			ownRefuge = this.refugeService.findRefugeByPlayer(player.getId());
			Assert.notNull(ownRefuge);
			Assert.isTrue(ownRefuge.equals(refuge));
			this.refugeService.delete(ownRefuge);

			result = new ModelAndView("redirect:/refuge/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(refuge, "refuge.commit.error");
		}

		return result;
	}

	// Ancillary methods --------------------------------------------------

	protected ModelAndView createEditModelAndView(final ItemDesign refuge) {
		ModelAndView result;

		result = this.createEditModelAndView(refuge, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final ItemDesign refuge, final String message) {
		ModelAndView result;

		result = new ModelAndView("refuge/edit");
		result.addObject("refuge", refuge);
		result.addObject("message", message);

		return result;

	}

}
