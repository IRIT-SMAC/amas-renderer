package fr.irit.smac.amasrenderer.model;

import org.graphstream.graph.implementations.SingleGraph;

public class AgentGraph extends SingleGraph {
	
	private int currentID;
	
	public AgentGraph(String id) {
		super(id);
		currentID = 0;
	}
	
	public int getCurrentID(){
		return this.currentID;
	}
}
