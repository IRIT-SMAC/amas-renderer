package fr.irit.smac.amasrenderer.MouseEventsAdapters;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import org.graphstream.ui.geom.Point3;

import fr.irit.smac.amasrenderer.controller.ControllerAmasRendering;

public class GraphMouseWheelListener implements MouseWheelListener {
    
    private ControllerAmasRendering controller;
    
    public void init(ControllerAmasRendering controller) {
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
