package fr.irit.smac.amasrenderer.controller;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import org.graphstream.ui.geom.Point3;
import org.graphstream.ui.swingViewer.ViewPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class GraphMouseMoveController.
 * DEPRECATED , pan the graph using the mouse wheel (GraphMouseWheelController)
 * This controller is used to pan the graph on left click drag
 * BUG: makes the graph flicker for whatever reason
 */
public class GraphMouseMoveController extends MouseAdapter{

    private ViewPanel graphView;

    private Point3 source;

    private Point3 target;

    private Point3 center;

    private Point3 difference;

    double xSource;

    double ySource;

    double xTarget;

    double yTarget;

    double xDiff;

    double yDiff;

    double xCenter;

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
     * On mouse press, gets the source of the panning 
     * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if(SwingUtilities.isLeftMouseButton(e) && ! e.isShiftDown()){
            source = graphView.getCamera().transformPxToGu(e.getPoint().getX(), e.getPoint().getY());
            xSource = source.x;
            ySource = source.y;
            System.out.println("test: "+xSource +':'+ySource);
        }
    }
    
    /**
     * On mouse dragged, pan the graph using panGraph or panGraph2
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
            System.out.println("target: "+target);
            System.out.println("source: "+source);
            difference = new Point3();
            difference.x = source.x - target.x;
            difference.y = source.y - target.y;
            difference.z = source.z - target.z;
            System.out.println("Difference: "+difference);
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
            System.out.println("target: "+xTarget+" : "+yTarget);
            System.out.println("test: "+xSource +':'+ySource);
            xDiff = xSource - xTarget;
            yDiff = ySource - yTarget;
            System.out.println("Difference: "+xDiff+" : "+yDiff);
            center = graphView.getCamera().getViewCenter();
            /*center.x += difference.x;
            center.y += difference.y;
            center.z += difference.z;*/
            graphView.getCamera().setViewCenter(xDiff, yDiff, 0);
            
        }
    }
    
    
    
    

}
