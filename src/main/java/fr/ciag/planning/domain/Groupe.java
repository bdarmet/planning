package fr.ciag.planning.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="GRP")
public class Groupe {
	/**
	 * identifiant unique de l'agent
	 */
	private int id;
	
	/**
	 * code Groupe
	 */
	private String code;
	
	/**
	 * libell�e
	 */
	private String libelle;

	
	private List<Occupation> lstOccupations;
	
	/**
	 * @return the id
	 */
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="GPGPID")
	public int getId() {
		return id;
	}

	/**
	 * @return the code
	 */
	@Column(name="GPGPCOD", length=3, unique=true)
	public String getCode() {
		return code;
	}

	/**
	 * @return the libelle
	 */
	@Column(name="GPGPLBC", length=10)
	public String getLibelle() {
		return libelle;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @param libelle the libelle to set
	 */
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	
	@OneToMany(mappedBy = "groupe", cascade = CascadeType.ALL )
	public List<Occupation> getOccupations() {
		return lstOccupations;
	}

	public void setOccupations(List<Occupation> list) {
		lstOccupations = list;
	}
	
	
	public Groupe() {
	}
	
	/**
	 * cr�er un nouveau groupe.
	 * @param code
	 * @param libelle
	 */
	public Groupe(String code, String libelle) {
		setCode(code);
		setLibelle(libelle);
	} 
	
	public String toString() {
		return getCode()+"-"+getLibelle();
	}
}
