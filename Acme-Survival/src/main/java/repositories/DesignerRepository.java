
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Designer;

@Repository
public interface DesignerRepository extends JpaRepository<Designer, Integer> {

}
