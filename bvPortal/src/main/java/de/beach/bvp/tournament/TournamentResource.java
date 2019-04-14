package de.beach.bvp.tournament;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import de.beach.bvp.tournament.registration.Registration;
import de.beach.bvp.tournament.registration.RegistrationRepository;

@RestController
public class TournamentResource {
	@Autowired
	private TournamentRepository tournamentRepository;
	@Autowired
	private RegistrationRepository registrationRepository;
	@Autowired
	private RegistrationValidator registrationValidiator;
	
	@GetMapping("/tournaments")
	public List<Tournament> getAllTournaments() {
		return tournamentRepository.findAll();
	}
	
	@GetMapping("/")
	public String gets() {
		return "HealthCheck";
	}
	
	@PostMapping("/tournaments")
	public Tournament createTournament(@RequestBody Tournament tournament) {
		Tournament savedTournament = tournamentRepository.save(tournament);
		return savedTournament;
	}
	
	@PutMapping("/tournaments")
	public Tournament updateTournament(@RequestBody Tournament tournament) {
		Tournament savedTournament = tournamentRepository.save(tournament);
		return savedTournament;
	}
	
	@GetMapping("/tournaments/{id}")
	public Tournament retrieveTournament(@PathVariable long id) throws TournamentNotFoundException {
		Optional<Tournament> tournament = tournamentRepository.findById(id);

		if (!tournament.isPresent()) {
			throw new TournamentNotFoundException("id-" + id);
		}

		return tournament.get();
	}
	
	@DeleteMapping("/tournaments/{id}")
	public void deleteTournament(@PathVariable long id) {
		tournamentRepository.deleteById(id);
	}
	
	@PostMapping("/register")
	public Registration registerTournament(@RequestBody Registration registration) throws RegistrationException {
		registrationValidiator.validate(registration);
		
		Registration savedRegistration = registrationRepository.save(registration);
		registration.tournament.registrations.add(savedRegistration);
		
		return savedRegistration;
	}

	@PostMapping("/unregister")
	public void deleteRegistration(Registration registration) {
		registration.tournament.registrations.remove(registration);
		registrationRepository.delete(registration);
	}	
}
