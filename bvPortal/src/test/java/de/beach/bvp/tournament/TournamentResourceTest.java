package de.beach.bvp.tournament;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Date;
import java.util.List;

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

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BvPortalApplication.class)
@TestPropertySource(locations = "classpath:test.properties")
@WithMockUser(username = "spring")
public class TournamentResourceTest
{

    @Autowired
    private TournamentResource tournamentResource;

    Category category = Category.CATEGORY1;
    String contact = "testContact";
    Date date = new Date();
    String description = "test tournament description";
    double entryFee = 25.0;
    Type type = Type.MIXED;
    String name = "testName";
    PlayMode playMode = PlayMode.DOUBLEOUT;
    double priceMoney = 300.0;

	private Tournament createdTournament;

	@BeforeEach
    public void createTournament()
    {
        createdTournament = createTournament(category, contact, date, description, entryFee, type,
        		name, playMode, priceMoney);
    }

    @Test
    public void testCreateTournament()
    {
    	assertEquals(category, createdTournament.getCategory());
    	assertEquals(contact, createdTournament.getContact());
    	assertEquals(date, createdTournament.getDate());
    	assertEquals(description, createdTournament.getDescription());
    	assertEquals(entryFee, createdTournament.getEntryFee(), 0.001);
    	assertEquals(type, createdTournament.getGender());
    	assertEquals(name, createdTournament.getName());
    	assertEquals(playMode, createdTournament.getPlayMode());
    	assertEquals(priceMoney, createdTournament.getPriceMoney(), 0.001);
    }


    @Test
    public void testDeleteTournament() throws TournamentNotFoundException {
        Tournament tournament = createTournament(category, contact, date, description, entryFee, type,
        		name, playMode, priceMoney);
    	
    	tournamentResource.deleteTournament(tournament.getId());
    	
    	assertThrows(TournamentNotFoundException.class, () -> tournamentResource.retrieveTournament(tournament.getId()));
    	
    }

    @Test
    public void testRetrieveTournament() throws Exception {
    	Tournament tournament = tournamentResource.retrieveTournament(createdTournament.getId());
    	assertEquals(createdTournament, tournament);	
    }
    
    @Test
    public void testUpdateTournament() throws Exception {
    	createdTournament.setName("newName");
    	
    	Tournament tournament = tournamentResource.updateTournament(createdTournament);
    	assertEquals("newName", tournament.getName());	
    }
    
    @Test
    public void testGetAllTournament() throws Exception {
    	
        Tournament tournament1 = createTournament(category, "otherContact", date, description, entryFee, type,
        		name, playMode, priceMoney);

        Tournament tournament2 = createTournament(category, contact, date, "otherDescription", entryFee, type,
        		name, playMode, priceMoney);
    	
    	List<Tournament> tournaments = tournamentResource.getAllTournaments();
    	assertEquals(3, tournaments.size());
    	assertThat(tournaments, containsInAnyOrder(tournament1, tournament2, createdTournament));
    }
    
    @AfterEach
    public void afterEach() {
    	tournamentResource.deleteTournament(createdTournament.getId());
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
