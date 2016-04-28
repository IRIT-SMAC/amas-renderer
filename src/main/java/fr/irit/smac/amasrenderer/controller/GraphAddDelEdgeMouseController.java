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
public class GraphAddDelEdgeMouseController extends MouseAdapter {

    private ViewPanel  graphView;
    
    private AgentGraph model;

    private Node source = null;
    
    private Node target = null;
    
    private boolean buttonAddEdge;
    private boolean buttonDelEdge;

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
        buttonAddEdge = buttonDelEdge = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if((source == null) && (e.isShiftDown() || (buttonAddEdge ^ buttonDelEdge))){
            source = (Node) graphView.findNodeOrSpriteAt(e.getX(), e.getY());
            if(!source.hasAttribute("ui.selected")){
                source.addAttribute("ui.selected");
            }
            target = null;
        }
        else{
            target = (Node) graphView.findNodeOrSpriteAt(e.getX(), e.getY());
            if(target != null){
                Edge edge = model.getEdge(source.getId()+""+target.getId());
                if((edge != null) && ((e.isShiftDown() && SwingUtilities.isRightMouseButton(e))^(SwingUtilities.isLeftMouseButton(e) && buttonDelEdge))){
                    model.removeEdge(edge);
                }
                else if((edge == null) && ((SwingUtilities.isLeftMouseButton(e) && (e.isShiftDown() || buttonAddEdge )))){
                    model.addEdge(source.getId()+""+target.getId(), source.getId(), target.getId(),true);
                    model.getEdge(source.getId()+""+target.getId()).setAttribute("layout.weight", 20);
                }
                source.addAttribute("ui.selected");
                source.removeAttribute("ui.selected");
                source = null;
                
            }
        }
        
    }
    
        
}
