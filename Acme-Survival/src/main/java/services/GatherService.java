
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.GatherRepository;
import domain.Character;
import domain.DesignerConfiguration;
import domain.Event;
import domain.Gather;
import domain.Location;
import domain.Notification;
import domain.Player;
import domain.Refuge;

@Service
@Transactional
public class GatherService {

	// Managed repository --------------------------------------------------

	@Autowired
	private GatherRepository				gatherRepository;

	// Supporting services --------------------------------------------------

	@Autowired
	private RefugeService					refugeService;

	@Autowired
	private CharacterService				characterService;

	@Autowired
	private ActorService					actorService;

	@Autowired
	private MoveService						moveService;

	@Autowired
	private LocationService					locationService;

	@Autowired
	private NotificationService				notificationService;

	@Autowired
	private Validator						validator;

	@Autowired
	private DesignerConfigurationService	designerConfigurationService;


	// Simple CRUD methods --------------------------------------------------

	//Recoleccion: controlador que reciba una location y redirigir a una vista que seleccione el personaje con un select (este no debe estar en una mision de recoleccion ya).
	//Cuando llegue de la mision, mostrar lo que ha ganado. Si ha ganado más de lo que puede llevar en la capacidad, tiene que decidir qué materias tirar y cuales quedarse.

	public Gather create(final int locationId) {
		Gather result;
		Date startMoment, endMoment;
		Long time;
		Player player;
		Refuge refuge;
		Location location, locationCenter;

		result = new Gather();
		location = this.locationService.findOne(locationId);
		locationCenter = this.locationService.getLocationCenter(location);
		player = (Player) this.actorService.findActorByPrincipal();
		refuge = this.refugeService.findRefugeByPlayer(player.getId());
		startMoment = new Date(System.currentTimeMillis() - 10);
		time = this.moveService.timeBetweenLocations(refuge.getLocation(), locationCenter);
		endMoment = new Date(System.currentTimeMillis() + time);

		result.setStartDate(startMoment);
		result.setEndMoment(endMoment);
		result.setLocation(location);
		result.setPlayer(player);

		return result;
	}

	public Collection<Gather> findAll() {

		Collection<Gather> result;

		Assert.notNull(this.gatherRepository);
		result = this.gatherRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public Gather findOne(final int recolectionId) {

		Gather result;

		result = this.gatherRepository.findOne(recolectionId);

		return result;

	}

	public Gather save(final Gather gather) {
		Assert.notNull(gather);
		Assert.notNull(gather.getLocation());
		Assert.notNull(gather.getCharacter());

		Gather result;
		Character character;

		// We check that there is someone logged in
		this.actorService.checkActorLogin();

		character = gather.getCharacter();

		// We check that the character that is trying to be sent into a mission is not already doing one
		Assert.isTrue(!character.getCurrentlyInGatheringMission());
		// We check that the player trying to make the mission is the same as the owner of the refuge of the character
		Assert.isTrue(gather.getPlayer().equals(character.getRefuge().getPlayer()));

		// We set to true this property, indicating that the character is now on a gathering mission
		character.setCurrentlyInGatheringMission(true);

		// And we save the character
		this.characterService.save(character);

		result = this.gatherRepository.save(gather);

		return result;

	}

	public void delete(final Gather gather) {

		assert gather != null;
		assert gather.getId() != 0;

		Assert.isTrue(this.gatherRepository.exists(gather.getId()));

		this.gatherRepository.delete(gather);

	}

	// Other business methods ----------------------------------------------------------------------------------------------------------

	/**
	 * This method returns all characters that are elegible for a Recolection Mission for the Player logged (the principal).
	 * 
	 * @return Collection<Character>
	 * @author antrodart
	 */
	public Collection<Character> findCharactersElegible() {
		Collection<Character> result, charactersInMission;
		Player player;
		Refuge refuge;

		player = (Player) this.actorService.findActorByPrincipal();
		refuge = this.refugeService.findRefugeByPlayer(player.getId());
		result = this.characterService.findCharactersByRefuge(refuge.getId());
		charactersInMission = this.findCharacterInGatheringMission();

		result.removeAll(charactersInMission);

		return result;
	}

	/**
	 * This method returns all the characters that are currently involved in a Gathering Mission.
	 * 
	 * @return Collection<Character>
	 * @author antrodart
	 */
	public Collection<Character> findCharacterInGatheringMission() {
		Collection<Character> result;
		Date now;
		Refuge refuge;
		Player player;

		player = (Player) this.actorService.findActorByPrincipal();
		refuge = this.refugeService.findRefugeByPlayer(player.getId());

		now = new Date();
		result = this.gatherRepository.findCharactersInGatheringMission(now, refuge.getId());

		return result;

	}

	public Gather findGatherNotFinishedByCharacter(final int characterId) {
		Gather result;
		Date now;

		now = new Date();
		result = this.gatherRepository.findGatherNotFinishedByCharacter(characterId, now);

		return result;
	}

	public Collection<Gather> findGatherCollectionNotFinishedByCharacter(final int characterId) {
		Collection<Gather> result;
		Date now;

		now = new Date();
		result = this.gatherRepository.findGatherCollectionNotFinishedByCharacter(characterId, now);

		return result;
	}

	/**
	 * This query returns a non finished gathering mission of a character
	 * 
	 * @param characterId
	 * @return
	 * 
	 * @author Juanmi
	 */
	public Gather findGatherFinishedByCharacter(final int characterId) {
		Gather result;

		result = this.gatherRepository.findGatherFinishedByCharacter(characterId);

		return result;
	}

	public Page<Gather> findGathersByPlayer(final int playerId, final Pageable pageable) {
		Page<Gather> result;

		Assert.notNull(pageable);

		result = this.gatherRepository.findGathersByPlayer(playerId, pageable);

		return result;

	}
	public Gather reconstruct(final Gather gather, final BindingResult binding) {
		Gather result = null;
		Date startMoment, endMoment;
		Long time;
		Player player;
		Refuge refuge;
		Location locationCenter;

		if (gather.getId() == 0) {
			result = gather;
			locationCenter = this.locationService.getLocationCenter(gather.getLocation());

			player = (Player) this.actorService.findActorByPrincipal();
			refuge = this.refugeService.findRefugeByPlayer(player.getId());
			startMoment = new Date(System.currentTimeMillis() - 10);
			time = this.moveService.timeBetweenLocations(refuge.getLocation(), locationCenter);
			endMoment = new Date(System.currentTimeMillis() + time);

			result.setStartDate(startMoment);
			result.setEndMoment(endMoment);
			result.setPlayer(player);

		}
		this.validator.validate(result, binding);

		return result;
	}

	public Collection<Gather> findGathersFinishedByPlayer(final int playerId) {
		Collection<Gather> result;
		Date now;

		now = new Date();
		result = this.gatherRepository.findGathersFinishedByPlayer(playerId, now);

		return result;
	}

	public Collection<Gather> findAllGathersOfPlayer(final int playerId) {
		Collection<Gather> result;

		result = this.gatherRepository.findAllGathersOfPlayer(playerId);

		return result;
	}

	public void updateGatheringMissions() {
		Player player;
		Gather gatherMission;
		Collection<Character> currentlyInGatheringMissionCharacters;
		Refuge refuge;
		List<Event> eventsDuringMission;
		Long missionMillis;
		Integer experience;
		Integer missionMinutes;
		DesignerConfiguration designerConfiguration;
		final Map<String, String> titleNotification = new HashMap<String, String>();
		titleNotification.put("en", "Gathering mission finished!");
		titleNotification.put("es", "¡Misión de recolección finalizada!");
		final Map<String, String> bodyNotification = new HashMap<String, String>();
		designerConfiguration = this.designerConfigurationService.findDesignerConfiguration();
		Character newCharacter;

		player = (Player) this.actorService.findActorByPrincipal();
		refuge = this.refugeService.findRefugeByPlayer(player.getId());

		Assert.notNull(player);

		currentlyInGatheringMissionCharacters = this.characterService.findCharactersCurrentlyInMission(refuge.getId());

		for (Character character : currentlyInGatheringMissionCharacters) {
			// We check if the current character has a gathering mission that has already finished
			gatherMission = this.findGatherFinishedByCharacter(character.getId());

			if (gatherMission != null) {
				Notification notification;
				eventsDuringMission = gatherMission.getLocation().getLootTable().getResultEvents(character.getLuck());
				//gatherMission.getLocation().getLootTable().getResultItems(character.getLuck(), character.getCapacity()); //TODO

				bodyNotification.put("en", "Your character \"" + character.getFullName() + "\" has returned from a gathering mission in \"" + gatherMission.getLocation().getName().get("en") + "\", you may have new objects in your refuge!");
				bodyNotification.put("es", "Tu personaje \"" + character.getFullName() + "\" ha vuelto de una misión de recolección en \"" + gatherMission.getLocation().getName().get("es") + "\", ¡puede que tengas nuevos objetos en tu refugio!");

				//this.delete(gatherMission); //TODO

				character.setCurrentlyInGatheringMission(false);

				if (eventsDuringMission.size() != 0)
					for (final Event event : eventsDuringMission) {
						character.setCurrentHealth(character.getCurrentHealth() + event.getHealth());
						if (character.getCurrentHealth() > 100)
							character.setCurrentHealth(100);

						character.setCurrentWater(character.getCurrentWater() + event.getWater());
						if (character.getCurrentWater() > 100)
							character.setCurrentWater(100);

						character.setCurrentFood(character.getCurrentFood() + event.getFood());
						if (character.getCurrentFood() > 100)
							character.setCurrentFood(100);

						if (event.getFindCharacter() && this.refugeService.getCurrentCharacterCapacity(refuge) > 0) {
							newCharacter = this.characterService.generateCharacter(refuge.getId());
							this.characterService.save(newCharacter);
						}

					}
				missionMillis = gatherMission.getEndMoment().getTime() - gatherMission.getStartDate().getTime();
				missionMinutes = (int) TimeUnit.MILLISECONDS.toMinutes(missionMillis);

				if ((missionMinutes / designerConfiguration.getFoodLostGatherFactor()) > character.getCurrentFood()) {
					character.setCurrentFood(0);
					character.setCurrentWater(0);

					character.setCurrentHealth((missionMinutes / designerConfiguration.getFoodLostGatherFactor()) - character.getCurrentFood() - (missionMinutes / designerConfiguration.getWaterLostGatherFactor()) - character.getCurrentWater());
				} else if ((missionMinutes / designerConfiguration.getFoodLostGatherFactor()) < character.getCurrentFood() && (missionMinutes / designerConfiguration.getWaterLostGatherFactor()) > character.getCurrentWater()) {
					character.setCurrentFood(character.getCurrentFood() - (missionMinutes / designerConfiguration.getFoodLostGatherFactor()));
					character.setCurrentWater(0);

					character.setCurrentHealth((missionMinutes / designerConfiguration.getWaterLostGatherFactor()) - character.getCurrentWater());
				} else if ((missionMinutes / designerConfiguration.getFoodLostGatherFactor()) < character.getCurrentFood() && (missionMinutes / designerConfiguration.getWaterLostGatherFactor()) < character.getCurrentWater()) {
					character.setCurrentFood(character.getCurrentFood() - (missionMinutes / designerConfiguration.getFoodLostGatherFactor()));
					character.setCurrentWater(character.getCurrentWater() - (missionMinutes / designerConfiguration.getWaterLostGatherFactor()));
				}

				experience = character.getExperience() + (missionMinutes * designerConfiguration.getExperiencePerMinute());
				character.setExperience(experience);

				character = this.characterService.save(character);

				if (character.getCurrentHealth() < 0)
					this.characterService.characterRIP(character);

				notification = this.notificationService.create();
				notification.setTitle(titleNotification);
				notification.setBody(bodyNotification);
				notification.setMoment(new Date(System.currentTimeMillis() - 1));
				notification.setPlayer(player);
				notification.setMission(gatherMission);
				if (eventsDuringMission.size() != 0)
					notification.setEvents(eventsDuringMission);
				else
					notification.setEvents(new ArrayList<Event>());
				this.notificationService.save(notification);
			}

		}
	}
}
