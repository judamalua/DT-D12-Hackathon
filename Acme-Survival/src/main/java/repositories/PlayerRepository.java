
package repositories;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Player;
import domain.Refuge;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {

	@Query("select p from Player p join p.refuges r where r.id=?1")
	Collection<Player> findPlayersKnowsRefuge(int refugeId);

	@Query("select r from Player p join p.refuges r where p.id=?1")
	Page<Refuge> findKnowRefugesByPlayer(int playerId, Pageable pageable);
}
