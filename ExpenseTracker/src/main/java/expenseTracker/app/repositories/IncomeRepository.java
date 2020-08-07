package expenseTracker.app.repositories;

import expenseTracker.app.model.Account;
import expenseTracker.app.model.User;
import expenseTracker.app.model.transactions.CategoryEnum;
import expenseTracker.app.model.transactions.Income;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.List;

public interface IncomeRepository extends CrudRepository<Income, Long>{
    @Query(value = "from Income where dtype = 'Income' and user_id = :userId")
    List<Income> findIncomeByUserAndType(Long userId);

    List<Income> findByUser(User user);

    //return all income for given account type
    @Query(value = "from Income where dtype = 'Income' and user_id = :userId and account = :account")
    List<Income> findIncomeByUserAndAccount(Long userId, Account account);

    //return all income for given category type
    @Query(value = "from Income where dtype = 'Income' and user_id = :userId and category = :category")
    List<Income> findIncomeByUserAndCategory(Long userId, CategoryEnum category);

    //return all income for given time period
    @Query(value = "from Income where dtype = 'Income' and user_id = :userId and date > :date")
    List<Income> findIncomeByUserAndDateIsAfter(Long userId, Date date);

    //return all income for given time period and category
    @Query(value = "from Income where dtype = 'Income' and user_id = :userId and date > :date  and category = :category")
    List<Income> findIncomeByUserAndCategoryAndDateIsAfter(Long userId, Date date, CategoryEnum category);
}

