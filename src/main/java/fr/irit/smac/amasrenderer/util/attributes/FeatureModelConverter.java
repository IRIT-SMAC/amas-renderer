package fr.irit.smac.amasrenderer.util.attributes;

import fr.irit.smac.amasrenderer.model.agent.feature.FeatureModel;
import javafx.util.StringConverter;

public class FeatureModelConverter extends StringConverter<FeatureModel> {
    
    private FeatureModel featureModel;
    
    @Override
    public String toString(FeatureModel value) {
        featureModel = value;
        return (value != null) ? value.getName() : "";
    }

    @Override
    public FeatureModel fromString(String value) {
        featureModel.setName(value);
//        featureModel.setId(value);
        return featureModel;
    }
}
