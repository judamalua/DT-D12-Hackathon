
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Forum;

@Repository
public interface ForumRepository extends JpaRepository<Forum, Integer> {

	@Query("select f from Forum f where f.forum.id=?1")
	Collection<Forum> findSubForums(int forumId);
}
