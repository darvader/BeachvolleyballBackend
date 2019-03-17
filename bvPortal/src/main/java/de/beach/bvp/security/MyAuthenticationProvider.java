package de.beach.bvp.security;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import de.beach.bvp.player.Player;
import de.beach.bvp.player.PlayerRepository;

@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private PlayerRepository playerRepository;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Player player = new Player();
		player.email = authentication.getName();
		player.password = authentication.getCredentials().toString();
		ExampleMatcher matcherMail = ExampleMatcher.matching().withMatcher("email", exact()).withMatcher("password",
				exact());
		Optional<Player> result = playerRepository.findOne(Example.of(player, matcherMail));
		if (result.isPresent()) {
			return new UsernamePasswordAuthenticationToken(player.name, player.password,
					AuthorityUtils.commaSeparatedStringToAuthorityList(result.get().role));
		} 
		throw new BadCredentialsException("External system authentication failed");
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
