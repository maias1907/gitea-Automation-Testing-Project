package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class NewProjectPage  extends LoadableComponent<NewProjectPage> {
    private WebDriver driver;
    private final String baseURL="https://668b-2a06-c701-78d3-4f00-f943-2c4c-636d-e810.ngrok-free.app";

    // Locators for New Project Fields
    private By titleField = By.id("_aria_auto_id_0");
    private By descriptionField = By.id("_combo_markdown_editor_1");
    private By templateDropdown =  By.id("_aria_auto_id_13");
    private By cardPreviewsDropdown = By.id("_aria_auto_id_17");
    private By createProjectButton = By.cssSelector(".ui.primary.button");
    private By cancelButton = By.cssSelector(".ui.cancel.button");

    // Locators for Description Buttons
     private By boldButton = By.cssSelector("md-bold.markdown-toolbar-button[aria-label='Add bold text']");
     private By numberProjects=By.cssSelector("div.ui.small.label");
    /*private By italicButton = By.cssSelector("button[aria-label='Italic']");
    private By addTableButton = By.cssSelector("button[aria-label='Insert Table']");
    private By addLinkButton = By.cssSelector("button[aria-label='Insert Link']");
    private By heading1Button = By.cssSelector("button[aria-label='Heading 1']");
    private By previewTab = By.cssSelector("button[aria-label='Preview']");*/

    // Constructor
    public NewProjectPage(WebDriver driver) {
        this.driver = driver;

        // This call sets the WebElement fields.
        PageFactory.initElements(driver, this);
    }

    // Actions for New Project Fields
    public void enterTitle(String title) {
        driver.findElement(titleField).sendKeys(title);
    }

    public void enterDescription(String description) {
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(5), Duration.ofMillis(500));
        wait.until(driver -> driver.findElement(descriptionField).isDisplayed());
        driver.findElement(descriptionField).sendKeys(description);
    }

    public void selectTemplate(String templateName) {
        Actions actions = new Actions(driver);

        // Click the dropdown
        WebElement dropdown = driver.findElement(By.xpath("//div[@class='ui selection dropdown']"));
        actions.moveToElement(dropdown).click().perform();

        // Click the option
        WebElement option = driver.findElement(By.xpath("//div[@role='option' and text()='" + templateName + "']"));
        actions.moveToElement(option).click().perform();
    }



    public void selectCardPreview(String cardPreviewOption) {

        Actions actions=new Actions(driver);

        WebElement dropdown= driver.findElement(By.xpath("//div[@class='ui selection dropdown']"));
        actions.moveToElement(dropdown).click().perform();

        WebElement option=driver.findElement(By.xpath("//div[@role='option' and text()='" + cardPreviewOption + "']"));
        actions.moveToElement(option).click().perform();

    }



    public void clickCreateProject() {
        driver.findElement(createProjectButton).click();
    }

    public void clickCancel() {
        driver.findElement(cancelButton).click();
    }

    // Actions for Description Buttons
    public void clickBoldButton() {
        driver.findElement(boldButton).click();
    }

    /* public void clickItalicButton() {
        driver.findElement(italicButton).click();
    }

    public void clickAddTableButton() {
        driver.findElement(addTableButton).click();
    }

    public void clickAddLinkButton() {
        driver.findElement(addLinkButton).click();
    }

    public void clickHeading1Button() {
        driver.findElement(heading1Button).click();
    }

    public void clickPreviewTab() {
        driver.findElement(previewTab).click();
    }*/

    // Getter for Description Content
    public String getDescriptionContent() {
        WebElement previewElement = driver.findElement(descriptionField);
        String content = previewElement.getAttribute("value");
        return content;
    }
    public int getNumberOfProjects(){
        WebElement numberElement = driver.findElement(numberProjects);
        String numberText = numberElement.getText();
        int number = Integer.parseInt(numberText);
        return number;

    }

    public boolean isSuccessfulProjectPage(){


        return driver.getTitle().equals("Projects - Gitea: Git with a cup of tea");

    }

    @Override
    protected void load() {
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        driver.get(baseURL+"/maias/-/projects/new");
    }

    @Override
    protected void isLoaded() throws Error {

        assertTrue(driver.getTitle().contains("New Project"));

    }
}
