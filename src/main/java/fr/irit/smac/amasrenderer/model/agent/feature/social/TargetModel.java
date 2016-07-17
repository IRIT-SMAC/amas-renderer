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
package fr.irit.smac.amasrenderer.model.agent.feature.social;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import fr.irit.smac.amasrenderer.Const;
import fr.irit.smac.amasrenderer.model.IModel;
import fr.irit.smac.amasrenderer.model.ModelWithAttributesMap;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TargetModel extends ModelWithAttributesMap implements IModel {

    private StringProperty name;

    private String agentTarget;

    private String portSource;

    private String portTarget;

    @JsonIgnore
    private String agentId;

    private static final String[] PROTECTED_VALUE = { Const.AGENT_TARGET, Const.PORT_SOURCE, Const.PORT_TARGET };
    private static final String[] NOT_EXPANDED    = {};

    public TargetModel() {
        this.name = new SimpleStringProperty();
    }

    @Override
    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    @Override
    public String getName() {
        return this.name.get();
    }

    @Override
    public String getNewName(String name) {
        return name;
    }

    public String getAgentId() {
        return this.agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    @JsonSetter
    public void setAgentTarget(String newValue) {
        this.agentTarget = newValue;
        this.attributesMap.put(Const.AGENT_TARGET, newValue);
    }

    @JsonSetter
    public void setPortSource(String portName) {
        this.portSource = portName;
        this.attributesMap.put(Const.PORT_SOURCE, portSource);
    }
    
    @JsonSetter
    public void setPortTarget(String portTarget) {
        this.portTarget = portTarget;
        this.attributesMap.put(Const.PORT_TARGET, portTarget);
    }
    
    @JsonIgnore
    public String getAgentTarget() {
        return this.agentTarget;
    }

    @JsonIgnore
    public String getPortSource() {
        return portSource;
    }

    @JsonIgnore
    public String getPortTarget() {
        return portTarget;
    }

    @Override
    public String[] getProtectedValue() {
        return PROTECTED_VALUE;
    }

    @Override
    public String[] getNotExpanded() {
        return NOT_EXPANDED;
    }
}
