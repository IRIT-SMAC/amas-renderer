package fr.irit.smac.amasrenderer.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.ui.geom.Point3;
import org.graphstream.ui.swingViewer.ViewPanel;

import fr.irit.smac.amasrenderer.model.AgentGraph;
import fr.irit.smac.amasrenderer.model.Stock;

/**
 * The Class GraphAddDelNodeMouseController.
 * This controller controls addition and deletion of nodes on the graph "model"
 * Creation and deletion are used by alt+click on an empty space to create one
 * and on a node to delete it and all connected edges
 */
public class GraphAddDelNodeMouseController extends MouseAdapter{

    private ViewPanel  graphView;
    
    private int        currentNodeId;

    private AgentGraph model;

    /**
     * Initialize the controller, and adds it to the graph.
     *
     * @param graphView the graph view
     * @param model the model
     */
    public void init(ViewPanel graphView, AgentGraph model) {
        this.graphView = graphView;
        this.model = model;
        this.currentNodeId = model.getNodeCount() + 1;
        graphView.addMouseListener(this);
    }
    
    /**
     * On mouse alt+click, if there is no node at mouse location creates a node at mouse location
     * else deletes the node at mouse location
     * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseClicked(MouseEvent e){
        if(e.isAltDown()){
            Node n = (Node) graphView.findNodeOrSpriteAt(e.getX(), e.getY());
            //if clicked on empty space, create a node
            if(n == null){
                String curId = Integer.toString(currentNodeId++);
                Point3 clicLoc = graphView.getCamera().transformPxToGu(e.getX(), e.getY());
                model.addNode(curId);
                model.getNode(curId).changeAttribute("xyz", clicLoc.x, clicLoc.y );
                model.getNode(curId).setAttribute("ui.stocked-info", new Stock());
                model.getNode(curId).setAttribute("layout.weight", 300);
                System.out.println("nodeAdded");
                
            }
            //else delete the node and all connected edges
            else{
                Iterable<Edge> edges = n.getEachEdge();
                if(edges != null){
                    for(Edge edge: edges){
                        model.removeEdge(edge.getId());
                    }
                }
                model.removeNode(n.getId());
            }
        }
    }
    
    
    
    
    
}
