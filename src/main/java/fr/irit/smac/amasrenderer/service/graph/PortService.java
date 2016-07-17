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
package fr.irit.smac.amasrenderer.service.graph;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.irit.smac.amasrenderer.Const;
import fr.irit.smac.amasrenderer.model.agent.AgentModel;
import fr.irit.smac.amasrenderer.model.agent.feature.social.PortModel;

public class PortService {

    private static PortService instance = new PortService();

    public static PortService getInstance() {

        return instance;
    }
    
    public PortModel addPort(AgentModel agent) {

        File file = new File(getClass().getResource("../../json/initial_port.json").getFile());
        final ObjectMapper mapper = new ObjectMapper();
        PortModel port = null;
        try {
            port = mapper.readValue(file, PortModel.class);
            port.setName(Const.PORT);
            agent.getCommonFeaturesModel().getFeatureSocial().getKnowledge().getPortMap().put(Const.PORT, port);
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return port;
    }
}
