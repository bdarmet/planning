package fr.ciag.planning.authentication;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "XPRM")
public class Permission {

 @Id
 @GeneratedValue
 private long id;

 @Size(max = 100)
 private String permission;

 public String getPermission() {
	return permission;
}

public void setPermission(String permission) {
	this.permission = permission;
}

@ManyToOne()
 @JoinColumn(name = "USER_ID")
 private User user;

 // getters and setters omitted
}