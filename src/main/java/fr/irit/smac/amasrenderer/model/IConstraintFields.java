package fr.irit.smac.amasrenderer.model;

import javafx.beans.property.StringProperty;

public interface IConstraintFields {

    public void setName(String name);
    
    String[] getRequiredKeySingle();
    
    String[] getProtectedValue();
    
    String[] getNotExpanded();
    
    String[] getRequiredKeyComplex();
}
