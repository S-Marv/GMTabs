package dev.wick.gmtabs.view;

import dev.wick.gmtabs.TestUtils;
import javafx.application.Platform;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

class GmTabPaneTest {

	private static File testDir;

	@BeforeAll
	static void makeTestDir(){
		testDir = TestUtils.makeTestDir("paneTest");
		TestUtils.requestPlatform();
	}

	@Test
	void testLoading_default(){
		GmTabPane tabPane = makeTabPane();
		Assertions.assertEquals(List.of(GmTabPane.DEFAULT_TAB), tabPane.getTabConfigs());
	}

	private GmTabPane makeTabPane(){
		File configFile = Path.of(testDir.getPath(), "nonexistentFileName").toFile();
		CountDownLatch latch = new CountDownLatch(1);
		AtomicReference<GmTabPane> pane = new AtomicReference<>();
		Platform.runLater(()->{
			pane.set(new GmTabPane(configFile, false));
			latch.countDown();
		});
		try {
			if(!latch.await(5, TimeUnit.SECONDS)) throw new RuntimeException("Did not return");
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		return pane.get();
	}

	@AfterAll
	static void shutdown(){
		TestUtils.requestPlatformShutdown();
	}
}