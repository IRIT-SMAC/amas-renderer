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
package fr.irit.smac.amasrenderer.service;

import java.io.File;
import java.util.Map;

import fr.irit.smac.amasrenderer.Const;
import fr.irit.smac.amasrenderer.model.InfrastructureModel;

/**
 * This service is related to the business logic about the infrastructure
 */
public class InfrastructureService {

    private InfrastructureModel infrastructure;

    private static InfrastructureService instance = new InfrastructureService();

    private InfrastructureService() {
    }

    /**
     * Gets the single instance of InfrastructureService.
     *
     * @return single instance of InfrastructureService
     */
    public static InfrastructureService getInstance() {
        return instance;
    }

    /**
     * Gets the infrastructure.
     *
     * @return the infrastructure
     */
    public InfrastructureModel getInfrastructure() {
        return this.infrastructure;
    }

    /**
     * Sets the infrastructure.
     *
     * @param infrastructure
     *            the new infrastructure
     */
    public void setInfrastructure(InfrastructureModel infrastructure) {
        this.infrastructure = infrastructure;
    }

    /**
     * Updates the infrastructure depending on the infrastructure of a JSON file
     * 
     * @param infrastructure
     */
    public void updateInfrastructureFromFile(InfrastructureModel infrastructure) {

        infrastructure.getAttributesMap().put("services", infrastructure.getServices());
        infrastructure.getAttributesMap().put("className", infrastructure.getClassName());
        String[] infrastructureName = infrastructure.getAttributesMap().get(Const.CLASSNAME).toString()
            .split("\\.");
        infrastructure
            .setName(infrastructureName[infrastructureName.length - 1]);
        infrastructure.setAttributes(infrastructure.getAttributesMap());
        
        this.infrastructure = infrastructure;
        ToolService.getInstance().updateToolFromFile(infrastructure.getServices());
    }

    /**
     * Initialize a default infrastructure
     */
    public void init() {

        File file = new File(getClass().getResource("../json/initial_config.json").getFile());
        LoadSaveService.getInstance().load(file);
    }
}