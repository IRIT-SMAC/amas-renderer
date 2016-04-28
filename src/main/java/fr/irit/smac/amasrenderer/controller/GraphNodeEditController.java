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

/**
 * The Class GraphNodeEditController.
 * This controller gets node information on click, it is linked to the Stock class
 */
public class GraphNodeEditController extends MouseAdapter{
    
    private ViewPanel graphView;
    
    /** The attribute synthesis. (for display) */
    @FXML
    private TextArea attributes_synthesis;
    
    /**
     * Inits the controller.
     *
     * @param graphView the graph view
     */
    public void init(ViewPanel graphView) {
        this.graphView = graphView;
    }
    
    //TODO modify method to return the stock class
    /** 
     *
     * On mouse press, if there is a node on clic location gets that node and ( for now ) return a string with node information in it
     * displays it in a text area
     * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 1) {
            GraphicElement elt = graphView.findNodeOrSpriteAt(e.getX(), e.getY());
            if (elt != null) {
                if (elt instanceof Node) {
                    String nodeSyntesis = "";
                    Node node = (Node) elt;
                    nodeSyntesis += "=========================================\n"
                        + "Node clicked: " + node.getId() + "\n"
                        + "Informations:\n";

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
    
    /**
     * Ajouter attribut.
     */
    @FXML
    public void ajouterAttribut(){
        
    }
    
    /**
     * Sets the attributes_synthesis. text area
     *
     * @param text the new attributes_synthesis
     */
    public void setAttributes_synthesis(String text) {
        attributes_synthesis.setText(text);
    }
    
    /**
     * Gets the attributes_synthesis. text area
     *
     * @return the attributes_synthesis
     */
    public String getAttributes_synthesis() {
        return attributes_synthesis.getText();
    }
}
