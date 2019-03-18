package de.beach.bvp.tournament.registration;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import de.beach.bvp.player.Player;
import de.beach.bvp.tournament.Tournament;

@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Registration {
	@Id
	@GeneratedValue
	public Long id;
	@OneToOne
	public Player player1;
	@OneToOne
	public Player player2;
	@OneToOne
	public Tournament tournament;	
}
