
package repositories;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Thread;

@Repository
public interface ThreadRepository extends JpaRepository<Thread, Integer> {

	@Query("select t from Thread t where t.forum.id=?1")
	Collection<Thread> findThreadsByForum(int forumId);

	@Query("select t from Thread t where t.forum.id=?1")
	Page<Thread> findThreadsByForum(int forumId, Pageable pageable);

	@Query("select cast(t.actor.name as string) ,cast(count(t) as string) from Thread t group by t.actor")
	Collection<Collection<String>> findNumThreadsByActor();
}
