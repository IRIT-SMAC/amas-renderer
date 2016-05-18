package fr.irit.smac.amasrenderer.service;

import java.util.Map;

import fr.irit.smac.amasrenderer.model.InfrastructureModel;
import javafx.collections.ObservableList;

/**
 * The Class InfrastructureService.
 */
public class InfrastructureService {

    private InfrastructureModel model;

    private static InfrastructureService instance = new InfrastructureService();

    /**
     * Instantiates a new infrastructure service.
     */
    private InfrastructureService() {
        model = new InfrastructureModel();
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
    public ObservableList<String> getInfrastructure() {
        return this.model.getInfrastructure();
    }

    /**
     * Edits the infrastructure.
     *
     * @param name
     *            the name
     */
    public void editInfrastructure(String name) {
        this.model.editInfrastructure(name);
    }

    /**
     * Sets the infrastructure.
     *
     * @param list
     *            the new infrastructure
     */
    public void setInfrastructure(ObservableList<String> list) {
        this.model.setInfrastructure(list);
    }

    /**
     * Creates the infrastructures from map.
     *
     * @param map
     *            the map
     */
    public void createInfrastructuresFromMap(Map<String, Object> map) {
        this.model.editInfrastructure((String) map.get("className"));
    }

}
