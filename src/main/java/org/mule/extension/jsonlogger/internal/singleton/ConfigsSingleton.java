package org.mule.extension.jsonlogger.internal.singleton;

import org.mule.extension.jsonlogger.internal.JsonloggerConfiguration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConfigsSingleton {

    private static final Map<String, JsonloggerConfiguration> configs = new ConcurrentHashMap<>();

    public Map<String, JsonloggerConfiguration> getConfigs() {
        return configs;
    }

    public JsonloggerConfiguration getConfig(String configName) {
        return this.configs.get(configName);
    }

    public void addConfig(String configName, JsonloggerConfiguration config) {
        configs.put(configName, config);
    }

    public void removeConfig(String configName) {
        configs.remove(configName);
    }

}
