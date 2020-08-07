package expenseTracker.app.interfaces.iterator;

import expenseTracker.app.model.transactions.Transaction;

//checking if there is a next object
public interface TransactionIterator {
    boolean hasNext();
    Transaction next();
}
