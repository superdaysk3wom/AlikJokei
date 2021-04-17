package ru.filestorage.project.dao.impl;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import ru.filestorage.project.dao.CrudService;
import ru.filestorage.project.dao.Id;
import ru.filestorage.project.user.User;

@Service
@Repository
public class UserCrudService implements CrudService<Id.StringId, UserWrapper> {

	@Inject
	private EntityManager em;

	@Override
	public Id.StringId save(UserWrapper object) throws Exception {
		em.persist(object.get());

		return object.getId();
	}

	@Override
	public void remove(UserWrapper object) {
		em.remove(object.get());
	}

	@Override
	public void removeByKey(Id.StringId key) {
		Query query = em.createQuery("DELETE FROM "
				+ User.class.getSimpleName() + " WHERE username = :username");
		query.setParameter("username", key.get());
		query.executeUpdate();
	}

	@Override
	public boolean update(UserWrapper object) {
		em.refresh(object);
		return true;
	}

	@Override
	public Optional<UserWrapper> findOne(Id.StringId key) {
		return Optional.ofNullable(new UserWrapper(em.find(User.class,
				key.get())));
	}

	@Override
	public Collection<UserWrapper> findAll() {
		return em
				.createQuery(
						"SELECT user FROM " + User.class.getSimpleName()
								+ " user", User.class).getResultList().stream()
				.map(UserWrapper::new).collect(Collectors.toList());
	}

}
