package fr.irit.smac.amasrenderer.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.swingViewer.ViewPanel;

import fr.irit.smac.amasrenderer.model.Stock;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * The Class GraphNodeEditController.
 * This controller gets node information on click, it is linked to the Stock class
 */
public class GraphNodeEditController extends MouseAdapter{
    
    /** The graph view. */
    private ViewPanel graphView;
    private Stage dialogStage;
//    @FXML
//    private TreeView<String> tree;
    
    public GraphNodeEditController() {
        
    }
    
    /**
     * Inits the controller.
     *
     * @param graphView the graph view
     */
    public void init(ViewPanel graphView, Stage dialogStage) {
        this.graphView = graphView;
        this.graphView.addMouseListener(this);
        this.dialogStage = dialogStage;
        System.out.println(dialogStage);

    }
    
    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
    }
    
    //TODO modify method to return the stock class
    /**
     *  
     * 
     * On mouse press, if there is a node on clic location gets that node and ( for now ) return a string with node information in it
     * displays it in a text area.
     * 
     * @param e the e
     * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 1) {
            GraphicElement elt = graphView.findNodeOrSpriteAt(e.getX(), e.getY());
            if (elt != null && elt instanceof Node) {
                Node node = (Node) elt;
                Stock s = node.getAttribute("ui.stocked-info");
                
                Platform.runLater(new Runnable() {
                    @Override public void run() {
                        dialogStage.showAndWait();
                           
                    }
                });
            }
        }
    }

}
