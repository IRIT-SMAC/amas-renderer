package fr.irit.smac.amasrenderer.model;

import java.util.HashMap;
import java.util.Map;

import org.graphstream.graph.implementations.SingleGraph;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

/**
 * The Class AgentGraph. 
 * Represents a graph of agents
 */
public class AgentGraph extends SingleGraph {
    
    private Map<String, Object> graphMap = new HashMap<String, Object>();
    
    
    /**
     * Constuctor for Jackson parser
     */
    public AgentGraph(){
        super("man lol");
    }
    
    /**
     * Instantiates a new agent graph.
     *
     * @param id the id of the graph
     */
    public AgentGraph(String id) {
        super(id);
    }

    @JsonAnyGetter
    public Map<String, Object> any() {
     return graphMap;
    }

     @JsonAnySetter
    public void set(String name, Object value) {
     graphMap.put(name, value);
    }
    
     /**
      * 
      * @return the graph map
      */
    public Map<String, Object> getGraphMap() {
        return graphMap;
    }

	public void setGraphMap(Map<String, Object> graphMap) {
		this.graphMap = graphMap;
	}
}
