package app.model;

import expenseTracker.app.facade.UserFacade;
import expenseTracker.app.facade.exceptions.UserAlreadyExisting;
import expenseTracker.app.model.User;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {  UserFacade.class})
@EnableConfigurationProperties
public class UserTest extends TestCase {
    @MockBean
    private UserFacade userFacade;

    public UserTest() {

    }



    @Test
    public void testAddNewUser_shouldAddAndNewUserAndTryToLoad() {
        User testUser = new User();
        testUser.setName("Max Muster");
        testUser.setLogin("TestUser");
        testUser.setPassword("password");

        try {
            userFacade.addUser(testUser);
        } catch (UserAlreadyExisting userAlreadyExisting) {
            userAlreadyExisting.printStackTrace();
        }

        User savedUser = null;

        assertEquals("TestUser", savedUser.getLogin());
    }

}