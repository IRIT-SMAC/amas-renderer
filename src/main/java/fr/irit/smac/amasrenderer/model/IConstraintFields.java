package fr.irit.smac.amasrenderer.model;

public interface IConstraintFields {

    String[] getRequiredKeySingle();
    
    String[] getProtectedValue();
    
    String[] getNotExpanded();
    
    String[] getRequiredKeyComplex();
}
