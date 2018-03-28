
package repositories;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

	@Query("select m from Message m where m.thread.id=?1")
	Collection<Message> findMessagesByThread(int threadId);

	@Query("select m from Message m where m.thread.id=?1")
	Page<Message> findMessagesByThread(int threadId, Pageable pageable);
}
