package expenseTracker.app.model;

import javax.persistence.*;

@Entity(name = "cabinet")
public class Cabinet  {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;


     private double currentIncomesAmount;
     private double currentExpensesAmount;
     private int incomeCounter;
     private int expenseCounter;
     private double max_border;

    public Cabinet() {
        currentIncomesAmount = 0;
        currentExpensesAmount = 0;
        incomeCounter = 0;
        //default
        max_border = -10000;
    }



    public double getCurrentIncomesAmount() {
        return currentIncomesAmount;
    }

    public void setCurrentIncomesAmount(double currentIncomesAmount) {
        this.currentIncomesAmount = currentIncomesAmount;
    }

    public double getCurrentExpensesAmount() {
        return currentExpensesAmount;
    }

    public void setCurrentExpensesAmount(double currentExpensesAmount) {
        this.currentExpensesAmount = currentExpensesAmount;
    }


    public int getIncomeCounter() {
        return incomeCounter;
    }

    public void setIncomeCounter(int incomeCounter) {
        this.incomeCounter = incomeCounter;
    }

    public int getExpenseCounter() {
        return expenseCounter;
    }

    public void setExpenseCounter(int expenseCounter) {
        this.expenseCounter = expenseCounter;
    }

    public double getMax_border() {
        return max_border;
    }

    public void setMax_border(double max_border) {
        this.max_border = max_border;
    }

    @Override
    public String toString() {
        return "Cabinet{" +
                "id=" + id +
                ", currentIncomesAmount=" + currentIncomesAmount +
                ", currentExpensesAmount=" + currentExpensesAmount +
                ", currentTransactionsCounter=" + incomeCounter +
                '}';
    }
}
