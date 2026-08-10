package dev.wick.gmtabs.view;

import dev.wick.gmtabs.TestUtils;
import dev.wick.gmtabs.files.ConfigFile;
import javafx.application.Platform;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

class GmTabPaneTest {

	private static File testDir;

	@BeforeAll
	static void makeTestDir(){
		testDir = TestUtils.makeTestDir("paneTest");
		Platform.startup(()->{});
	}

	@Test
	void testLoading_default(){
		GmTabPane tabPane = makeTabPane("nonexistentFileName");
		Assertions.assertEquals(List.of(GmTabPane.DEFAULT_TAB), tabPane.getTabConfigs());
	}

	private GmTabPane makeTabPane(String targetFileName){
		File configFile = Path.of(testDir.getPath(), targetFileName).toFile();
		CountDownLatch latch = new CountDownLatch(1);
		AtomicReference<GmTabPane> pane = new AtomicReference<>();
		Platform.runLater(()->{
			pane.set(new GmTabPane(configFile, false));
			latch.countDown();
		});
		try {
			latch.await();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		return pane.get();
	}

	@AfterAll
	static void shutdown(){
		Platform.exit();
	}
}