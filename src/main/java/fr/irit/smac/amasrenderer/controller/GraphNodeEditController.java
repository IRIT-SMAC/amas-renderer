package fr.irit.smac.amasrenderer.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import org.graphstream.graph.Node;
import org.graphstream.ui.geom.Point3;
import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.swingViewer.ViewPanel;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class GraphNodeEditController extends MouseAdapter{
    
    private ViewPanel graphView;
    
    private Point3            clicCoord;

    @FXML
    private TextArea attributes_synthesis;
    
    public void init(ViewPanel graphView) {
        this.graphView = graphView;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 1) {
            clicCoord = graphView.getCamera().transformPxToGu(e.getX(), e.getY());
            GraphicElement elt = graphView.findNodeOrSpriteAt(e.getX(), e.getY());
            if (elt != null) {
                if (elt instanceof Node) {
                    String nodeSyntesis = "";
                    Node node = (Node) elt;
                    nodeSyntesis += "=========================================\n"
                        + "Node clicked: " + node.getId() + "\n"
                        + "Informations:\n"
                        + "--Attribute count: " + node.getAttributeCount() + "\n"
                        + "--Edges In: " + node.getEnteringEdgeSet().size() + "\n"
                        + "--Edges Out: " + node.getOutDegree() + "\n";

                    for (String attr : node.getEachAttributeKey()) {
                        if (attr != "xyz") {
                            nodeSyntesis += "--" + attr + ": " + node.getAttribute(attr).toString() + "\n";
                        }
                    }
                    nodeSyntesis += "=========================================\n";
                    this.setAttributes_synthesis(nodeSyntesis);
                }
            }
        }
    }
    
    @FXML
    public void ajouterAttribut(){
        
    }
    
    public void setAttributes_synthesis(String text) {
        attributes_synthesis.setText(text);
    }
    public String getAttributes_synthesis() {
        return attributes_synthesis.getText();
    }
}
