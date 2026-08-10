package dev.wick.gmtabs;

import java.io.File;
import java.nio.file.Path;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestUtils {
	private static final File TEST_DIRECTORY = new File("./testFiles");

	static private final Lock testDirLock = new ReentrantLock();
	static public File makeTestDir(String dirName){
		File dir = Path.of(TEST_DIRECTORY.getPath(), dirName).toFile();
		testDirLock.lock();
		boolean testFolderCreated = (TEST_DIRECTORY.exists() || TEST_DIRECTORY.mkdir()) && (dir.exists() || dir.mkdir());
		testDirLock.unlock();
		if(!testFolderCreated){
			throw new RuntimeException("Test directory could not be created.");
		}
		return dir;
	}
}
