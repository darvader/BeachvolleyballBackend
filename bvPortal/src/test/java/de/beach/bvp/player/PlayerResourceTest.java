package de.beach.bvp.player;

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
public class PlayerResourceTest
{

    @Autowired
    private PlayerResource playerResource;

    @Test
    @WithMockUser(username = "spring")
    public void testCreatePlayer()
    {
        createPlayer("test@player.de");
    }

    private void createPlayer(String email)
    {
        Player player = new Player();
        player.email = email;
        player.firstName = "FirstName";
        player.name = "LastName";
        player.gender = Gender.MALE;

        playerResource.createPlayer(player);
    }

    @WithMockUser(username = "spring")
    public void testDeletePlayer()
    {
    }
}
