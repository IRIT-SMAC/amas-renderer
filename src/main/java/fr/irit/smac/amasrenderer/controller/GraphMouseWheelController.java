package fr.irit.smac.amasrenderer.controller;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import org.graphstream.ui.geom.Point3;
import org.graphstream.ui.swingViewer.ViewPanel;

public class GraphMouseWheelController implements MouseWheelListener {

    private ViewPanel graphView;

    public void init(ViewPanel graphView) {
        this.graphView = graphView;
        graphView.addMouseWheelListener(this);
    }

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
