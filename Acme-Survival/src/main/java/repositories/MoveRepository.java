
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

	@Query("select m from Move m where m.refuge.id=?1")
	Collection<Move> findMovesByRefuge(int refugeId);

	@Query("select m from Move m where m.refuge.id=?1 and m.endDate>CURRENT_TIMESTAMP")
	Move findCurrentMoveByRefuge(int refugeId);

	@Query("select m from Move m where m.refuge.id=?1 order by m.startDate desc")
	Page<Move> findMostRecentMoveByRefuge(int refugeId, Pageable pageable);

	@Query("select m from Move m where m.refuge.id=?1")
	Page<Move> findMovesByRefuge(int refugeId, Pageable pageable);
}
