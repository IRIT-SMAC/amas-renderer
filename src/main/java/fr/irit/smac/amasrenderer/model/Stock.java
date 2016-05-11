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
    public Stock() {
        root = new TreeItem<>("root node");
        init();
    }
    
    public Stock(String s){
        root = new TreeItem<>(s);
        init();
    }

    public void init(){
        int max_rand = (int)Math.floor(Math.random()*5+1);
        for (int i = 0; i < max_rand; i++) {
            TreeItem<String> item = new TreeItem<>(Const.BASE_DESCR[(int) Math.floor(Math.random() * 9)]);
            root.getChildren().add(item);
            max_rand = (int)Math.floor(Math.random()*5+1);
            for (int j = 0; j < max_rand ; j++) {
                TreeItem<String> item2 = new TreeItem<>(Const.BASE_DESCR[(int) Math.floor(Math.random() * 9)]);
                item.getChildren().add(item2);
                max_rand = (int)Math.floor(Math.random()*5+1);
                for (int k = 0; k < max_rand; k++) {
                    TreeItem<String> item3 = new TreeItem<>(Const.BASE_DESCR[(int) Math.floor(Math.random() * 9)]);
                    item2.getChildren().add(item3);
                }
            }
        }
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
