package selenium;

import org.example.DriverFactory;
import org.example.HomePageGit;
import org.example.LoginGit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import java.net.MalformedURLException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginGitTest {
    WebDriver driver;
    private LoginGit login;


    @BeforeEach
    public void setUp() throws MalformedURLException {
        driver= DriverFactory.getDriver();
        driver.manage().window().maximize();
        login = new LoginGit(driver).get();
    }
    @Test
    public void testInvalidLogin() {
        LoginGit page = login.loginWithInvalidCredentials("wrong", "wrong");
        assertTrue(page.isLoginFailed(), "Login should fail with invalid credentials");
    }

    @Test
    public void testValidLogin() {
        HomePageGit home = login.loginAsValidUser("maias", "Maias123");
        assertTrue(home.isLoggedInSuccessfully(), "Login should be successful with valid credentials");
    }

    @Test
    public void testEmptyUsername() {
        LoginGit page = login.loginWithInvalidCredentials("", "Maias123");
        assertTrue(page.isLoginFailed(), "Login should fail when the username is empty");
    }

    @Test
    public void testEmptyPassword() {
        LoginGit page = login.loginWithInvalidCredentials("maias", "");
        assertTrue(page.isLoginFailed(), "Login should fail when the password is empty");
    }

    @Test
    public void testEmptyUsernameAndPassword() {
        LoginGit page = login.loginWithInvalidCredentials("", "");
        assertTrue(page.isLoginFailed(), "Login should fail when both username and password are empty");
    }
    @AfterEach
    public void tearDown() {
        driver.quit();
    }

}
