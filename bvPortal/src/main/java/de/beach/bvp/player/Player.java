package de.beach.bvp.player;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"email"})})
public class Player {
	@Id
	@GeneratedValue
	public Long id;
	public String name;
	public String firstName;
	@Column(unique=true)
	public String email;
	public Gender gender;
	public String club;
	public String password;
	public String role;
	@Transient
	public String authdata;
}
