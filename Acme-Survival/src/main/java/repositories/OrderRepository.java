
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

	@Query("select o.player.name ,count(o) from Order o group by o.player")
	Collection<String> findNumOrdersByActor();
}
