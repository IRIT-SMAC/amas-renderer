package fr.irit.smac.amasrenderer.controller.tool;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import fr.irit.smac.amasrenderer.Main;
import fr.irit.smac.amasrenderer.controller.menu.MenuAttributesTreeCellController;
import fr.irit.smac.amasrenderer.model.ToolModel;
import fr.irit.smac.amasrenderer.service.AttributesService;
import fr.irit.smac.amasrenderer.service.ToolService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

/**
 * The Class TreeModifyController. Manage the modal window opening to modify
 * attributes
 */
public class ToolAttributesController implements Initializable {
    
    @FXML
    private Button confButton;

    @FXML
    private Button cancButton;

    @FXML
    private Button delButton;

    @FXML
    private TreeView<String> tree;

    private Stage dialogStage;

    private ListView<ToolModel> list;

    private ToolModel tool;

    /**
     * Sets the stage.
     *
     * @param stage
     *            the new stage
     */
    public void setStage(Stage stage) {
        dialogStage = stage;
    }
    
    /**
     * Initialize the controller
     * 
     * @param attributeMap
     *            the map of attributes
     * @param list
     *            the list of tools
     */
    public void init(ListView<ToolModel> list, String name, ToolModel tool) {

        this.list = list;
        this.tool = tool;
		TreeItem<String> myItem = new TreeItem<>(name);
		tree.setRoot(myItem);
		HashMap<String, Object> service = (HashMap<String, Object>) tool.getAttributesMap();
		AttributesService.getInstance().fillAttributes(service, myItem);
    }
	
    /**
     * deletes the service ( no confirmation )
     */
    @FXML
    public void deleteButton() {
        
        Platform.runLater(() -> loadFxml());
    }

    /**
     * Confirm button. sets the new tree as the node tree, and exit this window
     */
    @FXML
    public void confirmButton() {
        tree.getRoot();
		
        AttributesService.getInstance().updateAttributesMap(tree.getRoot().getValue(), tree.getRoot(), tool.getAttributesMap());
        Main.getMainStage().getScene().lookup("#rootLayout").getStyleClass().remove("secondaryWindow");
        dialogStage.close();
    }

    /**
     * Cancel button. just exit this window
     */
    @FXML
    public void cancelButton() {
        Main.getMainStage().getScene().lookup("#rootLayout").getStyleClass().remove("secondaryWindow");
        dialogStage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.tree.setEditable(true);
        tree.setCellFactory(p -> new MenuAttributesTreeCellController(tree));
    }
   
    public void loadFxml(){
        Stage stage = new Stage();
        stage.setTitle("Ajouter un service");
        stage.setResizable(false);

        dialogStage.getScene().lookup("#attributesServiceDialog").getStyleClass().add("secondaryWindow");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/tool/ConfirmationDialog.fxml"));
        try{
            
            DialogPane root = (DialogPane) loader.load();
            stage.initModality(Modality.WINDOW_MODAL);
            Window window = delButton.getScene().getWindow();
            stage.initOwner(delButton.getScene().getWindow());
            stage.initStyle(StageStyle.UNDECORATED);
            ConfirmationDialogController confimDialogController = loader.getController();
            Scene myScene = new Scene(root);
            
            double x = window.getX() + (window.getWidth() - root.getPrefWidth()) / 2;
            double y = window.getY() + (window.getHeight() - root.getPrefHeight()) / 2;
            stage.setX(x);
            stage.setY(y);
            confimDialogController.init(dialogStage, list.getSelectionModel().getSelectedIndex(), list.getSelectionModel().getSelectedItem().getName());
            stage.setScene(myScene);
            
            stage.showAndWait();
           
        } catch(IOException e){
            e.printStackTrace();
        }
    }

}