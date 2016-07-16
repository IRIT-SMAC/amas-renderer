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

import fr.irit.smac.amasrenderer.Const;
import fr.irit.smac.amasrenderer.model.tool.ToolModel;
import fr.irit.smac.amasrenderer.model.tool.ToolsModel;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This service is related to the business logic about the tools
 */
public class ToolService {

    private ObservableList<ToolModel> tools;

    private ToolsModel toolMap;

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
        this.toolMap.getServices().put(tool.getName(), tool);
        tool.nameProperty()
            .addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                toolMap.getServices().remove(oldValue);
                toolMap.getServices().put(newValue,
                    tool);
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

        this.toolMap.getServices().forEach((k, v) -> {
            v.setName(k);
            v.getAttributesMap().put(Const.CLASSNAME, v.getClassName());
            this.addTool(v);
        });

        this.toolMap.getAgentHandlerToolModel().setName("agentHandlerService");
        this.tools.add(toolMap.getAgentHandlerToolModel());
        toolMap.getAgentHandlerToolModel().nameProperty()
            .addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                toolMap.getServices().remove(oldValue);
                toolMap.getServices().put(newValue,
                    toolMap.getAgentHandlerToolModel());
            });
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

    public ToolsModel getToolMap() {
        return this.toolMap;
    }

    public void setToolsModel(ToolsModel toolMap) {
        this.toolMap = toolMap;
    }

    public void updateToolFromFile(ToolsModel tools) {

        this.setToolsModel(tools);
        this.createToolsFromMap();

        GraphService.getInstance().updateGraphFromFile(tools.getAgentHandlerToolModel().getAgentMap());
    }
}