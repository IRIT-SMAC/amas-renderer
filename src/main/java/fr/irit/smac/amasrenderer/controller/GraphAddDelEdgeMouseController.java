package fr.irit.smac.amasrenderer.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.ui.swingViewer.ViewPanel;

import fr.irit.smac.amasrenderer.model.AgentGraph;

public class GraphAddDelEdgeMouseController extends MouseAdapter {

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
        if(SwingUtilities.isRightMouseButton(e)){
            source = (Node) graphView.findNodeOrSpriteAt(e.getX(), e.getY());
            System.out.println("edgeSource : "+source);
        }
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        if(source != null){
            if(SwingUtilities.isRightMouseButton(e)){
                target = (Node) graphView.findNodeOrSpriteAt(e.getX(), e.getY());
                System.out.println("edgeTarget : "+target);
                if(target != null){
                    Edge edge = model.getEdge(source.getId()+""+target.getId()); 
                    // si l'edge n'existe pas encore on le crée
                    if(edge == null){
                        model.addEdge(source.getId()+""+target.getId(), source.getId(), target.getId(),true);
                        model.getEdge(source.getId()+""+target.getId()).setAttribute("layout.weight", 20);
                    }
                    // si il existe on le supprime
                    else{
                        model.removeEdge(edge);
                    }
                }
            }
        }
    }
}
