
package repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Resource;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Integer> {

	@Query("select r from Resource r where r.finalMode=true")
	Page<Resource> findFinal(Pageable pageable);

	@Query("select r from Resource r where r.finalMode=false")
	Page<Resource> findNotFinal(Pageable pageable);

}
