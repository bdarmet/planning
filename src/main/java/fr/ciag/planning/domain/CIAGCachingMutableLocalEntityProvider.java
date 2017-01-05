package fr.ciag.planning.domain;

import java.util.Date;

import javax.persistence.EntityManager;

import com.vaadin.addon.jpacontainer.provider.CachingMutableLocalEntityProvider;

public class CIAGCachingMutableLocalEntityProvider<T> extends CachingMutableLocalEntityProvider<T> {
	
	public CIAGCachingMutableLocalEntityProvider(Class<T> entityClass, EntityManager entityManager) {
        super(entityClass, entityManager);
    }
	
    public CIAGCachingMutableLocalEntityProvider(Class<T> entityClass) {
        super(entityClass);
    }

    @Override
    public T addEntity(T entity) throws RuntimeException {
    	((CIAGItem)entity).setCreateDate(new Date());
        return super.addEntity(entity);
    }
    
    @Override
    public void removeEntity(Object entityId) {
    	((CIAGItem)entityId).setDeleteDate(new Date());
    	((CIAGItem)entityId).setTopSup("O");
    	super.updateEntity((T)entityId);
    }

    @Override
    public T updateEntity(T entity) {
    	((CIAGItem)entity).setModifyDate(new Date());
        T result = super.updateEntity(entity);
        return result;
    }
}
