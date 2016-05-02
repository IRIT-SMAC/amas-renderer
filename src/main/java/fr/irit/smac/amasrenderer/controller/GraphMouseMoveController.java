package fr.irit.smac.amasrenderer.controller;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import org.graphstream.ui.geom.Point3;
import org.graphstream.ui.swingViewer.ViewPanel;

/**
 * The Class GraphMouseMoveController.
 * DEPRECATED , pan the graph using the mouse wheel (GraphMouseWheelController)
 * This controller is used to pan the graph on left click drag
 * BUG: makes the graph flicker for whatever reason
 */
public class GraphMouseMoveController extends MouseAdapter{

    /** The graph view. */
    private ViewPanel graphView;

    /** The source. */
    private Point3 source;

    /** The target. */
    private Point3 target;

    /** The center. */
    private Point3 center;

    /** The difference. */
    private Point3 difference;

    /** The x source. */
    double xSource;

    /** The y source. */
    double ySource;

    /** The x target. */
    double xTarget;

    /** The y target. */
    double yTarget;

    /** The x diff. */
    double xDiff;

    /** The y diff. */
    double yDiff;

    /** The x center. */
    double xCenter;

    /** The y center. */
    double yCenter;
    
    
    
    /**
     * Instantiates a new graph mouse move controller.
     *
     * @param graphView the graph view
     */
    public GraphMouseMoveController(ViewPanel graphView){
        this.graphView = graphView;
    }
    
    /**
     * On mouse press, gets the source of the panning .
     *
     * @param e the e
     * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if(SwingUtilities.isLeftMouseButton(e) && ! e.isShiftDown()) {
            source = graphView.getCamera().transformPxToGu(e.getPoint().getX(), e.getPoint().getY());
            xSource = source.x;
            ySource = source.y;
        }
    }
    
    /**
     * On mouse dragged, pan the graph using panGraph or panGraph2.
     *
     * @param e the e
     * @see java.awt.event.MouseAdapter#mouseDragged(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseDragged (MouseEvent e) {    
        panGraph2(e);
    }
    
   /**
    * Pan graph.
    * updates the target with mouse location and define the difference between source and target
    * then shifts the center of the graph by the same vector as the mouse start - mouse actual location 
    * @param e the e
    */
    void panGraph(MouseEvent e){
        if(SwingUtilities.isLeftMouseButton(e) && ! e.isShiftDown()){
            target = graphView.getCamera().transformPxToGu(e.getX(), e.getY());
            difference.x = source.x - target.x;
            difference.y = source.y - target.y;
            difference.z = source.z - target.z;
            center = graphView.getCamera().getViewCenter();
            center.x += difference.x;
            center.y += difference.y;
            center.z += difference.z;
            graphView.getCamera().setViewCenter(center.x, center.y, center.z);
        }
    }
    
    /**
     * Pan graph2.
     * updates the target with mouse location and define the difference between source and target
     * then sets the center of the graph to the difference between source and target
     * @param e the e
     */
    void panGraph2(MouseEvent e){
        if(SwingUtilities.isLeftMouseButton(e) && ! e.isShiftDown()){
            target = graphView.getCamera().transformPxToGu(e.getX(), e.getY());
            xTarget = target.x;
            yTarget = target.y;
            xDiff = xSource - xTarget;
            yDiff = ySource - yTarget;
            center = graphView.getCamera().getViewCenter();
            graphView.getCamera().setViewCenter(xDiff, yDiff, 0);
        }
    }
    
    
    
    

}
