package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.PlayerRepository;
import domain.Player;

@Service
@Transactional
public class PlayerService {

	// Managed repository --------------------------------------------------

	@Autowired
	private PlayerRepository	playerRepository;


	// Supporting services --------------------------------------------------

	// Simple CRUD methods --------------------------------------------------

	public Player create() {
		Player result;

		result = new Player();

		return result;
	}

	public Collection<Player> findAll() {

		Collection<Player> result;

		Assert.notNull(this.playerRepository);
		result = this.playerRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public Player findOne(final int playerId) {

		Player result;

		result = this.playerRepository.findOne(playerId);

		return result;

	}

	public Player save(final Player player) {

		assert player != null;

		Player result;

		result = this.playerRepository.save(player);

		return result;

	}

	public void delete(final Player player) {

		assert player != null;
		assert player.getId() != 0;

		Assert.isTrue(this.playerRepository.exists(player.getId()));

		this.playerRepository.delete(player);

	}
}

