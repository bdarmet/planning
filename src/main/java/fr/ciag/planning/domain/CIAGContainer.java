package fr.ciag.planning.domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.vaadin.crudui.crud.AddOperationListener;
import org.vaadin.crudui.crud.DeleteOperationListener;
import org.vaadin.crudui.crud.FindAllCrudOperationListener;
import org.vaadin.crudui.crud.UpdateOperationListener;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Property;

import fr.ciag.planning.ui.AnnotationInfo;

public class CIAGContainer implements FindAllCrudOperationListener<CIAGItem>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String PERSISTENCE_UNIT = "fr.ciag.planning";
	private static EntityManager em;
	private Class<CIAGItem> entityClass=null; 
	private AnnotationInfo info=null;
	
	/**
	 * @return le nom de la table
	 */
	private String getTablename() {
		return info.getTableName();
	}
	
	public Property<Object> getProperty(CIAGItem entity, String mappedBy) {
		return null;
	}
	
	//définition du lien avec la base de donnée
	static {
		em = JPAContainerFactory
				.createEntityManagerForPersistenceUnit(PERSISTENCE_UNIT);
	}

	public static Query getQuery(String query) {
		return em.createQuery(query);
	}

	public CIAGContainer(Class<CIAGItem> entityClass) {
		this.entityClass = entityClass;
		info = new AnnotationInfo(entityClass);
	}

	/**
	 * Fait un select * de la table
	 */
	@Override
	public Collection<CIAGItem> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CIAGItem> cq = cb.createQuery(entityClass);
        Root<CIAGItem> rootEntry = cq.from(entityClass);
        CriteriaQuery<CIAGItem> all = cq.select(rootEntry);
        TypedQuery<CIAGItem> allQuery = em.createQuery(all);
        return allQuery.getResultList();
	}

	/**
	 * Cette methode permet de définir le traitement effectué pour ajouter un nouvel element  
	 * @return
	 */
	@SuppressWarnings("serial")
	public AddOperationListener<CIAGItem> add() {
		return new AddOperationListener<CIAGItem>() {
			@Override
			public CIAGItem perform(CIAGItem domainObject) {
				em.getTransaction().begin();
				domainObject.setCreateCoockie("CRUDVIEW");
				em.merge(domainObject);
				em.getTransaction().commit();
				return domainObject;
			}
		};
	}

	@SuppressWarnings("serial")
	public DeleteOperationListener<CIAGItem> delete() {
		return new DeleteOperationListener<CIAGItem>() {
			@Override
			public void perform(CIAGItem domainObject) {
				em.getTransaction().begin();
				domainObject.setDeleteCoockie("CRUDVIEW");
				em.merge(domainObject);
				em.getTransaction().commit();
			}
		};
	}
	
	@SuppressWarnings("serial")
	public UpdateOperationListener<CIAGItem> update() {
		return new UpdateOperationListener<CIAGItem>() {

			@Override
			public CIAGItem perform(CIAGItem domainObject) {
				em.getTransaction().begin();
				domainObject.setModifyCoockie("CRUDVIEW");
				em.merge(domainObject);
				em.getTransaction().commit();
				return domainObject;
			}
		};
	}
}
