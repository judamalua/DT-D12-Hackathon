
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Forum;

@Repository
public interface ForumRepository extends JpaRepository<Forum, Integer> {

}
