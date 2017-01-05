package fr.ciag.planning.domain;

import fr.ciag.planning.authentication.User_;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-01-04T18:33:09.392+0100")
@StaticMetamodel(Agent.class)
public class Agent_ extends User_ {
	public static volatile SingularAttribute<Agent, String> codeNIMS;
	public static volatile SingularAttribute<Agent, Groupe> niveau;
}
