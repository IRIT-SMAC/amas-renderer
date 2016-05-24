package fr.irit.smac.amasrenderer.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

public class ConfigurationMapModel {
	
    private Map<String, Object> configurationMap = new HashMap<>();

    /**
     * Used by Jackson parser.
     */
    public ConfigurationMapModel() {
    	
    }
    
    /**
     * Gets the map (when the graphMap is serialized)
     *
     * @return the map
     */
    @JsonAnyGetter
    public Map<String, Object> any() {
        return configurationMap;
    }

    /**
     * Sets the map (when the graphMap is deserialized)
     *
     * @param name
     *            the name
     * @param value
     *            the value
     */
    @JsonAnySetter
    public void set(String name, Object value) {
        configurationMap.put(name, value);
    }
    

    /**
     * Gets the graph map.
     *
     * @return the graph map
     */
    public Map<String, Object> getConfigurationMap() {
        return configurationMap;
    }

    /**
     * Sets the graph map.
     *
     * @param configurationMap
     *            the graph map
     */
    public void setConfigurationMap(Map<String, Object> configurationMap) {
        this.configurationMap = configurationMap;
    }

}
