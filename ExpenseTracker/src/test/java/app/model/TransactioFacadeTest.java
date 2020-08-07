package app.model;

import expenseTracker.app.facade.TransactionFacade;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.sql.Date;
import java.time.LocalDate;

public class TransactioFacadeTest extends TestCase {

        public TransactioFacadeTest( String testName )
        {
            super( testName );
        }

        public static Test suite()
        {
            return new TestSuite( TransactioFacadeTest.class );
        }

        public void testGetTimePeriod_shouldReturn6MonthsBack() {
            TransactionFacade transactionFacade = new TransactionFacade();
            Date subtracted = transactionFacade.getStartDate("Last 6 months");
            LocalDate today = LocalDate.now();

            System.out.println("Today: " + today.toString() + ", 6 months ago: " + subtracted.toString());

            LocalDate subtractedCheck2 = today.minusMonths(6);

            assertEquals(subtractedCheck2.toString(), subtracted.toString());
        }


}
