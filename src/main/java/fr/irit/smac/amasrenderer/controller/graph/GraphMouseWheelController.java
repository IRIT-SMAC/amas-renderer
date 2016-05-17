package fr.irit.smac.amasrenderer.controller.graph;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import org.graphstream.ui.geom.Point3;
import org.graphstream.ui.swingViewer.ViewPanel;

import fr.irit.smac.amasrenderer.Const;

/**
 * The Class GraphMouseWheelController. This controller controls zooming Use the
 * mouse wheel to zoom and de-zoom , it will zoom towards the mouse location, so
 * its is useful to move the graph around
 */
public class GraphMouseWheelController implements MouseWheelListener {

    /** The graph view. */
    private ViewPanel graphView;

    /**
     * Inits the controller and adds it to the graph.
     *
     * @param graphView
     *            the graph view
     */
    public void init(ViewPanel graphView) {
        this.graphView = graphView;
        graphView.addMouseWheelListener(this);
    }

    /**
     * On mouse Wheel moved, zooms or unzoom depending on the motion, and gets
     * the center closer to mouse location.
     *
     * @param e
     *            the e
     * @see java.awt.event.MouseWheelListener#mouseWheelMoved(java.awt.event.MouseWheelEvent)
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        Double scale = graphView.getCamera().getViewPercent();
        if (e.getWheelRotation() >= 0) {
            graphView.getCamera().setViewPercent(scale * Const.SCALE_ZOOM_RATIO);
        }
        else {
            graphView.getCamera().setViewPercent(scale * Const.SCALE_UNZOOM_RATIO);
        }
        Point3 newCenter = graphView.getCamera().getViewCenter()
            .interpolate(graphView.getCamera().transformPxToGu(e.getX(), e.getY()), Const.TRANSLATE_ZOOM_RATIO);
        graphView.getCamera().setViewCenter(newCenter.x, newCenter.y, newCenter.z);
    }
}
