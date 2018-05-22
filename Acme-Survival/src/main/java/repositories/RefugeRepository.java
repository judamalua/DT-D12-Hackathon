
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Refuge;

@Repository
public interface RefugeRepository extends JpaRepository<Refuge, Integer> {

	@Query("select r from Refuge r where r.player.id=?1")
	Refuge findRefugeByPlayer(int playerId);

}
