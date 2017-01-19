package fr.ciag.planning.authentication;

import java.util.Set;
import java.util.TreeSet;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;

import fr.ciag.planning.domain.CIAGContainer;
import fr.ciag.planning.domain.CIAGItem;
import fr.ciag.planning.ui.Ihm;

@Ihm(libelle="Utilisateur")
@Entity
@Table(name = "XUSR")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@AttributeOverrides({
    @AttributeOverride(name="topSup", column=@Column(name="XUXUSUP")),
    @AttributeOverride(name="createUser", column=@Column(name="XUXCRTUSR")),
    @AttributeOverride(name="createOrigin", column=@Column(name="XUXCRTPGM")),
    @AttributeOverride(name="createDate", column=@Column(name="XUXCRTDAT")),
    @AttributeOverride(name="modifyUser", column=@Column(name="XUXMAJUSR")),
    @AttributeOverride(name="modifyOrigin", column=@Column(name="XUXMAJPGM")),
    @AttributeOverride(name="modifyDate", column=@Column(name="XUXMAJDAT")),
    @AttributeOverride(name="deleteUser", column=@Column(name="XUXSUPUSR")),
    @AttributeOverride(name="deleteOrigin", column=@Column(name="XUXSUPPGM")),
    @AttributeOverride(name="deleteDate", column=@Column(name="XUXSUPDAT"))
})
public class User extends CIAGItem {

	@NotNull
	@Column(name="XUXUUSR", length = 20)
	@Ihm(ordre = 2, grid="Code", form="Code", description="Veuillez saisir le nom de l'agent")
	private String login;

	@NotNull
	@Column(name="XUXUPWD", length = 64)
	@Ihm(ordre = 3, form="Mot de passe", description="Veuillez saisir le nom de l'agent")
	private String password;  

	@NotNull
	@Column(name="XUXUCLE", length = 80)
	private String salt;
	
	@NotNull
	@Column(name="XUXUNOM", length = 80)
	@Ihm(ordre = 4, grid="Nom", form="Nom", description="Veuillez saisir le nom de l'agent")
	private String nom;
	
	@NotNull
	@Column(name="XUXUPRN", length = 80)
	@Ihm(ordre = 5, grid="Prénom", form="Prénom", description="Veuillez saisir le nom de l'agent")
	private String prenom;

	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	private Set<Role> roles;

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	private Set<Permission> permissions;

	public Set<Role> getRoles() {
		if(roles==null)
			roles=new TreeSet<Role>();
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public String getSalt() {
		return salt;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Set<Permission> getPermissions() {
		if(permissions==null)
			permissions=new TreeSet<Permission>();
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	public static User findByLogin(String username) {
		Query q= CIAGContainer.getQuery("select user FROM User user where user.login LIKE :username");
		q.setParameter("username", username);
        q.setMaxResults(1);
        if(q.getResultList().size()>0)
        	return (User)q.getResultList().get(0);
        else
        	return null;
	}
	
	/**
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * @param prenom the prenom to set
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @return the prenom
	 */
	public String getPrenom() {
		return prenom;
	}
	
	public User(String login, String password, String nom, String prenom) {
		this();
		setSalt(JpaSecurityUtil.getSalt());
		setLogin(login);
		setPassword(JpaSecurityUtil.hashPassword(password, getSalt()));
		setNom(nom);
		setPrenom(prenom);
	}
	
	public User(String login, String password, String nom) {
		this(login, password, nom, "");
	}
	
	public User(String login, String password) {
		this(login, password, login);
	}
	
	public User() {
		super();
	}
}