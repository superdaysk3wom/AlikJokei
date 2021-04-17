package ru.filestorage.project.dao;

public interface ComplexId<K extends Id<?>, T extends Id<?>> {

	K  getFirstId();
	
	T getSecondId();
}
