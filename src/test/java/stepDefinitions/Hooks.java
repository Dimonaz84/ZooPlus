package stepDefinitions;

import helpers.TestContext;
import io.cucumber.java.After;

public class Hooks {

    TestContext testContext = TestContext.getInstance();

    @After
    public void afterScenario(){

        System.out.println("Cleaning test context...");
        testContext.clearContext();
    }
}
