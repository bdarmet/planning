package fr.ciag.planning.ui;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.addon.jpacontainer.fieldfactory.FieldFactory;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.event.Action;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.Action.Handler;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.themes.Reindeer;

import fr.ciag.planning.domain.CIAGContainer;
import fr.ciag.planning.domain.CIAGContainerFactory;

/**
 * This is a rudimentary general purpose CRUD view to list and edit JPA entities
 * with JPAContainer. Lists all entities in a table and puts the selected row
 * into a buffered form below it. Form uses {@link FieldFactory} to support most
 * common relation types.
 */
public class BasicCrudView<T> extends AbsoluteLayout
		implements Property.ValueChangeListener, Handler, ClickListener, View {

	private CIAGContainer<T> container;
	private Table _table;
	private Form form;
	private FieldFactory _fieldFactory;
	private Class<T> entityClass;
	private Button commit;
	private Button discard;
	private Object[] formPropertyIds;
	private Button addButton;
	private Button deleteButton;
	private Panel panel;
	private final String persistenceUnit;
	private HorizontalSplitPanel splitPanel;

	public BasicCrudView(Class<T> entityClass, final String persistenceUnit) {
		this.entityClass = entityClass;
		this.persistenceUnit = persistenceUnit;
		setSizeFull();
		initContainer();
		buildView();
	}

	protected FieldFactory getFieldFactory() {
		if (_fieldFactory == null)
			_fieldFactory = new CIAGFieldFactory(entityClass);
		return _fieldFactory;
	}

	protected Table getTable() {
		if (_table == null) {
			_table = new CIAGTable(null, getContainer());
			_table.setColumnReorderingAllowed(true);
			_table.setColumnCollapsingAllowed(true);
			_table.setTableFieldFactory(getFieldFactory());
		}
		return _table;
	}

	public void setVisibleTableProperties(Object... tablePropertyIds) {
		getTable().setVisibleColumns(tablePropertyIds);
	}

	public void setVisibleFormProperties(Object... formPropertyIds) {
		this.formPropertyIds = formPropertyIds;
		form.setVisibleItemProperties(formPropertyIds);
	}

	/**
	 * définition de la zone liste
	 */
	protected void buildView() {
		setSizeFull();
		panel = new Panel(getCaption());
		panel.setSizeFull();
		addComponent(panel);

		splitPanel = new HorizontalSplitPanel();
		splitPanel.setSizeFull();

		getTable().setSizeFull();
		getTable().setSelectable(true);
		getTable().addListener(this);
		getTable().setImmediate(true);
		getTable().addActionHandler(this);
		splitPanel.addComponent(getTable());

		addButton = new Button("", this);
		addButton.setIcon(new ThemeResource("img/add.png"));
		addButton.setDescription("Créer " + getEntityClass().getSimpleName());
		addButton.setStyleName(Reindeer.BUTTON_DEFAULT);
		addButton.setWidth(20, Unit.PIXELS);
		addComponent(addButton, "top:0;right:90px;");

		deleteButton = new Button("", this);
		deleteButton.setIcon(new ThemeResource("img/delete.png"));
		deleteButton.setDescription("Supprimer " + getEntityClass().getSimpleName());
		deleteButton.setStyleName(Reindeer.BUTTON_DEFAULT);
		deleteButton.setWidth(20, Unit.PIXELS);
		deleteButton.setEnabled(false);
		addComponent(deleteButton, "top:0;right:5px;");

		splitPanel.addComponent(getFormPanel());
		splitPanel.setSplitPosition(100, UNITS_PERCENTAGE);

		panel.setContent(splitPanel);

		// register action handler (enter and ctrl-n)
		panel.addActionHandler(this);
	}

	public Component getFormPanel() {
		if (form == null) {
			form = new Form();
			form.setVisible(false);

			form.setCaption(getEntityClass().getSimpleName());
			form.setFormFieldFactory(getFieldFactory());
			commit = new Button("Sauver", new Button.ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					form.commit();
					splitPanel.setSplitPosition(100, UNITS_PERCENTAGE);
				}
			});
			commit.setIcon(new ThemeResource("img/accept.png"));
			commit.setStyleName(Reindeer.BUTTON_DEFAULT);

			discard = new Button("Annuler", new Button.ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					form.discard();
					splitPanel.setSplitPosition(100, UNITS_PERCENTAGE);
				}
			});
			discard.setIcon(new ThemeResource("img/cancel.png"));
			form.getFooter().addComponent(commit);
			form.getFooter().addComponent(discard);
			((HorizontalLayout) form.getFooter()).setSpacing(true);
		}
		return form;
	}

	public Class<T> getEntityClass() {
		return entityClass;
	}

	protected void initContainer() {
		container = CIAGContainerFactory.makeCiag(getEntityClass(), persistenceUnit);
	}

	protected JPAContainer<T> getContainer() {
		return container;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void valueChange(ValueChangeEvent event) {
		Object itemId = event.getProperty().getValue();
		Item item = getTable().getItem(itemId);
		boolean entitySelected = item != null;
		// modify visibility of form and delete button if an item is selected
		getFormPanel().setVisible(entitySelected);
		deleteButton.setEnabled(entitySelected);
		if (entitySelected) {
			// set entity item to form and focus it
			splitPanel.setSplitPosition(80, Unit.PERCENTAGE, true);
			Collection sortedPropertyIds = (formPropertyIds != null) ? Arrays.asList(formPropertyIds) : item.getItemPropertyIds(); 
			form.setItemDataSource(item, sortedPropertyIds);
			form.focus();
		}
	}

	@Override
	public String getCaption() {
		return getEntityClass().getSimpleName() + "s";
	}

	private static final ShortcutAction SAVE = new ShortcutAction("Save", KeyCode.ENTER, null);
	private static final ShortcutAction SAVE2 = new ShortcutAction("^Save");
	private static final ShortcutAction NEW = new ShortcutAction("^New");
	private static final Action DELETE = new Action("Delete");

	private static final Action[] ACTIONS = new Action[] { NEW, DELETE };
	private static final Action[] SHORTCUT_ACTIONS = new Action[] { SAVE, SAVE2, NEW };

	@Override
	public Action[] getActions(Object target, Object sender) {
		if (sender == getTable()) {
			return ACTIONS;
		} else {
			return SHORTCUT_ACTIONS;
		}
	}

	@Override
	public void handleAction(Action action, Object sender, Object target) {
		if (action == NEW) {
			addItem();
		} else if (action == DELETE) {
			deleteItem(target);
		} else if (action == SAVE || action == SAVE2) {
			if (form.isVisible()) {
				form.commit();
			}
		}

	}

	private void deleteItem(Object itemId) {
		container.removeItem(itemId);
	}

	Object selectedItem=null;
	
	public Object getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(Object selectedItem) {
		this.selectedItem = selectedItem;
	}

	protected void addItem() { 
		try {
			T newInstance = newInstance();
			Object itemId = container.addEntity(newInstance);
			splitPanel.setSplitPosition(80, Unit.PERCENTAGE, true);
			getTable().setValue(itemId);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * This method creates a new instance of the main entity type.
	 * 
	 * @return a new instance of the main entity type
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	protected T newInstance() throws InstantiationException, IllegalAccessException {
		T newInstance = getEntityClass().newInstance();
		return newInstance;
	}

	/**
	 * Method to refresh containers in this view. E.g. if a bidirectional
	 * reference is edited from the opposite direction or if we knew that an
	 * other user had edited visible values.
	 */
	public void refreshContainer() {
		container.refresh();
		if (getTable().getValue() != null) {
			// reset form as e.g. referenced containers may have changed
			form.setItemDataSource(getTable().getItem(getTable().getValue()));
		}
	}

	@Override
	public void buttonClick(ClickEvent event) {
		if (event.getButton() == addButton) {
			addItem();
		} else if (event.getButton() == deleteButton) {
			deleteItem(getTable().getValue());
		}
	}

	@Override
	public void attach() {
		super.attach();
		panel.focus();
	}

	@Override
	public void enter(ViewChangeEvent event) {

	}
}