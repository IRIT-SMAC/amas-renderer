package fr.irit.smac.amasrenderer.model;

import java.util.HashMap;
import java.util.Map;

import org.graphstream.graph.implementations.SingleGraph;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

/**
 * The Class AgentGraph. Represents a graph of agents
 */
public class AgentGraphModel extends SingleGraph {

    private Map<String, Object> graphMap = new HashMap<>();

    /**
     * Used by Jackson parser.
     */
    public AgentGraphModel() {
        super("");
    }

    /**
     * Instantiates a new agent graph.
     *
     * @param id
     *            the id of the graph
     */
    public AgentGraphModel(String id) {
        super(id);
    }

    /**
     * Gets the map (when the graphMap is serialized)
     *
     * @return the map
     */
    @JsonAnyGetter
    public Map<String, Object> any() {
        return graphMap;
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
        graphMap.put(name, value);
    }

    /**
     * Gets the graph map.
     *
     * @return the graph map
     */
    public Map<String, Object> getGraphMap() {
        return graphMap;
    }

    /**
     * Sets the graph map.
     *
     * @param graphMap
     *            the graph map
     */
    public void setGraphMap(Map<String, Object> graphMap) {
        this.graphMap = graphMap;
    }
}
