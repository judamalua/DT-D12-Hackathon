
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Move;

@Repository
public interface MoveRepository extends JpaRepository<Move, Integer> {

}
