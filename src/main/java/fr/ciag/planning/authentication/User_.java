package fr.ciag.planning.authentication;

import fr.ciag.planning.domain.CIAGItem_;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-01-04T18:34:30.836+0100")
@StaticMetamodel(User.class)
public class User_ extends CIAGItem_ {
	public static volatile SingularAttribute<User, String> login;
	public static volatile SingularAttribute<User, String> password;
	public static volatile SingularAttribute<User, String> salt;
	public static volatile SetAttribute<User, Role> roles;
	public static volatile SetAttribute<User, Permission> permissions;
	public static volatile SingularAttribute<User, String> nom;
	public static volatile SingularAttribute<User, String> prenom;
}
