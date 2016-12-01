package fr.ciag.planning;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Viewport;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

import fr.ciag.planning.authentication.*;
import fr.ciag.planning.authentication.LoginScreen.*;
import fr.ciag.planning.ui.*;

/**
 * The Application's "main" class
 */
@SuppressWarnings("serial")
@Viewport("user-scalable=no,initial-scale=1.0")
@Theme("caftheme")
@Widgetset("fr.ciag.caf.Widgetset")
public class CAFUI extends UI {

    private AccessControl accessControl = new BasicAccessControl();
/*
	@Override
	public void init(VaadinRequest vaadinRequest) {
		setMainWindow(new AutoCrudViews());
	}
*/
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        Responsive.makeResponsive(this);
        setLocale(vaadinRequest.getLocale());
        getPage().setTitle("caf");
        if (!accessControl.isUserSignedIn()) {
            setContent(new LoginScreen(accessControl, new LoginListener() {
                @Override
                public void loginSuccessful() {
                    showMainView();
                }
            }));
        } else {
            showMainView();
        }
    }

    protected void showMainView() {
        addStyleName(ValoTheme.UI_WITH_MENU);
        setContent(new MainScreen(CAFUI.this));
        getNavigator().navigateTo(getNavigator().getState());
    }

    public static CAFUI get() {
        return (CAFUI) UI.getCurrent();
    }

    public AccessControl getAccessControl() {
        return accessControl;
    }

    @WebServlet(urlPatterns = "/*", name = "CAFUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = CAFUI.class, productionMode = false)
    public static class cafUIServlet extends VaadinServlet {
    }
}
