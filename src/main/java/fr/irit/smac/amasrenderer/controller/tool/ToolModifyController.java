package fr.irit.smac.amasrenderer.controller.tool;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;

import fr.irit.smac.amasrenderer.Const;
import fr.irit.smac.amasrenderer.Main;
import fr.irit.smac.amasrenderer.service.ToolService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.converter.DefaultStringConverter;

/**
 * The Class TreeModifyController. Manage the modal window opening to modify
 * attributes
 */
public class ToolModifyController implements Initializable {

    private Stage stage;
    
    
    
    @FXML
    private Button confButton;

    @FXML
    private Button cancButton;

    @FXML
    private Button delButton;

    @FXML
    private TreeView<String> tree;

    private TreeItem<String> value;

    private Stage dialogStage;

    private String key;

    private ListView<String> list;

    private String baseRootName;

    private String newServiceName = null;
    
    private boolean supressConfirm = false;

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
     * sets the node to be modified
     * 
     * @param node
     *            the node to modify
     */
    public void setKey(String label) {
        this.key = label;
    }

    /**
     * Sets the map of the attributes
     * 
     * @param attributeMap
     *            the attributeMap
     */
    public void setAttributeMap(Map<String, TreeItem<String>> attributeMap) {
        ToolService.getInstance().setAttributesMap(attributeMap);
    }

    /**
     * Initialize the controller
     * 
     * @param attributeMap
     *            the map of attributes
     * @param list
     *            the list of tools
     */
    public void init(Map<String, TreeItem<String>> attributeMap, ListView<String> list) {
        setAttributeMap(attributeMap);
        setKey(list.getSelectionModel().getSelectedItem());
        this.list = list;
        tree.setRoot(deepcopy(attributeMap.get(key)));
        baseRootName = key;
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
        value = tree.getRoot();
        ToolService.getInstance().getAttributes().put(key, value);
        newServiceName = tree.getRoot().getValue();
        if (newServiceName != baseRootName) {
            list.getItems().remove(key);
            key = newServiceName;
            list.getItems().add(key);
            System.out.println(key);
            ToolService.getInstance().getAttributes().put(key,
                ToolService.getInstance().getAttributes().remove(baseRootName));
        }
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

    /**
     * Deepcopy. recursively copies the tree, to be able to modify it without
     * changing the original
     * 
     * @param item
     *            the the tree to copy
     * @return a copy of the tree item the tree item
     */
    private TreeItem<String> deepcopy(TreeItem<String> item) {
        TreeItem<String> copy = new TreeItem<>(item.getValue());
        for (TreeItem<String> child : item.getChildren()) {
            copy.getChildren().add(deepcopy(child));
        }
        return copy;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.tree.setEditable(true);
        tree.setCellFactory(p -> new ToolAttributesTreeCell(tree));
    }

    private static class ToolAttributesTreeCell extends TextFieldTreeCell<String> {
        private ContextMenu menu = new ContextMenu();
        private TreeView<String> tree;
        public ToolAttributesTreeCell(TreeView<String> tree) {
            super(new DefaultStringConverter());
            this.tree = tree;
            menu.setId("treeAttributeItem");
            MenuItem renameItem = new MenuItem("Renommer");
            renameItem.setId("renameAttributeItem");
            menu.getItems().add(renameItem);

            renameItem.setOnAction(e -> startEdit());

            MenuItem addItem = new MenuItem("Ajouter");
            menu.getItems().add(addItem);
            addItem.setId("addAttributeItem");
            addItem.setOnAction(e -> getTreeItem().getChildren().add(new TreeItem<String>("Nouvel attribut")));

            MenuItem removeItem = new MenuItem("Supprimer");
            menu.getItems().add(removeItem);
            removeItem.setId("removeAttributeItem");
            removeItem.setOnAction(e -> {
                if(!isProtectedField(getItem())){
                    getTreeItem().getParent().getChildren().remove(getTreeItem());
                }
            });
        }

        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (!isEditing()) {
                setContextMenu(menu);
            }
        }
        /**
         * test if the string new value is valid
         * @param newValue
         * @return
         */
        private boolean isValidExpression(TreeItem<String> item, String newValue){
            boolean isSemiColonCorrect = false;
            boolean isValueKeyCorrect = false;
            //checks if the new value has the good key-value séparator ( and only one )
            if(item.getChildren().size() == 0){
                isSemiColonCorrect = newValue.split(Const.KEY_VALUE_SEPARATOR).length == 2; 
                if(isSemiColonCorrect) isValueKeyCorrect = !newValue.split(Const.KEY_VALUE_SEPARATOR)[0].trim().isEmpty() && !newValue.split(Const.KEY_VALUE_SEPARATOR)[1].trim().isEmpty();
            } else { // if it has childs then its a complex attribute
                isSemiColonCorrect = !(newValue.contains(":") || newValue.contains(" ")); 
                isValueKeyCorrect = !newValue.trim().isEmpty();
            }
            //checks if the new value contains an unauthorized string
            boolean containsNewUnauthorizedString = false;
            for(String str : Const.UNAUTHORISED_STRING){
                containsNewUnauthorizedString = newValue.contains(str) ? true : containsNewUnauthorizedString; 
            }
            
            return isSemiColonCorrect && isValueKeyCorrect && !containsNewUnauthorizedString;
        }
        
        private boolean isProtectedField(String oldValue){
          //checks if the oldValue was protected
            boolean oldContainsProtectedString = false;
            for(String str : Const.PROTECTED_STRING){
                oldContainsProtectedString = oldValue.contains(str) ? true : oldContainsProtectedString;
            }
            return oldContainsProtectedString;
        }
        
        @Override
        public void startEdit() {
            if(isProtectedField(this.getItem())){
                this.cancelEdit();
            }
            else{
                super.startEdit();
            }
        };
        
        @Override
        public void commitEdit(String newValue) {
            if (newValue.trim().isEmpty()) {
                return;
            }
            else if(this.getTreeItem() != tree.getRoot() && !isValidExpression(getTreeItem(),newValue)){
                return;
            }
            
            super.commitEdit(newValue);
        }

    }
    
    public void loadFxml(){
        stage = new Stage();
        stage.setTitle("Ajouter un service");
        stage.setResizable(false);

        Main.getMainStage().getScene().lookup("#rootLayout").getStyleClass().add("secondaryWindow");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/ConfirmationDialog.fxml"));
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
            confimDialogController.setList(list);
            confimDialogController.setKey(key);
            confimDialogController.setDialogStage(dialogStage);
            stage.setScene(myScene);
            
            stage.showAndWait();
           
        } catch(IOException e){
            e.printStackTrace();
            System.out.println("rage");
        }
    }

}