package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import java.time.Duration;

public class ProjectPage extends LoadableComponent<ProjectPage> {
    private WebDriver driver;
    private final String baseURL="https://668b-2a06-c701-78d3-4f00-f943-2c4c-636d-e810.ngrok-free.app";

    // CSS selector for the "New Project" button
    private By newProjectButtonBy = By.linkText("New Project");

    public ProjectPage(WebDriver driver) {
        this.driver = driver;
        // This call sets the WebElement fields.
        PageFactory.initElements(driver, this);

    }

    // Method to click the "New Project" button and go to the New Project page
    public NewProjectPage goToNewProjectPage() {
        // Find the "New Project" button
        WebElement newProjectButton = driver.findElement(newProjectButtonBy);

        // Click the "New Project" button
        newProjectButton.click();

        // Return a new instance of the NewProjectPage class (you need to create this class)
        return new NewProjectPage(driver);
    }

    @Override
    protected void load() {
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        driver.get(baseURL+"/maias/-/projects");
    }

    @Override
    protected void isLoaded() throws Error {
        //assertTrue(driver.getTitle().contains("Projects"));

    }
}
