package fr.irit.smac.amasrenderer.model;

import fr.irit.smac.amasrenderer.Const;

// TODO: Rework this class
/**
 * The Class Stock.
 * Contains the node's data
 */
public class Stock {

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

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "\n---Description: " + descr +
            "\n---Rank: " + rank;
    }
}
