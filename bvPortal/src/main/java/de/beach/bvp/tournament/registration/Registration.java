package de.beach.bvp.tournament.registration;

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
