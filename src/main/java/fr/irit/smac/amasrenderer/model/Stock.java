package fr.irit.smac.amasrenderer.model;

import fr.irit.smac.amasrenderer.Const;
import javafx.scene.control.TreeItem;

// TODO: Rework this class
/**
 * The Class Stock.
 * Contains the node's data
 */
public class Stock {

    TreeItem<String> root = new TreeItem<>("root node");
    
    /** The descr. test purposes, will change */
    private String descr;
    
    /** The rank. test purposes, will change*/
    private int    rank;

    /**
     * Instantiates a new stock.
     * test purposes, will change
     */
    public Stock() {
        final int justAnInt = 9;
        descr = Const.BASE_DESCR[(int) Math.floor(Math.random() * justAnInt)];
        rank = (int) Math.floor(Math.random() * justAnInt);
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
    
    /**
     * Gets the descr.
     * test purposes, will change
     *
     * @return the descr
     */
    public String getDescr() {
        return descr;
    }

    /**
     * Gets the rank.
     * test purposes, will change
     *
     * @return the rank
     */
    public int getRank() {
        return rank;
    }

    public TreeItem<String> getRoot() {
        return root;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "\n---Description: " + descr +
            "\n---Rank: " + rank;
    }
}
