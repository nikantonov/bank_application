package expenseTracker.app.interfaces.strategy;

import expenseTracker.app.interfaces.StrategyUpdate;

public class StrategyPlus implements StrategyUpdate {


    public Double update(Double amount) {
        return amount;
    }
}