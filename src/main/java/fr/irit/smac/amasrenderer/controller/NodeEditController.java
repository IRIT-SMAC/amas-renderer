package fr.irit.smac.amasrenderer.controller;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import org.graphstream.graph.Node;
import org.graphstream.ui.geom.Point3;
import org.graphstream.ui.graphicGraph.GraphicElement;

public class NodeEditController extends MouseAdapter {
    private AmasRenderingController controller;
    private Point3 clicCoord;
    
    
    
    public void init(AmasRenderingController controller) {
        this.controller = controller;
        controller.getGraphView().addMouseListener(this);
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        if(SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 1){
            clicCoord = controller.getGraphView().getCamera().transformPxToGu(e.getX(), e.getY());
            GraphicElement elt = controller.getGraphView().findNodeOrSpriteAt(e.getX(), e.getY());
            if(elt != null){
                if(elt instanceof Node){
                    String nodeSyntesis = "";
                    Node node = (Node) elt;
                    nodeSyntesis += "=========================================\n"
                            + "Node clicked: " + node.getId()+"\n"
                            + "Informations:\n"
                            + "--Attribute count: "+node.getAttributeCount()+"\n"
                            + "--Edges In: "+node.getEnteringEdgeSet().size()+"\n"
                            + "--Edges Out: "+node.getOutDegree()+"\n";
                   
                    for (String attr : node.getEachAttributeKey()) {
                        if(attr != "xyz"){
                            nodeSyntesis += "--"+attr+": "+node.getAttribute(attr).toString()+"\n";
                        }
                    }
                    nodeSyntesis += "=========================================\n";
                    System.out.println(nodeSyntesis);
                    controller.setAttributes_synthesis(nodeSyntesis);
                }
            }
        }
    }
}
