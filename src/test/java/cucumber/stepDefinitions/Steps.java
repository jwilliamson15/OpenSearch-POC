package cucumber.stepDefinitions;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.verychicpoc.dto.OpenSearchResponse;
import com.verychicpoc.dto.SearchHit;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.List;
import org.junit.Assert;

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
    response = request.get( OPENSEARCH_ENDPOINT + "/_search?q=hotelName:" + hotelName);
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

  @Given("a user has searched for hotels with multiple responses")
  public void aUserHasSearchedForHotelsWithMultipleResponses() {
    request = given().auth().preemptive().basic("admin", "admin").relaxedHTTPSValidation();
  }

  @When("a user filter for room type {string}")
  public void aUserFilterForRoomType(final String roomTypeId) {
    response = request.get(OPENSEARCH_ENDPOINT + "/_search?q=roomTypeId:" + roomTypeId);
  }

  @When("a user filter for room types {string} and {string}")
  public void aUserFilterForRoomType(final String roomTypeId1, final String roomTypedId2) {
    response = request.get(OPENSEARCH_ENDPOINT + "/_search?q=roomTypeId:" + roomTypeId1 + "," + roomTypedId2);
  }

  @Then("the user gets the previous list of results filtered by room type")
  public void theUserGetsThePreviousListOfResultsFilteredByRoomType() {
    response.then().statusCode(200).assertThat()
        .body("hits.total.value", greaterThanOrEqualTo(1));
  }

  @Then("the user gets the previous list of results filtered by multiple room types")
  public void theUserGetsThePreviousListOfResultsFilteredByMultipleRoomTypes()
      throws JsonProcessingException {
    response.then().statusCode(200).assertThat()
        .body("hits.total.value", greaterThanOrEqualTo(1));

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
    OpenSearchResponse searchFullResponse = objectMapper.readValue(
        response.getBody().asString(),
        OpenSearchResponse.class);

    List<SearchHit> searchHits = searchFullResponse.hits.hits;

    for (SearchHit hit: searchHits) {
      Assert.assertTrue(hit._source.roomTypeId.contains("DOUBLE_STANDARD")
          || hit._source.roomTypeId.contains("JUNIOR_SUITE"));
    }
  }
}
