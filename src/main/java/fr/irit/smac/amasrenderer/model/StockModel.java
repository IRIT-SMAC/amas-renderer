package fr.irit.smac.amasrenderer.model;

import javafx.scene.control.TreeItem;

// TODO: Rework this class
/**
 * The Class StockModel. Contains the node's data
 */
public class StockModel {

    TreeItem<String> root;

    /**
     * Instantiates a new stock
     * 
     * @param s
     *            the name of the root
     */
    public StockModel(String s) {
        root = new TreeItem<>(s);
    }

    public TreeItem<String> getRoot() {
        return root;
    }

    public void setRoot(TreeItem<String> root) {
        this.root = root;
    }

    @Override
    public String toString() {
        return root.toString();
    }
}
