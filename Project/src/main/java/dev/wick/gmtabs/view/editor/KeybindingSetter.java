package dev.wick.gmtabs.view.editor;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyEvent;

public class KeybindingSetter extends ToggleButton implements EditorOption{
	private static final String DEFAULT_TEXT = "Press to Bind Key";
	private final Label label = new Label("Set Keybind", this);
	private final ObjectProperty<KeyCodeCombination> combinationProperty;

	public KeybindingSetter(double prefWidth, ObjectProperty<KeyCodeCombination> combinationProperty){
		super(combinationProperty.get()==null? DEFAULT_TEXT : combinationProperty.get().getDisplayText());
		this.combinationProperty = combinationProperty;
		setPrefWidth(prefWidth);
		setOnKeyPressed(this::processKeyPress);
		setOnAction(this::processClick);
		focusedProperty().addListener(this::processFocusChange);
		combinationProperty.addListener(_->refreshText());
	}

	private void processKeyPress(KeyEvent event){
		if(!isSelected()) return;
		if(!event.getCode().isModifierKey()) {
			selectedProperty().set(false);
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
		if(wasFocused && !isFocused && selectedProperty().get()) {
			selectedProperty().set(false);
			refreshText();
		}
	}

	@Override
	public Node getLabel() {
		return label;
	}

	@Override
	public Node getNode() {
		return this;
	}

	private void refreshText(){
		String newText = combinationProperty.get()!=null? combinationProperty.get().getDisplayText() :
				selectedProperty().get()? "Listening" :
				DEFAULT_TEXT;
		setText(newText);
		System.out.println(combinationProperty.get());
	}
}
