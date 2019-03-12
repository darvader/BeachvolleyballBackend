package de.beach.bvp.player;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Player {
	@Id
	@GeneratedValue
	public Long id;
	public String name;
	public String firstName;
	public String email;
	public Gender gender;
	public String club;
}
