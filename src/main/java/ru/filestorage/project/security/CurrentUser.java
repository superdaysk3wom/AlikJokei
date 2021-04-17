package ru.filestorage.project.security;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import ru.filestorage.project.user.User;

/**
 * Интерфейс, описывающий текущего пользователя.
 * 
 * @author Alimurad A. Ramazanov
 * @version 1.0.0
 *
 */
public interface CurrentUser {

	/**
	 * Получение текущего пользователя из контекста приложения.
	 * <p>
	 * 
	 * @see User
	 * @return возвращает имя текущего пользователя; не может быть {@code null}.
	 */
	@NotNull
	@NotEmpty
	String getUsername();
}
