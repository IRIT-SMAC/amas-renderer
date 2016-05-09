package fr.irit.smac.amasrenderer.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.SwingUtilities;

import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.swingViewer.ViewPanel;

import fr.irit.smac.amasrenderer.Main;
import fr.irit.smac.amasrenderer.model.Stock;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

/**
 * The Class GraphNodeEditController.
 * This controller gets node information on click, it is linked to the Stock class
 */
public class GraphNodeEditController extends MouseAdapter{
    
    /** The graph view. */
    private ViewPanel graphView;
    /**The controller for the modal window*/
    private TreeModifyController treeModifyController;
    private Window window; 
    /*the modal window, in static for test purposes*/
    private static BorderPane root3;
    
    
    public GraphNodeEditController() {
        
    }
    
    /**
     * Inits the controller.
     *
     * @param graphView the graph view
     */
    public void init(ViewPanel graphView, Window window) {
        this.graphView = graphView;
        this.window = window;
        this.graphView.addMouseListener(this);

    }
    
    /**
     * gets the modal window, for testing purposes
     * @return the modal window
     */
    public static BorderPane getRoot3() {
        return root3;
    }
    
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
        if (SwingUtilities.isRightMouseButton(e) && e.isAltDown()) {
            GraphicElement elt = graphView.findNodeOrSpriteAt(e.getX(), e.getY());
            if (elt != null && elt instanceof Node) {
                Node node = (Node) elt;
                Stock s = node.getAttribute("ui.stocked-info");
                Platform.runLater(new Runnable() {
                    @Override public void run() {
                       
                        
                        FXMLLoader loaderServices = new FXMLLoader();
                        loaderServices.setLocation(Main.class.getResource("view/GraphAttributes.fxml"));
                        root3 = null;
                        try {
                            root3 = loaderServices.load();
                        }
                        catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        
                        TreeModifyController treeModifyController = loaderServices.getController();
                        
                        Stage dialogStage = new Stage();
                        dialogStage.setTitle("Modification d'attribut");
                        //fenetre modale, obligation de quitter pour revenir a la fenetre principale
                        dialogStage.initModality(Modality.WINDOW_MODAL);
                        dialogStage.initOwner(window);
                        Scene miniScene = new Scene(root3);
                        dialogStage.setScene(miniScene);
                        dialogStage.initStyle(StageStyle.UNIFIED);
                        dialogStage.setMinHeight(380);
                        dialogStage.setMinWidth(440);

                        treeModifyController.setStage(dialogStage);
                        treeModifyController.setStock(s);
                        
                        dialogStage.showAndWait();
                    }
                });
            }
        }
    }
    private void setupDialogStage(){
        
    }

}
