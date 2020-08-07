package expenseTracker.app.model.transactions;

public class TransactionLimits {

    public static boolean isLimitReached(Double amount){
        return amount < 0.0 || amount > 99999.0;
    }

}
