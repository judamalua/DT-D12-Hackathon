
package repositories;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Forum;

@Repository
public interface ForumRepository extends JpaRepository<Forum, Integer> {

	@Query("select f from Forum f where f.forum.id=?1")
	Collection<Forum> findSubForums(int forumId);

	@Query("select f from Forum f where f.forum.id=?1 and f.staff=?2 and f.support=?3")
	Page<Forum> findSubForums(int forumId, Boolean staff, Boolean support, Pageable pageable);

	@Query("select f from Forum f where f.staff=?1 and f.support=?2 and (f.forum=null or f.forum='')")
	Page<Forum> findForums(Boolean staff, Boolean support, Pageable pageable);

	@Query("select f from Forum f where f.staff=?1 and f.owner.id=?2")
	Collection<Forum> findForums(Boolean staff, Integer ownerId);

}
