package expenseTracker.app.facade;

import expenseTracker.app.facade.exceptions.UserAlreadyExisting;
import expenseTracker.app.facade.exceptions.UserNotFoundException;
import expenseTracker.app.facade.exceptions.UserOrPasswordNotValidException;
import expenseTracker.app.model.Cabinet;
import expenseTracker.app.model.User;
import expenseTracker.app.model.accounts.BankAccount;
import expenseTracker.app.model.accounts.CashAccount;
import expenseTracker.app.model.accounts.StockAccount;
import expenseTracker.app.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserFacade {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BankAccountRepository bankRepository;

    @Autowired
    private CashAccountRepository cashRepository;

    @Autowired
    private StockAccountRepository stockRepository;


    @Autowired
    private CabinetRepository cabinetRepository;

    @Autowired
    private CabinetFacade cabinetFacade;
    @Autowired
    private TransactionFacade transactionFacade;

    //adds a new user and saves to repository
    public void addUser(User user) throws UserAlreadyExisting {

        //if user exists, throw an exception, else make a new user account.
        Optional<User> checkUser = userRepository.findByLogin(user.getLogin());
        if(checkUser.isPresent()){
            throw new UserAlreadyExisting();
        }
        Cabinet cabinet = new Cabinet();
        user.setCabinet(cabinet);
        cabinetRepository.save(cabinet);
        userRepository.save(user);

        //create accounts for user and save them in repositories
        BankAccount bank = new BankAccount(user);
        StockAccount stock = new StockAccount(user);
        CashAccount cash = new CashAccount(user);



        System.out.println(cabinet.toString());

        bankRepository.save(bank);
        stockRepository.save(stock);
        cashRepository.save(cash);
    }

    public boolean proofUser(String login, String password){
        return userRepository.existsByLoginAndPassword(login, password);
    }


    public User loginUser(String login, String password) throws UserOrPasswordNotValidException {

        transactionFacade.registerObserver(cabinetFacade);
        return userRepository.findByLoginAndPassword(login,password).orElseThrow(UserOrPasswordNotValidException::new);
    }


    public User updateUser(String username) throws UserNotFoundException {
      Optional<User> loadedUser = userRepository.findByLogin(username);
      if(loadedUser.isPresent()){
          return loadedUser.get();
      }
      throw new UserNotFoundException();
    }
}
