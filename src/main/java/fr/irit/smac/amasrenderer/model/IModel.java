package fr.irit.smac.amasrenderer.model;

public interface IModel {

    public void setName(String name);

    public String getNewName(String name);
    
    public String[] getRequiredKeySingle();

    public String[] getProtectedValue();

    public String[] getNotExpanded();

    public String[] getRequiredKeyComplex();
}
