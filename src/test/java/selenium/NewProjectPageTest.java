package selenium;

import org.example.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import java.net.MalformedURLException;

import static org.junit.jupiter.api.Assertions.*;

public class NewProjectPageTest {
    private WebDriver driver;
    private ProjectPage projectPage;
    private LoginGit login;
    private HomePageGit home;
    private ProfilePage profile;
    private NewProjectPage newProjectPage;
    @BeforeEach
    public void setUp() throws MalformedURLException {
        driver= DriverFactory.getDriver();
        driver.manage().window().maximize();
        login = new LoginGit(driver).get();
    }
    @Test
    public void testCreateProject() {
       //the bot pattern
        newProjectPage=login.loginAsValidUser("maias", "Maias123").goToProfilePage().goToProjectsPage().goToNewProjectPage();
        newProjectPage.enterTitle("Test1 Project");
        newProjectPage.enterDescription("This is a test project description.");
        newProjectPage.selectTemplate("None");
        newProjectPage.selectCardPreview("Images and Text");
        newProjectPage.clickCreateProject();
        assertTrue(newProjectPage.isSuccessfulProjectPage());
    }
    @Test  //if we have emty title the project will not create
    public void testEmptyFields() {
        //the bot pattern
        newProjectPage=login.loginAsValidUser("maias", "Maias123").goToProfilePage().goToProjectsPage().goToNewProjectPage();
        newProjectPage.enterTitle("");
        newProjectPage.enterDescription("this is Description");
        newProjectPage.clickCreateProject();
        assertFalse(newProjectPage.isSuccessfulProjectPage());


    }
    @Test  //if we have emty title the project will not create
    public void testTitleOnly() {
        //the bot pattern
        newProjectPage=login.loginAsValidUser("maias", "Maias123").goToProfilePage().goToProjectsPage().goToNewProjectPage();
        newProjectPage.enterTitle("Test 3 Project");
        newProjectPage.clickCreateProject();
        assertTrue(newProjectPage.isSuccessfulProjectPage());


    }
    @Test  //if we have emty title the project will not create
    public void testCancelButton() {
        newProjectPage=login.loginAsValidUser("maias", "Maias123").goToProfilePage().goToProjectsPage().goToNewProjectPage();
        newProjectPage.enterTitle("Test 4 Project");
        newProjectPage.enterDescription("This is a test project description.");
        newProjectPage.selectTemplate("None");
        newProjectPage.selectCardPreview("Images and Text");
        int numberProjectBefore= newProjectPage.getNumberOfProjects();
        newProjectPage.clickCancel();
        int numberProjectafter=newProjectPage.getNumberOfProjects();
        assertEquals(numberProjectBefore,numberProjectafter);


    }

    @Test// test BoldStyle after we write the description
    public void testBoldStyleInDescription() {
        newProjectPage=login.loginAsValidUser("maias", "Maias123").goToProfilePage().goToProjectsPage().goToNewProjectPage();
        newProjectPage.enterTitle("Test5 Project");
        newProjectPage.enterDescription("This is a test description.");
        newProjectPage.selectTemplate("Basic Kanban");
        newProjectPage.selectCardPreview("Text Only");
        // Apply bold styling
        newProjectPage.clickBoldButton();
        // Get the HTML content after applying bold
        String afterBoldHtml = newProjectPage.getDescriptionContent();
        // Verify that bold tags are added in the HTML
        assertTrue(afterBoldHtml.contains("****") ,
                "Bold styling was not applied in the description preview.");
        //Verify the content inside the bold tags is as expected
        assertTrue(afterBoldHtml.contains("This is a test description."),
                "Description content is incorrect after applying bold styling.");
        // Create the project
        newProjectPage.clickCreateProject();
        // Verify that the project was created successfully
        assertTrue(newProjectPage.isSuccessfulProjectPage());
    }
    @Test// test BoldStyle before we write the description
    public void testBoldStyleInDescription2() {
        newProjectPage=login.loginAsValidUser("maias", "Maias123").goToProfilePage().goToProjectsPage().goToNewProjectPage();
        newProjectPage.enterTitle("Test6 Project");
        newProjectPage.clickBoldButton();
        newProjectPage.enterDescription("This is a test description.");
        newProjectPage.selectTemplate("Basic Kanban");
        newProjectPage.selectCardPreview("Text Only");
        // Get the HTML content after applying bold
        String afterBoldHtml = newProjectPage.getDescriptionContent();
        // Verify that bold tags are added in the HTML
        assertTrue(afterBoldHtml.equals("**This is a test description.**") ,
                "Bold styling was not applied in the description preview.");
        //Verify the content inside the bold tags is as expected
        assertTrue(afterBoldHtml.contains("This is a test description."),
                "Description content is incorrect after applying bold styling.");
        // Create the project
        newProjectPage.clickCreateProject();
        // Verify that the project was created successfully
        assertTrue(newProjectPage.isSuccessfulProjectPage());
    }
    @Test// test BoldStyle for some words we write the description
    public void testBoldStyleInDescription3() {
        newProjectPage=login.loginAsValidUser("maias", "Maias123").goToProfilePage().goToProjectsPage().goToNewProjectPage();
        newProjectPage.enterTitle("Test 7 Project");
        newProjectPage.enterDescription("This is a test description.");
        newProjectPage.clickBoldButton();
        newProjectPage.enterDescription("maias ");
        newProjectPage.selectTemplate("Basic Kanban");
        newProjectPage.selectCardPreview("Text Only");
        newProjectPage.enterDescription("This not Bold");
        String afterAdditionalText=newProjectPage.getDescriptionContent();
        assertTrue(afterAdditionalText.equals("This is a test description.**maias **This not Bold"),"Bold styling was not applied in the description preview.");
        newProjectPage.clickCreateProject();
        assertTrue(newProjectPage.isSuccessfulProjectPage());
    }

}
