
package repositories;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Player;
import domain.Shelter;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {

	@Query("select p from Player p join p.shelters r where r.id=?1")
	Collection<Player> findPlayersKnowsShelter(int shelterId);

	@Query("select r from Player p join p.shelters r where p.id=?1")
	Page<Shelter> findKnowSheltersByPlayer(int playerId, Pageable pageable);

	// Dashboard queries
	@Query("select count(p) from Player p")
	String findNumPlayers();

}
