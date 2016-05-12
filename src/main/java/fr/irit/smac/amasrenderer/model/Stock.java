package fr.irit.smac.amasrenderer.model;

import fr.irit.smac.amasrenderer.Const;
import javafx.scene.control.TreeItem;

// TODO: Rework this class
/**
 * The Class Stock.
 * Contains the node's data
 */
public class Stock {

    TreeItem<String> root;

    /**
     * Instantiates a new stock.
     * test purposes, will change
     */
    
    public Stock(String s){
        root = new TreeItem<>(s);
    }
    

    public TreeItem<String> getRoot() {
        return root;
    }
    
    public void setRoot(TreeItem<String> root){
        this.root = root; 
    }
    
    @Override
    public String toString() {
        return root.toString();
    }
}
