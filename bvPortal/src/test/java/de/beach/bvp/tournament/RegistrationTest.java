package de.beach.bvp.tournament;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import de.beach.bvp.BvPortalApplication;
import de.beach.bvp.player.Gender;
import de.beach.bvp.player.Player;
import de.beach.bvp.player.PlayerResource;
import de.beach.bvp.tournament.registration.Registration;
import de.beach.bvp.tournament.registration.RegistrationRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BvPortalApplication.class)
@TestPropertySource(locations = "classpath:test.properties")
@WithMockUser(username = "spring", roles = { "ADMIN", "USER" })
@Transactional
public class RegistrationTest
{

    @Autowired
    private TournamentResource tournamentResource;
    @Autowired
    private PlayerResource playerResource;
    @Autowired 
    private RegistrationRepository registrationRepository;

    Category category = Category.CATEGORY1;
    String contact = "testContact";
    Date date = new Date();
    String description = "test tournament description";
    double entryFee = 25.0;
    Type type = Type.MEN;
    String name = "testName";
    PlayMode playMode = PlayMode.DOUBLEOUT;
    double priceMoney = 300.0;


    @Test
    public void testRegisterTournamentSamePlayer() throws Exception {
    	Tournament tournament = createTournament(category, contact, date, description, entryFee, type,
    			name, playMode, priceMoney);
    	Player player1 = createPlayer("FirstName1", "Name1", "Email1.de", Gender.MALE);
    	
    	Registration registration = new Registration(tournament, player1, player1);
    	
    	assertThrows(RegistrationException.class, () -> tournamentResource.registerTournament(registration));
    }


    @Test
    public void testRegisterTournamentDifferentPlayers() throws Exception {
    	Tournament tournament = createTournament(category, contact, date, description, entryFee, Type.MEN,
    			name, playMode, priceMoney);

    	Player player1 = createPlayer("FirstName1", "Name1", "Email1.de", Gender.MALE);
    	Player player2 = createPlayer("FirstName2", "Name2", "Email2.de", Gender.MALE);
    	
    	Registration registration = new Registration(tournament, player1, player2);
    	
    	Registration createdRegistration = tournamentResource.registerTournament(registration);

    	assertEquals(registration, createdRegistration);
    }

    @Test
    public void testRegisterFemaleMenTournament() throws Exception {
    	Tournament tournament = createTournament(category, contact, date, description, entryFee, Type.MEN,
    			name, playMode, priceMoney);
    	
    	Player player1 = createPlayer("FirstName1", "Name1", "Email1.de", Gender.MALE);
    	Player player2 = createPlayer("FirstName2", "Name2", "Email2.de", Gender.FEMALE);
    	
    	assertThrows(RegistrationException.class, () -> tournamentResource.registerTournament(new Registration(tournament, player1, player2)));
    	assertThrows(RegistrationException.class, () -> tournamentResource.registerTournament(new Registration(tournament, player2, player1)));
    	player1.gender = Gender.FEMALE;
    	assertThrows(RegistrationException.class, () -> tournamentResource.registerTournament(new Registration(tournament, player1, player2)));
    	player1.gender = Gender.MALE;
    	player2.gender = Gender.MALE;
    	Registration registration = tournamentResource.registerTournament(new Registration(tournament, player1, player2));
    	
    	assertNotNull(registration);
    }
    
    @Test
    public void testRegisterMaleWomenTournament() throws Exception {
    	Tournament tournament = createTournament(category, contact, date, description, entryFee, Type.WOMEN,
    			name, playMode, priceMoney);
    	
    	Player player1 = createPlayer("FirstName1", "Name1", "Email1.de", Gender.MALE);
    	Player player2 = createPlayer("FirstName2", "Name2", "Email2.de", Gender.FEMALE);
    	
    	assertThrows(RegistrationException.class, () -> tournamentResource.registerTournament(new Registration(tournament, player1, player2)));
    	assertThrows(RegistrationException.class, () -> tournamentResource.registerTournament(new Registration(tournament, player2, player1)));
    	player1.gender = Gender.MALE;
    	assertThrows(RegistrationException.class, () -> tournamentResource.registerTournament(new Registration(tournament, player1, player2)));
    	player1.gender = Gender.FEMALE;
    	player2.gender = Gender.FEMALE;
    	Registration registration = tournamentResource.registerTournament(new Registration(tournament, player1, player2));
    	
    	assertNotNull(registration);
    }
    
    @Test
    public void testRegisterMixedTournament() throws Exception {
    	Tournament tournament = createTournament(category, contact, date, description, entryFee, Type.MIXED,
    			name, playMode, priceMoney);
    	
    	Player player1 = createPlayer("FirstName1", "Name1", "Email1.de", Gender.MALE);
    	Player player2 = createPlayer("FirstName2", "Name2", "Email2.de", Gender.MALE);
    	
    	assertThrows(RegistrationException.class, () -> tournamentResource.registerTournament(new Registration(tournament, player1, player2)));
    	player1.gender = Gender.FEMALE;
    	player2.gender = Gender.FEMALE;
    	assertThrows(RegistrationException.class, () -> tournamentResource.registerTournament(new Registration(tournament, player1, player2)));

    	player1.gender = Gender.FEMALE;
    	player2.gender = Gender.MALE;
    	Registration registration = tournamentResource.registerTournament(new Registration(tournament, player1, player2));
    	assertNotNull(registration);
    	tournamentResource.deleteRegistration(registration);

    	player1.gender = Gender.MALE;
    	player2.gender = Gender.FEMALE;
    	registration = tournamentResource.registerTournament(new Registration(tournament, player1, player2));
    	assertNotNull(registration);
    }
    
    @Test
    public void testDeleteRegistration() throws Exception {
    	Tournament tournament = createTournament(category, contact, date, description, entryFee, Type.MEN,
    			name, playMode, priceMoney);
    	
    	Player player1 = createPlayer("FirstName1", "Name1", "Email1.de", Gender.MALE);
    	Player player2 = createPlayer("FirstName2", "Name2", "Email2.de", Gender.MALE);
    	
    	Registration registration = new Registration(tournament, player1, player2);
    	
    	Registration createdRegistration = tournamentResource.registerTournament(registration);
    	
    	assertEquals(registration, createdRegistration);
    	tournament = tournamentResource.retrieveTournament(tournament.getId());
    	assertEquals(1, tournament.registrations.size());
    	
    	tournamentResource.deleteRegistration(createdRegistration);
    	Optional<Registration> one = registrationRepository.findById(createdRegistration.id);
    	
    	assertEquals(true, one.isEmpty());
    	
    	
    	tournament = tournamentResource.retrieveTournament(tournament.getId());
    	assertEquals(0, tournament.registrations.size());
    }
    
    @Test
    public void testRegisterMixedTournamentDifferentPlayers() throws Exception {
    	Tournament tournament = createTournament(category, contact, date, description, entryFee, Type.MIXED,
    			name, playMode, priceMoney);
    	
    	Player player1 = createPlayer("FirstName1", "Name1", "Email1.de", Gender.MALE);
    	Player player2 = createPlayer("FirstName2", "Name2", "Email2.de", Gender.FEMALE);
    	
    	Registration registration = new Registration(tournament, player1, player2);
    	
    	Registration createdRegistration = tournamentResource.registerTournament(registration);
    	
    	assertEquals(registration, createdRegistration);
    }
    
    @Test
    public void testRegister2TimesSamePlayers() throws Exception {
    	Tournament tournament = createTournament(category, contact, date, description, entryFee, type,
    			name, playMode, priceMoney);
    	
    	Player player1 = createPlayer("FirstName1", "Name1", "Email1.de", Gender.MALE);
    	Player player2 = createPlayer("FirstName2", "Name2", "Email2.de", Gender.MALE);
    	
    	Registration registration = new Registration(tournament, player1, player2);
    	
    	Registration createdRegistration = tournamentResource.registerTournament(registration);
    	assertThrows(RegistrationException.class, () -> tournamentResource.registerTournament(registration));
    	
    	assertEquals(registration, createdRegistration);
    }
    
    @Test
    public void testRegister2TimesSamePlayersSwitched() throws Exception {
    	Tournament tournament = createTournament(category, contact, date, description, entryFee, type,
    			name, playMode, priceMoney);
    	
    	Player player1 = createPlayer("FirstName1", "Name1", "Email1.de", Gender.MALE);
    	Player player2 = createPlayer("FirstName2", "Name2", "Email2.de", Gender.MALE);
    	
    	Registration registration = new Registration(tournament, player1, player2);
    	
    	Registration createdRegistration = tournamentResource.registerTournament(registration);
    	Registration registration2 = new Registration(tournament, player2, player1);
    	
    	assertThrows(RegistrationException.class, () -> tournamentResource.registerTournament(registration2));
    	
    	assertEquals(registration, createdRegistration);
    }
    
	private Player createPlayer(String firstName, String name, String email, Gender gender) {
		Player player1 = new Player();
    	player1.firstName = firstName;
    	player1.name = name;
    	player1.email = email;
    	player1.gender = gender;
    	
    	Player createdPlayer1 = playerResource.createPlayer(player1);
		return createdPlayer1;
	}

    
    private Tournament createTournament(Category category, String contact, Date date, String description,
    		double entryFee, Type type, String name, PlayMode playMode, double priceMoney) {
    	Tournament tournament = new Tournament();
    	
    	tournament.setCategory(category);
    	tournament.setContact(contact);
    	tournament.setDate(date);
    	tournament.setDescription(description);
    	tournament.setEntryFee(entryFee);
    	tournament.setType(type);
    	tournament.setName(name);
    	tournament.setPlayMode(playMode);
    	tournament.setPriceMoney(priceMoney);
    	
    	Tournament createdTournament = tournamentResource.createTournament(tournament);
    	return createdTournament;
    }
}
