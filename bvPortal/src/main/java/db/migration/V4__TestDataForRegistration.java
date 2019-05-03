package db.migration;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.stream.IntStream;

import org.apache.commons.lang3.RandomStringUtils;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.stereotype.Component;

import de.beach.bvp.SpringUtility;
import de.beach.bvp.player.Gender;
import de.beach.bvp.player.Player;
import de.beach.bvp.player.PlayerRepository;
import de.beach.bvp.tournament.Category;
import de.beach.bvp.tournament.PlayMode;
import de.beach.bvp.tournament.Tournament;
import de.beach.bvp.tournament.TournamentRepository;
import de.beach.bvp.tournament.Type;

@Component
public class V4__TestDataForRegistration extends BaseJavaMigration {

	private PlayerRepository playerRepo = SpringUtility.getBean(PlayerRepository.class);
	private TournamentRepository tournamentRepo = SpringUtility.getBean(TournamentRepository.class);

	public void migrate(Context context) throws Exception {
		IntStream.range(0, 10).forEach(i -> {
			Tournament t = new Tournament();
			t.setCategory(Category.values()[i % 4]);
			t.setContact("TestContact" + i);
			t.setDate(Date.from(Instant.now().plus(i, ChronoUnit.DAYS)));
			t.setDescription("My test description " + i);
			t.setEntryFee(i + 0.0);
			t.setName("TestNameE2E" + i);
			t.setPlayMode(PlayMode.values()[i % 3]);
			t.setPriceMoney(200.0 + i);
			t.setType(Type.values()[i % 3]);

			t = tournamentRepo.save(t);
		});

		IntStream.range(0, 10).forEach(i -> {
			Player player = new Player();
			player.club = "TestClub";
			player.email = "testEmailE2E" + i + "@test.de";
			player.firstName = "Test" + i + "FirstName" + i;
			player.gender = Gender.MALE;
			player.name = "TestNameE2E" + i;
			player.password = "testPassword";
			player.role = "ROLE_USER";

			playerRepo.save(player);
		});
	}
}
