package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import static helpers.RESTHelper.*;
import static constants.Endpoints.*;

public class PetSteps extends TestBase{

    private static final Logger LOGGER = LoggerFactory.getLogger(PetSteps.class);
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

        response = post(PET_ENDPOINT, body);

        JsonPath jsonPath = response.jsonPath();
        String createdName = jsonPath.get("name");
        long createdId = jsonPath.get("id");
        context.setContext("petId", createdId);

        Assert.assertEquals("Name of the created pet is not " + name + " as expected" +
                " and is " + createdName, name, createdName);
        Assert.assertNotEquals("Created id is empty", "", createdId);
    }

    @When("user uploads a picture for the pet")
    public void userUploadsPicture() {

        File file = new File("src\\test\\resources\\cat.jpg");
        Assert.assertTrue("Pet ID has not been set in previous step", context.isContains("petId"));
        String imageEndpoint = UPLOAD_IMAGE_ENDPOINT.replace("{petId}", (CharSequence) context.getContext("petId"));
        response = uploadFile(imageEndpoint, file);
        JsonPath jsonPath = response.jsonPath();
        String message = jsonPath.get("message");
        Assert.assertTrue("File has not been uploaded", message.contains("File uploaded to ./cat.jpg"));
    }
}
