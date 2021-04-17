package ru.filestorage.project.security;

import java.util.Optional;

import javax.inject.Inject;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ru.filestorage.project.dao.impl.UserCrudService;
import ru.filestorage.project.dao.impl.UserWrapper;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Inject
	private UserCrudService userService;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		Optional<UserWrapper> user = userService.findOne(() -> username);
		if (!user.isPresent() || !user.get().isPresent()) {
			throw new UsernameNotFoundException(String.format(
					"Not found user with username '%s'", username));
		}

		return user.get().get();
	}

}