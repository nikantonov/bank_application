package expenseTracker.app.facade;

import expenseTracker.app.facade.exceptions.UserNotFoundException;
import expenseTracker.app.interfaces.Observer;
import expenseTracker.app.model.Cabinet;
import expenseTracker.app.model.User;
import expenseTracker.app.model.transactions.Expense;
import expenseTracker.app.model.transactions.Income;
import expenseTracker.app.repositories.CabinetRepository;
import expenseTracker.app.repositories.ExpenseRepository;
import expenseTracker.app.repositories.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public class CabinetFacade implements Observer {
    @Autowired
    AccountFacade accountFacade;

    @Autowired
    TransactionFacade transactionFacade;

    @Autowired
    IncomeRepository incomeRepository;

    @Autowired
    ExpenseRepository expenseRepository;

    @Autowired
    CabinetRepository cabinetRepository;



    public double getIncomesByUser(User u) throws UserNotFoundException {
        return u.getCabinet().getCurrentIncomesAmount();
    }

    public double getExpensesByUser(User u) throws UserNotFoundException {
      return u.getCabinet().getCurrentExpensesAmount();
    }

    @Override
    public void update(double amount, User u, String type) {
        System.out.println(amount);
        Optional<Cabinet> opt_cabinet= cabinetRepository.findById(u.getId());
        Cabinet cabinet = opt_cabinet.get();
        int count = 0;
        if(type.equalsIgnoreCase("expense")){
            List<Expense> allExpenses = expenseRepository.findByUser(u);
            double amount_exp = 0.0;
            for (Expense expense : allExpenses){
                    count++;
                    amount_exp = amount_exp + expense.getAmount();
            }
            cabinet.setExpenseCounter(count);
            cabinet.setCurrentExpensesAmount(amount_exp);
            cabinetRepository.save(cabinet);
        }

        if(type.equalsIgnoreCase("income")){
            List<Income> allIncomes = incomeRepository.findByUser(u);
            double amount_inc = 0.0;
            for (Income income : allIncomes){
                    count++;
                    amount_inc = amount_inc +income.getAmount();
                    System.out.println(amount_inc);
        }

            cabinet.setIncomeCounter(count);
            cabinet.setCurrentIncomesAmount(amount_inc);
            cabinetRepository.save(cabinet);
            System.out.println(cabinet.toString());

          }
    }
}
