package APITests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.net.URL;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class APITestGiteaTest {

    private static final String owner = "maias";
    private static final String apiToken = "c8a457697a30f6ddca2e551d061343a898dc0dac";
    private static final String projectName = "newRepoAPITest";

    @BeforeAll
    public static void setup() {
        String baseURL1 = "http://localhost:3001";
        String dbUrl ="https://thin-zoos-ask.loca.lt";
        String baseURL="https://a7bd-2a06-c701-78fb-bc00-e162-b721-5502-6b4b.ngrok-free.app";
        RestAssured.baseURI =dbUrl + "/api/v1";
        //System.out.println(dbUrl);
    }

    @Test
    @Order(1)
    public void testCreateRepo() {
        given()
                .log().all()
                .header("Authorization", "token " + apiToken)
                .contentType(ContentType.JSON)
                .body("{ \"name\": \"" + projectName + "\", \"private\": false }")
                .when()
                .post("/user/repos")
                .then()
                .log().all()
                .statusCode(201)
                .body("name", equalTo(projectName))
                .body("private", equalTo(false));

    }

    @Test
    @Order(2)
    public void testDeleteRepo() {
        given()
                .log().all()
                .header("Authorization", "token " + apiToken)
                .when()
                .delete("/repos/" + owner + "/" + projectName)
                .then()
                .log().all()
                .statusCode(204);
    }


}
