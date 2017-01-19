package fr.ciag.planning.authentication;

import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "XROL")
public class Role implements Comparable<Role> {

	public Role() {
		super();
	}

	public Role(String RoleName) {
		this();
		setRoleName(RoleName);
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@Id
	@GeneratedValue
	private long id;

	@Size(max = 100)
	private String roleName;

	@ManyToMany(mappedBy = "roles")
	private Set<User> users;

	@Override
	public int compareTo(Role o) {
		// TODO Auto-generated method stub
		return getRoleName().compareTo(o.getRoleName());
	}

}