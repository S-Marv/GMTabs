package dev.wick.gmtabs.tab;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class TabConfigTest {

	public static TabContent TEST_URL = new TabContent(false, "https://google.com");
	public static String TEST_ICON = "images/image.PNG";
	public static TabConfig TEST_CONFIG = new TabConfig(new TabGraphic(TEST_ICON, Color.AQUA), TEST_URL, new KeyCodeCombination(KeyCode.C));


	@Test
	public void testEquals() {
		KeyCodeCombination testCombination = TEST_CONFIG.getKeyCombination();
		TabConfig actual = new TabConfig(
				TEST_CONFIG.getTabGraphic(),
				new TabContent(TEST_CONFIG.isContentAFile(), TEST_CONFIG.getContentPath()),
				new KeyCodeCombination(testCombination.getCode(), testCombination.getShift(), testCombination.getControl(), testCombination.getAlt(), testCombination.getMeta(), testCombination.getShortcut())
		);
		Assertions.assertEquals(TEST_CONFIG, actual);
	}
}