package ru.filestorage.project.security;

import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component("currentUser")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class CurrentUserImpl implements CurrentUser {

	@Override
	public String getUsername() {
		return ((UserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal()).getUsername();
	}

}
