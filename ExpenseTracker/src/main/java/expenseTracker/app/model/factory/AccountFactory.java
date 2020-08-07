package expenseTracker.app.model.factory;

import expenseTracker.app.facade.AccountFacade;
import expenseTracker.app.facade.exceptions.NoAccountFoundException;
import expenseTracker.app.model.Account;
import expenseTracker.app.model.User;
import expenseTracker.app.model.accounts.BankAccount;
import expenseTracker.app.model.accounts.CashAccount;
import expenseTracker.app.model.accounts.StockAccount;
import expenseTracker.app.model.transactions.Transaction;

public class AccountFactory {

    public static Account getAccount(String type, User user, AccountFacade accountFacade) throws NoAccountFoundException {

        switch(type) {
            case "Bank account":
                return accountFacade.getBankAccountByUser(user);
            case "Cash":
                return accountFacade.getCashAccountByUser(user);
            case "Stock":
                return accountFacade.getStockAccountByUser(user);
        }

        return null;

    }

    public static boolean isTransactionFromAccount(String type, Transaction t) {

        switch (type) {
            case "Bank account":
                return (t.getAccount() instanceof BankAccount);
            case "Cash":
                return (t.getAccount() instanceof CashAccount);
            case "Stock":
                return (t.getAccount() instanceof StockAccount);
        }

        return false;

        }

}
