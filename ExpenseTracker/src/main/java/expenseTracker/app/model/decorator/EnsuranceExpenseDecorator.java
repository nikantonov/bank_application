package expenseTracker.app.model.decorator;

import expenseTracker.app.model.transactions.Expense;

public class EnsuranceExpenseDecorator extends Expense {

    @Override
    public boolean Confirm(){

        // Monthly debit for insurance
        //last day of the month

       return super.Confirm();
    }
}
