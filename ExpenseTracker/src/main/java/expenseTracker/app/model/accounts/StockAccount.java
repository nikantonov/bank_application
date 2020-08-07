package expenseTracker.app.model.accounts;

import expenseTracker.app.model.Account;
import expenseTracker.app.model.User;

import javax.persistence.Entity;

@Entity
public class StockAccount extends Account {
	private int money_in_stock_portfolio;
	private int money_in_obligations;

	public StockAccount(){

	}

	public StockAccount(User user) {
		super(user);
	}

	@Override
	public void update_min_balance_border(int new_minus_border) {
		if (new_minus_border < 1000) {
			super.setMin_balance_border(1000);
		} else
			super.setMin_balance_border(new_minus_border);
	}

	public int getMoney_in_stock_portfolio() {
		return money_in_stock_portfolio;
	}

	public void setMoney_in_stock_portfolio(int money_in_stock_portfolio) {
		this.money_in_stock_portfolio = money_in_stock_portfolio;
	}

	public int getMoney_in_obligations() {
		return money_in_obligations;
	}

	public void setMoney_in_obligations(int money_in_obligations) {
		this.money_in_obligations = money_in_obligations;
	}
	
	

}
