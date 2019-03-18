package de.beach.bvp.player;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Login {
	@Id
	@GeneratedValue
	public String email;
	public String password;
}
