package expenseTracker.app.repositories;

import expenseTracker.app.model.Account;
import expenseTracker.app.model.User;
import expenseTracker.app.model.transactions.CategoryEnum;
import expenseTracker.app.model.transactions.Expense;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.List;

public interface ExpenseRepository extends CrudRepository<Expense, Long>{

    //returns a list of all expenses ??
    @Query(value = "from Expense where dtype = 'Expense' and user_Id = :userId")
    List<Expense> findExpenseByUserAndType(Long userId);

    //returns a list of all expenses of the user
    List<Expense> findByUser(User u);

    //return all expenses for given account type
    @Query(value = "from Expense where dtype = 'Expense' and user_Id = :userId and account = :account")
    List<Expense> findExpenseByUserAndAccount(Long userId, Account account);

    //return all expenses for given category type
    @Query(value = "from Expense where dtype = 'Expense' and user_Id = :userId and category = :category")
    List<Expense> findExpenseByUserAndCategory(Long userId, CategoryEnum category);

    //return all expenses for given time period
    @Query(value = "from Expense where dtype = 'Expense' and user_Id = :userId and date > :date")
    List<Expense> findExpenseByUserAndDateIsAfter(Long userId, Date date);

    //return all expenses for given time period and category
    @Query(value = "from Expense where dtype = 'Expense' and user_id = :userId and date > :date  and category = :category")
    List<Expense> findExpenseByUserAndCategoryAndDateIsAfter(Long userId, Date date, CategoryEnum category);
}
