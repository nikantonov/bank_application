package expenseTracker.app.repositories;

import expenseTracker.app.model.Cabinet;
import org.springframework.data.repository.CrudRepository;

public interface CabinetRepository extends CrudRepository<Cabinet, Long> {

    //Optional<Cabinet> findCabinetByUser(User user);
}
