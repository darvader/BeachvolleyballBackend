package db.migration;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.apache.commons.lang3.RandomStringUtils;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
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
import de.beach.bvp.tournament.registration.Registration;
import de.beach.bvp.tournament.registration.RegistrationRepository;

@Component
public class V3__Tournaments extends BaseJavaMigration {

	private TournamentRepository tournamentRepo = SpringUtility.getBean(TournamentRepository.class);
	private PlayerRepository playerRepo = SpringUtility.getBean(PlayerRepository.class);
	private RegistrationRepository regRepo = SpringUtility.getBean(RegistrationRepository.class);

	public void migrate(Context context) throws Exception {

		IntStream.range(0, 20).forEach(this::createTournament);

	}

	private void createTournament(int i) {
		String gen = RandomStringUtils.random(5, true, false);
		Tournament t = new Tournament();
		t.setCategory(Category.values()[i % 4]);
		t.setContact("TestContact" + gen);
		t.setDate(Date.from(Instant.now().plus(i, ChronoUnit.DAYS)));
		t.setDescription("My test description " + gen);
		t.setEntryFee(i + 0.0);
		t.setName("TestName" + gen);
		t.setPlayMode(PlayMode.values()[i % 3]);
		t.setPriceMoney(200.0 + i);
		t.setType(Type.values() [i % 3]);
		
		t = tournamentRepo.save(t);
		
		switch (t.getType()) {
		case MEN:
			addPlayers(t, Gender.MALE);
			break;
		case WOMEN:
			addPlayers(t, Gender.FEMALE);
			break;
		case MIXED:
			addMixedPlayers(t);
			break;

		default:
			break;
		}
		
		
	}

	private void addMixedPlayers(Tournament t) {
		List<Player> players = playerRepo.findAll();
		
		int j = 0;
		int k = 0;
		Player player1 = players.get(j++);
		Player player2 = players.get(k++);
		
		for (int i = 0; i < 20; i++) {
			while (player1.gender != Gender.FEMALE) {
				player1 = players.get(j++);
			}
			
			while (player2.gender != Gender.MALE) {
				player2 = players.get(k++);
			}
			
			Registration registration = new Registration(t, player1, player2);
			player1 = players.get(j++);
			player2 = players.get(k++);
			
			regRepo.save(registration);
			t.registrations.add(registration);
		}
	}

	private void addPlayers(Tournament t, Gender gender) {
		Player player = new Player();
		player.gender = gender;
		ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("gender", exact());

		List<Player> players = playerRepo.findAll(Example.of(player, matcher));
		
		for (int i = 0; i < 40; i+=2) {
			Registration registration = new Registration(t, players.get(i), players.get(i + 1));
			regRepo.save(registration);
			t.registrations.add(registration);
		}
		
	}
}
