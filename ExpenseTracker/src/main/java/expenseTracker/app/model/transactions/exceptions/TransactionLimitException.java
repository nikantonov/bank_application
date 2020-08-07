package expenseTracker.app.model.transactions.exceptions;

public class TransactionLimitException extends RuntimeException {

    public TransactionLimitException(String message) {
        super(message);
    }
}
