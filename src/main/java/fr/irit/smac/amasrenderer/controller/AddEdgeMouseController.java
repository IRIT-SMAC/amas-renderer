package fr.irit.smac.amasrenderer.controller;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import org.graphstream.graph.Node;

public class AddEdgeMouseController extends MouseAdapter {

    private AmasRenderingController controller;
    private int currentEdgeId;
    
    private Node source = null;
    private Node target = null;
    
    public void init(AmasRenderingController controller) {
        this.controller = controller;
        this.currentEdgeId = controller.getModel().getEdgeCount() + 1;
        controller.getGraphView().addMouseListener(this);
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        if(SwingUtilities.isRightMouseButton(e)){
            source = (Node) controller.getGraphView().findNodeOrSpriteAt(e.getX(), e.getY());
            System.out.println("edgeSource : "+source);
        }
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        if(source != null){
            if(SwingUtilities.isRightMouseButton(e)){
                target = (Node) controller.getGraphView().findNodeOrSpriteAt(e.getX(), e.getY());
                System.out.println("edgeTarget : "+target);
                if(target != null)
                    controller.getModel().addEdge(source+""+target, source.getId(), target.getId(),true);
                //graph.getViewer().enableAutoLayout();
            }
        }
    }
}
