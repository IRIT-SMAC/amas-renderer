package fr.irit.smac.amasrenderer.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import org.graphstream.graph.Node;
import org.graphstream.ui.swingViewer.ViewPanel;

import fr.irit.smac.amasrenderer.model.AgentGraph;

public class GraphAddEdgeMouseController extends MouseAdapter {

    private ViewPanel  graphView;
    private int        currentEdgeId;
    private AgentGraph model;

    private Node source = null;
    private Node target = null;

    public void init(ViewPanel graphView, AgentGraph model) {
        this.graphView = graphView;
        this.model = model;
        this.currentEdgeId = model.getEdgeCount() + 1;
        graphView.addMouseListener(this);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            source = (Node) graphView.findNodeOrSpriteAt(e.getX(), e.getY());
            System.out.println("edgeSource : " + source);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (source != null) {
            if (SwingUtilities.isRightMouseButton(e)) {
                target = (Node) graphView.findNodeOrSpriteAt(e.getX(), e.getY());
                System.out.println("edgeTarget : " + target);
                if (target != null) {
                    model.addEdge(source + "" + target, source.getId(), target.getId(), true);
                }
                // graph.getViewer().enableAutoLayout();
            }
        }
    }
}
