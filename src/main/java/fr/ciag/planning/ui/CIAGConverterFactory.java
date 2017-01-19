package fr.ciag.planning.ui;

import java.util.Set;

import com.vaadin.data.util.converter.Converter;
import com.vaadin.data.util.converter.DefaultConverterFactory;
import com.vaadin.data.util.converter.StringToBooleanConverter;
import com.vaadin.data.util.converter.StringToIntegerConverter;

/**
 * cette classe permet de convertir une chaine de caratère en autre chose (booleen)
 * @author bda
 *
 */
public class CIAGConverterFactory extends DefaultConverterFactory {

    @SuppressWarnings("unchecked")
	@Override
    protected <PRESENTATION, MODEL> Converter<PRESENTATION, MODEL> findConverter(
            Class<PRESENTATION> presentationType, Class<MODEL> modelType) {
        // Handle Boolean <-> String
        if (presentationType == String.class && (modelType == Boolean.class || modelType == boolean.class)) {
            return (Converter<PRESENTATION, MODEL>) new StringToBooleanConverter();
        }

        // Handle Int <-> String
        if (presentationType == String.class && (modelType == Integer.class || modelType == int.class)) {
            return (Converter<PRESENTATION, MODEL>) new StringToIntegerConverter();
        }
        
        // Handle Boolean <-> String
        if (presentationType == String.class && (modelType == Set.class)) {
            return (Converter<PRESENTATION, MODEL>) new MultiSelectConverter<>();
        }
        
        
        // Let default factory handle the rest
        return super.findConverter(presentationType, modelType);
    }
	
}
