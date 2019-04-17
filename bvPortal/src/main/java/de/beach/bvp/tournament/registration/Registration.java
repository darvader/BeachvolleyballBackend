package de.beach.bvp.tournament.registration;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import de.beach.bvp.player.Player;
import de.beach.bvp.tournament.Tournament;

@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"player1_id", "player2_id", "tournament_id"})})
public class Registration {

	public Registration(Tournament tournament, Player player1, Player player2) {
		this.tournament = tournament;
		this.player1 = player1;
		this.player2 = player2;
	}
	
	public Registration() {
	}
	
	@Id
	@GeneratedValue
	public Long id;
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
		Registration other = (Registration) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@OneToOne
	public Player player1;
	@OneToOne
	public Player player2;
	@OneToOne(cascade = CascadeType.REFRESH)
	public Tournament tournament;	
}
