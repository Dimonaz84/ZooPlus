package stepDefinitions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestBase {

    protected static final Logger LOGGER = LoggerFactory.getLogger(TestBase.class);
    protected static final String URL = "https://petstore.swagger.io";

    public TestBase(){
        LOGGER.info("Initializing TestBase class...");
        System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "INFO");
    }
}
