package fr.ciag.planning.domain;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.addon.jpacontainer.provider.CachingMutableLocalEntityProvider;

import fr.ciag.planning.authentication.User;

public class CIAGContainerFactory extends JPAContainerFactory {
	
	public static final String PERSISTENCE_UNIT = "fr.ciag.planning";
	private static EntityManager em;
	
	//définition du lien avec la base de donnée
	static {
		em = JPAContainerFactory
				.createEntityManagerForPersistenceUnit(PERSISTENCE_UNIT);
	}

	public static <T> CIAGContainer<T> make(Class<T> entityClass) {
		CIAGContainer<T> container = new CIAGContainer<T>(entityClass);
		container.setEntityProvider(new CIAGCachingMutableLocalEntityProvider<T>(entityClass, createEntityManagerForPersistenceUnit(PERSISTENCE_UNIT)));
		return container;
	}
	
    public static Query getQuery(String query) {
    	 return em.createQuery(query);
    }
    
    public static void persist(Object entity) {
    	em.getTransaction().begin();
    	em.persist(entity);
    	em.getTransaction().commit();
    }
	
}
