package fr.irit.smac.amasrenderer.controller.tool;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.DefaultStringConverter;

/**
 * The Class TreeModifyController. Manage the modal window opening to modify
 * attributes
 */
public class ToolModifyController implements Initializable {

    @FXML
    private Button confButton;

    @FXML
    private Button cancButton;

    @FXML
    private TreeView<String> tree;

    private TreeItem<String> value;

    private HashMap<Label,TreeItem<String>> attributeMap = new HashMap<Label, TreeItem<String>>();
    
    private Stage dialogStage;
    
    private Label key;
    
    private ListView<Label> list;
    
    private Label baseRootName;
    
    /**the new agent name*/
    private String newServiceName = null;
    
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
     * @param node the node to modify
     */
    public void setKey(Label label) {
        this.key = label;
    }
    
    public void setAttributeMap(HashMap<Label, TreeItem<String>> attributeMap) {
        this.attributeMap = attributeMap;
    }
    
    public void init(HashMap<Label, TreeItem<String>> attributeMap,ListView<Label> list){
        setAttributeMap(attributeMap);
        setKey(list.getSelectionModel().getSelectedItem());
        this.list = list;
        System.out.println(key);
        System.out.println("hey" + attributeMap);
        System.out.println(attributeMap);
        System.out.println(attributeMap.get(key));
        tree.setRoot(deepcopy(attributeMap.get(key)));
        baseRootName = key;
    }
    

    /**
     * Confirm button. sets the new tree as the node tree, and exit this window
     */
    public void confirmButton() {
        value = (tree.getRoot());
        attributeMap.put(key, value);
        newServiceName = tree.getRoot().getValue();
        if(newServiceName != baseRootName.getText()){
            list.getItems().remove(key);
            key.setText(newServiceName);
            list.getItems().add(key);
            attributeMap.put(key,attributeMap.remove(baseRootName));
        }
        dialogStage.close();
    }

    /**
     * Cancel button. just exit this window
     */
    @FXML
    public void cancelButton() {
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
        TreeItem<String> copy = new TreeItem<String>(item.getValue());
        for (TreeItem<String> child : item.getChildren()) {
            copy.getChildren().add(deepcopy(child));
        }
        return copy;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.tree.setEditable(true);
        tree.setCellFactory(new Callback<TreeView<String>, TreeCell<String>>() {
            @Override
            public TreeCell<String> call(TreeView<String> p) {
                return new ServiceRenameMenuTreeCell();
            }
        });
    }

    private static class ServiceRenameMenuTreeCell extends TextFieldTreeCell<String> {
        private ContextMenu menu = new ContextMenu();

        public ServiceRenameMenuTreeCell() {
            super(new DefaultStringConverter());

            menu.setId("treeAttributeItem");
            MenuItem renameItem = new MenuItem("Renommer");
            renameItem.setId("renameAttributeItem");
            menu.getItems().add(renameItem);
            renameItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent arg0) {
                    startEdit();
                    
                }
            });

            MenuItem addItem = new MenuItem("Ajouter");
            menu.getItems().add(addItem);
            addItem.setId("addAttributeItem");
            addItem.setOnAction(new EventHandler() {
                public void handle(Event t) {
                    TreeItem newItem = new TreeItem<String>("Nouvel attribut");
                    getTreeItem().getChildren().add(newItem);
                   
                }
            });

            MenuItem removeItem = new MenuItem("Supprimer");
            menu.getItems().add(removeItem);
            removeItem.setId("removeAttributeItem");
            removeItem.setOnAction(new EventHandler() {
                public void handle(Event t) {
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
    }

}