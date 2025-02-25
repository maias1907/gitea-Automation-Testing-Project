package APITests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class APITestGiteaTest {

    private static final String owner = "maias";
    private static final String apiToken = "39ff7ec15e48381ac2dd05e51aacee8bc9e3489f";
    private static final String projectName = "newRepoAPITest";

    @BeforeAll
    public static void setup() {
        String baseURL = "https://e968-79-177-145-60.ngrok-free.app";
        RestAssured.baseURI = baseURL + "/api/v1";
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
