
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.DesignerConfiguration;

@Repository
public interface DesignerConfigurationRepository extends JpaRepository<DesignerConfiguration, Integer> {

}
