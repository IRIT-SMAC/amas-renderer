package fr.irit.smac.amasrenderer.controller;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import org.graphstream.ui.geom.Point3;

public class GraphMouseWheelController implements MouseWheelListener {
    
    private AmasRenderingController controller;
    
    public void init(AmasRenderingController controller) {
        this.controller = controller;
        controller.getGraphView().addMouseWheelListener(this);
    }
    
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        Double scale = controller.getGraphView().getCamera().getViewPercent();
        if(e.getWheelRotation() >= 0){
            controller.getGraphView().getCamera().setViewPercent(scale*1.2);
        }
        else{
            controller.getGraphView().getCamera().setViewPercent(scale*0.8);
        }
        Point3 newCenter = controller.getGraphView().getCamera().getViewCenter().interpolate(controller.getGraphView().getCamera().transformPxToGu(e.getX(), e.getY()), 0.2);
        controller.getGraphView().getCamera().setViewCenter(newCenter.x,newCenter.y,newCenter.z);
    }
}
