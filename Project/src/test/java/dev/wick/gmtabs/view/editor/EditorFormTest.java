package dev.wick.gmtabs.view.editor;

import dev.wick.gmtabs.tab.TabConfig;
import dev.wick.gmtabs.tab.TabContent;
import dev.wick.gmtabs.tab.TabGraphic;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.FieldSource;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static dev.wick.gmtabs.view.editor.IncompleteFormException.VitalProperty.*;

class EditorFormTest {

	@SuppressWarnings("unused")
	static final List<TabConfig> VALID_CONFIGS = List.of(
			new TabConfig(new TabContent(true, "path"), new KeyCodeCombination(KeyCode.K)),
			new TabConfig(new TabContent(false, "anotherPath"), new KeyCodeCombination(KeyCode.C)),
			new TabConfig(new TabGraphic("iconPath", Color.BLUE), new TabContent(false, "3rdUrl"), new KeyCodeCombination(KeyCode.C, KeyCombination.SHIFT_DOWN))
	);

	@SuppressWarnings("unused")
	private static final List<FailingCase> INVALID_CONFIGS = List.of(
			new FailingCase(new TabConfig(new TabContent(false, null), new KeyCodeCombination(KeyCode.B)), PATH),
			new FailingCase(new TabConfig(new TabContent(true, ""), new KeyCodeCombination(KeyCode.T)), PATH),
			new FailingCase(new TabConfig(new TabContent(true, null), null), PATH, KEYBIND),
			new FailingCase(new TabConfig(new TabContent(true, "test"), null), KEYBIND)
	);

	@ParameterizedTest
	@FieldSource("VALID_CONFIGS")
	void testIsValid(TabConfig testConfig) {
		EditorForm form =new EditorForm(null, testConfig);
		Assertions.assertTrue(form.isValid());
	}

	@ParameterizedTest
	@FieldSource("INVALID_CONFIGS")
	void testIsValid_invalidConfigs(FailingCase failingCase) {
		EditorForm form =new EditorForm(null, failingCase.getConfig());
		Assertions.assertFalse(form.isValid());
	}

	/**
	 * Check if form is invalid after switching to another path type.
	 */
	@Test
	void testIsValid_invalidAfterSwitch(){
		TabConfig testConfig = VALID_CONFIGS.getFirst();
		EditorForm form = new EditorForm(null, testConfig);
		form.isFilePropertyProperty().set(!testConfig.isContentAFile());
		Assertions.assertFalse(form.isValid());
	}

	@ParameterizedTest
	@FieldSource("VALID_CONFIGS")
	void testConstructConfig_sanityTest(TabConfig testConfig) {
		EditorForm form = new EditorForm(null, testConfig);
		Assertions.assertEquals(testConfig, form.constructConfig());
	}

	@ParameterizedTest
	@FieldSource("INVALID_CONFIGS")
	void testConstructConfig_invalid(FailingCase failingCase){
		EditorForm editorForm = new EditorForm(null, failingCase.getConfig());
		try{
			editorForm.constructConfig();
			assert false; //
		} catch (IncompleteFormException e){
			Assertions.assertEquals(failingCase.getReasons(), e.getMissingProperties());
		}
	}


	private static class FailingCase{
		private final TabConfig config;
		private final HashSet<IncompleteFormException.VitalProperty> strings;

		FailingCase(TabConfig config, IncompleteFormException.VitalProperty reason, IncompleteFormException.VitalProperty... additionalReasons){
			this.config = config;
			strings = new HashSet<>(List.of(additionalReasons));
			strings.add(reason);
		}

		public TabConfig getConfig() {
			return config;
		}

		public Set<IncompleteFormException.VitalProperty> getReasons() {
			return strings;
		}
	}
}