package ru.filestorage.project.configs;

import javax.inject.Inject;

import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.stereotype.Service;

import com.mongodb.gridfs.GridFS;

@Service
public class GridFsFactoryImpl implements GridFsFactory {

	@Inject
	private MongoDbFactory factory;

	@Override
	public GridFS createGridFs(final String bucketId) {
		return new GridFS(factory.getLegacyDb(), bucketId);
	}

}
