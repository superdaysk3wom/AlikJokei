package ru.filestorage.project.dao;

import java.util.Collection;
import java.util.Optional;


public interface CrudService<K, V extends Value<?>> {

	K save(V object) throws Exception;
	
	void remove(V object);
	
	void removeByKey(K key);
	
	boolean update(V object);
	
	Optional<V> findOne(K key);
	
	Collection<V> findAll();
}
