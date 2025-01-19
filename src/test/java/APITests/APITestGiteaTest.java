package APITests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class APITestGiteaTest {

    private final String owner = "maias";
    private static String apiToken="39ff7ec15e48381ac2dd05e51aacee8bc9e3489f";
    private static final String projectName = "newProjectAPITest";




    //echo "GITEA_TOKEN=...." > .env
    //echo "GITEA_TOKEN=${{ secrets.GITEA_TOKEN }}" > .env

    @BeforeAll
    public static void setup() {
        String baseURL="https://e968-79-177-145-60.ngrok-free.app";
        RestAssured.baseURI = baseURL+"/api/v1";
        //RestAssured.port = 3000;

    }
    @Test
    public void testCreateRepo() {
        given().
                log().all().
                header("Authorization", "token " + apiToken).
                contentType(ContentType.JSON).
                body("{ \"name\": \"" + projectName + "\", \"private\": false }").
                when().
                post("/user/repos"). // Endpoint to create a repository
                then().
                log().all().
                statusCode(201). // Expecting HTTP 201 Created
                body("name", equalTo(projectName), // Verifying project name
                "private", equalTo(false)); // Verifying project privacy
    }
    @Test
    public void testDeleteRepo(){
        given().
                log().all().
                header("Authorization", "token " + apiToken).
                when().
                delete("/repos/" + owner + "/" + projectName).
                then().
                log().all().
                statusCode(204);
    }
    // POST: Create Organization Repository
    @Test
    public void testCreateOrganization() {
        String organizationName = "newOrganization"; // Replace with the desired organization name

        given().
                log().all().
                header("Authorization", "token " + apiToken).
                contentType(ContentType.JSON).
                body("{ \"username\": \"" + organizationName + "\", \"full_name\": \"New Organization\", \"description\": \"This is a test organization.\" }").
                when().
                post("/orgs").
                then().
                log().all().
                statusCode(201). // Expecting HTTP 201 Created
                body("username", equalTo(organizationName),
                "full_name", equalTo("New Organization"),
                "description", equalTo("This is a test organization."));
    }
    @Test
    public void testGetOrganization() {
        String organizationName = "newOrganization"; // Replace with the name of the organization to fetch

        given().
                log().all().
                header("Authorization", "token " + apiToken).
                when().
                get("/orgs/" + organizationName). // Endpoint for fetching organization details
                then().
                log().all().
                statusCode(200). // Expecting HTTP 200 OK for successful retrieval
                body("username", equalTo(organizationName)); // Verifying the organization username
    }



    @Test // DELETE: Delete Organization
    public void testDeleteOrganization() {
        String organizationName = "newOrganization"; // Replace with the organization name to delete

        given().
                log().all().
                header("Authorization", "token " + apiToken).
                when().
                delete("/orgs/" + organizationName). // Endpoint for deleting an organization
                then().
                log().all().
                statusCode(204); // Expecting HTTP 204 No Content for successful deletion
    }



}