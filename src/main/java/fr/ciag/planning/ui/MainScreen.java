package fr.ciag.planning.ui;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.*;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;

import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;

import fr.ciag.planning.CAFUI;
import fr.ciag.planning.authentication.JpaAuthorizingRealm;
import fr.ciag.planning.authentication.User;

/**
 * Content of the UI when the user is logged in.
 * 
 * 
 */
public class MainScreen extends HorizontalLayout {
	// contient le menu principal de l'application
	private Menu menu;
	
	public MainScreen(CAFUI ui) {

		// renseigne la classe chargée de faire les conversions  
		VaadinSession.getCurrent().setConverterFactory(new CIAGConverterFactory());
		// définie le style CSS
		setStyleName("main-screen");
		CssLayout viewContainer = new CssLayout();
		final Navigator navigator = new Navigator(ui, viewContainer);
		navigator.setErrorView(ErrorView.class);
		menu = new Menu(navigator);
		//TODO : appeler CRUDVIEW 

		/*
		Metamodel metamodel = JPAContainerFactory.createEntityManagerForPersistenceUnit(ui.getPersistenceUnit())
				.getEntityManagerFactory().getMetamodel();
		Set<EntityType<?>> entities = metamodel.getEntities();
		for (EntityType<?> entityType : entities) {
			Class<?> javaType = entityType.getJavaType();
			BasicCrudView view = new BasicCrudView(javaType);
			menu.addView(view, view.getCaption(), view.getCaption(), FontAwesome.EDIT);
		}
		*/
		CRUDView view=new CRUDView(User.class);
		menu.addView(view, view.getCaption(), view.getCaption(), FontAwesome.EDIT);

		//navigator.addViewChangeListener(viewChangeListener);
		addComponent(menu);

		viewContainer.addStyleName("valo-content");
		viewContainer.setSizeFull();
		addComponent(viewContainer);
		setExpandRatio(viewContainer, 1);
		setSizeFull();
	}

	// notify the view menu about view changes so that it can display which view
	// is currently active
	/**
	ViewChangeListener viewChangeListener = new ViewChangeListener() {

		@Override
		public boolean beforeViewChange(ViewChangeEvent event) {
			return true;
		}

		@Override
		public void afterViewChange(ViewChangeEvent event) {
			menu.setActiveView(event.getViewName());
		}

	};
	 * *
	 */
}
