package fr.ciag.planning.ui;

import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.ui.Table;

public class CIAGTable extends com.vaadin.ui.Table {

	public CIAGTable(String caption, Container dataSource) {
		super(caption, dataSource);

		setCellStyleGenerator(new CellStyleGenerator() {
			@Override
			public String getStyle(Table source, Object itemId, Object propertyId) {
				if (propertyId != null) {
					Property p = dataSource.getItem(itemId).getItemProperty(propertyId);
					if (p.getType().isAssignableFrom(Boolean.class)) {
						if(p.getValue()==null)
							return "unchecked-checkbox";
						else
							return ((boolean)p.getValue()) ? "checked-checkbox" : "unchecked-checkbox";
					}
					return null;
				}
				return null;
			}
		});
	}

	protected Object getPropertyValue(Object rowId, Object colId, Property property) {
		return super.getPropertyValue(rowId, colId, property);
	}

}
