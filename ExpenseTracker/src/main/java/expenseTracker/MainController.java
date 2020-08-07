package expenseTracker;

import expenseTracker.app.facade.AccountFacade;
import expenseTracker.app.facade.TransactionFacade;
import expenseTracker.app.facade.UserFacade;
import expenseTracker.app.facade.exceptions.NoAccountFoundException;
import expenseTracker.app.facade.exceptions.UserAlreadyExisting;
import expenseTracker.app.facade.exceptions.UserNotFoundException;
import expenseTracker.app.facade.exceptions.UserOrPasswordNotValidException;
import expenseTracker.app.model.Account;
import expenseTracker.app.model.User;
import expenseTracker.app.model.factory.AccountFactory;
import expenseTracker.app.model.transactions.CategoryEnum;
import expenseTracker.app.model.transactions.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class MainController {

	@Autowired
	private UserFacade userFacade;

	@Autowired
	private TransactionFacade transactionFacade;

	@Autowired
	private AccountFacade  accountFacade;

	private User current_user;


	/**
	 * This is a method to return the start page of our application
	 *
	 */
	@RequestMapping("/start")
	public String startShow() {
		return "StartPage";
	}

	/**
	 * This is a method to return the registration page
	 */
	@RequestMapping("/get/registration")
	public String returnRegistration () {
		return "Registration";
	}

	/**
	 * This is method to calculate the saldos and balance of the specific user
	 */
	@RequestMapping("/cabinet")
	public String cabinet(Model model) {

		Double total = current_user.getCabinet().getCurrentIncomesAmount() - current_user.getCabinet().getCurrentExpensesAmount();

		model.addAttribute("bankSaldo", getDecimalString(transactionFacade.getAccountSaldo("Bank account", current_user)));
		model.addAttribute("cashSaldo", getDecimalString(transactionFacade.getAccountSaldo("Cash", current_user)));
		model.addAttribute("stockSaldo", getDecimalString(transactionFacade.getAccountSaldo("Stock", current_user)));
        model.addAttribute("incomeBalance", getDecimalString(current_user.getCabinet().getCurrentIncomesAmount()));
        model.addAttribute("expensesBalance", getDecimalString(current_user.getCabinet().getCurrentExpensesAmount()));
        model.addAttribute("totalBalance", getDecimalString(total));

        return "Cabinet";
	}

	/**
	 * This is a method to return the page of transaction
	 */
	@RequestMapping("/transaction")
	public String addTransaction() {
		return "Transaction";
	}


	/**
	 * This is a method, which enables the specific user to add his own max limit of the expenses, not default -10000
	 */
	@RequestMapping(value = "/add/newlimit")
	public void addLimit(
			@RequestParam(value = "limit") double limit
	) {
        current_user.getCabinet().setMax_border(limit);
	}

	/**
	 * This is a boolean method, that checks is the user on the max limit border or not
	 */
	@RequestMapping(value = "/check/warnung")
	public boolean check(){
        return current_user.getCabinet().getCurrentExpensesAmount() - 100 <= current_user.getCabinet().getMax_border();
    }

	/**
	 * This is a string method, that checks is the user on the max limit border or not and return warnung
	 */
	@RequestMapping(value = "/check/warnung/string")
	public String checkString(){
		if(current_user.getCabinet().getCurrentExpensesAmount() - 100 <= current_user.getCabinet().getMax_border()){
			return "Warning!";
		} else
			return " ";
	}

	/**
	 * This is a method to add new transaction and save it in SQL
	 * @throws NoAccountFoundException
	 */
	@RequestMapping(value = "/add/transaction")
	public String addTransaction(
			@RequestParam(value = "type") String type,
			@RequestParam(value = "account") String account,
			@RequestParam(value = "category") String category,
			@RequestParam(value = "date") java.sql.Date date,
			@RequestParam(value = "amount") Double amount,
			@RequestParam(value = "description") String description,
			Model model ) throws NoAccountFoundException {

		Account current_account = AccountFactory.getAccount(account, current_user, accountFacade);
        CategoryEnum current_category = CategoryEnum.fromString(category);

		if(type.equals("income")){
            transactionFacade.addIncome(current_user, current_account, date, amount, current_category, description);
        } else if(type.equals("expense")){
            transactionFacade.addExpense(current_user, current_account, date, amount, current_category, description);
        }

		model.addAttribute("user", current_user);
		model.addAttribute("account", current_account);
		model.addAttribute("category", current_category);
		model.addAttribute("date", date);
		model.addAttribute("amount", amount);
		model.addAttribute("description", description);

        try {
            updateUser();
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }

        if (type.equals("income")){

			model.addAttribute("incomeList", transactionFacade.getIncomeByUserAndFilterPeriod(current_user, "All time"));
			model.addAttribute("incomeBalance", getDecimalString(current_user.getCabinet().getCurrentIncomesAmount()));

			return "IncomeList";
		}
		else {
			model.addAttribute("expensesList", transactionFacade.getExpensesByUserAndFilterPeriod(current_user, "All time"));
			model.addAttribute("expensesBalance", getDecimalString(current_user.getCabinet().getCurrentExpensesAmount()));

			return "ExpenseList";
		}
		
	}

	/**
	 * This is a method to update user
	 * @throws UserNotFoundException
	 */
    private void updateUser() throws UserNotFoundException {
        current_user = userFacade.updateUser(current_user.getLogin());
    }

	/**
	 * This is a method, that returns a list of all expense transactions of the specific user for specified time period to HTML
	 */
	@RequestMapping("/all/expenses")
    public String  getAllExpensesList (
            @RequestParam(value = "period") String period, Model model)  {
		List<Transaction> transactions = transactionFacade.getExpensesByUserAndFilterPeriod(current_user, period);

		model.addAttribute("expensesList", transactions);
		model.addAttribute("expensesBalance", getDecimalString(current_user.getCabinet().getCurrentExpensesAmount()));

		return "ExpenseList";
    }

	/**
	 * This is a method, that returns a list of all income transactions of the specific user to HTML
	 */
	@RequestMapping("/all/income")
    public String getAllIncomeList (
            @RequestParam(value = "period") String period, Model model)  {
        List<Transaction> transactions = transactionFacade.getIncomeByUserAndFilterPeriod(current_user, period);

        model.addAttribute("incomeList" , transactions);
		model.addAttribute("incomeBalance", getDecimalString(current_user.getCabinet().getCurrentIncomesAmount()));

		return "IncomeList";
    }

	/**
	 * This is a method to calculate percentages of the specific categories for expense diagrams
	 */
    @RequestMapping(value = "/get/expenseDiagram")
    public String getExpenseCategoriesDiagram(
            @RequestParam(value = "period") String period,
            Model model ) {
	    model.addAttribute("homePercentage", transactionFacade.getExpensePercentageByCategory(current_user, CategoryEnum.Home, period));
        model.addAttribute("foodPercentage", transactionFacade.getExpensePercentageByCategory(current_user, CategoryEnum.Food, period));
        model.addAttribute("educationPercentage", transactionFacade.getExpensePercentageByCategory(current_user, CategoryEnum.Education, period));
        model.addAttribute("transportPercentage", transactionFacade.getExpensePercentageByCategory(current_user, CategoryEnum.Transport, period));
        model.addAttribute("healthPercentage", transactionFacade.getExpensePercentageByCategory(current_user, CategoryEnum.Health, period));
        model.addAttribute("travelPercentage", transactionFacade.getExpensePercentageByCategory(current_user, CategoryEnum.Travel, period));

        return "ExpenseDiagram";
    }

	/**
	 * This is a method to calculate percentages of the specific categories for income diagrams
	 */
    @RequestMapping(value = "/get/incomeDiagram")
    public String getIncomeCategoriesDiagram(
            @RequestParam(value = "period") String period,
            Model model ) {


        model.addAttribute("salaryPercentage", transactionFacade.getIncomePercentageByCategory(current_user, CategoryEnum.Salary, period));
        model.addAttribute("dividendPercentage", transactionFacade.getIncomePercentageByCategory(current_user, CategoryEnum.Dividend, period));
        model.addAttribute("freelancePercentage", transactionFacade.getIncomePercentageByCategory(current_user, CategoryEnum.Freelance, period));
        model.addAttribute("otherPercentage", transactionFacade.getIncomePercentageByCategory(current_user, CategoryEnum.Other, period));

        return "IncomeDiagram";
    }

	/**
	 * This method is to add new user and to save it in SQL
	 */
	@RequestMapping(value = "/add/user")
	public String submit(
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "login", required = false) String login,
			@RequestParam(value = "password", required = false) String password,
			Model model) {
		User user = new User(name, login, password);
		model.addAttribute("name", user.getName());
		model.addAttribute("login", user.getLogin());
		model.addAttribute("password", user.getPassword());

		if(userFacade.proofUser(login, password)){
			return "UserExists";
		}

		try {
			userFacade.addUser(user);
			//return new ResponseEntity(HttpStatus.OK);
		}catch(Exception | UserAlreadyExisting e){
			// return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}

		return "StartPage";
	}


	/**
	 * This method is to validate login and password of a user
	 */
		@RequestMapping("/validate")
	public String proofUser(
			@RequestParam(value = "login", required = false) String login,
			@RequestParam(value = "password", required = false) String password,
			Model model
	) {

		try {
			User user = userFacade.loginUser(login, password);
			model.addAttribute("login", user.getLogin());
			model.addAttribute("name", user.getName());

			current_user = user;

            return cabinet(model);

		} catch (UserOrPasswordNotValidException e) {
			e.printStackTrace();
			return "StartPage"; //TODO in HTML: warning
		}
	}

	private String getDecimalString(Double d){
		return String.format("%.2f", d);
	}

}