package de.beach.bvp.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import de.beach.bvp.BvPortalApplication;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BvPortalApplication.class)
@TestPropertySource(locations = "classpath:test.properties")
@WithMockUser(username = "spring", roles = { "ADMIN", "USER" })
public class PlayerResourceTest {

	@Autowired
	private PlayerResource playerResource;
	private Player player;

	@BeforeEach
	public void createPlayer() {
		player = createPlayer("test@player.de", "FirstName", "LastName", Gender.MALE, "testClub", "testPassword",
				"testRole", "testAuthData");
	}

	@Test
	public void testCreatePlayer() {
		assertEquals("testClub", player.club);
		assertEquals("testAuthData", player.authdata);
		assertEquals("test@player.de", player.email);
		assertEquals("FirstName", player.firstName);
		assertEquals(Gender.MALE, player.gender);
		assertEquals("LastName", player.name);
		assertEquals("testPassword", player.password);
		assertEquals("testRole", player.role);
	}

	@Test
	public void testDeletePlayer() throws PlayerNotFoundException {
		Player player2 = createPlayer("test" + System.currentTimeMillis()	+ "@player.de", "FirstName2", "LastName2", Gender.MALE, "testClub",
				"testPassword", "testRole", "testAuthData");

		playerResource.deletePlayer(player2.id);

		assertThrows(PlayerNotFoundException.class, () -> playerResource.retrievePlayer(player2.id));
	}

	@Test
	public void testCreateDuplicateEmail() throws PlayerNotFoundException {
		assertThrows(DataIntegrityViolationException.class, () -> createPlayer("test@player.de", "FirstName2",
				"LastName2", Gender.MALE, "testClub", "testPassword", "testRole", "testAuthData"));
	}

	@Test
	public void testDeleteNotExistingPlayer() throws PlayerNotFoundException {
		Player player2 = createPlayer("test" + System.currentTimeMillis() + "@player.de", "FirstName2", "LastName2", Gender.MALE, "testClub",
				"testPassword", "testRole", "testAuthData");

		playerResource.deletePlayer(player2.id);
		assertThrows(PlayerNotFoundException.class, () -> playerResource.retrievePlayer(player2.id));

		assertThrows(EmptyResultDataAccessException.class, () -> playerResource.deletePlayer(player2.id));
	}

	@Test
	public void testLogin() throws PlayerNotFoundException {
		Login login = new Login();
		login.email = "test@player.de";
		login.password = "testPassword";
		Player player = playerResource.login(login);

		assertNotNull(player);

		assertEquals("FirstName", player.firstName);
	}

	@Test
	public void testWrongPasswordLogin() throws PlayerNotFoundException {
		Login login = new Login();
		login.email = "test@player.de";
		login.password = "testPaszword";

		assertThrows(PlayerNotFoundException.class, () -> playerResource.login(login));
	}

	@Test
	public void testWrongEmailLogin() throws PlayerNotFoundException {
		Login login = new Login();
		login.email = "test@plaier.de";
		login.password = "testPassword";

		assertThrows(PlayerNotFoundException.class, () -> playerResource.login(login));
	}

	@Test
	public void testGetAllPlayers() throws PlayerNotFoundException {
		Player player2 = createPlayer("test2@player.de", "FirstName1", "LastName", Gender.MALE, "testClub",
				"testPassword", "testRole", "testAuthData");
		Player player3 = createPlayer("test3@player.de", "FirstName", "LastName", Gender.MALE, "testClub",
				"testPassword", "testRole", "testAuthData");

		List<Player> players = playerResource.getAllPlayers();

		assertEquals(204, players.size());
		
		Arrays.asList(new Player[] {player, player2, player3}).forEach(p -> players.contains(p));

		playerResource.deletePlayer(player2.id);
		playerResource.deletePlayer(player3.id);
	}

	@Test
    public void testRetrievePlayer() throws PlayerNotFoundException {
		
		Player retrievedPlayer = playerResource.retrievePlayer(player.id);
		
		assertNotNull(retrievedPlayer);
		
		assertEquals(player, retrievedPlayer);
		assertNotSame(player, retrievedPlayer);
	}

	private Player createPlayer(String email, String firstName, String lastName, Gender gender, String club,
			String password, String role, String authData) {
		Player player = new Player();
		player.email = email;
		player.firstName = firstName;
		player.name = lastName;
		player.gender = gender;
		player.club = club;
		player.password = password;
		player.role = role;
		player.authdata = authData;

		Player createdPlayer = playerResource.createPlayer(player);
		return createdPlayer;
	}

	@AfterEach
	public void deletePlayer() {
		playerResource.deletePlayer(player.id);
	}
}
