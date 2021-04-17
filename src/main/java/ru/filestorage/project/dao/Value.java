package ru.filestorage.project.dao;

public interface Value<T> {

	T get();
	
	boolean isPresent();
}
