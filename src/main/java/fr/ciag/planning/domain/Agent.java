package fr.ciag.planning.domain;

import javax.persistence.*;

import fr.ciag.planning.authentication.User;
import fr.ciag.planning.ui.Ihm;

@Entity
@Table(name="AGE")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@AttributeOverrides({
    @AttributeOverride(name="topSup", column=@Column(name="AGAGSUP")),
    @AttributeOverride(name="createUser", column=@Column(name="AGXCRTUSR")),
    @AttributeOverride(name="createOrigin", column=@Column(name="AGXCRTPGM")),
    @AttributeOverride(name="createDate", column=@Column(name="AGXCRTDAT")),
    @AttributeOverride(name="modifyUser", column=@Column(name="AGXMAJUSR")),
    @AttributeOverride(name="modifyOrigin", column=@Column(name="AGXMAJPGM")),
    @AttributeOverride(name="modifyDate", column=@Column(name="AGXMAJDAT")),
    @AttributeOverride(name="deleteUser", column=@Column(name="AGXSUPUSR")),
    @AttributeOverride(name="deleteOrigin", column=@Column(name="AGXSUPPGM")),
    @AttributeOverride(name="deleteDate", column=@Column(name="AGXSUPDAT"))
})
public class Agent extends User {
	/**
	 * identifiant unique de l'agent
	 */
	private int id;

	/**
	 * code NIMS
	 */
	private String codeNIMS;
	
	/**
	 * groupe d'actitiv�
	 */
	private Groupe niveau;
	
	/**
	 * @return the codeNIMS
	 */
	@Column(name="AGAGNIM", length=5)
	@Ihm(ordre = 2)
	public String getCodeNIMS() {
		return codeNIMS;
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
	 * @param codeNIMS the codeNIMS to set
	 */
	public void setCodeNIMS(String codeNIMS) {
		this.codeNIMS = (codeNIMS!=null) ?codeNIMS.substring(0, 4).toUpperCase():null;
	}

	/**
	 * @param niveau the niveau to set
	 */
	public void setNiveau(Groupe niveau) {
		this.niveau = niveau;
	}
}
