package stepDefinitions;

import io.cucumber.java.en.Given;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static stepDefinitions.RESTHelper.*;

public class PetSteps extends TestBase{

    private static final Logger LOGGER = LoggerFactory.getLogger(PetSteps.class);
    private static String petEndpoint = URL + "/v2/pet";
    private Response response;

    @Given("user creates new pet {string} with pet endpoint")
    public void userCreatesNewPet(String name) {
        LOGGER.info("Creating new pet...");
        String body = "{  \"id\": 0,  " +
                "\"category\": {    \"id\": 0,    " +
                "\"name\": \"string\"  },  " +
                "\"name\": \"" + name + "\",  " +
                "\"photoUrls\": [    \"string\"  ],  " +
                "\"tags\": [    {      \"id\": 0,      " +
                "\"name\": \"string\"    }  ],  " +
                "\"status\": \"available\"}";

        response = post(petEndpoint, body);

        JsonPath jsonPath = response.jsonPath();
        String createdName = jsonPath.get("name");
        long createdId = jsonPath.get("id");

        Assert.assertEquals("Name of the created pet is not " + name + " as expected" +
                " and is " + createdName, name, createdName);
        Assert.assertNotEquals("Created id is empty", "", createdId);
    }
}
