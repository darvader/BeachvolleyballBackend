package de.beach.bvp.tournament;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
    Type type = Type.MIXED;
    String name = "testName";
    PlayMode playMode = PlayMode.DOUBLEOUT;
    double priceMoney = 300.0;

	private Tournament tournament;

	@BeforeEach
    public void createTournament()
    {
        tournament = createTournament(category, contact, date, description, entryFee, type,
        		name, playMode, priceMoney);
    }

    
    @Test
    public void testRegisterTournamentDifferentPlayers() throws Exception {

    	Player player1 = createPlayer("FirstName1", "Name1", "Email1.de");
    	Player player2 = createPlayer("FirstName2", "Name2", "Email2.de");
    	
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
    	
    	playerResource.deletePlayer(player1.id);
    	playerResource.deletePlayer(player2.id);
    }

    @Test
    public void testRegister2TimesSamePlayers() throws Exception {
    	
    	Player player1 = createPlayer("FirstName1", "Name1", "Email1.de");
    	Player player2 = createPlayer("FirstName2", "Name2", "Email2.de");
    	
    	Registration registration = new Registration(tournament, player1, player2);
    	
    	Registration createdRegistration = tournamentResource.registerTournament(registration);
    	assertThrows(RegistrationException.class, () -> tournamentResource.registerTournament(registration));
    	
    	assertEquals(registration, createdRegistration);
    	
    	registrationRepository.delete(createdRegistration);
    	registrationRepository.flush();

    	playerResource.deletePlayer(player1.id);
    	playerResource.deletePlayer(player2.id);
    }
    
    @Test
    public void testRegister2TimesSamePlayersSwitched() throws Exception {
    	
    	Player player1 = createPlayer("FirstName1", "Name1", "Email1.de");
    	Player player2 = createPlayer("FirstName2", "Name2", "Email2.de");
    	
    	Registration registration = new Registration(tournament, player1, player2);
    	
    	Registration createdRegistration = tournamentResource.registerTournament(registration);
    	Registration registration2 = new Registration(tournament, player2, player1);
    	
    	assertThrows(RegistrationException.class, () -> tournamentResource.registerTournament(registration2));
    	
    	assertEquals(registration, createdRegistration);
    	
    	registrationRepository.delete(createdRegistration);
    	registrationRepository.flush();
    	
    	playerResource.deletePlayer(player1.id);
    	playerResource.deletePlayer(player2.id);
    }
    
    @AfterEach
    public void afterEach() {
    	tournamentResource.deleteTournament(tournament.getId());
    }
    
	private Player createPlayer(String firstName, String name, String email) {
		Player player1 = new Player();
    	player1.firstName = firstName;
    	player1.name = name;
    	player1.email = email;
    	player1.gender = Gender.MALE;
    	
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
    	tournament.setGender(type);
    	tournament.setName(name);
    	tournament.setPlayMode(playMode);
    	tournament.setPriceMoney(priceMoney);
    	
    	Tournament createdTournament = tournamentResource.createTournament(tournament);
    	return createdTournament;
    }
}
