package fr.irit.smac.amasrenderer;

/**
 * The Class Const Contains all the constants
 */
public final class Const {

    public static final int NODE_INIT = 3;

    public static final int EDGE_INIT = 4;

    public static final int LAYOUT_QUALITY = 4;

    public static final int LAYOUT_WEIGHT_NODE = 1;

    public static final int LAYOUT_WEIGHT_EDGE = 1;

    public static final double SCALE_UNZOOM_RATIO = 0.8;

    public static final double SCALE_ZOOM_RATIO = 1.2;

    public static final double TRANSLATE_ZOOM_RATIO = 0.2;

    public static final int FONT_SIZE = 18;

    public static final String NODE_WEIGHT = "layout.weight";
    
    public static final String NODE_CLICKED = "ui.clicked";
    
    public static final String NODE_SELECTED = "ui.selected";

    public static final String NODE_LABEL = "ui.label";
        
    public static final String NODE_XY = "xy";
    
    public static final String KEY_VALUE_SEPARATOR = " : ";
    
    public static final String[] UNAUTHORISED_STRING = 
    {
        "j'aime pas amas-renderer",
        "graph stream",
        "graphstream",
        "GraphStream"
    }; 
    
    public static final String[] PROTECTED_STRING = {"michel c'est le bresil","il danse la samba"}; 
    
    /**
     * Instantiates a new const (just to hide the constructor).
     */
    private Const() {

    }
}
