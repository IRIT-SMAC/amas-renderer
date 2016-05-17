package fr.irit.smac.amasrenderer.controller.tool;

import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.TreeView.EditEvent;
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
    private Button delButton;

    @FXML
    private TreeView<String> tree;

    private TreeItem<String> value;

    private HashMap<String,TreeItem<String>> attributeMap = new HashMap<String, TreeItem<String>>();
    
    private Stage dialogStage;
    
    private String key;
    
    private ListView<String> list;
    
    private String baseRootName;
    
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
    public void setKey(String label) {
        this.key = label;
    }
    
    public void setAttributeMap(HashMap<String, TreeItem<String>> attributeMap) {
        this.attributeMap = attributeMap;
    }
    
    public void init(HashMap<String, TreeItem<String>> attributeMap,ListView<String> list){
        setAttributeMap(attributeMap);
        setKey(list.getSelectionModel().getSelectedItem());
        this.list = list;
        tree.setRoot(deepcopy(attributeMap.get(key)));
        baseRootName = key;
        
        /*tree.setOnEditCommit(new EventHandler<TreeView.EditEvent<String>>() {
            @Override
            public void handle(EditEvent<String> event) {
                if(event.getNewValue().trim().isEmpty() && event.getOldValue() != event.getNewValue()){
//                    event.getTreeItem().setValue(event.getOldValue());
//                    tree.getEditingItem().setValue(event.getOldValue());
                    event.fireEvent(event.getTarget(), event);
                }
            }
        });*/
    }
    
    
    
    /**
     * deletes the service ( no confirmation )
     * */
    @FXML
    private void deleteButton(){
        list.getItems().remove(key);
        attributeMap.remove(key);
        dialogStage.close();
    }
    
    
    /**
     * Confirm button. sets the new tree as the node tree, and exit this window
     */
    @FXML
    public void confirmButton() {
        value = (tree.getRoot());
        attributeMap.put(key, value);
        newServiceName = tree.getRoot().getValue();
        if(newServiceName != baseRootName){
            list.getItems().remove(key);
            key = newServiceName;
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
        
        /**
         * This commitEdit is the same as super.commitEdit
         * I copied it to allow for mid commit check of the newValue
         * This version checks if the newValue is empty (or whitespaces only)  
         * and if it is, stops the edit. 
         */
        @Override
        public void commitEdit(String newValue) {
            if (! isEditing()) return;
            final TreeItem<String> treeItem = getTreeItem();
            final TreeView<String> tree = getTreeView();
            if (tree != null) {
                // Inform the TreeView of the edit being ready to be committed.
                tree.fireEvent(new TreeView.EditEvent<String>(tree,
                        TreeView.<String>editCommitEvent(),
                        treeItem,
                        getItem(),
                        newValue));
            }
            
            if(newValue.trim().isEmpty()){
                return;
            }

            // inform parent classes of the commit, so that they can switch us
            // out of the editing state.
            // This MUST come before the updateItem call below, otherwise it will
            // call cancelEdit(), resulting in both commit and cancel events being
            // fired (as identified in RT-29650)
            super.commitEdit(newValue);
            
            // update the item within this cell, so that it represents the new value
            if (treeItem != null) {
                treeItem.setValue(newValue);
                updateTreeItem(treeItem);
                updateItem(newValue, false);
            }
            
            if (tree != null) {
                // reset the editing item in the TreetView
                tree.edit(null);

                // request focus back onto the tree, only if the current focus
                // owner has the tree as a parent (otherwise the user might have
                // clicked out of the tree entirely and given focus to something else.
                // It would be rude of us to request it back again.
                Scene scene = tree.getScene();
                final Node focusOwner = scene == null ? null : scene.getFocusOwner();
                if (focusOwner == null) {
                    tree.requestFocus();
                } else if (! tree.equals(focusOwner)) {
                    Parent p = focusOwner.getParent();
                    while (p != null) {
                        if (tree.equals(p)) {
                            tree.requestFocus();
                            break;
                        }
                        p = p.getParent();
                    }
                }
            }

        }
        
        
        
    }

}