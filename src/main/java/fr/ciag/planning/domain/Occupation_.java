package fr.ciag.planning.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-01T10:16:57.157+0100")
@StaticMetamodel(Occupation.class)
public class Occupation_ {
	public static volatile SingularAttribute<Occupation, Integer> id;
	public static volatile SingularAttribute<Occupation, String> raccourci;
	public static volatile SingularAttribute<Occupation, String> code;
	public static volatile SingularAttribute<Occupation, String> libelle;
	public static volatile SingularAttribute<Occupation, Boolean> validation;
	public static volatile SingularAttribute<Occupation, Boolean> present;
	public static volatile SingularAttribute<Occupation, Boolean> prestation;
	public static volatile SingularAttribute<Occupation, String> couleur;
	public static volatile SingularAttribute<Occupation, Groupe> groupe;
}
