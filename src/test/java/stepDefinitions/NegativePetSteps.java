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

import java.util.Map;

import static constants.Endpoints.PET_ENDPOINT;
import static constants.Endpoints.STATUS_ENDPOINT;
import static helpers.RESTHelper.*;

public class NegativePetSteps extends TestBase{

    private static final Logger LOGGER = LoggerFactory.getLogger(NegativePetSteps.class);
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

    @And("user updates the pet name to {string} providing invalid id")
    public void userUpdatesPetWithInvalidId(String name) {

        Assert.assertFalse("Pet has not been created in previous step", context.isEmpty());

        String body = "{  \"id\": abcd,  " +
                "\"category\": {    \"id\": 0,    " +
                "\"name\": \"string\"  },  " +
                "\"name\": \"" + name + "\",  " +
                "\"photoUrls\": [    \"string\"  ],  " +
                "\"tags\": [    {      \"id\": 0,      " +
                "\"name\": \"string\"    }  ],  " +
                "\"status\": \"available\"}";

        response = put(PET_ENDPOINT, body);
        jsonPath = response.jsonPath();
        context.setContext("code", response.getStatusCode());
    }

    @Given("user tries seraching the pet providing wrong status")
    public void userSerachesProvidingWrongStatus() {

        LOGGER.info("Getting pets providing wrong status");

        response = get(STATUS_ENDPOINT + "123a423");
        jsonPath = response.jsonPath();
        context.setContext("code", response.getStatusCode());
    }

    @When("user deletes same pet second time")
    public void userDeletesSamePet() {

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
    }

    @And("user tries searching for deleted pet")
    public void userSearchesForDeletedPet() {
        Assert.assertFalse("Pet has not been created in previous step", context.isEmpty());
        String petId = "";
        for(Map.Entry<String, Object> entry: context.getContext().entrySet()) {
            petId = entry.getKey();
            break;
        }

        LOGGER.info("Getting the pet with id " + petId);

        String petEndpoint = PET_ENDPOINT + "/" + petId;

        response = get(petEndpoint);
        context.setContext("code", response.getStatusCode());
    }
}
