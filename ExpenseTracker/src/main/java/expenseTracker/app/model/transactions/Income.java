package expenseTracker.app.model.transactions;

import expenseTracker.app.interfaces.Observable;
import expenseTracker.app.interfaces.StrategyUpdate;
import expenseTracker.app.interfaces.strategy.StrategyMinus;
import expenseTracker.app.interfaces.strategy.StrategyPlus;
import expenseTracker.app.interfaces.strategy.StrategyUpdateProxy;

import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
public class Income extends Transaction  {

    @Transient
    private StrategyUpdate strategy;

    public Income() {
        super();
        strategy = new StrategyUpdateProxy(new StrategyPlus());
    }

    public void executeStrategy(Double a) {
        super.setAmount(strategy.update(a));
    }

    @Override
    public boolean Confirm() {
        return false;
    }
}

