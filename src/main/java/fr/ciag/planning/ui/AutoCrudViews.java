package fr.ciag.planning.ui;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Tree;
import com.vaadin.ui.Window;

public class AutoCrudViews extends Window {
	
	//définition du lien avec la base de donnée
	public static final String PERSISTENCE_UNIT = "fr.ciag.planning";
	static {
		EntityManager em = JPAContainerFactory
				.createEntityManagerForPersistenceUnit(PERSISTENCE_UNIT);
	}

	//Création de la page
	public AutoCrudViews() {
		/*on coupe la page en 2 parties : 
		- navtree qu contient le menu
		
		*/
		
		final HorizontalSplitPanel horizontalSplitPanel = new HorizontalSplitPanel();
		Tree navTree = new Tree();
		navTree.addListener(new Property.ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				BasicCrudView cv = (BasicCrudView) event.getProperty()
						.getValue();
				cv.refreshContainer();
				horizontalSplitPanel.setSecondComponent(cv);
			}
		});
		navTree.setSelectable(true);
		navTree.setNullSelectionAllowed(false);
		navTree.setImmediate(true);

		//horizontalSplitPanel.setSplitPosition(200, Unit.PIXELS);
		horizontalSplitPanel.addComponent(navTree);
		setContent(horizontalSplitPanel);

		// add a basic crud view for all entities known by the JPA
		// implementation, most often this is not desired and developers
		// should just list those entities they want to have editors for
		Metamodel metamodel = JPAContainerFactory
				.createEntityManagerForPersistenceUnit(PERSISTENCE_UNIT)
				.getEntityManagerFactory().getMetamodel();
		Set<EntityType<?>> entities = metamodel.getEntities();
		for (EntityType<?> entityType : entities) {
			Class<?> javaType = entityType.getJavaType();
			BasicCrudView view = new BasicCrudView(javaType,
					PERSISTENCE_UNIT);
			navTree.addItem(view);
			navTree.setItemCaption(view, view.getCaption());
			navTree.setChildrenAllowed(view, false);
		}

		// select first entity view
		navTree.setValue(navTree.getItemIds().iterator().next());
	}
}
