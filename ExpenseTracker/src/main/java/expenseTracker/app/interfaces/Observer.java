package expenseTracker.app.interfaces;

import expenseTracker.app.model.User;

public interface Observer {
    void update (double amount, User u, String type);
}
