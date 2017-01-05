package fr.ciag.planning.domain;

import java.util.Date;

import com.vaadin.addon.jpacontainer.JPAContainer;

import fr.ciag.planning.authentication.User;

public class CIAGContainer<T> extends JPAContainer<T> {
	
	public CIAGContainer(Class<T> entityClass) {
		super(entityClass);
	} 

   public T addEntity(T entity) throws RuntimeException {
	   return (T)super.addEntity(entity);
   }
	
	
    public Object addEntity(CIAGItem entity) throws UnsupportedOperationException,
    IllegalStateException {
    	((CIAGItem) entity).setCreateDate(new Date());
    	return super.addEntity((T) entity);
    }	
    
    public boolean removeItem(Object itemId) {
    	return super.removeItem(itemId);
    }
}
