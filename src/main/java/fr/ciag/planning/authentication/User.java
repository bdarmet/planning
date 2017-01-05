package fr.ciag.planning.authentication;

import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.*;
import fr.ciag.planning.domain.CIAGContainerFactory;
import fr.ciag.planning.domain.CIAGItem;
import fr.ciag.planning.ui.Ihm;

@Entity
@Table(name = "XUSR")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@AttributeOverrides({
    @AttributeOverride(name="topSup", column=@Column(name="XUXUSUP")),
    @AttributeOverride(name="createDate", column=@Column(name="XUXCRTDAT")),
    @AttributeOverride(name="modifyDate", column=@Column(name="XUXMAJDAT")),
    @AttributeOverride(name="deleteDate", column=@Column(name="XUXSUPDAT"))
})
public class User extends CIAGItem {

	@NotNull
	@Column(name="XUXUUSR", length = 20)
	private String login;

	@NotNull
	@Column(name="XUXUPWD", length = 64)
	private String password;  

	@NotNull
	@Column(name="XUXUCLE", length = 80)
	private String salt;
	
	@NotNull
	@Column(name="XUXUNOM", length = 80)
	private String nom;
	
	@NotNull
	@Column(name="XUXUPRN", length = 80)
	private String prenom;

	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	private Set<Role> roles;

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	private Set<Permission> permissions;

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
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	public static User findByLogin(String username) {
		Query q= CIAGContainerFactory.getQuery("select c FROM users where c.login LIKE :username");
		q.setParameter("username", username);
        q.setMaxResults(1);
        return (User)q.getResultList().get(0);
	}
	
	/**
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = (nom!=null)?nom.substring(0, 49).toUpperCase():null;
	}

	/**
	 * @param prenom the prenom to set
	 */
	public void setPrenom(String prenom) {
		this.prenom = (prenom!=null)?prenom.substring(0, 49):null;
	}

	/**
	 * @return the nom
	 */
	@Ihm(ordre = 2, description="Veuillez saisir le nom de l'agent")
	public String getNom() {
		return nom;
	}

	/**
	 * @return the prenom
	 */
	@Ihm(ordre = 3, libelle="Prénom", description="Veuillez saisir le nom de l'agent")
	public String getPrenom() {
		return prenom;
	}
}