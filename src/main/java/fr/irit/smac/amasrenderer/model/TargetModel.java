package fr.irit.smac.amasrenderer.model;

import java.util.HashMap;
import java.util.Map;

public class TargetModel implements IModel {

    private String name;

    private Map<String, Object> attributesMap;

    private static final String[] REQUIRED_KEY_SINGLE  = {"agentTarget","portSource","portTarget","className"};
    private static final String[] PROTECTED_VALUE      = {"agentTarget","portSource","portTarget"};
    private static final String[] NOT_EXPANDED         = {};
    private static final String[] REQUIRED_KEY_COMPLEX = {};

    public TargetModel() {

    }

    public TargetModel(String agentId, String id) {
        this.attributesMap = new HashMap<String, Object>();
        this.attributesMap.put("agentTarget", agentId);
        this.attributesMap.put("portSource", "null");
        this.attributesMap.put("portTarget", "null");
        this.attributesMap.put("className", "fr.irit.smac.amasfactory.agent.features.social.impl.Target");
        this.name = id;
    }

    public Map<String, Object> getAttributesMap() {
        return this.attributesMap;
    }

    public void setAttributesMap(Map<String, Object> attributesMap) {
        this.attributesMap = attributesMap;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {

        return this.name;
    }

    @Override
    public String getNewName(String name) {

        return this.name;
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

}
