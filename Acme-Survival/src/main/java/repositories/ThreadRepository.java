
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Thread;

@Repository
public interface ThreadRepository extends JpaRepository<Thread, Integer> {

}
