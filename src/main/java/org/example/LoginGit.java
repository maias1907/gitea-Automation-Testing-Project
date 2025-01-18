package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;




public class LoginGit extends LoadableComponent<LoginGit> {
    private WebDriver driver;
    private final String baseURL=" https://6c37-2a06-c701-78d3-4f00-adba-8056-f580-4086.ngrok-free.app ";

    private By userNameFieldBy = By.id("user_name");
    public By passwordFieldBy = By.cssSelector("input[name=\"password\"]");
    private By signinButtonBy = By.cssSelector("button.ui.primary.button.tw-w-full");

    public LoginGit(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);

    }
    @Override
    protected void load() {
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(baseURL+"/user/login");
        System.out.println(driver.getCurrentUrl());

    }
    public  HomePageGit loginAsValidUser(String userName, String password) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));


        // Wait until the username field is present and visible
        WebElement userNameField = wait.until(d -> d.findElement(userNameFieldBy));
        userNameField.sendKeys(userName);
        // Wait until the password field is present and visible
        WebElement passwordField = wait.until(d -> d.findElement(passwordFieldBy));
        passwordField.sendKeys(password);
        // Wait until the sign-in button is clickable
        WebElement signinButton = wait.until(d -> d.findElement(signinButtonBy));

        // Interact with the fields


        signinButton.click();

        return new HomePageGit(driver);
    }
  /*  public LoginGit loginWithInvalidCredentials(String userName, String password){
        driver.findElement(userNameFieldBy).clear();
        driver.findElement(userNameFieldBy).sendKeys(userName);
        driver.findElement(passwordFieldBy).clear();
        driver.findElement(passwordFieldBy).sendKeys(password);
        driver.findElement(signinButtonBy).click();
        return  new LoginGit(driver) ;
    }
    public boolean isLoginFailed(){

        return driver.getTitle().contains("Sign In");
    }*/



    @Override
    protected void isLoaded() throws Error {
    }
}

