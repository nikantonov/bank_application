package expenseTracker.app.model.transactions;

import expenseTracker.app.model.Account;
import expenseTracker.app.model.User;

import javax.persistence.*;

@Entity
public abstract class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // @ManyToOne links 2 tables based on foreign key column
    // solves error: MappingException: Could not determine type for: (...) at table: transaction, for columns: [org.hibernate.mapping.Column(user)]

    @ManyToOne
    private User user;
    @ManyToOne
    private Account account;

    private CategoryEnum category;

    private java.sql.Date date;
    private double amount;
    private String description;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public java.sql.Date getDate() {
        return date;
    }

    public void setDate(java.sql.Date date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public String getDecimalAmountString() {
        return String.format("%.2f",amount);
    }

    public void setAmount(double amount) { this.amount = amount; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public void setCategory(CategoryEnum category) {
        this.category = category;
    }

    public abstract boolean Confirm ();
}
