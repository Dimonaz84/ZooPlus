package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.util.*;

import static helpers.RESTHelper.*;
import static constants.Endpoints.*;

public class PetSteps extends TestBase{

    private static final Logger LOGGER = LoggerFactory.getLogger(PetSteps.class);
    private static Response response;
    private static JsonPath jsonPath;

    @Given("user creates new pet {string}")
    public void userCreatesNewPet(String name) {

        LOGGER.info("Creating new pet with name " + name);

        String body = "{  \"id\": 0,  " +
                "\"category\": {    \"id\": 0,    " +
                "\"name\": \"string\"  },  " +
                "\"name\": \"" + name + "\",  " +
                "\"photoUrls\": [    \"string\"  ],  " +
                "\"tags\": [    {      \"id\": 0,      " +
                "\"name\": \"string\"    }  ],  " +
                "\"status\": \"available\"}";

        response = post(PET_ENDPOINT, body);
        Assert.assertEquals("Status code is not 200", 200, response.getStatusCode());

        jsonPath = response.jsonPath();
        long createdId = jsonPath.get("id");
        context.setContext(String.valueOf(createdId), "");
    }

    @Given("user creates new pet {string} with status {string}")
    public void userCreatesNewPetWithStatus(String name, String status) {
        LOGGER.info("Creating new pet with name " + name + " and status " + status);
        String body = "{  \"id\": 0,  " +
                "\"category\": {    \"id\": 0,    " +
                "\"name\": \"string\"  },  " +
                "\"name\": \"" + name + "\",  " +
                "\"photoUrls\": [    \"string\"  ],  " +
                "\"tags\": [    {      \"id\": 0,      " +
                "\"name\": \"string\"    }  ],  " +
                "\"status\": \"" + status + "\"}";

        response = post(PET_ENDPOINT, body);
        Assert.assertEquals("Status code is not 200", 200, response.getStatusCode());

        jsonPath = response.jsonPath();
        long createdId = jsonPath.get("id");
        Map<String, String> pet = new HashMap<>();
        pet.put("name", name);
        pet.put("status", status);
        context.setContext(String.valueOf(createdId), pet);
    }

    @Then("user gets {string} status code")
    public void userGetsErrorMessage(String expectedCode) {

        Assert.assertFalse("Status code has not been provided in text context", context.isEmpty());

        String code = context.getContext().get("code").toString();

        Assert.assertEquals("Status code is not " +  expectedCode, expectedCode, code);
    }

    @When("user uploads a picture for the pet")
    public void userUploadsPicture() {

        LOGGER.info("Uploading picture for pet");

        File file = new File("src\\test\\resources\\cat.jpg");
        Assert.assertFalse("Pet has not been created in previous step", context.isEmpty());
        String petId = "";
        for(Map.Entry<String, Object> entry: context.getContext().entrySet()) {
            petId = entry.getKey();
            break;
        }
        String imageEndpoint = UPLOAD_IMAGE_ENDPOINT.replace("{petId}", petId);

        response = uploadFile(imageEndpoint, file);
        Assert.assertEquals("Status code is not 200", 200, response.getStatusCode());

        jsonPath = response.jsonPath();
        String message = jsonPath.get("message");
        Assert.assertTrue("File has not been uploaded", message.contains("File uploaded to ./cat.jpg"));
    }

    @Then("user verifies if pet with the given {string} is available")
    public void petIsAvailable(String name) {

        Assert.assertFalse("Pet has not been created in previous step", context.isEmpty());
        String petId = "";
        for(Map.Entry<String, Object> entry: context.getContext().entrySet()) {
            petId = entry.getKey();
            break;
        }

        LOGGER.info("Getting the pet " + name + " with id " + petId);

        String petEndpoint = PET_ENDPOINT + "/" + petId;

        response = get(petEndpoint);
        Assert.assertEquals("Status code is not 200", 200, response.getStatusCode());

        jsonPath = response.jsonPath();
        String createdName = jsonPath.get("name");
        String obtainedPetId = jsonPath.get("id").toString();

        Assert.assertEquals("Name of the created pet is not " + name + " as expected" +
                " and is " + createdName, name, createdName);
        Assert.assertEquals("Pet id is not as expected", petId, obtainedPetId);
    }

    @Then("pet is not available")
    public void petIsNotAvailable() {

        Assert.assertFalse("Pet has not been created in previous step", context.isEmpty());
        String petId = "";
        for(Map.Entry<String, Object> entry: context.getContext().entrySet()) {
            petId = entry.getKey();
            break;
        }

        LOGGER.info("Getting pet with id " + petId);

        String petEndpoint = PET_ENDPOINT + "/" + petId;

        response = get(petEndpoint);
        Assert.assertEquals("Status code is not 404", 404, response.getStatusCode());

        jsonPath = response.jsonPath();
        String message = jsonPath.get("message");
        Assert.assertEquals("Message is not as expected", "Pet not found", message);
    }

    @And("user updates the pet name to {string}")
    public void userUpdatesPetName(String newName) {

        LOGGER.info("Updating the pet");

        Assert.assertFalse("Pet has not been created in previous step", context.isEmpty());
        String petId = "";
        for(Map.Entry<String, Object> entry: context.getContext().entrySet()) {
            petId = entry.getKey();
            break;
        }

        String body = "{  \"id\": " + petId + ",  " +
                "\"category\": {    \"id\": 0,    " +
                "\"name\": \"string\"  },  " +
                "\"name\": \"" + newName + "\",  " +
                "\"photoUrls\": [    \"string\"  ],  " +
                "\"tags\": [    {      \"id\": 0,      " +
                "\"name\": \"string\"    }  ],  " +
                "\"status\": \"available\"}";

        response = put(PET_ENDPOINT, body);
        Assert.assertEquals("Status code is not 200", 200, response.getStatusCode());
        context.setContext("code", response.getStatusCode());
    }

    @Then("pet {string} is available with status {string}")
    public void petIsAvailableWithStatus(String name, String status) {
        Assert.assertFalse("Pet has not been created in previous step", context.isEmpty());

        LOGGER.info("Getting pets with available status");

        response = get(STATUS_ENDPOINT + status);
        Assert.assertEquals("Status code is not 200", 200, response.getStatusCode());

        String message = response.getBody().asString();
        for (Map.Entry<String, Object> createdPets: context.getContext().entrySet()) {
            if (((HashMap) createdPets.getValue()).get("status").equals(status))
            Assert.assertTrue("Id of created pet is not available in response", message.contains(createdPets.getKey()));
        }

        jsonPath = response.jsonPath();
        ArrayList<LinkedHashMap<String, Object>> pets = jsonPath.get("");

        for (LinkedHashMap<String, Object> pet: pets){
            for (Map.Entry<String, Object> createdPets: context.getContext().entrySet()){
                if (pet.containsValue(Long.valueOf(createdPets.getKey()))) {
                    Assert.assertEquals("The name of created pet is not " + name + " as expected", pet.get("name"), name);
                    Assert.assertEquals("Status of created pet is not as expected " + status, pet.get("status"), status);
                }
            }
        }
    }

    @When("user updates the pet with form data and sets name {string} and status {string}")
    public void userUpdatesPetWithFormData(String name, String status) {

        LOGGER.info("Updating pet with form data: name = " + name + " and status= " + status);

        Assert.assertFalse("Pet has not been created in previous step", context.isEmpty());
        String petId = "";
        for(Map.Entry<String, Object> entry: context.getContext().entrySet()) {
            petId = entry.getKey();
            break;
        }

        String endpoint = PET_ENDPOINT + "/" + petId;
        response = updateWithFormData(endpoint, name, status);
        Assert.assertEquals("Status code is not 200", 200, response.getStatusCode());
    }

    @When("user deletes the pet")
    public void userDeletesThePet() {

        Assert.assertFalse("Pet has not been created in previous step", context.isEmpty());
        String petId = "";
        for(Map.Entry<String, Object> entry: context.getContext().entrySet()) {
            petId = entry.getKey();
            break;
        }

        LOGGER.info("Deleting pet with id = " + petId);

        String endpoint = PET_ENDPOINT + "/" + petId;
        response = delete(endpoint);
        context.setContext("code", response.getStatusCode());

        Assert.assertEquals("Status code is not 200", 200, response.getStatusCode());
    }
}
