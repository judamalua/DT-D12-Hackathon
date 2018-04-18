
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

	@Query("select f from Forum f where f.forum.id=?1 and f.staff=?2")
	Page<Forum> findSubForums(int forumId, Boolean staff, Pageable pageable);

	@Query("select f from Forum f where f.staff=?1 and (f.forum=null or f.forum='')")
	Page<Forum> findForums(Boolean staff, Pageable pageable);

	@Query("select f from Forum f where f.staff=?1 and (f.forum=null or f.forum='')")
	Collection<Forum> findForums(Boolean staff);

}
