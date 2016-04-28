package fr.irit.smac.amasrenderer.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.ui.swingViewer.ViewPanel;

import fr.irit.smac.amasrenderer.model.AgentGraph;

/**
 * The Class GraphAddDelEdgeMouseController_Old.
 * This controller controls addition and deletion of edges on the graph "model"
 * Creation and deletion are used by right-click dragging from one node to an other
 */
public class GraphAddDelEdgeMouseController_Old extends MouseAdapter {

    private ViewPanel  graphView;
    
    private AgentGraph model;

    private Node source = null;
    
    private Node target = null;

    /**
     * Initialize the controller, and adds it to the graph.
     *
     * @param graphView the graph view
     * @param model the model (AgentGraph)
     */
    public void init(ViewPanel graphView, AgentGraph model) {
        this.graphView = graphView;
        this.model = model;
        graphView.addMouseListener(this);
    }

    /**
     * Gets the node on which the right mouse button was pressed and sets it as source(to prepare for mouseReleased)
     * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if(SwingUtilities.isRightMouseButton(e)){
            source = (Node) graphView.findNodeOrSpriteAt(e.getX(), e.getY());
            System.out.println("edgeSource : "+source);
        }
    }
    
    /**
     * Gets the node on which the right click was released and sets it as target, if source or target is null does nothing
     * Else if no edge exists between theses nodes, creates one.
     * If an edge exists, deletes it.
     * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if(source != null){
            if(SwingUtilities.isRightMouseButton(e)){
                target = (Node) graphView.findNodeOrSpriteAt(e.getX(), e.getY());
                System.out.println("edgeTarget : "+target);
                if(target != null){
                    Edge edge = model.getEdge(source.getId()+""+target.getId()); 
                    // if the edge doesn't exist , creates it
                    if(edge == null){
                        model.addEdge(source.getId()+""+target.getId(), source.getId(), target.getId(),true);
                        model.getEdge(source.getId()+""+target.getId()).setAttribute("layout.weight", 20);
                    }
                    // else deletes it
                    else{
                        model.removeEdge(edge);
                    }
                }
            }
        }
    }
}
