package fr.irit.smac.amasrenderer.service;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import fr.irit.smac.amasrenderer.model.InfrastructureModel;

/**
 * This service is related to the business logic about the loading and the
 * saving of an infrastructure (and so of the tools and the graph of agents)
 */
public class LoadSaveService {

    private static LoadSaveService instance = new LoadSaveService();

    private static final Logger LOGGER = Logger.getLogger(LoadSaveService.class.getName());

    private LoadSaveService() {
    }

    /**
     * Gets the single instance of InfrastructureService.
     *
     * @return single instance of InfrastructureService
     */
    public static LoadSaveService getInstance() {
        return instance;
    }

    /**
     * Saves the data in a JSON file
     * 
     * @param file
     *            the JSON file
     */
    public void save(File file) {
        if (file != null) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.enable(SerializationFeature.INDENT_OUTPUT);
                mapper.writeValue(file,
                    InfrastructureService.getInstance().getInfrastructure().getAttributesMap());
            }
            catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Impossible to save the data.", e);
            }
        }
    }

    /**
     * Loads a JSON file
     * 
     * @param file
     *            the JSON file
     */
    public void load(File file) {

        if (file != null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                InfrastructureModel infrastructure = mapper.readValue(file, InfrastructureModel.class);
                InfrastructureService.getInstance().updateInfrastructureFromFile(infrastructure);
            }
            catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Impossible to read this file.", e);
            }
        }
    }
}
