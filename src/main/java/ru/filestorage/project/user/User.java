package ru.filestorage.project.user;

import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "users_cache")
public class User implements UserDetails {

	private static final long serialVersionUID = 2133666487601067446L;

	@Email
	@NotNull
	private String email;

	@NotNull
	@Past
	@Column(name = "reg_date", nullable = false, updatable = false)
	@Temporal(TemporalType.DATE)
	private LocalDate regDate;

	@NotNull
	@NotEmpty
	@Column(nullable = false)
	private String password;

	@NotNull
	@NotEmpty
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "user_roles", uniqueConstraints = { @UniqueConstraint(name = "idx_user_id", columnNames = {
			"user_id", "roles" }) }, joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") })
	private Collection<String> roles;

	@NotNull
	@NotEmpty
	@Column(name = "id", nullable = false, updatable = false, length = 64)
	private String username;

	private boolean isActive;

	@Transient
	private boolean isNew;

	public User(String username, String password) {
		this.password = password;
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getRegDate() {
		return regDate;
	}

	public User setRegDate(LocalDate regDate) {
		if (!isNew()) {
			throw new IllegalStateException(
					"Operation is allowed only for new entity!");
		}

		this.regDate = regDate;
		return this;
	}

	public User makeNonActive() {
		this.isActive = false;
		return this;
	}

	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles.stream().map(SimpleGrantedAuthority::new)
				.collect(Collectors.toSet());
	}

	public boolean isNew() {
		return this.isNew;
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.isActive;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.isActive;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.isActive;
	}

	@Override
	public boolean isEnabled() {
		return this.isActive;
	}
}
