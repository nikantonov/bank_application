package expenseTracker.app.model.transactions;

import expenseTracker.app.interfaces.StrategyUpdate;
import expenseTracker.app.interfaces.strategy.StrategyMinus;
import expenseTracker.app.interfaces.strategy.StrategyUpdateProxy;

import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
public class Expense extends Transaction {

    @Transient
    private StrategyUpdate strategy;

    public Expense() {
        super();
        strategy = new StrategyUpdateProxy(new StrategyMinus());
    }

    public void executeStrategy(Double a) {
        super.setAmount(strategy.update(a));
    }

    @Override
    public boolean Confirm() {
        return false;
    }
}
