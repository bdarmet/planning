package fr.ciag.planning.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-11-28T09:41:32.335+0100")
@StaticMetamodel(Groupe.class)
public class Groupe_ {
	public static volatile SingularAttribute<Groupe, Integer> id;
	public static volatile SingularAttribute<Groupe, String> code;
	public static volatile SingularAttribute<Groupe, String> libelle;
	public static volatile ListAttribute<Groupe, Occupation> occupations;
}
