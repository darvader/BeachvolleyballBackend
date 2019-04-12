package de.beach.bvp.player;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
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
	
	@Secured("ROLE_ADMIN")
	@PutMapping("/players")
	public Player updatePlayer(@RequestBody Player Player) {
		Player savedPlayer = playerRepository.save(Player);
		return savedPlayer;
	}
	
	@GetMapping("/players/{id}")
	public Player retrievePlayer(@PathVariable long id) throws PlayerNotFoundException {
		Optional<Player> player = playerRepository.findById(id);

		if (!player.isPresent())
			throw new PlayerNotFoundException("id-" + id);

		return player.get();
	}
	
	@Secured("ROLE_ADMIN")
	@DeleteMapping("/players/{id}")
	public void deletePlayer(@PathVariable long id) {
		playerRepository.deleteById(id);
	}
	
	@PostMapping("/login")
	public Player login(@RequestBody Login login) throws PlayerNotFoundException {
		Optional<Player> player = login(login.email, login.password);
		if (!player.isPresent())
			throw new PlayerNotFoundException("Spieler wurde nicht gefunden.");
		return player.get();
	}
	
	private Optional<Player> login(String mail, String password) {
		Player player = new Player();
		player.email = mail;
		player.password = password;
		ExampleMatcher matcherMail = ExampleMatcher.matching().withMatcher("email", exact()).withMatcher("password",
				exact());
		Optional<Player> result = playerRepository.findOne(Example.of(player, matcherMail));
		return result;
	}

}
