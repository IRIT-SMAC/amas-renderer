package fr.irit.smac.amasrenderer.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.ui.swingViewer.ViewPanel;

import fr.irit.smac.amasrenderer.model.AgentGraph;
import fr.irit.smac.amasrenderer.service.GraphService;
import javafx.scene.control.ToggleButton;

/**
 * The Class GraphAddDelEdgeMouseController_Old.
 * This controller controls addition and deletion of edges on the graph "model"
 * Creation and deletion are used by right-click dragging from one node to an other
 */
public class GraphAddDelEdgeMouseController extends MouseAdapter {

    private GraphService graphNodeService;
    private ViewPanel  graphView;
    
    private Node source = null;
    
    private Node target = null;
    

    private ToggleButton buttonAddEdge;
    private ToggleButton buttonDelEdge;
    
    /**
     * Initialize the controller, and adds it to the graph.
     *
     * @param graphView the graph view
     * @param model the model (AgentGraph)
     */
    public void init(ViewPanel graphView, GraphService graphNodeService) {
        this.graphNodeService = graphNodeService;
        this.graphView = graphView;
        graphView.addMouseListener(this);
    }
    
    // the headache function
    @Override
    public void mouseClicked(MouseEvent e) {
        //if explanation:
        //if source = null --> its the first click
        //and one of the edge button is pressed or shift is down, and control is not down
        if((source == null) && (e.isShiftDown() || (this.buttonAddEdge.isSelected() ^ this.buttonDelEdge.isSelected())) && (! e.isControlDown())){
            firstClick(e);
        }
        //same but source not null so its the second click
        else if((source != null) && (e.isShiftDown() || (this.buttonAddEdge.isSelected() ^ this.buttonDelEdge.isSelected())) && (! e.isControlDown())){
            secondClick(e);
        }
        
    }
    
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
    // or tries to delete it (if user did shift + right click or delEdgeButton + left click )
    private void secondClick(MouseEvent e){
        target = (Node) graphView.findNodeOrSpriteAt(e.getX(), e.getY());
        if(target != null){
            
            AgentGraph model = this.graphNodeService.getModel();
            Edge edge = model.getEdge(source.getId()+""+target.getId());
            if((edge != null) && (!e.isControlDown()) && ((e.isShiftDown() && SwingUtilities.isRightMouseButton(e))^(SwingUtilities.isLeftMouseButton(e) && this.buttonDelEdge.isSelected()))){
                model.removeEdge(edge);
            }
            else if((edge == null) && ((SwingUtilities.isLeftMouseButton(e) && (e.isShiftDown() || this.buttonAddEdge.isSelected() )))){
                model.addEdge(source.getId()+""+target.getId(), source.getId(), target.getId(),true);
                model.getEdge(source.getId()+""+target.getId()).setAttribute("layout.weight", 200);
            }
            source.addAttribute("ui.selected");
            source.removeAttribute("ui.selected");
            source = null;
            
        }
    }
    
    public void setButtonAddEdge(ToggleButton buttonAddEdge) {
        this.buttonAddEdge = buttonAddEdge;
    }
    
    public void setButtonDelEdge(ToggleButton buttonDelEdge) {
        this.buttonDelEdge = buttonDelEdge;
    }
}
