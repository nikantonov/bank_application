package expenseTracker.app.interfaces.strategy;

import expenseTracker.app.interfaces.StrategyUpdate;
import expenseTracker.app.model.transactions.TransactionLimits;
import expenseTracker.app.model.transactions.exceptions.TransactionLimitException;

public class StrategyUpdateProxy implements StrategyUpdate {

    private StrategyUpdate strategyUpdate;

    public StrategyUpdateProxy(StrategyUpdate strategyUpdate) {
        this.strategyUpdate = strategyUpdate;
    }

    @Override
    public Double update(Double amount) {
        if(TransactionLimits.isLimitReached(amount)){
            throw new TransactionLimitException("Transaction limit for one transaction is reached. amount: " + amount);
        }
        return strategyUpdate.update(amount);
    }
}
