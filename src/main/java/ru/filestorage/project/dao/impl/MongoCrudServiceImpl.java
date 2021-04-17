package ru.filestorage.project.dao.impl;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import ru.filestorage.project.configs.GridFsFactory;
import ru.filestorage.project.dao.ComplexId;
import ru.filestorage.project.dao.Id;
import ru.filestorage.project.dao.Id.StringId;
import ru.filestorage.project.dao.MongoCrudService;
import ru.filestorage.project.utils.FileUtils;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

@Service
@Repository
public class MongoCrudServiceImpl implements
		MongoCrudService<ComplexId<Id.StringId, Id.StringId>, FileWrapper> {

	@Inject
	private GridFsFactory gridFsFactory;

	@Override
	@NotNull
	public ComplexId<Id.StringId, Id.StringId> save(
			@NotNull final FileWrapper object) throws IOException {
		GridFSInputFile gfsFile = gridFsFactory.createGridFs(
				object.getBucketId().get()).createFile(object.get());
		gfsFile.setId(object.getId());
		gfsFile.setFilename(object.get().getName());
		gfsFile.put("parentId", object.getParentId());
		gfsFile.put("id", object.getId());

		gfsFile.save();

		return object.getComplexId();
	}

	@Override
	public void remove(FileWrapper object) {
		removeByKey(object.getComplexId());
	}

	@Override
	public boolean update(FileWrapper object) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeByKey(ComplexId<Id.StringId, Id.StringId> key) {
		gridFsFactory.createGridFs(key.getFirstId().get()).remove(
				key.getSecondId().get());
	}

	@Override
	public Optional<FileWrapper> findOne(ComplexId<Id.StringId, Id.StringId> key) {
		GridFSDBFile file = gridFsFactory.createGridFs(key.getFirstId().get())
				.findOne(
						BasicDBObjectBuilder.start("id", key.getSecondId())
								.get());
		return getFileWrapper(file, key.getFirstId().get());
	}

	@Override
	public Collection<FileWrapper> findAll() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Collection<FileWrapper> findAllByParent(FileWrapper parent) {
		return gridFsFactory
				.createGridFs(parent.getBucketId().get())
				.find(BasicDBObjectBuilder.start("parentId",
						parent.getId().get()).get())
				.stream()
				.map(file -> getFileWrapper(file, parent.getBucketId().get())
						.get()).collect(Collectors.toList());
	}

	@Override
	public Collection<ComplexId<Id.StringId, Id.StringId>> save(
			Collection<FileWrapper> values) throws IOException {
		return values.stream().map(value -> {
			try {
				return save(value);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}).collect(Collectors.toList());
	}

	@Override
	public Collection<FileWrapper> findAllByParent(
			ComplexId<StringId, StringId> parent) {
		return gridFsFactory
				.createGridFs(parent.getFirstId().get())
				.find(BasicDBObjectBuilder.start("parentId",
						parent.getSecondId().get()).get())
				.stream()
				.map(file -> getFileWrapper(file, parent.getFirstId().get())
						.get()).collect(Collectors.toList());
	}

	private Optional<FileWrapper> getFileWrapper(GridFSDBFile file,
			final String bucketId) {
		return Optional.ofNullable(file).map(f -> {
			final Id.StringId parentId = (StringId) file.get("parentId");
			File parentDir = new File("/" + bucketId + "/" + parentId.get());
			if (!parentDir.exists()) {
				parentDir.mkdir();
			}

			File targetFile = new File(parentDir, file.getFilename());
			FileUtils.writeToFile(f, targetFile);
			return new FileWrapper(targetFile, parentId, () -> bucketId);
		});
	}

}
