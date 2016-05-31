package fr.irit.smac.amasrenderer.controller.tool;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

import fr.irit.smac.amasrenderer.Const;
import fr.irit.smac.amasrenderer.Main;
import fr.irit.smac.amasrenderer.model.ToolModel;
import fr.irit.smac.amasrenderer.service.ToolService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.converter.DefaultStringConverter;

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

    private String key;

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
     * sets the node to be modified
     * 
     * @param node
     *            the node to modify
     */
    public void setKey(String label) {
        this.key = label;
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
        setKey(list.getSelectionModel().getSelectedItem().getName());
        this.list = list;
        this.tool = tool;
        
		TreeItem<String> myItem = new TreeItem<>(name);
		tree.setRoot(myItem);
		HashMap<String, Object> service = (HashMap<String, Object>) tool.getAttributesMap();
		fillToolAttributes(service, myItem);
    }

	@SuppressWarnings("unchecked")
	private void fillToolAttributes(HashMap<String, Object> tool, TreeItem<String> parent) {

		Iterator<Map.Entry<String, Object>> attributeIterator = tool.entrySet().iterator();
		while (attributeIterator.hasNext()) {
			Map.Entry<String, Object> attribute = attributeIterator.next();
			String name = attribute.getKey();
			Object value = attribute.getValue();

			if (value instanceof HashMap<?, ?>) {
				TreeItem<String> item = new TreeItem<>();
				item.setValue(name);
				fillToolAttributes((HashMap<String, Object>) value, item);
				parent.getChildren().add(item);
			} else {
				TreeItem<String> item = new TreeItem<>();
				item.setValue(name + " : " + value);
				parent.getChildren().add(item);
			}
		}
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
		
        ToolService.getInstance().updateToolsMap(tree.getRoot().getValue(), tree.getRoot(), tool);
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
        Stage stage = new Stage();
        stage.setTitle("Ajouter un service");
        stage.setResizable(false);

        dialogStage.getScene().lookup("#attributesServiceDialog").getStyleClass().add("secondaryWindow");

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
            confimDialogController.init(dialogStage, list.getSelectionModel().getSelectedIndex());
            stage.setScene(myScene);
            
            stage.showAndWait();
           
        } catch(IOException e){
            e.printStackTrace();
        }
    }

}