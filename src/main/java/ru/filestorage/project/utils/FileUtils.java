package ru.filestorage.project.utils;

import java.io.File;
import java.io.IOException;

import com.mongodb.gridfs.GridFSDBFile;

public final class FileUtils {

	private FileUtils() {
		super();
	}

	public static void writeToFile(GridFSDBFile fromFile, File toFile) {
		try {
			fromFile.writeTo(toFile);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
