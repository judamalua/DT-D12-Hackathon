
package repositories;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Event;
import domain.ItemDesign;
import domain.ProbabilityItem;

@Repository
public interface ItemDesignRepository extends JpaRepository<ItemDesign, Integer> {

	@Query("select e from Event e where (e.itemDesign!=null or e.itemDesign!='') and e.itemDesign.id=?1")
	Collection<Event> findEventsByItemDesign(int itemDesignId);

	@Query("select pi from ProbabilityItem pi where pi.itemDesign.id=?1")
	Collection<ProbabilityItem> findProbabilityItemsByItemDesign(int itemDesignId);

	@Query("select i from ItemDesign i where i.finalMode=false")
	Page<ItemDesign> findNotFinal(Pageable pageable);

	@Query("select i from ItemDesign i where i.finalMode=true")
	Page<ItemDesign> findFinal(Pageable pageable);

	@Query("select i from ItemDesign i where i.finalMode=false")
	Collection<ItemDesign> findNotFinal();

	@Query("select i from ItemDesign i where i.finalMode=true")
	Collection<ItemDesign> findFinal();

	//Dashboard queries

	@Query("select count(i) from ItemDesign i where i.finalMode=true")
	String findNumItemDesigns();

}
