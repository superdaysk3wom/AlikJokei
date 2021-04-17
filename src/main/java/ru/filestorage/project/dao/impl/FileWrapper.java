package ru.filestorage.project.dao.impl;

import java.io.File;

import javax.validation.constraints.NotNull;

import ru.filestorage.project.dao.ComplexId;
import ru.filestorage.project.dao.Id;
import ru.filestorage.project.dao.Value;

public class FileWrapper implements Value<File> {

	private static final String SEP = "$$";

	private final File file;
	private final Id.StringId bucketId;
	private final Id.StringId parentId;

	public FileWrapper(final File file, final Id.StringId parentId,
			final Id.StringId bucketId) {
		this.file = file;
		this.bucketId = bucketId;
		this.parentId = parentId;
	}

	@Override
	@NotNull
	public File get() {
		return file;
	}

	@Override
	public boolean isPresent() {
		return file != null;
	}

	public Id.StringId getBucketId() {
		return bucketId;
	}

	public Id.StringId getParentId() {
		return parentId;
	}

	public Id.StringId getId() {
		return () -> getParentId().get() + SEP + file.getName();
	}

	public ComplexId<Id.StringId, Id.StringId> getComplexId() {
		return new ComplexId<Id.StringId, Id.StringId>() {

			@Override
			public Id.StringId getFirstId() {
				return getBucketId();
			}

			@Override
			public Id.StringId getSecondId() {
				return getId();
			}

		};
	}
}
