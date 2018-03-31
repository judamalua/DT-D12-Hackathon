
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {

	@Query("select p from Player p join p.refuges r where r.id=?1")
	Collection<Player> findPlayersKnowsRefuge(int refugeId);
}
