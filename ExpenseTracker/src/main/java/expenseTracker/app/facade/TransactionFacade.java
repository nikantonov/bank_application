package expenseTracker.app.facade;

import expenseTracker.app.interfaces.Observable;
import expenseTracker.app.interfaces.Observer;
import expenseTracker.app.model.Account;
import expenseTracker.app.model.User;
import expenseTracker.app.model.factory.AccountFactory;
import expenseTracker.app.model.transactions.CategoryEnum;
import expenseTracker.app.model.transactions.Expense;
import expenseTracker.app.model.transactions.Income;
import expenseTracker.app.model.transactions.Transaction;
import expenseTracker.app.repositories.ExpenseRepository;
import expenseTracker.app.repositories.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class TransactionFacade implements Observable {

    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    private double current_amount;

    private User u;

    private String type;

    private List<Observer> observers;

    public TransactionFacade(){
        this.observers = new ArrayList<>();
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }


    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers)
            observer.update(current_amount, u, type);
    }

    //method to add a new expense and save it in the repository
    public void addExpense(User user, Account account, java.sql.Date date, double amount, CategoryEnum category, String description) {
        Expense expense = new Expense();
        expense.setCategory(category);
        expense.setUser(user);
        expense.setAccount(account);
        expense.setDate(date);
        //expense.setAmount(amount);
        expense.executeStrategy(amount);
        expense.setDescription(description);
        u = user;
        type="expense";
        current_amount = amount;
        expenseRepository.save(expense);
        notifyObservers();
    }

    //method to add a new income and save it in the repository
    public void addIncome(User user, Account account, java.sql.Date date, double amount, CategoryEnum category, String description) {
        Income income = new Income();
        income.setCategory(category);
        income.setAccount(account);
        income.setUser(user);
        income.setDate(date);
        //income.setAmount(amount);
        income.executeStrategy(amount);
        income.setDescription(description);
        u = user;
        type = "income";
        current_amount = amount;
        incomeRepository.save(income);
        notifyObservers();
    }

    //returns all income transactions for the user
    public List<Income> getIncomeTransactionsByUser(User u) {
        return incomeRepository.findIncomeByUserAndType(u.getId());
    }

    //returns all expense transactions for the user
    public List<Expense> getExpenseTransactionsByUser(User u) {
        return expenseRepository.findByUser(u);
    }

    //returns all transactions for the user
    public List<Transaction> getTransactionsByUser(User u) {
        List<Transaction> allTransactions = new ArrayList<>();

        allTransactions.addAll(getExpenseTransactionsByUser(u));
        allTransactions.addAll(getIncomeTransactionsByUser(u));

        return allTransactions;
    }

    public List<Transaction> getExpensesByUserAndFilterPeriod(User u, String period) {

        if(period.equals("All time")){
            return new ArrayList<>(getExpenseTransactionsByUser(u));
        }

        Date date = getStartDate(period);

        return new ArrayList<>(expenseRepository.findExpenseByUserAndDateIsAfter(u.getId(), date));
    }

    public List<Transaction> getIncomeByUserAndFilterPeriod(User u, String period) {

        if(period.equals("All time")){
            return new ArrayList<>(getIncomeTransactionsByUser(u));
        }
        Date date = getStartDate(period);

        return new ArrayList<>(incomeRepository.findIncomeByUserAndDateIsAfter(u.getId(), date));
    }

    public Double getExpensePercentageByCategory(User user, CategoryEnum categoryEnum, String period) {

        Date date = getStartDate(period);
        double totalExpensesAmount = user.getCabinet().getCurrentExpensesAmount();

        if(totalExpensesAmount == 0) return totalExpensesAmount;

        double totalCatExpenses = 0.0;
        List<Expense> expenses = expenseRepository.findExpenseByUserAndCategoryAndDateIsAfter(user.getId(), date, categoryEnum);
        for(Expense e : expenses){
            totalCatExpenses += e.getAmount();
        }

        double perc = totalCatExpenses/totalExpensesAmount * (100);
        return new BigDecimal(Double.toString(perc)).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public Double getIncomePercentageByCategory(User user, CategoryEnum categoryEnum, String period) {

        Date date = getStartDate(period);
        double totalIncomeAmount = user.getCabinet().getCurrentIncomesAmount();

        if(totalIncomeAmount == 0) return totalIncomeAmount;

        double totalCatIncome = 0.0;
        List<Income> income = incomeRepository.findIncomeByUserAndCategoryAndDateIsAfter(user.getId(), date, categoryEnum);
        for(Income i : income){
            totalCatIncome += i.getAmount();
        }

        double perc = totalCatIncome/totalIncomeAmount * (100);
        return new BigDecimal(Double.toString(perc)).setScale(2, RoundingMode.HALF_UP).doubleValue();

    }

    public Date getStartDate(String period) {

        LocalDate afterDate = LocalDate.now();

        if(period.equals("Current month")){
            afterDate = LocalDate.of(afterDate.getYear(), afterDate.getMonth(), 1);
        } else if(period.equals( "Last 2 months")){
            afterDate = LocalDate.now().minusMonths(2);
        } else if(period.equals( "Last 6 months")){
            afterDate = LocalDate.now().minusMonths(6);
        }
        return Date.valueOf(afterDate);
    }

    public double getAccountSaldo(String type, User u){

        double saldo = 0;

        List<Transaction> all = getTransactionsByUser(u);

        for(Transaction t : all){
            if(AccountFactory.isTransactionFromAccount(type, t)){
                saldo += t.getAmount();
            }
        }
        return saldo;
    }
}
