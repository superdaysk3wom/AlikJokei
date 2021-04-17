package ru.filestorage.project.dao.impl;

import ru.filestorage.project.dao.Id;
import ru.filestorage.project.dao.Value;
import ru.filestorage.project.user.User;

public class UserWrapper implements Value<User> {

	private final User user;

	public UserWrapper(User user) {
		this.user = user;
	}

	@Override
	public User get() {
		return user;
	}

	@Override
	public boolean isPresent() {
		return user != null;
	}

	public Id.StringId getId() {
		return () -> user.getUsername();
	}
}
