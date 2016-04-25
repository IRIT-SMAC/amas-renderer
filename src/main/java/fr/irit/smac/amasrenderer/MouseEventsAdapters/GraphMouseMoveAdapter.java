package fr.irit.smac.amasrenderer.MouseEventsAdapters;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import org.graphstream.ui.geom.Point3;

import fr.irit.smac.amasrenderer.controller.ControllerAmasRendering;

public class GraphMouseMoveAdapter extends MouseAdapter{

    private ControllerAmasRendering controller;
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
    
    
    
    public GraphMouseMoveAdapter(ControllerAmasRendering controller){
        this.controller = controller;
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        if(SwingUtilities.isLeftMouseButton(e) && ! e.isShiftDown()){
            source = controller.getGraphView().getCamera().transformPxToGu(e.getPoint().getX(), e.getPoint().getY());
            xSource = source.x;
            ySource = source.y;
            System.out.println("test: "+xSource +':'+ySource);
        }
    }
    
    @Override
    public void mouseDragged (MouseEvent e) {    
        panGraph2(e);
    }
    
   /* @Override
    public void mouseReleased(MouseEvent e) {
        panGraph(e);
    }
    */
    void panGraph(MouseEvent e){
        if(SwingUtilities.isLeftMouseButton(e) && ! e.isShiftDown()){
            target = controller.getGraphView().getCamera().transformPxToGu(e.getX(), e.getY());
            System.out.println("target: "+target);
            System.out.println("source: "+source);
            difference = new Point3();
            difference.x = source.x - target.x;
            difference.y = source.y - target.y;
            difference.z = source.z - target.z;
            System.out.println("Difference: "+difference);
            center = controller.getGraphView().getCamera().getViewCenter();
            center.x += difference.x;
            center.y += difference.y;
            center.z += difference.z;
            controller.getGraphView().getCamera().setViewCenter(center.x, center.y, center.z);
        }
    }
    
    void panGraph2(MouseEvent e){
        if(SwingUtilities.isLeftMouseButton(e) && ! e.isShiftDown()){
            target = controller.getGraphView().getCamera().transformPxToGu(e.getX(), e.getY());
            xTarget = target.x;
            yTarget = target.y;
            System.out.println("target: "+xTarget+" : "+yTarget);
            System.out.println("test: "+xSource +':'+ySource);
            xDiff = xSource - xTarget;
            yDiff = ySource - yTarget;
            System.out.println("Difference: "+xDiff+" : "+yDiff);
            center = controller.getGraphView().getCamera().getViewCenter();
            /*center.x += difference.x;
            center.y += difference.y;
            center.z += difference.z;*/
            controller.getGraphView().getCamera().setViewCenter(xDiff, yDiff, 0);
            
        }
    }
    
    
    
    

}
