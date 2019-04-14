package de.beach.bvp.tournament;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.beach.bvp.tournament.registration.Registration;

@Component
public class RegistrationValidator {
	
	@Autowired
	private TournamentRepository tournamentRepository;
	
	public void validate(Registration registration) throws RegistrationException {
		Optional<Tournament> tournament = tournamentRepository.findById(registration.tournament.getId());
		
		Set<Registration> registrations = tournament.get().registrations;
		
		if (registrations == null) {
			return;
		}
		
		if (registrations.stream().anyMatch(r -> r.player1.equals(registration.player1) 
				|| r.player1.equals(registration.player2)
				|| r.player2.equals(registration.player1)
				|| r.player2.equals(registration.player2)
				)) {
			throw new RegistrationException("Player(s) already registrated in this tournament.");
		}
		
		
	}
}
