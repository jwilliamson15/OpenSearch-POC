package cucumber;

import static io.restassured.RestAssured.given;

import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import io.restassured.http.ContentType;
import java.io.File;

public class Hooks {

  private final static String OPENSEARCH_ENDPOINT = "https://localhost:9200";
  private final static String INDEX_NAME = "hotels";


  //ultimately this data load should be moved outside of cucumber, maybe to the docker file
  @BeforeAll
  public static void beforeAll() throws InterruptedException {
    File jsonDataInFile = new File("src/test/resources/data/MOCK_DATA.json");

    given()
      .auth().preemptive().basic("admin", "admin")
      .relaxedHTTPSValidation()
      .baseUri(OPENSEARCH_ENDPOINT+ "/_bulk")
      .contentType(ContentType.JSON)
      .body(jsonDataInFile)
    .when()
      .post()
    .then()
      .assertThat().statusCode(200);

    //Temporary to avoid race condition with data load and tests running
    Thread.sleep(300);
  }

  @AfterAll
  public static void afterAll() {
    given()
      .auth().preemptive().basic("admin", "admin")
      .relaxedHTTPSValidation()
      .baseUri(OPENSEARCH_ENDPOINT + "/" + INDEX_NAME)
    .when()
        .delete()
    .then()
        .assertThat().statusCode(200);
  }
}
