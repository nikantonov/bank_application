package app.model;

import expenseTracker.app.model.accounts.BankAccount;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class BankAccountTest extends TestCase {

    public BankAccountTest( String testName )
    {
        super( testName );
    }

    public static Test suite()
    {
        return new TestSuite( BankAccountTest.class );
    }

    public void testUpdateMinBalanceBorder_ShouldBe1000()
    {
        BankAccount account = new BankAccount(null);
        account.update_min_balance_border(400);

        assertEquals(1000, account.getMin_balance_border());
    }

    public void testUpdateMinBalanceBorder_ShouldBeGivenValue()
    {
         BankAccount account = new BankAccount(null);
        account.update_min_balance_border(10000);

        assertEquals(10000, account.getMin_balance_border());
    }
    
}
