
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Shelter;

@Repository
public interface ShelterRepository extends JpaRepository<Shelter, Integer> {

	@Query("select r from Shelter r where r.player.id=?1")
	Shelter findShelterByPlayer(int playerId);

	@Query("select r from Shelter r where r.location.id=?1 and r not in (select re from Shelter re where re.id=?2)")
	Collection<Shelter> findAllSheltersInLocationExceptPlayerShelter(int locationId, int shelterId);

}
