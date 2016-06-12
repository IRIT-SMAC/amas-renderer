package fr.irit.smac.amasrenderer.model;

/**
 * Used by each model of the program
 */
public interface IModel {

    /**
     * Sets the name of the model
     * 
     * @param name
     *            the name
     */
    public void setName(String name);

    /**
     * Gets the name of the model
     * 
     * @return the name
     */
    public String getName();

    /**
     * Gets the new name of the model depending on the given name
     * 
     * @param name
     * @return the new name
     */
    public String getNewName(String name);

    /**
     * Gets all the key with a single value
     * 
     * @return the keys
     */
    public String[] getRequiredKeySingle();

    /**
     * Gets all the value the user don't have to update
     * 
     * @return the values
     */
    public String[] getProtectedValue();

    /**
     * Gets all the attributes to hide to the user
     * 
     * @return the attributes
     */
    public String[] getNotExpanded();

    /**
     * Gets all the key with a complex value
     * 
     * @return the keys
     */
    public String[] getRequiredKeyComplex();
}
