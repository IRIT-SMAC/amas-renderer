package fr.irit.smac.amasrenderer.util.attributes;

import fr.irit.smac.amasrenderer.model.agent.feature.FeatureModel;
import fr.irit.smac.amasrenderer.model.agent.feature.social.AbstractFeatureModel;
import javafx.util.StringConverter;

public class FeatureModelConverter extends StringConverter<AbstractFeatureModel> {
    
    private AbstractFeatureModel featureModel;
    
    @Override
    public String toString(AbstractFeatureModel value) {
        featureModel = value;
        return (value != null) ? value.getName() : "";
    }

    @Override
    public AbstractFeatureModel fromString(String value) {
        featureModel.setName(value);
//        featureModel.setId(value);
        return featureModel;
    }
}
