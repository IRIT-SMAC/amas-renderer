/*
 * #%L
 * amas-renderer
 * %%
 * Copyright (C) 2016 IRIT - SMAC Team
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */
package fr.irit.smac.amasrenderer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    @JsonIgnore
    public void setName(String name);

    /**
     * Gets the name of the model
     * 
     * @return the name
     */
    @JsonIgnore
    public String getName();

    /**
     * Gets the new name of the model depending on the given name
     * 
     * @param name
     * @return the new name
     */
    @JsonIgnore
    public String getNewName(String name);

    /**
     * Gets all the key with a single value
     * 
     * @return the keys
     */
    @JsonIgnore
    public String[] getRequiredKeySingle();

    /**
     * Gets all the value the user don't have to update
     * 
     * @return the values
     */
    @JsonIgnore
    public String[] getProtectedValue();

    /**
     * Gets all the attributes to hide to the user
     * 
     * @return the attributes
     */
    @JsonIgnore
    public String[] getNotExpanded();

    /**
     * Gets all the key with a complex value
     * 
     * @return the keys
     */
    @JsonIgnore
    public String[] getRequiredKeyComplex();
}
