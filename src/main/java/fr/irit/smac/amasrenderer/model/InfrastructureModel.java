package fr.irit.smac.amasrenderer.model;

import java.util.HashMap;
import java.util.Map;

import javafx.collections.ObservableList;

/**
 * The Class InfrastructureModel.
 */
public class InfrastructureModel {

    private ObservableList<String> infrastructure;
	private Map<String, Object> infrastructureMap = new HashMap<String,Object>();

	/**
     * Sets the infrastructure.
     *
     * @param label
     *            the new infrastructure
     */
    public void setInfrastructure(ObservableList<String> label) {
        this.infrastructure = label;
    }

    /**
     * Gets the infrastructure.
     *
     * @return the infrastructure
     */
    public ObservableList<String> getInfrastructure() {
        return infrastructure;
    }

    /**
     * Edits the infrastructure.
     *
     * @param name
     *            the name
     */
    public void editInfrastructure(String name) {
        infrastructure.add(0, name);
    }

	public Map<String, Object> getInfrastructureMap() {

		return this.infrastructureMap;
	}
	
    public void setInfrastructureMap(Map<String, Object> infrastructureMap) {

        this.infrastructureMap = infrastructureMap;
	}
}
