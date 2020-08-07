package expenseTracker.app.model.decorator;

import expenseTracker.app.model.transactions.Income;

public class SalaryIncomeDecorator extends Income {

    @Override
    public boolean Confirm() {
        // monthly fixed income from salary
        //last day of the month
        return super.Confirm();

    }

}
