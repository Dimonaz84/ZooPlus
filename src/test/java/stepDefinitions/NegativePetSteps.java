package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static constants.Endpoints.PET_ENDPOINT;
import static helpers.RESTHelper.post;

public class NegativePetSteps extends TestBase{

    private static final Logger LOGGER = LoggerFactory.getLogger(PetSteps.class);
    private static Response response;
    private static JsonPath jsonPath;

    @Given("user creates new pet providing invalid body")
    public void userCreatesNewPetProvidingInvalidBody() {
        LOGGER.info("Creating new pet using invalid request body");

        String body = "{  \"id\": 0,  " +
                "\"category\": {    \"id\": 0,    " +
                "\"name\": \"string\"  },  " +
                "\"name\": \" InvalidPet \",  " +
                "\"photoUrls\": [    \"string\"  ],  " +
                "\"tags\": [    {      \"id\": 0,      " +
                "\"name\": \"string\"    }  ],  " +
                "\"stat";

        response = post(PET_ENDPOINT, body);

        jsonPath = response.jsonPath();
        context.setContext("code", response.getStatusCode());
    }

    @Then("user gets {string} error message")
    public void userGetsErrorMessage(String expectedCode) {

        Assert.assertFalse("Status code has not been provided in text context", context.isEmpty());

        String code = context.getContext().get("code").toString();

        Assert.assertEquals("Status code is not " +  expectedCode, expectedCode, code);
    }
}
