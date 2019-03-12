package de.beach.bvp.player;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
public class PlayerResource {
	@Autowired
	private PlayerRepository playerRepository;
	
	@GetMapping("/players")
	public List<Player> getAllPlayers() {
		return playerRepository.findAll();
	}
	
	@Secured("ROLE_USER")
	@PostMapping("/players")
	public Player createPlayer(@RequestBody Player Player) {
		Player savedPlayer = playerRepository.save(Player);
		return savedPlayer;
	}
	
	@Secured("ROLE_USER")
	@PutMapping("/players")
	public Player updatePlayer(@RequestBody Player Player) {
		Player savedPlayer = playerRepository.save(Player);
		return savedPlayer;
	}
	
	@GetMapping("/players/{id}")
	public Player retrievePlayer(@PathVariable long id) throws PlayerNotFoundException {
		Optional<Player> Player = playerRepository.findById(id);

		if (!Player.isPresent())
			throw new PlayerNotFoundException("id-" + id);

		return Player.get();
	}
	
	@Secured("ROLE_USER")
	@DeleteMapping("/players/{id}")
	public void deletePlayer(@PathVariable long id) {
		playerRepository.deleteById(id);
	}
}
