package fr.irit.smac.amasrenderer.controller;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import org.graphstream.ui.geom.Point3;
import org.graphstream.ui.swingViewer.ViewPanel;

/**
 * The Class GraphMouseWheelController.
 * This controller controls zooming 
 * Use the mouse wheel to zoom and de-zoom , it will zoom towards the mouse location, so its is useful to move the graph around
 */
public class GraphMouseWheelController implements MouseWheelListener {

    private ViewPanel graphView;

    /**
     * Inits the controller and adds it to the graph
     *
     * @param graphView the graph view
     */
    public void init(ViewPanel graphView) {
        this.graphView = graphView;
        graphView.addMouseWheelListener(this);
    }

    /**
     * On mouse Wheel moved, zooms or unzoom depending on the motion, and gets the center closer to mouse location
     * @see java.awt.event.MouseWheelListener#mouseWheelMoved(java.awt.event.MouseWheelEvent)
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        Double scale = graphView.getCamera().getViewPercent();
        if (e.getWheelRotation() >= 0) {
            graphView.getCamera().setViewPercent(scale * 1.2);
        }
        else {
            graphView.getCamera().setViewPercent(scale * 0.8);
        }
        Point3 newCenter = graphView.getCamera().getViewCenter()
            .interpolate(graphView.getCamera().transformPxToGu(e.getX(), e.getY()), 0.2);
        graphView.getCamera().setViewCenter(newCenter.x, newCenter.y, newCenter.z);
    }
}