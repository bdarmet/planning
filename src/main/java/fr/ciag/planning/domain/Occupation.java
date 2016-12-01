package fr.ciag.planning.domain;

import java.awt.Color;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import fr.ciag.planning.ui.Ihm;

@Entity
@Table(name="OCC")
public class Occupation {
	/**
	 * identifiant unique de l'agent
	 */
	private int id;

	
	/**
	 * touche d'acces rapide � l'occupation
	 */
	private String raccourci;
	
	/**
	 * code
	 */
	private String code;
	
	/**
	 * libell�
	 */
	private String libelle;
	
	/**
	 * validation
	 */
	private boolean validation;
	
	/**
	 * present
	 */
	private boolean present;
	
	/**
	 * prestation
	 */
	private boolean prestation;
	
	/**
	 * couleur 
	 */
	private String couleur;
	
	/**
	 *groupe associ�
	 */
	private Groupe  groupe;

	/**
	 * @return the id
	 */
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="OCOCID")
	public int getId() {
		return id;
	}

	/**
	 * @return the raccourci
	 */
	@Column(name="OCOCRAC", length=1)
	public String getRaccourci() {
		return raccourci;
	}

	/**
	 * @return the code
	 */
	@Column(name="OCOCCOD", length=3)
	public String getCode() {
		return code;
	}

	/**
	 * @return the libelle
	 */
	@Column(name="OCOCLBC", length=10)
	public String getLibelle() {
		return libelle;
	}

	/**
	 * @return the validation
	 */
	@Column(name="OCOCVAL")
	public boolean isValidation() {
		return validation;
	}

	/**
	 * @return the present
	 */
	@Column(name="OCOCPRS")
	public boolean isPresent() {
		return present;
	}

	/**
	 * @return the prestation
	 */
	@Column(name="OCOCPRE")
	public boolean isPrestation() {
		return prestation;
	}

	/**
	 * @return the couleur
	 */
	@Column(name="OCOCCOU")
	@Ihm(type="color")
	public String getCouleur() {
		return couleur;
	}

	/**
	 * @return the groupe
	 */
	@ManyToOne
	@JoinColumn(name="OCGPCOD",  referencedColumnName = "GPGPCOD")
	public Groupe getGroupe() {
		return groupe;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @param raccourci the raccourci to set
	 */
	public void setRaccourci(String raccourci) {
		this.raccourci = raccourci;
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

	/**
	 * @param validation the validation to set
	 */
	public void setValidation(boolean validation) {
		this.validation = validation;
	}

	/**
	 * @param present the present to set
	 */
	public void setPresent(boolean present) {
		this.present = present;
	}

	/**
	 * @param prestation the prestation to set
	 */
	public void setPrestation(boolean prestation) {
		this.prestation = prestation;
	}

	/**
	 * @param couleur the couleur to set
	 */
	public void setCouleur(String couleur) {
		this.couleur = couleur;
	}

	/**
	 * @param groupe the groupe to set
	 */
	public void setGroupe(Groupe groupe) {
		this.groupe = groupe;
	}
	
	public Occupation() {
		
	}
	/**
	 * Cr�e une nouvelle 
	 * @param code
	 * @param Libelle
	 * @param raccourci
	 * @param present
	 * @param prestation
	 * @param validation
	 * @param couleur
	 */
	Occupation(String code, String Libelle, String raccourci, boolean present, boolean prestation, boolean validation, String couleur) {
		this();
		setCode(code);
		setLibelle(Libelle);
		setRaccourci(raccourci);
		setPresent(present);
		setPrestation(prestation);
		setValidation(validation);
		setCouleur(couleur);
		
	}

}
