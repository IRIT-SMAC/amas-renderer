package fr.irit.smac.amasrenderer.model;

import java.util.Map;

import org.graphstream.graph.implementations.SingleGraph;

/**
 * The Class AgentGraph. Represents a graph of agents
 */
public class GraphModel extends SingleGraph {

	private Map<String, Object> agentMap;

	/**
     * Instantiates a new agent graph.
     *
     * @param id
     *            the id of the graph
     */
    public GraphModel(String id) {
        super(id);
    }
    
    public Map<String, Object> getAgentMap() {
		return agentMap;
	}

	public void setAgentMap(Map<String, Object> agentMap) {
		this.agentMap = agentMap;
	}    
}
