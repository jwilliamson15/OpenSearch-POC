package cucumber.stepDefinitions;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.text.Collator;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
  public void theUserGetsThePreviousListOfResultsFilteredByMultipleRoomTypes() {
    response.then().statusCode(200).assertThat()
        .body("hits.total.value", greaterThanOrEqualTo(1));

    JsonPath json = new JsonPath(response.asString());

    Map<String,String> firstElement = (Map<String, String>) json.getList("hits.hits").get(0);


    System.out.println(">>>" + json.getList("hits.hits").toString());

//    String searchHits = getJsonPathAsString(response, "hits.hits");
//    searchResults.stream()
//        .filter(entry -> entry.getKey().startsWith("_source"))
//        .map(entry -> entry.getValue().toString())
//        .collect(Collectors.toList());
  }

  public static String getJsonPathAsString(Response response, String key) {
    JsonPath json = new JsonPath(response.asString());
    return json.get(key).toString();
  }
}
