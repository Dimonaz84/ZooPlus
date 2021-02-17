package helpers;

import java.util.HashMap;
import java.util.Map;

public class TestContext {

    private Map<String, Object> scenarioContext;

    public TestContext(){
        scenarioContext = new HashMap<>();
    }

    public void setContext(String key, Object value) {
        scenarioContext.put(key, value);
    }

    public boolean isEmpty(){ return scenarioContext.isEmpty(); }

    public Map<String, Object> getContext(){
        return scenarioContext;
    }
}
