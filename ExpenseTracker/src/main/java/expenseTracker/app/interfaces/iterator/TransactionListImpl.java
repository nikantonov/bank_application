package expenseTracker.app.interfaces.iterator;

import expenseTracker.app.model.transactions.Transaction;

import java.util.List;

public class TransactionListImpl implements TransactionList {

    private List<Transaction> transactions;

    public TransactionListImpl(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public TransactionIterator getIterator() {
        return new TransactionIteratorImpl();
    }
// calculate if there is another object in the list
    private class TransactionIteratorImpl implements TransactionIterator{
        private int i = 0;

        @Override
        public boolean hasNext() {
            return i < transactions.size();
        }

        @Override
        public Transaction next() {
            if(!hasNext()){
                return null;
            }
            Transaction transaction = transactions.get(i);
            i++;
            return transaction;
        }
    }
}
