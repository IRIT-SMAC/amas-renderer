package fr.irit.smac.amasrenderer.util.attributes;

import fr.irit.smac.amasrenderer.model.agent.feature.social.PortModel;
import javafx.util.StringConverter;

public class PortModelConverter extends StringConverter<PortModel> {
    
    private PortModel portModel;
    
    @Override
    public String toString(PortModel value) {
        portModel = value;
        return (value != null) ? value.getName() : "";
    }

    @Override
    public PortModel fromString(String value) {
        portModel.setName(value);
        portModel.setId(value);
        return portModel;
    }
}
