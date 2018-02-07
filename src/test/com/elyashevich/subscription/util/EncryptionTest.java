package test.com.elyashevich.subscription.util;

import com.elyashevich.subscription.util.Encryption;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

public class EncryptionTest{
    private static String somePassword;

    @BeforeClass
    public static void initSphereActionTest() {
        somePassword = "HelloWorld";
    }

    @Test
    public void testSurfaceArea() {
        String expected = "80e0fb9a1a872aab68b4a189c2570";
        String actual = Encryption.encryptPassword(somePassword);
        assertEquals(actual, expected);
    }

    @AfterClass
    public static void clearSphere() {
        somePassword = null;
    }
}
