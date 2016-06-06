package fr.irit.smac.amasrenderer.model;

public interface IConstraintFields {

    String[] getRequiredKey();
    
    String[] getProtectedValue();
    
    String[] getNotExpanded();
    
    String[] getRequiredKeyComplex();
}
