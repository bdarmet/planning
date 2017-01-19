package fr.ciag.planning.ui;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.persistence.Table;

public class AnnotationInfo {

	private Class<?> managedClass;
	private List<PropertyInfo> lstProperties = null;

	public AnnotationInfo(Class<?> managedClass) {
		this.managedClass = managedClass;
	}

	/**
	 * @return le nom de la table (s'il est renseigné associé au POJO)
	 */
	public String getIhmLibelle() {
		Ihm annotation = (Ihm) managedClass.getAnnotation(Ihm.class);
		if (annotation != null)
			return annotation.libelle();
		return null;
	}

	/**
	 * @return le nom de la table (s'il est renseigné associé au POJO)
	 */
	public String getTableName() {
		Table annotation = (Table) managedClass.getAnnotation(Table.class);
		if (annotation != null)
			return annotation.name();
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public List<PropertyInfo> getProperties() {
		if (lstProperties == null || lstProperties.size() == 0) {
			lstProperties = new Vector<PropertyInfo>();
			try {
				for (Field field : managedClass.getDeclaredFields()) {
					Ihm ihmInfo = null;
					Annotation annotation = field.getAnnotation(Ihm.class);
					lstProperties.add(new PropertyInfo(field, (Ihm) annotation));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return lstProperties;
	}

	/**
	 * @return la liste triée des colonnes à afficher.
	 */
	@SuppressWarnings("unchecked")
	public List<PropertyInfo> getGridColumns() {
		List<PropertyInfo> list = new ArrayList<PropertyInfo>();
		for (PropertyInfo info : getProperties()) {
			if (!info.getIhm().grid().equals("hidden"))
				list.add(info);
		}
		java.util.Collections.sort(list);
		return list;
	}

	/**
	 * @return le nom de la classe sans le packge et sans l'extension.
	 */
	public String getEntityName() {
		return getManagedClass().getName();
	}

	/**
	 * @return the managedClass
	 */
	public Class<?> getManagedClass() {
		return managedClass;
	}

	/**
	 * @param managedClass
	 *            the managedClass to set
	 */
	public void setManagedClass(Class<?> managedClass) {
		this.managedClass = managedClass;
	}
}
