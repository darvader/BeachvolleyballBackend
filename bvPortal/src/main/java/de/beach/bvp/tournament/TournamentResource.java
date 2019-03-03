package de.beach.bvp.tournament;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
	public ResponseEntity<Object> createTournament(@RequestBody Tournament tournament) {
		Tournament savedTournament = tournamentRepository.save(tournament);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedTournament.getId()).toUri();

		return ResponseEntity.created(location).build();

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
