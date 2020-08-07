package expenseTracker.app.model;

import javax.persistence.*;

@Entity(name = "account")
public abstract class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private User user;
    private int max_balance;
    private int min_balance;
    private int min_balance_border;

    public Account () {}

	public Account(User user) {
		super();
		this.user = user;
	}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getMin_balance_border() {
        return min_balance_border;
    }

    public int getMax_balance() {
        return max_balance;
    }

    public void setMax_balance(int max_balance) {
        this.max_balance = max_balance;
    }

    public int getMin_balance() {
        return min_balance;
    }

    public void setMin_balance(int min_balance) {
        this.min_balance = min_balance;
    }

    public void setMin_balance_border(int min_balance_border) {
        this.min_balance_border = min_balance_border;
    }

    public abstract void update_min_balance_border(int new_minus_border);

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", user=" + user +
                ", max_balance=" + max_balance +
                ", min_balance=" + min_balance +
                ", min_balance_border=" + min_balance_border +
                '}';
    }
}
