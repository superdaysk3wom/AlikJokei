package ru.filestorage.project.dao;

@FunctionalInterface
public interface Id<V> {

	V get();

	interface StringId extends Id<String> {

	}

	interface IntegerId extends Id<Integer> {

	}

	interface LongId extends Id<Long> {

	}

}
