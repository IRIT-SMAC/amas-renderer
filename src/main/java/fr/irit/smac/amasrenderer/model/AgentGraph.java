package fr.irit.smac.amasrenderer.model;

import org.graphstream.graph.implementations.SingleGraph;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class AgentGraph extends SingleGraph {
	
	private IntegerProperty currentID;
	
	public AgentGraph(String id) {
		super(id);
		currentID = new SimpleIntegerProperty(0);
	}
	
	public IntegerProperty getCurrentID(){
		return this.currentID;
	}
}
