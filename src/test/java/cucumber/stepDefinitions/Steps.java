package cucumber.stepDefinitions;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Steps {

  private final static String OPENSEARCH_ENDPOINT = "https://localhost:9200/hotels";
  private Response response;
  private RequestSpecification request;

  @Given("a user is active")
  public void activeUser() {
    request = given().auth().preemptive().basic("admin", "admin").relaxedHTTPSValidation();
  }

  @When("a user searches for an existing hotel with name {string}")
  public void aUserSearchesForAHotelWithAnExistingName(final String hotelName) {
    response = request.get( OPENSEARCH_ENDPOINT+ "/_search?q=hotelName:" + hotelName);
  }


  @Then("a list of matching hotels is returned")
  public void aListOfMatchingHotelsIsReturned() {
    response.then().statusCode(200).assertThat()
        .body("hits.total.value", greaterThanOrEqualTo(1));
  }

  @Then("an empty list of hotels is returned")
  public void anEmptyListOfHotelsIsReturned() {
    response.then().statusCode(200).assertThat()
        .body("hits.total.value", equalTo(0));
  }
}
