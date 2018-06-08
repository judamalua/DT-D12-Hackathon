
package repositories;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Move;

@Repository
public interface MoveRepository extends JpaRepository<Move, Integer> {

	@Query("select m from Move m where m.shelter.id=?1")
	Collection<Move> findMovesByShelter(int shelterId);

	@Query("select m from Move m where m.shelter.id=?1 and m.endDate>CURRENT_TIMESTAMP")
	Move findCurrentMoveByShelter(int shelterId);

	@Query("select m from Move m where m.shelter.id=?1 order by m.startDate desc")
	Page<Move> findMostRecentMoveByShelter(int shelterId, Pageable pageable);

	@Query("select m from Move m where m.shelter.id=?1")
	Page<Move> findMovesByShelter(int shelterId, Pageable pageable);
}
