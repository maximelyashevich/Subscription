package test.com.elyashevich.subscription.validator;

import com.elyashevich.subscription.validator.UserValidator;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;

import static org.testng.AssertJUnit.assertEquals;

public class UserValidatorTest {
    private static String loginCorrect;
    private static String loginIncorrect;
    private static String passwordCorrect;
    private static String postIndexCorrect;
    private static LocalDate localDateCorrect;
    private static LocalDate localDateIncorrect;
    private static UserValidator validator;

    @BeforeClass
    public static void initSphereActionTest() {
        loginCorrect = "helloWorld";
        loginIncorrect = "oops";
        passwordCorrect = "heyheyhey";
        postIndexCorrect = "123456";
        localDateCorrect = LocalDate.now().minusDays(1);
        localDateIncorrect = LocalDate.now().plusDays(1);
        validator = new UserValidator();
    }

    @Test
    public void isLoginCorrect() {
        boolean actual = validator.isLoginCorrect(loginCorrect);
        assertEquals(actual, true);
    }

    @Test
    public void isLoginIncorrect() {
        boolean actual = validator.isLoginCorrect(loginIncorrect);
        assertEquals(actual, false);
    }

    @Test
    public void isPasswordCorrect() {
        boolean actual = validator.isPasswordCorrect(passwordCorrect);
        assertEquals(actual, true);
    }

    @Test
    public void isPasswordIncorrect() {
        boolean actual = validator.isPasswordCorrect(null);
        assertEquals(actual, false);
    }

    @Test
    public void isPostIndexCorrect() {
        boolean actual = validator.isPostIndexCorrect(postIndexCorrect);
        assertEquals(actual, true);
    }

    @Test
    public void isPostIndexIncorrect() {
        boolean actual = validator.isPostIndexCorrect(null);
        assertEquals(actual, false);
    }

    @Test
    public void isDateCorrect() {
        boolean actual = validator.isDateCorrect(localDateCorrect);
        assertEquals(actual, true);
    }

    @Test
    public void isDateIncorrect() {
        boolean actual = validator.isDateCorrect(localDateIncorrect);
        assertEquals(actual, false);
    }

    @AfterClass
    public static void clearSphere() {
        loginCorrect = null;
        loginIncorrect = null;
        passwordCorrect = null;
        postIndexCorrect = null;
        localDateCorrect = null;
        localDateIncorrect = null;
        validator = null;
    }
}
