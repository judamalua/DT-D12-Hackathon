
package controllers.player;

import java.util.Collection;

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
import services.AttackService;
import services.ConfigurationService;
import services.GatherService;
import services.InventoryService;
import services.ItemService;
import services.RefugeService;
import services.MoveService;
import services.RefugeService;
import controllers.AbstractController;
import domain.Character;
import domain.Configuration;
import domain.Gather;
import domain.Inventory;
import domain.Item;
import domain.ItemDesign;
import domain.Move;
import domain.Player;
import domain.Refuge;
import domain.Resource;
import domain.Tool;
import domain.Refuge;

@Controller
@RequestMapping("/gather/player")
public class GatherPlayerController extends AbstractController {

	@Autowired
	private GatherService			gatherService;

	@Autowired
	private AttackService			attackService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private RefugeService			refugeService;

	@Autowired
	private MoveService				moveService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ItemService				itemService;

	@Autowired
	private RefugeService			refugeService;

	@Autowired
	private InventoryService		inventoryService;


	// Constructors -----------------------------------------------------------

	public GatherPlayerController() {
		super();
	}

	// Create -----------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(final int locationId) {
		ModelAndView result;
		Gather gather;
		Move move;
		Player player;
		Refuge refuge;
		try {
			player = (Player) this.actorService.findActorByPrincipal();
			refuge = this.refugeService.findRefugeByPlayer(player.getId());
			move = this.moveService.findCurrentMoveByRefuge(refuge.getId());

			gather = this.gatherService.create(locationId);
			result = this.createEditModelAndView(gather);
			result.addObject("isAttacking", this.attackService.playerAlreadyAttacking(player.getId()));
			if (move == null)
				result = result.addObject("isMoving", true);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("gather") Gather gather, final BindingResult binding) {
		ModelAndView result;
		Move move;
		Player player;
		Refuge refuge;

		try {
			gather = this.gatherService.reconstruct(gather, binding);
		} catch (final Throwable oops) {
		}
		if (binding.hasErrors())
			result = this.createEditModelAndView(gather, "gather.params.error");
		else
			try {
				player = (Player) this.actorService.findActorByPrincipal();
				refuge = this.refugeService.findRefugeByPlayer(player.getId());
				move = this.moveService.findCurrentMoveByRefuge(refuge.getId());
				Assert.isTrue(move == null);

				gather = this.gatherService.save(gather);
				result = new ModelAndView("redirect:/map/player/display.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:/misc/403");
			}
		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(defaultValue = "0") final int page) {
		ModelAndView result;
		Page<Gather> gathers;
		Configuration configuration;
		Pageable pageable;
		Player player;

		try {
			configuration = this.configurationService.findConfiguration();
			pageable = new PageRequest(page, configuration.getPageSize());

			result = new ModelAndView("gather/list");

			player = (Player) this.actorService.findActorByPrincipal();

			gathers = this.gatherService.findGathersByPlayer(player.getId(), pageable);

			result.addObject("gathers", gathers.getContent());
			result.addObject("page", page);
			result.addObject("pageNum", gathers.getTotalPages());
			result.addObject("requestUri", "gather/player/list.do?");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}

		return result;
	}

	/**
	 * That method returns a model and view with the characters list of a player
	 * 
	 * @param page
	 * 
	 * @return ModelandView
	 * @author Luis
	 */
	@RequestMapping("/foundItems")
	public ModelAndView arsenal(@RequestParam(required = false, defaultValue = "0") final int page, @RequestParam(required = true) final int gatherId) {
		ModelAndView result;
		Collection<ItemDesign> items;
		Refuge refuge;
		final Gather gather;
		Configuration configuration;
		final Collection<Item> tools;
		final Collection<Resource> resources;
		Item item;
		Player player;
		Inventory inventory;
		boolean fullCapacityRefuge = false;

		try {

			result = new ModelAndView("gather/foundItems");
			configuration = this.configurationService.findConfiguration();
			gather = this.gatherService.findOne(gatherId);
			player = (Player) this.actorService.findActorByPrincipal();
			refuge = this.refugeService.findRefugeByPlayer(player.getId());
			inventory = this.inventoryService.findInventoryByRefuge(refuge.getId());
			items = gather.getLocation().getLootTable().getResultItems(gather.getCharacter().getLuck(), gather.getCharacter().getCapacity());

			for (final ItemDesign itemDesign : items)
				if (itemDesign instanceof Tool) {
					item = this.itemService.create();
					item.setEquipped(false);
					item.setTool((Tool) itemDesign);
					final Item saveditem = this.itemService.save(item);
					tools.add(item);
				} else {
					final Double currentWaterCapacity = inventory.getWaterCapacity() - inventory.getWater();
					final Double currentFoodCapacity = inventory.getFoodCapacity() - inventory.getFood();
					final Double currentWoodCapacity = inventory.getWoodCapacity() - inventory.getWood();
					final Double currentMetalCapacity = inventory.getMetalCapacity() - inventory.getMetal();
					final Resource resource = (Resource) itemDesign;
					if (currentWaterCapacity - resource.getWater() > 0)
						inventory.setWater(inventory.getWater() + resource.getWater());
					if (currentWaterCapacity - resource.getWater() < 0)
						inventory.setWater(inventory.getWaterCapacity());
					if (currentWoodCapacity - resource.getWood() > 0)
						inventory.setWood(inventory.getWood() + resource.getWood());
					if (currentWoodCapacity - resource.getWood() < 0)
						inventory.setWood(inventory.getWoodCapacity());
					if (currentFoodCapacity - resource.getFood() > 0)
						inventory.setFood(inventory.getFood() + resource.getFood());
					if (currentFoodCapacity - resource.getFood() < 0)
						inventory.setFood(inventory.getFoodCapacity());
					if (currentMetalCapacity - resource.getMetal() > 0)
						inventory.setMetal(inventory.getMetal() + resource.getMetal());
					if (currentMetalCapacity - resource.getMetal() < 0)
						inventory.setMetal(inventory.getMetalCapacity());

					resources.add(resource);

					this.inventoryService.save(inventory);

				}
			if (this.refugeService.getCurrentCapacity(refuge) < tools.size())
				fullCapacityRefuge = true;

			result.addObject("items", tools);
			result.addObject("resources", resources);
			result.addObject("fullCapacityRefuge", fullCapacityRefuge);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}
		return result;
	}
	// Ancilliary methods  -----------------------------------------------------------------
	private ModelAndView createEditModelAndView(final Gather gather) {
		ModelAndView result;

		result = this.createEditModelAndView(gather, null);

		return result;
	}

	private ModelAndView createEditModelAndView(final Gather gather, final String message) {
		ModelAndView result;
		final Collection<Character> elegibleCharacters;

		// Here we update gathering missions
		this.gatherService.updateGatheringMissions();

		elegibleCharacters = this.gatherService.findCharactersElegible();
		result = new ModelAndView("gather/edit");

		result.addObject("gather", gather);
		result.addObject("message", message);
		result.addObject("characters", elegibleCharacters);

		return result;
	}

}
