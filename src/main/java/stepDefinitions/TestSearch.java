package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;
import org.junit.Assert;


public class TestSearch {

    String baseURL;
    String[] titles;
    int statusCode;
    private Response response;

    public TestSearch() {
        loadConfig();
    }

    private void loadConfig() {
        Properties prop = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            prop.load(input);
            baseURL = prop.getProperty("baseURL");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("a client of the API")
    public void setClientAPIBaseURL(){
       RestAssured.baseURI=baseURL;
   }

    @When("^a search for pages containing for (.*) is executed$")
    public void searchPageName(String searchKeyword){
        response  = RestAssured.get("/search/page?q="+searchKeyword);
       statusCode = response.getStatusCode();
       Assert.assertTrue("test",statusCode==200);
       titles = response.jsonPath().getString("pages.title").split(",");
   }

    @Then("^a page with the title (.*) is found$")
    public void verifyPageTile(String pageTitle){
       Assert.assertTrue("Page does not contain tile "+pageTitle,Arrays.toString(titles).contains(pageTitle) );
    }

    @When("^the page details for (.*) are requested$")
    public void searchPageDetails(String pageTitle) {
        // Send GET request to retrieve page details
        response = RestAssured.given().get("/page/"+pageTitle+"/bare");
        statusCode = response.getStatusCode();
        Assert.assertTrue("test",statusCode==200);
    }
    @Then("^it has a latest timestamp > (.*)$")
    public void verifyTimeStamp(String expectedTimestamp) {
        // Extract timestamp from the response
        String actualTimestamp = response.jsonPath().getString("latest.timestamp");
        // Compare timestamps
        Assert.assertTrue("Actual timestamp should be greater than expected timestamp",
                          actualTimestamp.compareTo(expectedTimestamp) > 0);
    }
}
