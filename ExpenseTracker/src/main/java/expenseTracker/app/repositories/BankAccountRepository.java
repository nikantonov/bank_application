package expenseTracker.app.repositories;

import expenseTracker.app.model.accounts.BankAccount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BankAccountRepository extends CrudRepository<BankAccount, Long>{

    //custom query to sort the account type, as all accounts are saved in one table in db.
    @Query(value = "from BankAccount where dtype = 'BankAccount' and user_id = :id")
    Optional<BankAccount> findBankAccountByUserAndType(Long id);
}
