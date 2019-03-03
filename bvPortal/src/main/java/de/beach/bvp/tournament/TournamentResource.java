package de.beach.bvp.tournament;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class TournamentResource {
	@Autowired
	private TournamentRepository tournamentRepository;
	
	@GetMapping("/tournaments")
	public List<Tournament> getAllTournaments() {
		return tournamentRepository.findAll();
	}
	
	@PostMapping("/tournaments")
	public ResponseEntity<Object> createStudent(@RequestBody Tournament tournament) {
		Tournament savedTournament = tournamentRepository.save(tournament);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedTournament.getId()).toUri();

		return ResponseEntity.created(location).build();

	}
	
	@GetMapping("/tournaments/{id}")
	public Tournament retrieveStudent(@PathVariable long id) throws TournamentNotFoundException {
		Optional<Tournament> student = tournamentRepository.findById(id);

		if (!student.isPresent())
			throw new TournamentNotFoundException("id-" + id);

		return student.get();
	}
	
	@DeleteMapping("/tournaments/{id}")
	public void deleteStudent(@PathVariable long id) {
		tournamentRepository.deleteById(id);
	}
}
