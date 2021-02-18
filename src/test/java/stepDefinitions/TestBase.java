package stepDefinitions;

import helpers.TestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestBase {

    protected static final Logger LOGGER = LoggerFactory.getLogger(TestBase.class);
    protected TestContext context;

    public TestBase(){
        LOGGER.info("Initializing TestBase class...");
        System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "INFO");
        context = TestContext.getInstance();
    }
}
