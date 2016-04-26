package fr.irit.smac.amasrenderer.controller;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import org.graphstream.ui.geom.Point3;
import org.graphstream.ui.swingViewer.ViewPanel;

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
    
    
    
    public GraphMouseMoveController(ViewPanel graphView){
        this.graphView = graphView;
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        if(SwingUtilities.isLeftMouseButton(e) && ! e.isShiftDown()){
            source = graphView.getCamera().transformPxToGu(e.getPoint().getX(), e.getPoint().getY());
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
