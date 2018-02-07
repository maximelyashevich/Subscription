package test.com.elyashevich.subscription.connection;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.testng.AssertJUnit.assertEquals;

public class ConnectionTest{
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("resource/database");
    private static String url;
    private static String user;
    private static String passIncorrect;

    @BeforeClass
    public static void initSphereActionTest() {
        url = resourceBundle.getString("db.url");
        user = resourceBundle.getString("db.user");
        passIncorrect = "blablabla";
    }

    @Test(expectedExceptions = SQLException.class)
    public void testCreateConnection() throws SQLException {
        Connection actual = DriverManager.getConnection(url, user, passIncorrect);
        assertEquals(actual, null);
    }

    @AfterClass
    public static void clearSphere() {
        url = null;
        user = null;
        passIncorrect = null;
    }
}
