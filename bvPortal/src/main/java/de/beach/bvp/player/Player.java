package de.beach.bvp.player;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.beach.bvp.tournament.registration.Registration;

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

	@JsonIgnore
	@OneToMany(mappedBy ="player1", fetch = FetchType.LAZY)
	public Set<Registration> registrationsPlayer1;
	
	@JsonIgnore
	@OneToMany(mappedBy ="player2", fetch = FetchType.LAZY)
	public Set<Registration> registrationsPlayer2;
	
	@Transient
	public String authdata;
		
	@Override
	public String toString() {
		return "Player [id=" + id + ", name=" + name + ", firstName=" + firstName + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
