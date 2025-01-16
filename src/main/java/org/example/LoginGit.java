package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginGit extends LoadableComponent<LoginGit> {
    private WebDriver driver;


    private By userNameFieldBy = By.id("user_name");
    public By passwordFieldBy = By.cssSelector("input[name=\"password\"]");
    private By signinButtonBy = By.cssSelector("button.ui.primary.button.tw-w-full");

    public LoginGit(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);




    }
    public  HomePageGit loginAsValidUser(String userName, String password) {
        driver.findElement(userNameFieldBy).sendKeys(userName);
        driver.findElement(passwordFieldBy).sendKeys(password);
        driver.findElement(signinButtonBy).click();

        return new HomePageGit(driver);
    }
    public LoginGit loginWithInvalidCredentials(String userName, String password){
        driver.findElement(userNameFieldBy).clear();
        driver.findElement(userNameFieldBy).sendKeys(userName);
        driver.findElement(passwordFieldBy).clear();
        driver.findElement(passwordFieldBy).sendKeys(password);
        driver.findElement(signinButtonBy).click();
        return  new LoginGit(driver) ;
    }
    public boolean isLoginFailed(){
       
        return driver.getTitle().contains("Sign In");
    }

    @Override
    protected void load() {
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("http://localhost:3000/user/login");
        System.out.println(driver.getCurrentUrl());

    }

    @Override
    protected void isLoaded() throws Error {
        assertTrue(driver.getTitle().contains("Sign In"));

    }
}

