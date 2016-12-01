package fr.ciag.planning.ui;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;

import fr.ciag.planning.CAFUI;

/**
 * Content of the UI when the user is logged in.
 * 
 * 
 */
public class MainScreen extends HorizontalLayout {

	// définition du lien avec la base de donnée
	public static final String PERSISTENCE_UNIT = "fr.ciag.planning";
	static {
		EntityManager em = JPAContainerFactory.createEntityManagerForPersistenceUnit(PERSISTENCE_UNIT);
	}

	private Menu menu;

	public MainScreen(CAFUI ui) {
		VaadinSession.getCurrent().setConverterFactory(new ConverterFactory());
		setStyleName("main-screen");
		CssLayout viewContainer = new CssLayout();
		final Navigator navigator = new Navigator(ui, viewContainer);
		navigator.setErrorView(ErrorView.class);
		menu = new Menu(navigator);

		Metamodel metamodel = JPAContainerFactory.createEntityManagerForPersistenceUnit(PERSISTENCE_UNIT)
				.getEntityManagerFactory().getMetamodel();
		Set<EntityType<?>> entities = metamodel.getEntities();
		for (EntityType<?> entityType : entities) {
			Class<?> javaType = entityType.getJavaType();
			BasicCrudView view = new BasicCrudView(javaType, PERSISTENCE_UNIT);
			menu.addView(view, view.getCaption(), view.getCaption(), FontAwesome.EDIT);
		}

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