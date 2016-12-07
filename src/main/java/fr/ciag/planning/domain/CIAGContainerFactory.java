package fr.ciag.planning.domain;

import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.addon.jpacontainer.provider.CachingMutableLocalEntityProvider;

public class CIAGContainerFactory extends JPAContainerFactory {

	public static <T> CIAGContainer<T> makeCiag(Class<T> entityClass, String persistenceUnitName) {
		CIAGContainer<T> container = new CIAGContainer<T>(entityClass);
		container.setEntityProvider(new CIAGCachingMutableLocalEntityProvider<T>(entityClass, createEntityManagerForPersistenceUnit(persistenceUnitName)));
		return container;
	}
}
