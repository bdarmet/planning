package fr.ciag.planning.ui;

import org.vaadin.crudui.crud.impl.GridBasedCrudComponent;
import org.vaadin.crudui.form.impl.GridLayoutCrudFormFactory;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Panel;

import fr.ciag.planning.domain.CIAGContainer;
import fr.ciag.planning.domain.CIAGItem;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;

public class CRUDView extends AbsoluteLayout implements Property.ValueChangeListener, Handler, ClickListener, View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Class<CIAGItem> entityClass = null;
	private Panel panel;

	/**
	 * créé la vue de gestion de la classe entityClass.
	 * 
	 * @param entityClass
	 */
	@SuppressWarnings("unchecked")
	public CRUDView(Class entityClass) {
		this.entityClass = entityClass;
		setSizeFull();
		buildView();
	}
	
	public String getCaption() {
		return new AnnotationInfo(entityClass).getIhmLibelle();
	}	
	
	/**
	 * construit la vue
	 */
	private void buildView() {
		setSizeFull();
		GridBasedCrudComponent<CIAGItem> crud = new GridBasedCrudComponent<CIAGItem>(entityClass);
		FormLayout layout=new FormLayout();
		layout.addComponent(crud);

		// use lambda expressions or method references to delegate CRUD operations to your backend:
		CIAGContainer container= new CIAGContainer(entityClass);
		crud.setFindAllOperation(container);
		crud.setAddOperation(container.add());
		crud.setUpdateOperation(container.update());
		crud.setDeleteOperation(container.delete());
//		crud.setCrudFormFactory(new CIAGCrudGridLayout(entityClass));

		panel = new Panel(getCaption());
		panel.setSizeFull();
		addComponent(panel);
		panel.setContent(layout);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public Action[] getActions(Object target, Object sender) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void handleAction(Action action, Object sender, Object target) {
		// TODO Auto-generated method stub

	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
