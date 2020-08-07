package expenseTracker.app.repositories;

import expenseTracker.app.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface UserRepository  extends CrudRepository<User, Long> {
    Optional<User> findByLoginAndPassword(String login, String password);
    boolean existsByLoginAndPassword(String login, String password);
    Optional<User> findByLogin(String login);
}