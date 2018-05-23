
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Refuge;

@Repository
public interface RefugeRepository extends JpaRepository<Refuge, Integer> {

	@Query("select r from Refuge r where r.player.id=?1")
	Refuge findRefugeByPlayer(int playerId);

	@Query("select r from Refuge r where r.location.id=?1 and r not in (select re from Refuge re where re.id=?2)")
	Collection<Refuge> findAllRefugesInLocationExceptPlayerRefuge(int locationId, int refugeId);

}
