package fr.irit.smac.amasrenderer.controller;

import fr.irit.smac.amasrenderer.model.Stock;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;

/**
 * The Class TreeModifyController.
 * Manage the modal window opening to modify attributes
 */
public class TreeModifyController {

    /** The add button. */
    @FXML
    private Button addButton;
    
    /** The modify button. */
    @FXML
    private Button modButton;
    
    /** The delete button. */
    @FXML
    private Button delButton;
    
    /** The confirm button. */
    @FXML
    private Button confButton;
    
    /** The cancel button. */
    @FXML
    private Button cancButton;
    
    /** The attribute tree. */
    @FXML
    private TreeView<String> tree;
    
    /** The add value. */
    @FXML
    private TextField addValue;
    
    /** The modify value. */
    @FXML
    private TextField modifyValue;
    
    /** The stock var of the clicked node. */
    private Stock stock;
    
    /** The dialog stage. */
    private Stage dialogStage;

    /**
     * Sets the stage.
     *
     * @param stage the new stage
     */
    public void setStage(Stage stage){
        dialogStage = stage;
    }
    
    /**
     * Gets the delete button.
     *
     * @return the del button
     */
    public Button getDelButton() {
        return delButton;
    }
    
    /**
     * Sets the stock.
     *
     * @param s the new stock
     */
    public void setStock(Stock s){
        stock = s;
        tree.setRoot(deepcopy(s.getRoot()));
    }
    
    /**
     * Adds the attribute.
     * called when the user click on the add attribute button
     * gets the value of addValue and adds it to the selected treeItem if the value != null
     */
    @FXML
    public void addAttribute() {
        TreeItem<String> itemGet = tree.getSelectionModel().getSelectedItem();
        String text = addValue.getText();
        if(itemGet != null && text != null && text.length() > 0){
            TreeItem<String> itemNew = new TreeItem<String>(text);
            itemGet.getChildren().add(itemNew);
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Addition Error");
            if(itemGet == null){
                alert.setHeaderText("No node selected !");
                alert.setContentText("Please select a node to add to.");
            } else {
                alert.setHeaderText("No text entered !");
                alert.setContentText("Please enter something in the text field.");
            }
            alert.showAndWait();
        }
        addValue.clear();
        modifyValue.clear();
    }

    /**
     * Modify attribute.
     * called when the user click on the modify attribute button
     * gets the value of modValue and sets the selected treeItem's value to modValue if the value != null
     */
    @FXML
    public void modifyAttribute() {
        TreeItem<String> itemGet = tree.getSelectionModel().getSelectedItem();
        String text = modifyValue.getText();
        if(itemGet != null && text != null && text.length() > 0){
            itemGet.setValue(text);
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Modify Error");
            if(itemGet == null){
                alert.setHeaderText("No node selected !");
                alert.setContentText("Please select a node to modify.");
            } else {
                alert.setHeaderText("No text entered !");
                alert.setContentText("Please enter something in the text field.");
            }
            alert.showAndWait();
        }
        addValue.clear();
        modifyValue.clear();
    }

    /**
     * Delete attribute.
     * called when the user click on the delete attribute button
     * removes the selected treeItem from the tree
     */
    @FXML
    public void deleteAttribute() {
        TreeItem<String> itemGet = tree.getSelectionModel().getSelectedItem();
        if(itemGet != null && itemGet != tree.getRoot()){
            itemGet.getParent().getChildren().remove(itemGet);
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Deletion Error");
            if(itemGet == null){
                alert.setHeaderText("No node selected !");
                alert.setContentText("Please select a node to delete.");
            } else {
                alert.setHeaderText("Can't delete the root node !");
                alert.setContentText("Please select an other node to delete.");
            }
            alert.showAndWait();
        }
        addValue.clear();
        modifyValue.clear();    
    }
    
    /**
     * Confirm button.
     * sets the new tree as the node tree, and exit this window
     */
    @FXML
    public void confirmButton(){
        stock.setRoot(tree.getRoot());
        dialogStage.close();
    }
    
    /**
     * Cancel button.
     * just exit this window
     */
    @FXML
    public void cancelButton(){
        dialogStage.close();
        
    }
    
    /**
     * Deepcopy.
     * recursively copies the tree, to be able to modify it without changing the original
     * 
     * @param item the the tree to copy
     * @return a copy of the tree item the tree item
     */
    private TreeItem<String> deepcopy(TreeItem<String> item) {
        TreeItem<String> copy = new TreeItem<String>(item.getValue());
        for (TreeItem<String> child : item.getChildren()) {
            copy.getChildren().add(deepcopy(child));
        }
        return copy;
    }
    
}