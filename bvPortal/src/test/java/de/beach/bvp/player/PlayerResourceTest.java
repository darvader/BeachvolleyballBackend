package de.beach.bvp.player;

import static org.junit.Assert.fail;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.beach.bvp.BvPortalApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BvPortalApplication.class)
@Ignore
public class PlayerResourceTest {

	@Autowired
	private PlayerResource playerResource;
	
	@Test
	public void testGetAllPlayers() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreatePlayer() {
		for (int i = 0; i < 100; i++) {
			Player player = new Player();
			player.email = "test" + i + "@player.de";
			player.firstName = "FirstName" + i;
			player.gender = Gender.MALE;
			player.name = "LastName" + i;

			playerResource.createPlayer(player);
		}
	}

	@Test
	public void testUpdatePlayer() {
		fail("Not yet implemented");
	}

	@Test
	public void testRetrievePlayer() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeletePlayer() {
		fail("Not yet implemented");
	}

}
