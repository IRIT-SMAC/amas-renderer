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
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.irit.smac.amasrenderer.Const;
import fr.irit.smac.amasrenderer.model.tool.Tool;
import fr.irit.smac.amasrenderer.model.tool.Tools;
import fr.irit.smac.amasrenderer.service.graph.GraphService;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This service is related to the business logic about the tools
 */
public class ToolService {

    private ObservableList<Tool> tools;

    private Tools toolsModel;

    private static ToolService instance = new ToolService();

    private ToolService() {

        this.setTools(FXCollections.observableArrayList(actionStep -> new Observable[] { actionStep.nameProperty() }));
    }

    public static ToolService getInstance() {
        return instance;
    }

    public ObservableList<Tool> getTools() {
        return this.tools;
    }

    public void setTools(ObservableList<Tool> tools) {
        this.tools = tools;
    }

    /**
     * Adds a tool to the list of tools
     * 
     * @param tool
     *            the tool
     */
    public void addToolToTools(Tool tool) {

        this.tools.add(tool);
        this.toolsModel.getServices().put(tool.getName(), tool);
        handleToolModelChange(tool);
    }

    /**
     * Updates the map of tools when the name of a tool is updated
     * 
     * @param tool
     */
    private void handleToolModelChange(Tool tool) {

        tool.nameProperty()
            .addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                toolsModel.getServices().remove(oldValue);
                toolsModel.getServices().put(newValue,
                    tool);
            });
    }

    /**
     * Creates the tools from a tool map.
     *
     * @param toolsModel
     *            the map
     */
    public void createToolsFromTools() {

        this.getTools().clear();

        this.toolsModel.getServices().forEach((k, v) -> {
            v.setName(k);
            v.getAttributesMap().put(Const.CLASSNAME, v.getClassName());
            this.addToolToTools(v);
        });

        this.toolsModel.getAgentHandlerToolModel().setName(Const.AGENT_HANDLER_SERVICE);
        this.tools.add(toolsModel.getAgentHandlerToolModel());
        handleToolModelChange(toolsModel.getAgentHandlerToolModel());
    }

    public void removeTool(Tool tool) {
        this.getTools().remove(tool);
    }

    public void removeTools() {
        this.getTools().clear();
    }

    public Tools getToolsModel() {
        return this.toolsModel;
    }

    public void setToolMap(Tools toolsModel) {
        this.toolsModel = toolsModel;
    }

    public void updateToolFromFile(Tools tools) {

        this.setToolMap(tools);
        this.createToolsFromTools();

        GraphService.getInstance().updateGraphFromAgentMap(tools.getAgentHandlerToolModel().getAgentMap());
    }

    /**
     * Adds a tool. The tool to instantiate is defined in a json file
     * 
     * @param id
     */
    public void addTool(String id) {

        File file = new File(getClass().getResource("../json/initial_tool.json").getFile());
        final ObjectMapper mapper = new ObjectMapper();
        Tool tool = null;
        try {
            tool = mapper.readValue(file, Tool.class);
            tool.setName(tool.getNewName(id));
            addToolToTools(tool);
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}