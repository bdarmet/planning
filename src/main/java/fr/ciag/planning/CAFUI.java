package fr.ciag.planning;

import javax.persistence.EntityManager;
import javax.servlet.annotation.WebServlet;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.Subject;

import com.vaadin.addon.jpacontainer.JPAContainerFactory;
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
	
	// définition du lien avec la base de donnée
	// le paramétrage de la base de données est définie sous le nom "fr.ciag.planning"
	// dans le fichier META-INF/persistence.xml
	public static final String PERSISTENCE_UNIT = "fr.ciag.planning";
	public static String getPersistenceUnit() {
		return PERSISTENCE_UNIT;
	}

	static {
		EntityManager em = JPAContainerFactory.createEntityManagerForPersistenceUnit(PERSISTENCE_UNIT);
		Realm realm = new JpaAuthorizingRealm(); 
		SecurityManager securityManager = new DefaultSecurityManager(realm);
		SecurityUtils.setSecurityManager(securityManager);
		
		
		//on renseigne les informations du role root
		User root=User.findByLogin("root");
		if(root==null) {
			root=new User("root", "root");
			root.getRoles().add(new Role("manager"));
			em.getTransaction().begin();
			em.persist(root);
			em.getTransaction().commit();		
		}
	}
	
    @Override
    /**
     * appel initial. 
     * renseigne le titre de la page (caf)
     * récupere de shiro l'utilisateur.<br/> 
     * si l'utilisateur n'est pas authentifié,<br/>  
     *       on appelle LoginScreen (qui appelera showMainView)<br/> 
     * sinon   <br/>
     *       on appelle showMainView<br/>
     * finSi<br/>
     */
    protected void init(VaadinRequest vaadinRequest) {
        Responsive.makeResponsive(this);
        setLocale(vaadinRequest.getLocale());
        getPage().setTitle("caf");
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated()) {
            setContent(new LoginScreen(new LoginListener() {
                @Override
                public void loginSuccessful() {
                	showMainView();
                }
            }));
        } else {
            showMainView();
        }
    }

    /**
     * la fenetre principale devient MainScreen 
     */
    protected void showMainView() {
        addStyleName(ValoTheme.UI_WITH_MENU);
        setContent(new MainScreen(CAFUI.this));
        getNavigator().navigateTo(getNavigator().getState());
    }

    public static CAFUI get() {
        return (CAFUI) UI.getCurrent();
    }

    @WebServlet(urlPatterns = "/*", name = "CAFUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = CAFUI.class, productionMode = false)
    public static class cafUIServlet extends VaadinServlet {
    }
}
