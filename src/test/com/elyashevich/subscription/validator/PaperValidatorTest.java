package test.com.elyashevich.subscription.validator;

import com.elyashevich.subscription.validator.PaperValidator;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

public class PaperValidatorTest{
    private static String someDataCorrect;
    private static String moneyCorrect;
    private static String moneyIncorrect;
    private static String paperTypeCorrect;
    private static String paperTypeIncorrect;
    private static String restrictionCorrect;
    private static String restrictionIncorrect;
    private static PaperValidator validator;

    @BeforeClass
    public static void initSphereActionTest() {
        someDataCorrect = "hello";
        moneyCorrect = "0.25485";
        moneyIncorrect = "-12.852$";
        paperTypeCorrect = "BoOk";
        paperTypeIncorrect = "maGazin";
        restrictionCorrect = "25";
        restrictionIncorrect = "25+";
        validator = new PaperValidator();
    }

    @Test
    public void isDataCorrect() {
        boolean actual = validator.isPaperDataCorrect(someDataCorrect);
        assertEquals(actual, true);
    }

    @Test
    public void isDataIncorrect() {
        boolean actual = validator.isPaperDataCorrect(null);
        assertEquals(actual, false);
    }

    @Test
    public void isMoneyCorrect() {
        boolean actual = validator.isMoneyCorrect(moneyCorrect);
        assertEquals(actual, true);
    }

    @Test
    public void isMoneyIncorrect() {
        boolean actual = validator.isMoneyCorrect(moneyIncorrect);
        assertEquals(actual, false);
    }

    @Test
    public void isPaperTypeCorrect() {
        boolean actual = validator.isPaperTypeCorrect(paperTypeCorrect);
        assertEquals(actual, true);
    }

    @Test
    public void isPaperTypeIncorrect() {
        boolean actual = validator.isMoneyCorrect(paperTypeIncorrect);
        assertEquals(actual, false);
    }

    @Test
    public void isRestrictionCorrect() {
        boolean actual = validator.isRestrictionCorrect(restrictionCorrect);
        assertEquals(actual, true);
    }

    @Test
    public void isRestrictionIncorrect() {
        boolean actual = validator.isRestrictionCorrect(restrictionIncorrect);
        assertEquals(actual, false);
    }

    @AfterClass
    public static void clearSphere() {
        someDataCorrect = null;
        moneyCorrect = null;
        moneyIncorrect = null;
        paperTypeCorrect = null;
        paperTypeIncorrect = null;
        restrictionCorrect = null;
        restrictionIncorrect = null;
        validator = null;
    }
}
