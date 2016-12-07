package fr.ciag.planning.domain;

import javax.persistence.*;

import fr.ciag.planning.ui.Ihm;

@Entity
@Table(name="AGE")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@AttributeOverrides({
    @AttributeOverride(name="topSup", column=@Column(name="AGAGSUP")),
    @AttributeOverride(name="createDate", column=@Column(name="AGXCRTDAT")),
    @AttributeOverride(name="modifyDate", column=@Column(name="AGXMAJDAT")),
    @AttributeOverride(name="deleteDate", column=@Column(name="AGXSUPDAT"))
})
public class Agent extends CIAGItem {
	/**
	 * identifiant unique de l'agent
	 */
	private int id;

	/**
	 * nom de l'agent
	 */
	private String nom;

	/**
	 * prenom de l'agent
	 */
	private String prenom;
	
	/**
	 * code Agent
	 */
	private String code; 
	
	/**
	 * code NIMS
	 */
	private String codeNIMS;
	
	/**
	 * est ou non Responsable toto
	 */
	private Boolean responsable;
	
	/**
	 * mot de passe
	 */
	private String motDePasse;
	
	/**
	 * groupe d'actitiv�
	 */
	private Groupe niveau;
	
	
	/**
	 * @return l'id de l'agent
	 */
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="AGAGID")
	@Ihm(visible=false)
	public int getId() {
		return id;
	}

	/**
	 * @param id l'ID a mettre
	 */
	public void setId(int id) {
		this.id=id;
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
	 * @return the code
	 */
	@Column(name="AGAGCOD", length=3, nullable=false)
	@Ihm(ordre = 1)
	public String getCode() {
		return code;
	}

	/**
	 * @return the codeNIMS
	 */
	@Column(name="AGAGNIM", length=5)
	@Ihm(ordre = 2)
	public String getCodeNIMS() {
		return codeNIMS;
	}

	/**
	 * @return the nom
	 */
	@Column(name="AGAGNOM", length=50, nullable=false)
	@Ihm(ordre = 2, description="Veuillez saisir le nom de l'agent")
	public String getNom() {
		return nom;
	}

	/**
	 * @return the prenom
	 */
	@Column(name="AGAGPRN", length=50)
	@Ihm(ordre = 3, libelle="Prénom", description="Veuillez saisir le nom de l'agent")
	public String getPrenom() {
		return prenom;
	}
	
	/**
	 * @return the motDePasse
	 */
	@Column(name="AGAGPWD", length=20, nullable=false)
	@Ihm(ordre = 4, type="password")
	public String getMotDePasse() {
		return motDePasse;
	}


	/**
	 * @return the niveau
	 */
	@ManyToOne
	@JoinColumn(name="ACGPCOD", referencedColumnName = "GPGPCOD")
	@Ihm(ordre = 5, type="password")
	public Groupe getNiveau() {
		return niveau;
	}

	/**
	 * @return the responsable
	 */ 
	@Column(name="AGAGRES")
	@Ihm(ordre = 6)
	public Boolean getResponsable() {
		return responsable;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = (code!=null) ?code.substring(0, 2).toUpperCase():null;
	}

	/**
	 * @param codeNIMS the codeNIMS to set
	 */
	public void setCodeNIMS(String codeNIMS) {
		this.codeNIMS = (codeNIMS!=null) ?codeNIMS.substring(0, 4).toUpperCase():null;
	}

	/**
	 * @param responsable the responsable to set
	 */
	public void setResponsable(Boolean responsable) {
		this.responsable = responsable;
	}

	/**
	 * @param motDePasse the motDePasse to set
	 */
	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	/**
	 * @param niveau the niveau to set
	 */
	public void setNiveau(Groupe niveau) {
		this.niveau = niveau;
	}
	
}
