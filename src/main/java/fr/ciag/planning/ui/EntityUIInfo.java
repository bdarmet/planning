package fr.ciag.planning.ui;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Vector;

public class EntityUIInfo {

	private Class<?> managedClass;
	private List<PropertyInfo> lstProperties = null;

	public EntityUIInfo(Class<?> managedClass) {
		this.managedClass = managedClass;
	}

	public List<PropertyInfo> getProperties() {
		if (lstProperties == null || lstProperties.size() == 0) {
			lstProperties = new Vector<PropertyInfo>();
			try {
				for (Field field : managedClass.getDeclaredFields()) {
					Ihm ihmInfo = null;
					try {
						PropertyDescriptor pd = new PropertyDescriptor(field.getName(), managedClass);
						if (pd.getReadMethod() != null) {
							if (pd.getReadMethod().isAnnotationPresent(Ihm.class)) {
								Annotation annotation = pd.getReadMethod().getAnnotation(Ihm.class);
								ihmInfo = (Ihm) annotation;
							} else
								ihmInfo = (Ihm) null;
						}
						lstProperties.add(new PropertyInfo(field, ihmInfo));
					} catch (Exception e) {
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return lstProperties;
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
