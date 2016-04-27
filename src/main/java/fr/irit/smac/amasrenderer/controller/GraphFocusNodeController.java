package fr.irit.smac.amasrenderer.controller;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import org.graphstream.ui.geom.Point3;

/**
 * The Class GraphFocusNodeController.
 * Scroll wheel to move the graph is better, use GraphMouseWheelController instead
 * This controller is used to move the center of the graph to the mouse location
 * shift+click on the graph to set that point as the center
 */
public class GraphFocusNodeController extends MouseAdapter {
    
    private GraphMainController controller; 
    
    /**
     * Initialize the controller and adds it to the graph.
     *
     * @param controller the controller
     */
    public void init(GraphMainController controller) {
        this.controller = controller;
        controller.getGraphView().addMouseListener(this);
    }
    
    /**
     * gets the clic location and if shift is pressed sets that point as the center of the camera 
     * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        Point3 clicLocation = controller.getGraphView().getCamera().transformPxToGu(e.getX(), e.getY());
        if(e.isShiftDown()){
            controller.getGraphView().getCamera().setViewCenter(clicLocation.x, clicLocation.y, clicLocation.z);
        }
    }
}
