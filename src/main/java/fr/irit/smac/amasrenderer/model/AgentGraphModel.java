package fr.irit.smac.amasrenderer.model;

import java.util.HashMap;
import java.util.Map;

import org.graphstream.graph.implementations.SingleGraph;

import javafx.scene.control.TreeItem;

/**
 * The Class AgentGraph. Represents a graph of agents
 */
public class AgentGraphModel extends SingleGraph {

	private Map<String, Object> agentMap;
	private Map<String, TreeItem<String>> attributeMap;

	public Map<String, TreeItem<String>> getAttributeMap() {
		return attributeMap;
	}

	public void setAttributeMap(Map<String, TreeItem<String>> attributeMap) {
		this.attributeMap = attributeMap;
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
    
    public Map<String, Object> getAgentMap() {
		return agentMap;
	}

	public void setAgentMap(Map<String, Object> agentMap) {
		this.agentMap = agentMap;
	}    
}
