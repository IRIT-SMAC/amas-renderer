package fr.irit.smac.amasrenderer.controller;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import org.graphstream.ui.geom.Point3;

public class FocusNodeController extends MouseAdapter {
    private AmasRenderingController controller; 
    
    public void init(AmasRenderingController controller) {
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
