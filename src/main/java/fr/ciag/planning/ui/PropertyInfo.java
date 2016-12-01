package fr.ciag.planning.ui;

import java.lang.reflect.Field;

public class PropertyInfo {
	private Field field; 
	private Ihm ihm;
	
	public PropertyInfo(Field method, Ihm ihm) {
		setField(method);
		setIhm(ihm);
	}

	public Field getField() {
		return field;
	}
	
	public Class getType() {
		return field.getType();
	}
	
	/**
	 * @return the ihm
	 */
	public Ihm getIhm() {
		return ihm;
	}

	/**
	 * @param ihm the ihm to set
	 */
	public void setIhm(Ihm ihm) {
		this.ihm = ihm;
	}
	
	public String toString() {
		return getIhm().libelle()+":"+getField().toString()+"-"+getIhm().description();
	}

	/**
	 * @param field the field to set
	 */
	public void setField(Field field) {
		this.field = field;
	} 
}
