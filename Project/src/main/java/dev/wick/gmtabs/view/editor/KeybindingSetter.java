package dev.wick.gmtabs.view.editor;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyEvent;

public class KeybindingSetter extends EditorOption{
	private static final String DEFAULT_TEXT = "Press to Bind Key";
	private final ToggleButton toggleButton;
	private final ObjectProperty<KeyCodeCombination> combinationProperty;

	public KeybindingSetter(ObjectProperty<KeyCodeCombination> combinationProperty){
		super("Set Keybind");
		this.combinationProperty = combinationProperty;
		toggleButton = makeToggleButton();
		combinationProperty.addListener(_->refreshText());
	}

	private ToggleButton makeToggleButton() {
		ToggleButton button = new ToggleButton(combinationProperty.get()==null? DEFAULT_TEXT : combinationProperty.get().getDisplayText());
		button.setOnKeyPressed(this::processKeyPress);
		button.setOnAction(this::processClick);
		button.focusedProperty().addListener(this::processFocusChange);
		return format(button);
	}

	private void processKeyPress(KeyEvent event){
		if(!toggleButton.isSelected()) return;
		if(!event.getCode().isModifierKey()) {
			toggleButton.setSelected(false);
			combinationProperty.set(KeyEventConverter.convert(event));
		}
		event.consume();
	}

	private void processClick(ActionEvent e){
		if(combinationProperty.get()==null) refreshText();
		else combinationProperty.set(null);
		e.consume();
	}

	private void processFocusChange(ObservableValue<? extends Boolean> ignored, Boolean wasFocused, Boolean isFocused){
		if(wasFocused && !isFocused && toggleButton.selectedProperty().get()) {
			toggleButton.setSelected(false);
			refreshText();
		}
	}

	@Override
	public Node getNode() {
		return toggleButton;
	}

	private void refreshText(){
		String newText = combinationProperty.get()!=null? combinationProperty.get().getDisplayText() :
				toggleButton.isSelected()? "Listening" :
				DEFAULT_TEXT;
		toggleButton.setText(newText);
		System.out.println(combinationProperty.get());
	}
}
