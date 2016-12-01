package fr.ciag.planning.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-01T10:16:57.021+0100")
@StaticMetamodel(Agent.class)
public class Agent_ {
	public static volatile SingularAttribute<Agent, Integer> id;
	public static volatile SingularAttribute<Agent, String> code;
	public static volatile SingularAttribute<Agent, String> codeNIMS;
	public static volatile SingularAttribute<Agent, String> nom;
	public static volatile SingularAttribute<Agent, String> prenom;
	public static volatile SingularAttribute<Agent, String> motDePasse;
	public static volatile SingularAttribute<Agent, Groupe> niveau;
	public static volatile SingularAttribute<Agent, Boolean> responsable;
}
