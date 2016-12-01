package fr.ciag.planning.ui;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.util.Locale;

import javax.persistence.Column;

import com.vaadin.addon.jpacontainer.fieldfactory.FieldFactory;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.colorpicker.Color;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ColorPicker;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

import eu.maxschuster.vaadin.colorpickerfield.ColorPickerField;

public class CIAGFieldFactory extends FieldFactory {

	/*
	 * EntityUIInfo factory=new EntityUIInfo(getEntityClass()); for(PropertyInfo
	 * info:factory.getProperties()) { String
	 * columnName=info.getField().getName(); Class<?>
	 * representationClass=Label.class; if(info.getIhm()!=null) {
	 * columnName=info.getIhm().libelle(); }
	 * if(info.getField().getType()==Boolean.class ||
	 * info.getField().getType()==boolean.class)
	 * representationClass=CheckBox.class;
	 * table.addContainerProperty(columnName, representationClass, null); }
	 */
	private Class managedClass = null;

	public CIAGFieldFactory(Class managedClass) {
		this.managedClass = managedClass;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Field createField(Container container, Object itemId, Object propertyId, Component uiContext) {
		Field f = super.createField(container, itemId, propertyId, uiContext);
		f.setReadOnly(true);
		return f;
	}

	@Override
	public Field<?> createField(Item item, Object propertyId, Component uiContext) {
		Field<?> field=null;
		try {
			PropertyDescriptor pd = new PropertyDescriptor(propertyId.toString(), managedClass);
			Column column = pd.getReadMethod().getAnnotation(Column.class);
			Ihm ihm = pd.getReadMethod().getAnnotation(Ihm.class);
			if(ihm!=null && ihm.type().equals("color"))
				field = getColorPickerField(column, ihm, item, propertyId, uiContext);
			else if(ihm==null || ihm.type().equals("text"))
				field = getTextField(column, ihm, item, propertyId, uiContext);
			else if(ihm.type().equals("password"))
				field = getPasswordField(column, ihm, item, propertyId, uiContext);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return field;
	}

	private Field getTextField(Column column, Ihm ihm, Item item, Object propertyId, Component uiContext) {
		Field field = super.createField(item, propertyId, uiContext);
		if (field != null && field.getClass().isAssignableFrom(TextField.class)) {
			TextField textField = (TextField) field;
			textField.setImmediate(true);
			textField.setColumns(column.length());
			// textField.setWidth(annotation.length(), Unit.EM);
			// Gestion de la taille
			textField.setMaxLength(column.length());
			textField.addValidator(new StringLengthValidator(propertyId.toString() + " limité à " + column.length() + "caractères !! ", 1, column.length(),
					column.nullable()));
		}
		return field;
	}
	
	private Field getPasswordField(Column column, Ihm ihm, Item item, Object propertyId, Component uiContext) {
		PasswordField field=new PasswordField();
		field.setImmediate(true);
		field.setColumns(column.length());
		// textField.setWidth(annotation.length(), Unit.EM);
		// Gestion de la taille
		field.setMaxLength(column.length());
		field.addValidator(new StringLengthValidator(
				propertyId.toString() + "limité à " + column.length() + "caractères !! ", 1, column.length(),
				column.nullable()));
		field.setCaption((ihm!=null && ihm.libelle()!=null)?ihm.libelle():"Mot de Passe");
		return field;
	}
	
	public static String toRGBCode(Color color )
    {
		return String.format( "#%02X%02X%02X",
				color.getRed(),
				color.getGreen(),
				color.getBlue());
    }	

	public static Color fromRGBCode(String colorStr )
    {
        if(colorStr!=null) {
		return new Color(Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
	            Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
	            Integer.valueOf( colorStr.substring( 5, 7 ), 16 ));
        }else
        	return Color.WHITE;
    }	
	
	private Field getColorPickerField(Column column, Ihm ihm, Item item, Object propertyId, Component uiContext) {
		ColorPickerField field = new  ColorPickerField("Choisissez votre couleur");
		field.setWidth(40, Unit.PIXELS);
		field.setConverter(new Converter<com.vaadin.shared.ui.colorpicker.Color, String>() {

			@Override
			public String convertToModel(Color value, Class<? extends String> targetType,
					Locale locale) throws com.vaadin.data.util.converter.Converter.ConversionException {
				return toRGBCode(value);
			}

			@Override
			public Color convertToPresentation(String value,
					Class<? extends Color> targetType, Locale locale)
					throws com.vaadin.data.util.converter.Converter.ConversionException {
				
				return fromRGBCode(value);
			}

			@Override
			public Class<String> getModelType() {
				return String.class;
			}

			@Override
			public Class<com.vaadin.shared.ui.colorpicker.Color> getPresentationType() {
				return com.vaadin.shared.ui.colorpicker.Color.class;
			}
		});
		return field;
	}
}
