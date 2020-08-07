package expenseTracker.app.facade;

import expenseTracker.app.facade.exceptions.NoAccountFoundException;
import expenseTracker.app.model.User;
import expenseTracker.app.model.accounts.BankAccount;
import expenseTracker.app.model.accounts.CashAccount;
import expenseTracker.app.model.accounts.StockAccount;
import expenseTracker.app.repositories.BankAccountRepository;
import expenseTracker.app.repositories.CashAccountRepository;
import expenseTracker.app.repositories.StockAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AccountFacade {

    @Autowired
    BankAccountRepository bankAccountRepository;

    @Autowired
    StockAccountRepository stockAccountRepository;

    @Autowired
    CashAccountRepository cashAccountRepository;

    //get methods for user accounts
    public BankAccount getBankAccountByUser(User u) throws NoAccountFoundException {

        Optional<BankAccount> bankAccount = bankAccountRepository.findBankAccountByUserAndType(u.getId());
        if(!bankAccount.isPresent())
            throw new NoAccountFoundException();

        return bankAccount.get();
    }

    public StockAccount getStockAccountByUser(User u) throws NoAccountFoundException {
        Optional<StockAccount> stockAccount = stockAccountRepository.findStockAccountByUserAndType(u.getId());
        if(!stockAccount.isPresent())
            throw new NoAccountFoundException();

        return stockAccount.get();
    }

    public CashAccount getCashAccountByUser(User u) throws NoAccountFoundException {
        Optional<CashAccount> cashAccount = cashAccountRepository.findCashAccountByUserAndType(u.getId());
        if(!cashAccount.isPresent())
            throw new NoAccountFoundException();

        return cashAccount.get();
    }

}
