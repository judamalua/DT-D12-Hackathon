
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Character;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Integer> {

}
