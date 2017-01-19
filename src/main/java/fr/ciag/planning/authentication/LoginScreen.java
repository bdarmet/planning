package fr.ciag.planning.authentication;

import java.io.Serializable;

import org.apache.shiro.*;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.*;

import com.vaadin.event.ShortcutAction;
import com.vaadin.server.Page;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import fr.ciag.planning.CAFUI;
import fr.ciag.planning.ui.MainScreen;

/**
 * UI content when the user is not logged in yet.
 */
public class LoginScreen extends CssLayout {

    private TextField username;
    private PasswordField password;
    private Button login;
    private CheckBox checkbox = new CheckBox("Se souvenir de moi");
    private Button forgotPassword;
    private LoginListener loginListener;
    //private AccessControl accessControl;
    /**
     * permet l'appel multiple à la fenetre de login
     */
	private boolean goToAuthorizedManagerView = false;  
    
	public void clear () {
		username.clear();
		password.clear();
		checkbox.clear();
	}
	
    public LoginScreen(LoginListener loginListener) {
    	goToAuthorizedManagerView = true;
        this.loginListener = loginListener;
        buildUI();
        username.focus();
    }
	
    private void buildUI() {
        addStyleName("login-screen");

        Component loginForm = buildLoginForm();

        VerticalLayout centeringLayout = new VerticalLayout();
        centeringLayout.setStyleName("centering-layout");
        centeringLayout.addComponent(loginForm);
        centeringLayout.setComponentAlignment(loginForm,
                Alignment.MIDDLE_CENTER);

        CssLayout loginInformation = buildLoginInformation();

        addComponent(centeringLayout);
        addComponent(loginInformation);
    }

    private Component buildLoginForm() {
        FormLayout loginForm = new FormLayout();

        loginForm.addStyleName("login-form");
        loginForm.setSizeUndefined();
        loginForm.setMargin(false);

        loginForm.addComponent(username = new TextField("Nom d'utilisateur", "admin"));
        username.setWidth(15, Unit.EM);
        loginForm.addComponent(password = new PasswordField("Mot de passe"));
        password.setWidth(15, Unit.EM);
        password.setDescription("Vous pouvez ecrire n'importe quoi");
        CssLayout buttons = new CssLayout();
        buttons.setStyleName("buttons");
        loginForm.addComponent(buttons);

        buttons.addComponent(login = new Button("Connexion"));
        login.setDisableOnClick(true);
        login.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    login();
                } finally {
                    login.setEnabled(true);
                }
            }
        });
        login.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        login.addStyleName(ValoTheme.BUTTON_FRIENDLY);

        buttons.addComponent(forgotPassword = new Button("Mot de passe oublié ?"));
        forgotPassword.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                showNotification(new Notification("Hint: Try anything"));
            }
        });
        loginForm.addComponent(checkbox);
        forgotPassword.addStyleName(ValoTheme.BUTTON_LINK);
        return loginForm;
    }

    private CssLayout buildLoginInformation() {
        CssLayout loginInformation = new CssLayout();
        loginInformation.setStyleName("login-information");
        Label loginInfoText = new Label(
                "<h1>Login Information</h1>"
                        + "Veuillez saisr vos informations de connexion, Si vous ne les connaissez pas, je vous invite à vous rapprocher de M. Piller",
                ContentMode.HTML);
        loginInformation.addComponent(loginInfoText);
        return loginInformation;
    }

    private void login() {
		Subject currentUser = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(
				username.getValue(), password.getValue());
		
		token.setRememberMe(checkbox.getValue()); // if true user will not have to enter username/password in new browser session
		
		try {
			currentUser.login(token); //tries to authenticate user
			clear();
			
			if (goToAuthorizedManagerView) {
				if (currentUser.hasRole("manager")) {
					loginListener.loginSuccessful();
				} else {
					currentUser.logout();
					Page.getCurrent().reload();
				}
			} else {
				loginListener.loginSuccessful();
			}
		} catch (Exception ex) { //if authentication is unsuccessful
			clear();
			Notification.show("Login Error:", "Invalid username/password combination.", Notification.TYPE_ERROR_MESSAGE);
		}
    }

    
    
    private void showNotification(Notification notification) {
        // keep the notification visible a little while after moving the
        // mouse, or until clicked
        notification.setDelayMsec(2000);
        notification.show(Page.getCurrent());
    }

    public interface LoginListener extends Serializable {
        void loginSuccessful();
    }
}
