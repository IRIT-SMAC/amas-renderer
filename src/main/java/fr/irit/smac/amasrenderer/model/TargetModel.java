package fr.irit.smac.amasrenderer.model;

public class TargetModel {

    private String agentTarget;
    private String portTarget;
    private String portSource;
    private String className;

    public TargetModel() {

    }

    public TargetModel(String agentTarget, String portTarget, String portSource, String className) {
        this.agentTarget = agentTarget;
        this.portTarget = portTarget;
        this.portSource = portSource;
        this.className = className;
    }

    public String getAgentTarget() {
        return agentTarget;
    }

    public void setAgentTarget(String agentTarget) {
        this.agentTarget = agentTarget;
    }

    public String getPortTarget() {
        return portTarget;
    }

    public void setPortTarget(String portTarget) {
        this.portTarget = portTarget;
    }

    public String getPortSource() {
        return portSource;
    }

    public void setPortSource(String portSource) {
        this.portSource = portSource;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

}
