package fr.irit.smac.amasrenderer.model;

import java.util.Collection;
import java.util.HashMap;

// TODO: Auto-generated Javadoc
/**
 * The Class AttributeTree.
 * Used to store the attributes of an agent (can be used to store anything)
 * @param <T> the generic type, the value of each "node"
 */
public class AttributeTree<T> {

    /** The list of all children. */
    private HashMap<T, AttributeTree<T>> children;
    
    /**
     * Instantiates a new empty attribute tree.
     */
    public AttributeTree() {
        children = new HashMap<T, AttributeTree<T>>();
    }
    
    /**
     * Instantiates a new attribute tree containing "values"
     *
     * @param values the values to store
     */
    public AttributeTree(T ... values) {
        children = new HashMap<T, AttributeTree<T>>();
        for(T value : values){
            AttributeTree<T> newNode = new AttributeTree<T>();
            children.put(value, newNode);
        }
    }
    
    /**
     * Adds a child of value "value".
     *
     * @param value the value to store
     */
    public void addChild(T value){
        AttributeTree<T> newNode = new AttributeTree<T>();
        children.put(value, newNode);
    }
    
    /**
     * Adds children of value "values".
     *
     * @param values the values to store
     */
    public void addChildren(T ... values){
        for(T value: values){
            AttributeTree<T> newNode = new AttributeTree<T>();
            children.put(value, newNode);
        }
    }
    
    /**
     * Removes a child of value "value".
     *
     * @param value the value to remove
     * @return the attribute tree
     */
    public AttributeTree<T> removeChild(T value){
        return children.remove(value);
    }
    
    /**
     * Removes children of value "values".
     *
     * @param values the values to remove
     * @return the number of failure in removing children, 0 if no failure
     */
    public int removeChildren(T ... values){
        int unableToRemove = 0;
        for(T value : values){
            if(children.remove(value) == null){
                unableToRemove++;
            }
        }
        return unableToRemove;
    }
    
    /**
     * Clear all children.
     */
    public void clearChildren(){
        children.clear();
    }
    
    /**
     * Rename the child of value "oldValue" to "newValue".
     *
     * @param oldValue the old value
     * @param newValue the new value
     * @return false if the child was not found, true otherwise
     */
    public boolean renameChild(T oldValue, T newValue){
        AttributeTree<T> temp = children.remove(oldValue);
        if(temp == null){
            return false;
        }
        children.put(newValue, temp);
        return true;
    }
    
    /**
     * Gets all children names.
     *
     * @return the children names ( keys )
     */
    public Collection<T> getChildrenNames(){
        Collection<T> returnValue = children.keySet();
        return returnValue;
    }
    
    /**
     * return the child of value "value"
     * @param value the value to get
     * @return the child
     */
    
    public AttributeTree<T> getChild(T value){
        return children.get(value);
    }
    
    /**
     * Checks if this node is a leaf.
     * 
     * @return true, if this node is a leaf
     */
    public boolean isLeaf(){
        return children.isEmpty();
    }
    
    /**
     * Checks if the child of value "child" is a leaf.
     *
     * @param child the child
     * @return true, if it is a leaf
     */
    public boolean isLeaf(T child){
        return children.get(child).getChildren().isEmpty();
    }
    
    /**
     * Gets the children.
     * 
     * @return the children
     */
    private HashMap<T, AttributeTree<T>> getChildren() {
        return children;
    }
    
    
}
