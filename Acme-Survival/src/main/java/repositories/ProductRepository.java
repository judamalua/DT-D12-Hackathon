
package repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

	@Query("select p from Product p where p.finalMode=true order by p.price")
	Page<Product> getFinalModeProducts(Pageable page);

	@Query("select p from Product p where p.finalMode = false order by p.price")
	Page<Product> getDraftModeProducts(Pageable page);

	@Query("select p from Product p where p.discontinued = false order by p.price")
	Page<Product> getCataloguedProducts(Pageable page);

}
