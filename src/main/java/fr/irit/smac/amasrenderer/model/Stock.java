package fr.irit.smac.amasrenderer.model;

import fr.irit.smac.amasrenderer.Const;

public class Stock {
    private String descr;
    private int rank;
    
    public Stock() {
        descr = (String)Const.BASE_DESCR[(int)Math.floor(Math.random()*9)];
        rank = (int)Math.floor(Math.random()*9);
    }
    @Override
    public String toString() {
        return "\n---Description: "+ descr +
        "\n---Rank: n°"+rank;
    }
}
