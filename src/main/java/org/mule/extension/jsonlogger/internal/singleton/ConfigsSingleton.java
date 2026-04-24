package org.mule.extension.jsonlogger.internal.singleton;

import org.mule.extension.jsonlogger.internal.JsonloggerConfiguration;
import org.mule.runtime.api.artifact.Registry;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class ConfigsSingleton {

    @Inject
    private Registry registry;
    
    private Map<String, JsonloggerConfiguration> configs = new HashMap<String, JsonloggerConfiguration>();

    public JsonloggerConfiguration getConfig(String configName) {
        JsonloggerConfiguration config = configs.get(configName);
        if (config == null) {
            registry.lookupByName(configName);
            config = configs.get(configName);
        }
        return config;
    }

    public void addConfig(String configName, JsonloggerConfiguration config) {
        this.configs.put(configName, config);
    }

}
