package fr.irit.smac.amasrenderer.controller.graph;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;

import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.swingViewer.ViewPanel;

import fr.irit.smac.amasrenderer.Main;
import fr.irit.smac.amasrenderer.controller.attribute.TreeModifyController;
import fr.irit.smac.amasrenderer.controller.tool.ToolController;
import fr.irit.smac.amasrenderer.model.StockModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

/**
 * The Class GraphNodeEditController. This controller gets node information on
 * click, it is linked to the Stock class
 */
public class GraphNodeEditController extends MouseAdapter {

    private ViewPanel graphView;

    private Window window;

    @FXML
    private TextArea attributeSynthesis;

    private static final Logger LOGGER = Logger.getLogger(ToolController.class.getName());

    public GraphNodeEditController() {

    }

    /**
     * Inits the controller.
     *
     * @param graphView
     *            the graph view
     * @param window
     *            the parent window
     */
    public void init(ViewPanel graphView, Window window) {
        this.graphView = graphView;
        this.window = window;
        this.graphView.addMouseListener(this);

    }

    /**
     * 
     * 
     * On mouse press, if there is a node on clic location gets that node and (
     * for now ) return a string with node information in it displays it in a
     * text area.
     * 
     * @param e
     *            the e
     * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e) && !e.isShiftDown() && !e.isControlDown()) {

            GraphicElement elt = graphView.findNodeOrSpriteAt(e.getX(), e.getY());
            if (elt != null && elt instanceof Node) {
                Node node = (Node) elt;
                StockModel s = node.getAttribute("ui.stocked-info");
                Platform.runLater(() -> {

                    FXMLLoader loaderServices = new FXMLLoader();

                    loaderServices.setLocation(Main.class.getResource("view/GraphAttributes.fxml"));
                    try {
                        BorderPane root = loaderServices.load();

                        TreeModifyController treeModifyController = loaderServices.getController();

                        Stage dialogStage = new Stage();
                        dialogStage.setTitle("Modification d'attribut");

                        dialogStage.initModality(Modality.WINDOW_MODAL);
                        dialogStage.initOwner(window);
                        Scene miniScene = new Scene(root);
                        dialogStage.setScene(miniScene);
                        dialogStage.initStyle(StageStyle.UNDECORATED);

                        double x = window.getX() + (window.getWidth() - root.getPrefWidth()) / 2;
                        double y = window.getY() + (window.getHeight() - root.getPrefHeight()) / 2;
                        dialogStage.setX(x);
                        dialogStage.setY(y);

                        treeModifyController.setStage(dialogStage);
                        treeModifyController.setStock(s);
                        treeModifyController.setNode(node);

                        dialogStage.showAndWait();
                    }
                    catch (IOException e2) {
                        LOGGER.log(Level.SEVERE, "The loading of the graph attributes fxml failed", e2);
                    }

                });

            }
        }
    }
}