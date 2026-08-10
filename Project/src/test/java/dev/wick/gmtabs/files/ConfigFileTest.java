package dev.wick.gmtabs.files;

import dev.wick.gmtabs.tab.TabConfig;
import dev.wick.gmtabs.tab.TabConfigTest;
import dev.wick.gmtabs.tab.TabContent;
import dev.wick.gmtabs.tab.TabGraphic;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.List;

import static javafx.scene.input.KeyCombination.ModifierValue.DOWN;
import static javafx.scene.input.KeyCombination.ModifierValue.UP;

public class ConfigFileTest {

	private static final File TEST_DIRECTORY = new File("testFiles/configFolder");
	private final List<TabConfig> testConfigurations = List.of(
			TabConfigTest.TEST_CONFIG,
			new TabConfig(TabGraphic.EMPTY_GRAPHIC, new TabContent(false, "website.com"), new KeyCodeCombination(KeyCode.F2, DOWN, DOWN, DOWN, DOWN, UP))
	);


	@BeforeAll
	static void makeTestDirectory(){
		File parentFile = TEST_DIRECTORY.getParentFile();
		boolean testFolderCreated = (parentFile.exists() || parentFile.mkdir()) && (TEST_DIRECTORY.exists() || TEST_DIRECTORY.mkdir());
		if(!testFolderCreated){
			throw new RuntimeException("Test directory could not be created.");
		}
	}


	@Test
	public void testLoad() throws IOException {
		InputStream inputStream = getClass().getResourceAsStream("testLoad.xml");
		Path path = Paths.get(TEST_DIRECTORY.getPath(), "testLoad.xml");
		assert inputStream != null;
		Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
		List<TabConfig> actual = new ConfigFile(path.toFile()).load();
		Assertions.assertEquals(testConfigurations, actual);
	}

	@Test
	public void testSave() throws FileNotFoundException {
		ConfigFile configFile = new ConfigFile(Paths.get(TEST_DIRECTORY.getPath(), "testSave.xml").toFile());
		configFile.save(testConfigurations);
		Assertions.assertEquals(testConfigurations, configFile.load());
	}
}