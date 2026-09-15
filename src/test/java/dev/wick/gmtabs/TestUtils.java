package dev.wick.gmtabs;

import javafx.application.Platform;

import java.io.File;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestUtils {
	private static final File TEST_DIRECTORY = new File("./testFiles");

	private static final Lock testDirLock = new ReentrantLock();
	public static  File makeTestDir(String dirName){
		File dir = Path.of(TEST_DIRECTORY.getPath(), dirName).toFile();
		testDirLock.lock();
		boolean testFolderCreated = (TEST_DIRECTORY.exists() || TEST_DIRECTORY.mkdir()) && (dir.exists() || dir.mkdir());
		testDirLock.unlock();
		if(!testFolderCreated){
			throw new RuntimeException("Test directory could not be created.");
		}
		return dir;
	}

	private static final int THREAD_TEST_CLASSES = 2; //Must be equal to the number of classes that call requestPlatform
	private static final AtomicInteger finishedTestClasses = new AtomicInteger(0);
	public static void requestPlatform(){
		try{
			Platform.startup(()->{});
		} catch (IllegalStateException ignored){}
	}

	public static void requestPlatformShutdown(){
		if(finishedTestClasses.incrementAndGet() == THREAD_TEST_CLASSES) {
			Platform.exit();
		}
	}
}
