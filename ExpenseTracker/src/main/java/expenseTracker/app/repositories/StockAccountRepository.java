package expenseTracker.app.repositories;

import expenseTracker.app.model.accounts.StockAccount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StockAccountRepository extends CrudRepository<StockAccount, Long>{
    @Query(value = "from StockAccount where dtype = 'StockAccount' and user_id = :id") //custom query to sort the account type, as they are saved in one table.
    Optional<StockAccount> findStockAccountByUserAndType(Long id);
}
