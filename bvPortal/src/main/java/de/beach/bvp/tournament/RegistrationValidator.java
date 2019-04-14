package de.beach.bvp.tournament;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.beach.bvp.player.Gender;
import de.beach.bvp.tournament.registration.Registration;

@Component
public class RegistrationValidator {
	
	@Autowired
	private TournamentRepository tournamentRepository;
	
	public void validate(Registration registration) throws RegistrationException {
		if (checkSamePlayer(registration)) {
			throw new RegistrationException("Given players must be different.");
		}
		
		Tournament tournament = tournamentRepository.findById(registration.tournament.getId()).get();
		
		if (checkGender(registration, tournament)) {
			throw new RegistrationException("Given players must fit the tournament type.");
		}
		
		Set<Registration> registrations = tournament.registrations;
		
		if (registrations == null) {
			return;
		}
		
		if (checkAlreadyRegistered(registration, registrations)) {
			throw new RegistrationException("Player(s) already registrated in this tournament.");
		}
		
		
	}

	private boolean checkGender(Registration registration, Tournament tournament) {
		switch (tournament.getType()) {
		case MEN:
			if (registration.player1.gender.equals(Gender.FEMALE))
				return true;
			if (registration.player2.gender.equals(Gender.FEMALE))
				return true;
			return false;
		case WOMEN:
			if (registration.player1.gender.equals(Gender.MALE))
				return true;
			if (registration.player2.gender.equals(Gender.MALE))
				return true;
			return false;
		case MIXED:
			return registration.player1.gender.equals(registration.player2.gender);

		default:
			return true;
		}
	}

	private boolean checkAlreadyRegistered(Registration registration, Set<Registration> registrations) {
		return registrations.stream().anyMatch(r -> r.player1.equals(registration.player1) 
				|| r.player1.equals(registration.player2)
				|| r.player2.equals(registration.player1)
				|| r.player2.equals(registration.player2)
				);
	}

	private boolean checkSamePlayer(Registration registration) {
		return registration.player1.equals(registration.player2);
	}
}
