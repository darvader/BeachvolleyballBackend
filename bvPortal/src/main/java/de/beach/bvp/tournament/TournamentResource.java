package de.beach.bvp.tournament;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TournamentResource {
	@Autowired
	private TournamentRepository tournamentRepository;
	
	@GetMapping("/tournaments")
	public List<Tournament> getAllTournaments() {
		return tournamentRepository.findAll();
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

		if (!tournament.isPresent())
			throw new TournamentNotFoundException("id-" + id);

		return tournament.get();
	}
	
	@DeleteMapping("/tournaments/{id}")
	public void deleteTournament(@PathVariable long id) {
		tournamentRepository.deleteById(id);
	}
}
