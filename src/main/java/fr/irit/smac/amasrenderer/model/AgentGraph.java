package fr.irit.smac.amasrenderer.model;

import org.graphstream.graph.implementations.SingleGraph;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * The Class AgentGraph. 
 * Represents a graph of agents
 */
public class AgentGraph extends SingleGraph {
    
    private IntegerProperty currentID;

    /**
     * Instantiates a new agent graph.
     *
     * @param id the id of the graph
     */
    public AgentGraph(String id) {
        super(id);
        currentID = new SimpleIntegerProperty(0);
    }

    /**
     * Gets the current id.
     *
     * @return the current id
     */
    public IntegerProperty getCurrentID(){
        return this.currentID;
    }
}
