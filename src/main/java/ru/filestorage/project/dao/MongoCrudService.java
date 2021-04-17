package ru.filestorage.project.dao;

import java.util.Collection;

public interface MongoCrudService<K, V extends Value<?>> extends
		CrudService<K, V> {

	Collection<V> findAllByParent(V parent);
	
	Collection<V> findAllByParent(K parent);

	Collection<K> save(Collection<V> values) throws Exception;
}
