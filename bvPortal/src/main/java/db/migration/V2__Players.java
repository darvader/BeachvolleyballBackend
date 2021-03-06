package db.migration;

import java.util.stream.IntStream;

import org.apache.commons.lang3.RandomStringUtils;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.stereotype.Component;

import de.beach.bvp.SpringUtility;
import de.beach.bvp.player.Gender;
import de.beach.bvp.player.Player;
import de.beach.bvp.player.PlayerRepository;

@Component
public class V2__Players extends BaseJavaMigration {

	private PlayerRepository playerRepo = SpringUtility.getBean(PlayerRepository.class);

	public void migrate(Context context) throws Exception {

		IntStream.range(0, 100).forEach(i -> {
			String gen = RandomStringUtils.random(5, true, false);
			Player player = new Player();
			player.club = "TestClub";
			player.email = "testEmail" + i + "@test.de";
			player.firstName = "Test" + gen + "FirstName" + i;
			player.gender = Gender.MALE;
			player.name = "Test" + gen + "Name" + i;
			player.password = "testPassword";
			player.role = "ROLE_USER";

			playerRepo.save(player);
		});

		IntStream.range(100, 200).forEach(i -> {
			String gen = RandomStringUtils.random(5, true, false);
			Player player = new Player();
			player.club = "TestClub";
			player.email = "testEmail" + i + "@test.de";
			player.firstName = "Test" + gen + "FirstName" + i;
			player.gender = Gender.FEMALE;
			player.name = "Test" + gen + "Name" + i;
			player.password = "testPassword";
			player.role = "ROLE_USER";

			playerRepo.save(player);
		});

		try {
			Player player = new Player();
			player.club = "VSV-Jena 90 e.V.";
			player.email = "afiedler@test.de";
			player.firstName = "Andreas";
			player.gender = Gender.MALE;
			player.name = "Fiedler";
			player.password = "password";
			player.role = "ROLE_USER|ROLE_ADMIN|ROLE_TOURNAMENT";
			playerRepo.save(player);
		} catch (Exception e){
			// do nothing if already exists
		}
	}
}
