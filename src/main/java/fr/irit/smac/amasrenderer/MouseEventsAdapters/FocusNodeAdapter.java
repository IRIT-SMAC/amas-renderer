package fr.irit.smac.amasrenderer.MouseEventsAdapters;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import org.graphstream.ui.geom.Point3;

import fr.irit.smac.amasrenderer.controller.ControllerAmasRendering;

public class FocusNodeAdapter extends MouseAdapter {
    private ControllerAmasRendering controller; 
    
    public void init(ControllerAmasRendering controller) {
        this.controller = controller;
        controller.getGraphView().addMouseListener(this);
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        Point3 clicLocation = controller.getGraphView().getCamera().transformPxToGu(e.getX(), e.getY());
        if(e.isShiftDown()){
            controller.getGraphView().getCamera().setViewCenter(clicLocation.x, clicLocation.y, clicLocation.z);
        }
    }
}
