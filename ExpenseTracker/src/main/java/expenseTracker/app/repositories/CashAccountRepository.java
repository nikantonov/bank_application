package expenseTracker.app.repositories;

import expenseTracker.app.model.accounts.CashAccount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CashAccountRepository extends CrudRepository<CashAccount, Long>{

    //custom query to sort the account type, as they are saved in one table.
    @Query(value = "from CashAccount where dtype = 'CashAccount'  and user_id = :id")
    Optional<CashAccount> findCashAccountByUserAndType(Long id);
}
