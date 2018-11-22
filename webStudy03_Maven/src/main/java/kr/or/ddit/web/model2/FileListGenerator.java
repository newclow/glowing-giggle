package kr.or.ddit.web.model2;

import java.io.File;

public class FileListGenerator {
	public File[] getFileList(String parentPath, String fileName) {
		File[] fileList = null;
		
		File parentFolder = new File(parentPath);
		File targetFile = new File(parentFolder, fileName);
		
		if (targetFile.isDirectory()) {
			fileList = targetFile.listFiles();
		} else {
			fileList = new File(parentFolder,"").listFiles();
		}
		return fileList;
	}
}
