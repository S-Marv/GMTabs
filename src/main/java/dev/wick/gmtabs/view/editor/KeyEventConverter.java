package dev.wick.gmtabs.view.editor;

import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.List;

public class KeyEventConverter {
	public static KeyCodeCombination convert(KeyEvent keyEvent){
		List<KeyCombination.Modifier> modifiers = new ArrayList<>();
		if(keyEvent.isAltDown()) modifiers.add(KeyCombination.ALT_DOWN);
		if(keyEvent.isControlDown()) modifiers.add(KeyCombination.CONTROL_DOWN);
		if(keyEvent.isShiftDown()) modifiers.add(KeyCombination.SHIFT_DOWN);
		if(keyEvent.isMetaDown()) modifiers.add(KeyCombination.META_DOWN);
		KeyCombination.Modifier[] modifierArray = modifiers.toArray(new KeyCombination.Modifier[0]);
		return new KeyCodeCombination(keyEvent.getCode(), modifierArray);
	}
}
