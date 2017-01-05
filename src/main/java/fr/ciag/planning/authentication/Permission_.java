package fr.ciag.planning.authentication;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-01-04T16:37:38.956+0100")
@StaticMetamodel(Permission.class)
public class Permission_ {
	public static volatile SingularAttribute<Permission, Long> id;
	public static volatile SingularAttribute<Permission, String> permission;
	public static volatile SingularAttribute<Permission, User> user;
}
