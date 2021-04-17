package ru.filestorage.project.configs;

import com.mongodb.gridfs.GridFS;

public interface GridFsFactory {

	GridFS createGridFs(String bucketId);
}
