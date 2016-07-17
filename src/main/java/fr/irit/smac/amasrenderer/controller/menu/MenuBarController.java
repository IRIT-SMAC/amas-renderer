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
package fr.irit.smac.amasrenderer.controller.menu;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.irit.smac.amasrenderer.controller.LoadSecondaryWindowController;
import fr.irit.smac.amasrenderer.service.LoadSaveService;
import fr.irit.smac.amasrenderer.service.graph.GraphService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.stage.FileChooser;

/**
 * This controller is related to the menu bar
 */
public class MenuBarController extends LoadSecondaryWindowController {

    private static final String FILE_CHOOSER = "Choisir un fichier de configuration";

    private static final String EXTENSION_FILTER_DESCRIPTION = "JSON files (*.json)";

    private static final String EXTENSION_FILTER_EXTENSION = "*.json";

    private static final Logger LOGGER = Logger.getLogger(MenuBarController.class.getName());

    @FXML
    MenuBar menuBar;

    LoadSaveService loadSaveService = LoadSaveService.getInstance();

    /**
     * When the load menuItem is clicked, the data of the chosen JSON file
     * allows to generate the infrastructure, the tools and the graph of agents
     */
    @FXML
    public void clickMenuLoad() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(FILE_CHOOSER);
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(EXTENSION_FILTER_DESCRIPTION,
            EXTENSION_FILTER_EXTENSION);
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(window);

        try {
            loadSaveService.load(file);
        }
        catch (Exception e) {

            GraphService.getInstance().clearGraph();
            LOGGER.log(Level.SEVERE, "An error occured during the loading of a configuration file", e);
            loadFxmlIndependent("view/ErrorWindow.fxml", "Chargement du fichier impossible",
                "Le fichier de configuration n'a pu être chargé en raison d'un contenu non conforme à celui attendu.");
        }
    }

    /**
     * When the exit menuItem is clicked, the program is closed
     */
    @FXML
    public void clickMenuExit() {

        Platform.exit();
        System.exit(0);
    }

    /**
     * When the save menuItem is clicked, the informations about the
     * infrastructure (and so about the tools and the agents) are saved in a
     * JSON file
     */
    @FXML
    public void clickMenuSave() {

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(EXTENSION_FILTER_DESCRIPTION,
            EXTENSION_FILTER_EXTENSION);
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(window);
        loadSaveService.save(file);
    }

    /**
     * When the documentation menuItem is clicked, a window showing the
     * documentation is opened
     */
    @FXML
    public void clickDocumentation() {

        loadFxmlIndependent("view/help/Documentation.fxml");
    }
}
