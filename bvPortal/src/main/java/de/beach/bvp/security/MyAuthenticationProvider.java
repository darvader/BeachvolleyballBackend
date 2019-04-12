package de.beach.bvp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import de.beach.bvp.player.Login;
import de.beach.bvp.player.Player;
import de.beach.bvp.player.PlayerNotFoundException;
import de.beach.bvp.player.PlayerResource;

@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private PlayerResource playerResource;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String mail = authentication.getName();
		String password = authentication.getCredentials().toString();
		Login login = new Login(mail, password);
		Player result;
		try {
			result = playerResource.login(login);
		} catch (PlayerNotFoundException e) {
			throw new BadCredentialsException("External system authentication failed", e);
		}
		return new UsernamePasswordAuthenticationToken(mail, password,
				AuthorityUtils.commaSeparatedStringToAuthorityList(result.role));
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
