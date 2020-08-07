package expenseTracker.app.model.accounts;

import expenseTracker.app.model.Account;
import expenseTracker.app.model.User;

import javax.persistence.Entity;

@Entity
public class BankAccount extends Account {
	public BankAccount() {

	}

	public BankAccount(User user) {
		super(user);
	}

	@Override
	public void update_min_balance_border(int new_minus_border) {
		if (new_minus_border < 1000) {
			super.setMin_balance_border(1000);
		} else
			super.setMin_balance_border(new_minus_border);
	}

}
