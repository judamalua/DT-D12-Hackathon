
package controllers.player;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AttackService;
import services.CharacterService;
import services.ConfigurationService;
import services.GatherService;
import services.InventoryService;
import services.ItemService;
import services.MoveService;
import services.NotificationService;
import services.ShelterService;
import controllers.AbstractController;
import domain.Character;
import domain.Configuration;
import domain.Gather;
import domain.Inventory;
import domain.Item;
import domain.ItemDesign;
import domain.Move;
import domain.Notification;
import domain.Player;
import domain.Resource;
import domain.Shelter;
import domain.Tool;

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
	private ShelterService			shelterService;

	@Autowired
	private MoveService				moveService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ItemService				itemService;

	@Autowired
	private InventoryService		inventoryService;

	@Autowired
	private NotificationService		notificationService;

	@Autowired
	private CharacterService		characterService;


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
		Shelter shelter;
		try {
			player = (Player) this.actorService.findActorByPrincipal();
			shelter = this.shelterService.findShelterByPlayer(player.getId());
			move = this.moveService.findCurrentMoveByShelter(shelter.getId());

			gather = this.gatherService.create(locationId);
			result = this.createEditModelAndView(gather);
			result.addObject("isAttacking", this.attackService.playerAlreadyAttacking(player.getId()));
			if (move != null) {
				result = result.addObject("isMoving", true);
			}

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
		Shelter shelter;
		Character character;

		try {
			gather = this.gatherService.reconstruct(gather, binding);
		} catch (final Throwable oops) {
		}
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(gather, "gather.params.error");
		} else {
			try {
				player = (Player) this.actorService.findActorByPrincipal();
				shelter = this.shelterService.findShelterByPlayer(player.getId());
				move = this.moveService.findCurrentMoveByShelter(shelter.getId());
				Assert.isTrue(move == null);
				character = this.characterService.updateStats(gather.getCharacter());
				gather.setCharacter(character);

				gather = this.gatherService.save(gather);
				result = new ModelAndView("redirect:/map/player/display.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:/misc/403");
			}
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
	public ModelAndView arsenal(@RequestParam(required = false, defaultValue = "0") final int page, @RequestParam(required = true) final Integer notificationId, @RequestParam(defaultValue = "false", required = false) final Boolean failedSelection) {
		ModelAndView result;
		Collection<ItemDesign> items;
		Shelter shelter;
		Character character;
		final Collection<Item> tools = new ArrayList<Item>();
		final Collection<Resource> resources = new ArrayList<Resource>();
		Item item;
		Player player;
		Inventory inventory;
		int currentCapacityShelter;
		int totalTools;
		final Notification notification;

		try {
			player = (Player) this.actorService.findActorByPrincipal();

			notification = this.notificationService.findOne(notificationId);
			Assert.isTrue(notification.getPlayer().equals(player));
			character = this.characterService.findOne(notification.getCharacterId());
			if (character.getCurrentHealth() == 0) {
				this.notificationService.delete(notification);
				this.gatherService.delete(notification.getGather());
				this.characterService.characterRIP(character);
				result = new ModelAndView("redirect:/shelter/player/display.do");

			} else {
				result = new ModelAndView("gather/foundItems");

				shelter = this.shelterService.findShelterByPlayer(player.getId());
				inventory = this.inventoryService.findInventoryByShelter(shelter.getId());
				items = notification.getItemDesigns();

				Assert.isTrue(items.size() <= character.getCapacity());

				for (final ItemDesign itemDesign : items) {
					if (itemDesign instanceof Tool) {
						item = this.itemService.create();
						item.setEquipped(false);
						item.setTool((Tool) itemDesign);
						final Item saveditem = this.itemService.save(item);
						tools.add(saveditem);
					} else {
						final Resource resource = (Resource) itemDesign;

						resources.add(resource);

						this.inventoryService.save(inventory);

					}
				}
				currentCapacityShelter = this.shelterService.getCurrentCapacity(shelter);
				totalTools = tools.size();

				result.addObject("failedSelection", failedSelection);
				result.addObject("items", tools);
				result.addObject("resources", resources);
				result.addObject("currentCapacityShelter", currentCapacityShelter);
				result.addObject("totalTools", totalTools);
				result.addObject("notificationId", notificationId);
			}
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}
		return result;
	}

	@RequestMapping(value = "/finishMission", method = RequestMethod.POST)
	public @ResponseBody
	String finishMission(@ModelAttribute("selected") String itemsSelected, @ModelAttribute("notSelected") String itemsNotSelected, @ModelAttribute("notificationId") final Integer notificationId) {
		String result;
		final Notification notification;
		final Player player;
		Gather gather;
		final Shelter shelter;
		final int currentCapacity;
		final List<Integer> itemsToSave = new ArrayList<Integer>();
		final List<Integer> itemsToRemove = new ArrayList<Integer>();
		String[] itemsIds;
		String[] notItemsIds;
		Collection<ItemDesign> resources;
		final Inventory inventory;

		itemsSelected = itemsSelected.substring(1, itemsSelected.length() - 1);
		itemsNotSelected = itemsNotSelected.substring(1, itemsNotSelected.length() - 1);

		itemsIds = itemsSelected.split(",");
		notItemsIds = itemsNotSelected.split(",");

		try {

			player = (Player) this.actorService.findActorByPrincipal();
			shelter = this.shelterService.findShelterByPlayer(player.getId());
			currentCapacity = this.shelterService.getCurrentCapacity(shelter);
			notification = this.notificationService.findOne(notificationId);
			Assert.isTrue(notification.getPlayer().equals(player));
			resources = notification.getItemDesigns();
			inventory = this.inventoryService.findInventoryByShelter(shelter.getId());
			gather = notification.getGather();
			result = "notification/player/list.do";

			for (final String al : itemsIds) {
				if (!al.equals("") && !(al.equals("\"\""))) {
					final String s = al.substring(1, al.length() - 1);
					final Integer i = new Integer(s);
					itemsToSave.add(i);
				}
			}
			for (final String al : notItemsIds) {
				if (!al.equals("") && !(al.equals("\"\""))) {
					final String s = al.substring(1, al.length() - 1);
					final Integer i = new Integer(s);
					itemsToRemove.add(i);
				}
			}

			if (itemsToSave.size() <= currentCapacity) {

				for (final ItemDesign i : resources) {
					final Double currentWaterCapacity = inventory.getWaterCapacity() - inventory.getWater();
					final Double currentFoodCapacity = inventory.getFoodCapacity() - inventory.getFood();
					final Double currentWoodCapacity = inventory.getWoodCapacity() - inventory.getWood();
					final Double currentMetalCapacity = inventory.getMetalCapacity() - inventory.getMetal();
					if (i instanceof Resource) {
						final Resource resource = (Resource) i;
						if (currentWaterCapacity - resource.getWater() > 0) {
							inventory.setWater(inventory.getWater() + resource.getWater());
						}
						if (currentWaterCapacity - resource.getWater() < 0) {
							inventory.setWater(inventory.getWaterCapacity());
						}
						if (currentWoodCapacity - resource.getWood() > 0) {
							inventory.setWood(inventory.getWood() + resource.getWood());
						}
						if (currentWoodCapacity - resource.getWood() < 0) {
							inventory.setWood(inventory.getWoodCapacity());
						}
						if (currentFoodCapacity - resource.getFood() > 0) {
							inventory.setFood(inventory.getFood() + resource.getFood());
						}
						if (currentFoodCapacity - resource.getFood() < 0) {
							inventory.setFood(inventory.getFoodCapacity());
						}
						if (currentMetalCapacity - resource.getMetal() > 0) {
							inventory.setMetal(inventory.getMetal() + resource.getMetal());
						}
						if (currentMetalCapacity - resource.getMetal() < 0) {
							inventory.setMetal(inventory.getMetalCapacity());
						}
					}

				}
				for (int i = 0; i < itemsToSave.size(); i++) {
					final Item item = this.itemService.findOne(new Integer(itemsToSave.get(i)));
					item.setShelter(shelter);
					this.itemService.save(item);
				}
				for (int i = 0; i < itemsToRemove.size(); i++) {
					final Item item = this.itemService.findOne(new Integer(itemsToRemove.get(i)));
					this.itemService.delete(item);
				}
				final Character character = gather.getCharacter();
				character.setCurrentlyInGatheringMission(false);
				this.characterService.save(character);
				this.notificationService.delete(notification);
				this.gatherService.delete(gather);

			} else {
				result = "gather/player/foundItems.do?notificationId=" + notification.getId() + "&failedSelection=true";
			}

		} catch (final Throwable oops) {
			result = "misc/403";
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
		Player player;
		Shelter shelter;

		player = (Player) this.actorService.findActorByPrincipal();
		shelter = this.shelterService.findShelterByPlayer(player.getId());

		// Here we update gathering missions
		this.gatherService.updateGatheringMissions();

		elegibleCharacters = this.gatherService.findCharactersWithoutGatheringMission(shelter.getId());
		result = new ModelAndView("gather/edit");

		result.addObject("gather", gather);
		result.addObject("message", message);
		result.addObject("characters", elegibleCharacters);

		return result;
	}

}
