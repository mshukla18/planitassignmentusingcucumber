package org.planit.util;

import java.util.HashMap;

public class ScenarioContext {
    private HashMap<String, Object> data;

    public ScenarioContext() {
        data = new HashMap<>();
    }

    public Object getData(String key) {
        return data.get(key);
    }

    public void setData(String key, Object value) {
        this.data.put(key, value);
    }
}