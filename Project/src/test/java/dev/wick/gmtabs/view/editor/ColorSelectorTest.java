package dev.wick.gmtabs.view.editor;

import dev.wick.gmtabs.TestUtils;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.FieldSource;

import java.util.List;

class ColorSelectorTest {

	@BeforeAll
	static void startup(){
		TestUtils.requestPlatform();
	}


	@SuppressWarnings("unused")
	private static final List<InitializationInput> INITIALIZATION_TESTS = List.of(
			new InitializationInput("Basic", Color.RED, Color.RED),
			new InitializationInput("Transparency", new Color(.1, .2, .4, 0), Color.TRANSPARENT),
			new InitializationInput("Null", null, Color.TRANSPARENT)
	);

	@ParameterizedTest @FieldSource("INITIALIZATION_TESTS")
	void testInitialization(InitializationInput parameters){
		ColorSelector colorSelector = new ColorSelector(new SimpleObjectProperty<>(parameters.initial));
		Assertions.assertEquals(parameters.expected, colorSelector.getColor());
	}

	@SuppressWarnings("unused")
	private static final List<BindingInput> BINDING_TESTS = List.of(
			new BindingInput("Basic", Color.RED, Color.SILVER),
			new BindingInput("Transparency", Color.BLUE, new Color(0, .2, 0, 0), Color.TRANSPARENT),
			new BindingInput("Null", Color.AQUA, null, Color.TRANSPARENT)
	);

	@ParameterizedTest @FieldSource("BINDING_TESTS")
	void testBinding(BindingInput parameters){
		SimpleObjectProperty<Color> property = new SimpleObjectProperty<>(parameters.initial);
		ColorSelector colorSelector = new ColorSelector(property);
		property.set(parameters.subsequent);
		Assertions.assertEquals(parameters.expected, colorSelector.getColor());
	}

	@AfterAll
	static void shutdown(){
		TestUtils.requestPlatformShutdown();
	}


	private record InitializationInput(String testName, Color initial, Color expected){
		@Override
		public String toString() {
			return String.format("%s (initial: %s, expected: %s)", testName, initial, expected);
		}
	}

	private record BindingInput(String testName, Color initial, Color subsequent, Color expected){
		BindingInput(String testName, Color initial, Color expected){
			this(testName, initial, expected, expected);
		}

		@Override
		public String toString() {
			return String.format("%s (initial: %s, subsequent: %s, expected: %s)", testName, initial, subsequent, expected);
		}
	}
}