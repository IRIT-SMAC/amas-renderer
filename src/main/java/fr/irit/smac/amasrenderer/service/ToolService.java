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

import java.util.HashMap;
import java.util.Map;

import fr.irit.smac.amasrenderer.Const;
import fr.irit.smac.amasrenderer.model.ToolModel;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This service is related to the business logic about the tools
 */
public class ToolService {

    private ObservableList<ToolModel> tools;

    private Map<String, Object> toolMap;

    private static ToolService instance = new ToolService();

    private ToolService() {

        this.setTools(FXCollections.observableArrayList(actionStep -> new Observable[] { actionStep.nameProperty() }));
    }

    /**
     * Gets the single instance of ToolService.
     *
     * @return single instance of ToolService
     */
    public static ToolService getInstance() {
        return instance;
    }

    /**
     * Gets the tools
     *
     * @return the tools
     */
    public ObservableList<ToolModel> getTools() {
        return this.tools;
    }

    /**
     * Sets the tools
     *
     * @param items
     *            the new tools
     */
    public void setTools(ObservableList<ToolModel> tools) {
        this.tools = tools;
    }

    /**
     * Adds a tool
     * 
     * @param tool
     *            the tool
     */
    public void addTool(ToolModel tool) {

        this.tools.add(tool);
        toolMap.put(tool.getName(),
            tool.getAttributesMap());
        tool.nameProperty()
            .addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                toolMap.remove(oldValue);
                toolMap.put(newValue,
                    tool.getAttributesMap());
            });
    }

    /**
     * Creates the tools from a tool map.
     *
     * @param toolMap
     *            the map
     */
    public void createToolsFromMap() {

        this.getTools().clear();

        for (Map.Entry<String, Object> pair : this.toolMap.entrySet()) {
            if (pair.getKey().contains(Const.TOOL)) {
                this.addTool(new ToolModel(pair.getKey(), pair.getValue()));
            }
        }
    }

    /**
     * Remove a tool from the list of tools
     * 
     * @param tool
     *            the tool to remove
     */
    public void removeTool(ToolModel tool) {
        this.getTools().remove(tool);
    }

    /**
     * Remove all the tools
     */
    public void removeTools() {
        this.getTools().clear();
    }

    public Map<String, Object> getToolMap() {
        return toolMap;
    }

    public void setToolMap(Map<String, Object> toolMap) {
        this.toolMap = toolMap;
    }

    public void updateToolFromFile(Map<String, Object> toolMap) {
        
        this.setToolMap(toolMap);
        this.createToolsFromMap();

        @SuppressWarnings("unchecked")
        Map<String, Object> agentMap = (HashMap<String, Object>) ((Map<String, Object>) toolMap
            .get(Const.AGENT_HANDLER_SERVICE)).get(Const.AGENT_MAP);
        GraphService.getInstance().updateGraphFromFile(agentMap);
    }
}