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

import fr.irit.smac.amasrenderer.model.Infrastructure;

/**
 * This service is related to the business logic about the infrastructure
 */
public class InfrastructureService {

    private Infrastructure infrastructure;

    private static InfrastructureService instance = new InfrastructureService();

    private InfrastructureService() {
    }

    public static InfrastructureService getInstance() {
        return instance;
    }

    public Infrastructure getInfrastructure() {
        return infrastructure;
    }

    /**
     * Updates the infrastructure depending on a given infrastructure
     * 
     * @param infrastructure
     */
    public void updateInfrastructure(Infrastructure infrastructure) {

        String[] infrastructureName = infrastructure.getClassName().toString()
            .split("\\.");
        infrastructure
            .setName(infrastructureName[infrastructureName.length - 1]);

        this.infrastructure = infrastructure;
        ToolService.getInstance().updateToolFromFile(infrastructure.getServices());
    }

    /**
     * Initialises a default infrastructure. The infrastructure to instantiate
     * is defined in a json file
     */
    public void init() {

        File file = new File(getClass().getResource("../json/initial_config.json").getFile());
        LoadSaveService.getInstance().load(file);
    }
}