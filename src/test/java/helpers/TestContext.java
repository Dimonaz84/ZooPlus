package helpers;

import java.util.HashMap;
import java.util.Map;

public class TestContext {

    private static TestContext testContext = null;
    private Map<String, Object> scenarioContext;

    private TestContext(){
        scenarioContext = new HashMap<>();
    }

    public static TestContext getInstance()
    {
        if (testContext == null)
            testContext = new TestContext();

        return testContext;
    }

    public void setContext(String key, Object value) {
        scenarioContext.put(key, value);
    }

    public boolean isEmpty(){ return scenarioContext.isEmpty(); }

    public void clearContext(){
        scenarioContext.clear();
    }

    public Map<String, Object> getContext(){
        return scenarioContext;
    }
}
