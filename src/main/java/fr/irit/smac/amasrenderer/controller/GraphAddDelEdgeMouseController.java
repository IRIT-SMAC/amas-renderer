package fr.irit.smac.amasrenderer.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.ui.swingViewer.ViewPanel;

import fr.irit.smac.amasrenderer.Const;
import fr.irit.smac.amasrenderer.model.AgentGraph;
import fr.irit.smac.amasrenderer.service.GraphService;

/**
 * The Class GraphAddDelEdgeMouseControllerOld.
 * This controller controls addition and deletion of edges on the graph "model"
 * Creation and deletion are used by right-click dragging from one node to an other
 */
public class GraphAddDelEdgeMouseController extends MouseAdapter {

    /** The graph node service. */
    private GraphService graphNodeService;
    
    /** The graph view. */
    private ViewPanel  graphView;
    
    /** The source. */
    private Node source = null;
    
    /** The target. */
    private Node target = null;
    
    /** The button add edge. */
    private static boolean buttonAddEdge;
    
    /** The button del edge. */
    private static boolean buttonDelEdge;

    /**
     * Initialize the controller, and adds it to the graph.
     *
     * @param graphView the graph view
     * @param graphNodeService the graphNodeService
     */
    public void init(ViewPanel graphView, GraphService graphNodeService) {
        this.graphNodeService = graphNodeService;
        this.graphView = graphView;
        graphView.addMouseListener(this);
        buttonAddEdge = buttonDelEdge = false;
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
     */
    // the headache function
    @Override
    public void mouseClicked(MouseEvent e) {
        //if explanation:
        //if source = null --> its the first click
        //and one of the edge button is pressed or shift is down, and control is not down
        if((source == null) && (e.isShiftDown() || (buttonAddEdge ^ buttonDelEdge)) && (! e.isControlDown())){
            firstClick(e);
        } else if((source != null) && (e.isShiftDown() || (buttonAddEdge ^ buttonDelEdge)) && (! e.isControlDown())){
            //same but source not null so its the second click
            secondClick(e);
        }
        
    }
    
    /**
     * First click.
     *
     * @param e the e
     */
    //this first click sets the source var, and cleans the target var
    private void firstClick(MouseEvent e){
        source = (Node) graphView.findNodeOrSpriteAt(e.getX(), e.getY());
        if(source != null){
            if(!source.hasAttribute("ui.selected")){
                source.addAttribute("ui.selected");
            }
            target = null;
        }
    }
    
    //this second click gets the target var , if it is valid , checks how the used clicked on that node, and tries to add an edge (if addEdgeButton or shift + left click )
    /**
     * Second click.
     *
     * @param e the e
     */
    // or tries to delete it (if user did shift + right click or delEdgeButton + left click )
    private void secondClick(MouseEvent e){
        target = (Node) graphView.findNodeOrSpriteAt(e.getX(), e.getY());
        if(target != null){
            AgentGraph model = this.graphNodeService.getModel();
            Edge edge = model.getEdge(source.getId()+""+target.getId());
            if((edge != null) && removeEdgeClickRequirement(e)){
                model.removeEdge(edge);
            } else if(edge == null && (SwingUtilities.isLeftMouseButton(e) && (e.isShiftDown() || buttonAddEdge ))) {
                model.addEdge(source.getId()+""+target.getId(), source.getId(), target.getId(),true);
                model.getEdge(source.getId()+""+target.getId()).setAttribute("layout.weight", Const.LAYOUT_WEIGHT_EDGE);
            }
            source.addAttribute("ui.selected");
            source.removeAttribute("ui.selected");
            source = null;
            
        }
    }
    
    /**
     * Removes the edge click requirement.
     *
     * @param e the e
     * @return true, if successful
     */
    private boolean removeEdgeClickRequirement(MouseEvent e){
        return (!e.isControlDown()) && ((e.isShiftDown() && SwingUtilities.isRightMouseButton(e))^(SwingUtilities.isLeftMouseButton(e) && buttonDelEdge));
    }
    
    /**
     * Sets the button add edge.
     *
     * @param buttonAddEdge the new button add edge
     */
    public void setButtonAddEdge(boolean buttonAddEdge) {
        this.buttonAddEdge = buttonAddEdge;
    }
    
    /**
     * Sets the button del edge.
     *
     * @param buttonDelEdge the new button del edge
     */
    public void setButtonDelEdge(boolean buttonDelEdge) {
        this.buttonDelEdge = buttonDelEdge;
    }
    
        
}
