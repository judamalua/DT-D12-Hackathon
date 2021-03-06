
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
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
import domain.ItemDesign;
import domain.Location;
import domain.Notification;
import domain.Player;
import domain.Shelter;

@Service
@Transactional
public class GatherService {

	// Managed repository --------------------------------------------------

	@Autowired
	private GatherRepository				gatherRepository;

	// Supporting services --------------------------------------------------

	@Autowired
	private ShelterService					shelterService;

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
	private DesignerConfigurationService	designerConfigurationService;

	@Autowired
	private PlayerService					playerService;

	@Autowired
	private Validator						validator;


	// Simple CRUD methods --------------------------------------------------

	//Recoleccion: controlador que reciba una location y redirigir a una vista que seleccione el personaje con un select (este no debe estar en una mision de recoleccion ya).
	//Cuando llegue de la mision, mostrar lo que ha ganado. Si ha ganado m�s de lo que puede llevar en la capacidad, tiene que decidir qu� materias tirar y cuales quedarse.

	public Gather create(final int locationId) {
		Gather result;
		Date startMoment, endMoment;
		Long time;
		Player player;
		Shelter shelter;
		Location location, locationCenter;

		result = new Gather();
		location = this.locationService.findOne(locationId);
		locationCenter = this.locationService.getLocationCenter(location);
		player = (Player) this.actorService.findActorByPrincipal();
		shelter = this.shelterService.findShelterByPlayer(player.getId());
		startMoment = new Date(System.currentTimeMillis() - 10);
		time = this.moveService.timeBetweenLocations(shelter.getLocation(), locationCenter);
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
		// We check that the player trying to make the mission is the same as the owner of the shelter of the character
		Assert.isTrue(gather.getPlayer().equals(character.getShelter().getPlayer()));

		// We set to true this property, indicating that the character is now on a gathering mission
		character.setCurrentlyInGatheringMission(true);

		character.setGatherNotificated(false);

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
		Shelter shelter;

		player = (Player) this.actorService.findActorByPrincipal();
		shelter = this.shelterService.findShelterByPlayer(player.getId());
		result = this.characterService.findCharactersByShelter(shelter.getId());
		charactersInMission = this.findCharacterInGatheringMission();

		result.removeAll(charactersInMission);

		return result;
	}

	public Collection<Character> findCharactersWithoutGatheringMission(final int shelterId) {
		Collection<Character> result;

		result = this.gatherRepository.findCharactersWithoutGatheringMission(shelterId);

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
		Shelter shelter;
		Player player;

		player = (Player) this.actorService.findActorByPrincipal();
		shelter = this.shelterService.findShelterByPlayer(player.getId());

		result = this.gatherRepository.findCharactersInGatheringMission(shelter.getId());

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
		Shelter shelter;
		Location locationCenter;

		if (gather.getId() == 0) {
			result = gather;
			locationCenter = this.locationService.getLocationCenter(gather.getLocation());

			player = (Player) this.actorService.findActorByPrincipal();
			shelter = this.shelterService.findShelterByPlayer(player.getId());
			startMoment = new Date(System.currentTimeMillis() - 10);
			time = this.moveService.timeBetweenLocations(shelter.getLocation(), locationCenter);
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
		Shelter shelter, foundShelter;
		List<Event> eventsDuringMission;
		List<ItemDesign> itemsDuringMission = new ArrayList<ItemDesign>();
		Long missionMillis;
		Integer experience;
		Random randomItemIndex;
		Integer itemIndex;
		Integer missionMinutes;
		Notification notification;
		DesignerConfiguration designerConfiguration;
		final Map<String, String> titleNotification = new HashMap<String, String>();
		final Map<String, String> titleNotificationDead = new HashMap<String, String>();
		titleNotification.put("en", "Gathering mission finished!");
		titleNotification.put("es", "�Misi�n de recolecci�n finalizada!");
		titleNotificationDead.put("en", "Your character has dead!");
		titleNotificationDead.put("es", "�Tu personaje ha muerto!");
		final Map<String, String> bodyNotification = new HashMap<String, String>();
		final Map<String, String> bodyNotificationDead = new HashMap<String, String>();
		designerConfiguration = this.designerConfigurationService.findDesignerConfiguration();
		Character newCharacter;

		player = (Player) this.actorService.findActorByPrincipal();
		shelter = this.shelterService.findShelterByPlayer(player.getId());

		Assert.notNull(player);

		currentlyInGatheringMissionCharacters = this.characterService.findCharactersNotNotificatedOfGather(shelter.getId());

		for (final Character character : currentlyInGatheringMissionCharacters) {
			// We check if the current character has a gathering mission that has already finished
			gatherMission = this.findGatherFinishedByCharacter(character.getId());

			if (gatherMission != null) {
				eventsDuringMission = gatherMission.getLocation().getLootTable().getResultEvents(character.getLuck());
				itemsDuringMission = gatherMission.getLocation().getLootTable().getResultItems(character.getLuck(), character.getCapacity());

				bodyNotification.put("en", "Your character \"" + character.getFullName() + "\" has returned from a gathering mission in \"" + gatherMission.getLocation().getName().get("en") + "\", you may have new objects in your shelter!");
				bodyNotification.put("es", "Tu personaje \"" + character.getFullName() + "\" ha vuelto de una misi�n de recolecci�n en \"" + gatherMission.getLocation().getName().get("es") + "\", �puede que tengas nuevos objetos en tu refugio!");

				character.setGatherNotificated(true);
				missionMillis = gatherMission.getEndMoment().getTime() - gatherMission.getStartDate().getTime();
				missionMinutes = (int) TimeUnit.MILLISECONDS.toMinutes(missionMillis);

				if ((missionMinutes / designerConfiguration.getFoodLostGatherFactor()) > character.getCurrentFood()) {
					character.setCurrentFood(0);
					character.setCurrentWater(0);

					character.setCurrentHealth(character.getCurrentHealth() - (missionMinutes / designerConfiguration.getWaterLostGatherFactor()) - character.getCurrentWater() - (missionMinutes / designerConfiguration.getFoodLostGatherFactor())
						- character.getCurrentFood());
				} else if ((missionMinutes / designerConfiguration.getFoodLostGatherFactor()) < character.getCurrentFood() && (missionMinutes / designerConfiguration.getWaterLostGatherFactor()) > character.getCurrentWater()) {
					character.setCurrentFood(character.getCurrentFood() - (missionMinutes / designerConfiguration.getFoodLostGatherFactor()));
					character.setCurrentWater(0);

					character.setCurrentHealth(character.getCurrentHealth() - (missionMinutes / designerConfiguration.getWaterLostGatherFactor()) - character.getCurrentWater());
				} else if ((missionMinutes / designerConfiguration.getFoodLostGatherFactor()) < character.getCurrentFood() && (missionMinutes / designerConfiguration.getWaterLostGatherFactor()) < character.getCurrentWater()) {
					character.setCurrentFood(character.getCurrentFood() - (missionMinutes / designerConfiguration.getFoodLostGatherFactor()));
					character.setCurrentWater(character.getCurrentWater() - (missionMinutes / designerConfiguration.getWaterLostGatherFactor()));
				}

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

						if (event.getFindCharacter() && this.shelterService.getCurrentCharacterCapacity(shelter) > 0) {
							newCharacter = this.characterService.generateCharacter(shelter.getId());
							this.characterService.save(newCharacter);
						}

						if (event.getItemDesign() != null) {
							if (itemsDuringMission.size() == character.getCapacity()) {
								randomItemIndex = new Random();
								itemIndex = randomItemIndex.nextInt(itemsDuringMission.size());

								itemsDuringMission.remove(itemsDuringMission.get(itemIndex));
							}
							itemsDuringMission.add(event.getItemDesign());
						}

					}

				experience = character.getExperience() + (missionMinutes * designerConfiguration.getExperiencePerMinute());
				character.setExperience(experience);

				if (character.getCurrentHealth() <= 0) {
					character.setCurrentHealth(0);

					this.characterService.save(character);

					bodyNotificationDead.put("en", "Your character \"" + character.getFullName() + "\" has dead during the gathering mission in \"" + gatherMission.getLocation().getName().get("en")
						+ "\", everything that could have been gathered has disappeared!");
					bodyNotificationDead.put("es", "Tu personaje \"" + character.getFullName() + "\" ha muerto durante su misi�n de recolecci�n en \"" + gatherMission.getLocation().getName().get("es") + "\", todo lo que haya recolectado ha desaparecido!");

					notification = this.notificationService.create();
					notification.setTitle(titleNotificationDead);
					notification.setBody(bodyNotificationDead);
					notification.setMoment(new Date(System.currentTimeMillis() - 10000));
					notification.setPlayer(player);
					notification.setGather(gatherMission);
					notification.setCharacterId(character.getId());
					if (eventsDuringMission.size() != 0)
						notification.setEvents(eventsDuringMission);
					this.notificationService.save(notification);

				} else {
					this.characterService.save(character);
					notification = this.notificationService.create();

					foundShelter = this.findShelterInGatheringMission(gatherMission.getLocation(), shelter, missionMinutes);
					if (foundShelter != null) {
						player.getShelters().add(foundShelter);
						this.playerService.save(player);
						notification.setFoundShelter(true);
					}

					notification.setTitle(titleNotification);
					notification.setBody(bodyNotification);
					notification.setMoment(new Date(System.currentTimeMillis() - 10000));
					notification.setPlayer(player);
					notification.setGather(gatherMission);
					notification.setCharacterId(character.getId());
					if (eventsDuringMission.size() != 0)
						notification.setEvents(eventsDuringMission);
					if (itemsDuringMission.size() != 0)
						notification.setItemDesigns(itemsDuringMission);
					this.notificationService.save(notification);
				}
			}
		}
	}
	public Shelter findShelterInGatheringMission(final Location location, final Shelter shelter, final Integer missionMinutes) {
		Shelter result = null;
		List<Shelter> sheltersInLocation;
		Double probability;
		Random random;
		Double randomDouble;
		Integer randomShelterIndex, augmentProbability, augmentProbabilityDesigner;

		augmentProbabilityDesigner = this.designerConfigurationService.findDesignerConfiguration().getShelterFindingMinuteAugmentProbability();

		augmentProbability = missionMinutes / augmentProbabilityDesigner;

		if (augmentProbability == 0 && missionMinutes != 0)
			augmentProbability = 1;

		random = new Random();

		randomDouble = random.nextDouble() / 0.9;

		sheltersInLocation = (List<Shelter>) this.shelterService.findAllSheltersInLocationExceptPlayerShelter(location.getId(), shelter.getId());

		probability = this.designerConfigurationService.findDesignerConfiguration().getShelterFindingProbability();

		if (((probability * augmentProbability) > randomDouble) && sheltersInLocation.size() != 0) {
			if (sheltersInLocation.size() == 1)
				randomShelterIndex = 0;
			else
				randomShelterIndex = (random.nextInt(sheltersInLocation.size())) / (sheltersInLocation.size() - 1);
			result = sheltersInLocation.get(randomShelterIndex);
		}

		return result;
	}
}
