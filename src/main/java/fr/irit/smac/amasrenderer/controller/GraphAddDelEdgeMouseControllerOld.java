package fr.irit.smac.amasrenderer.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.ui.swingViewer.ViewPanel;

import fr.irit.smac.amasrenderer.Const;
import fr.irit.smac.amasrenderer.model.AgentGraph;

/**
 * The Class GraphAddDelEdgeMouseControllerOld.
 * This controller controls addition and deletion of edges on the graph "model"
 * Creation and deletion are used by right-click dragging from one node to an other
 * DEPRECATED USE THE NEW ONE: GraphAddDelEdgeMouseController
 */
public class GraphAddDelEdgeMouseControllerOld extends MouseAdapter {

    /** The graph view. */
    private ViewPanel  graphView;
    
    /** The model. */
    private AgentGraph model;

    /** The source. */
    private Node source = null;
    
    /** The target. */
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
     * Gets the node on which the right mouse button was pressed and sets it as source(to prepare for mouseReleased).
     *
     * @param e the e
     * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if(SwingUtilities.isRightMouseButton(e)){
            source = (Node) graphView.findNodeOrSpriteAt(e.getX(), e.getY());
        }
    }
    
    /**
     * Gets the node on which the right click was released and sets it as target, if source or target is null does nothing
     * Else if no edge exists between theses nodes, creates one.
     * If an edge exists, deletes it.
     *
     * @param e the e
     * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if(source != null && SwingUtilities.isRightMouseButton(e)){
            target = (Node) graphView.findNodeOrSpriteAt(e.getX(), e.getY());
            if(target != null){
                Edge edge = model.getEdge(source.getId()+""+target.getId()); 
                // if the edge doesn't exist , creates it
                if(edge == null){
                    model.addEdge(source.getId()+""+target.getId(), source.getId(), target.getId(),true);
                    model.getEdge(source.getId()+""+target.getId()).setAttribute("layout.weight", Const.LAYOUT_WEIGHT_EDGE);
                } else {
                    // else deletes it
                    model.removeEdge(edge);
                }
            }
        }
    }
}
