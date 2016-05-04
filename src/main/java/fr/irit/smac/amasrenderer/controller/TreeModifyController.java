package fr.irit.smac.amasrenderer.controller;

import fr.irit.smac.amasrenderer.model.Stock;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;

public class TreeModifyController {

    @FXML
    private TreeView<String> tree;
    
    @FXML
    private TextField addValue;
    
    @FXML
    private TextField modifyValue;
    
    private Stock stock;
    
    private Stage dialogStage;

    public void setStage(Stage stage){
        dialogStage = stage;
    }
    
    public void setStock(Stock s){
        stock = s;
        tree.setRoot(deepcopy(s.getRoot()));
    }
    /*
    public void setTree(TreeItem<String> treeItem){
        tree.setRoot(treeItem);
    }
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
    
    @FXML
    public void confirmButton(){
        stock.setRoot(tree.getRoot());
        dialogStage.close();
    }
    
    @FXML
    public void cancelButton(){
        dialogStage.close();
        
    }
    
    private TreeItem<String> deepcopy(TreeItem<String> item) {
        TreeItem<String> copy = new TreeItem<String>(item.getValue());
        for (TreeItem<String> child : item.getChildren()) {
            copy.getChildren().add(deepcopy(child));
        }
        return copy;
    }
    
}