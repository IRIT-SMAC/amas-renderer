package fr.irit.smac.amasrenderer.model;

import java.util.HashMap;
import java.util.Map;

import fr.irit.smac.amasrenderer.Const;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TargetModel implements IModel {

    private StringProperty name;

    private Map<String, Object> attributesMap;

    private String agentId;

    private String agentTarget;

    private String portSource;

    private String portTarget;

    private static final String[] REQUIRED_KEY_SINGLE  = { Const.AGENT_TARGET, Const.PORT_SOURCE, Const.PORT_TARGET,
        Const.CLASSNAME };
    private static final String[] PROTECTED_VALUE      = { Const.AGENT_TARGET, Const.PORT_SOURCE, Const.PORT_TARGET };
    private static final String[] NOT_EXPANDED         = {};
    private static final String[] REQUIRED_KEY_COMPLEX = {};

    public TargetModel() {

    }

    public TargetModel(String agentId, String id) {
        this.attributesMap = new HashMap<String, Object>();
        this.agentTarget = agentId;
        this.attributesMap.put(Const.AGENT_TARGET, agentId);
        this.attributesMap.put(Const.PORT_SOURCE, null);
        this.attributesMap.put(Const.PORT_TARGET, null);
        this.attributesMap.put(Const.CLASSNAME, "fr.irit.smac.amasfactory.agent.features.social.impl.Target");
        this.name = new SimpleStringProperty(id);
    }

    public TargetModel(String id, Map<String, Object> attributesMap) {
        this.attributesMap = attributesMap;
        this.agentTarget = (String) attributesMap.get(Const.AGENT_TARGET);
        this.portSource = (String) attributesMap.get(Const.PORT_SOURCE);
        this.portTarget = (String) attributesMap.get(Const.PORT_TARGET);
        this.name = new SimpleStringProperty(id);
    }

    public Map<String, Object> getAttributesMap() {
        return this.attributesMap;
    }

    public void setAttributesMap(Map<String, Object> attributesMap) {
        this.attributesMap = attributesMap;
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

    @Override
    public String[] getRequiredKeySingle() {
        return REQUIRED_KEY_SINGLE;
    }

    @Override
    public String[] getProtectedValue() {
        return PROTECTED_VALUE;
    }

    @Override
    public String[] getNotExpanded() {
        return NOT_EXPANDED;
    }

    @Override
    public String[] getRequiredKeyComplex() {
        return REQUIRED_KEY_COMPLEX;
    }

    public String getAgentId() {
        return this.agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getAgentTarget() {
        return this.agentTarget;
    }

    public void setAgentTarget(String newValue) {
        this.agentTarget = newValue;
        this.attributesMap.put(Const.AGENT_TARGET, newValue);
    }

    public String getPortSource() {
        return portSource;
    }

    public String getPortTarget() {
        return portTarget;
    }
}
