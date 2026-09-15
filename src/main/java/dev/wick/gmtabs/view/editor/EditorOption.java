package dev.wick.gmtabs.view.editor;

import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;

public abstract class EditorOption {
	private final Label label;
	private static final int CELL_WIDTH = 150;

	EditorOption(String labelText){
		label=makeLabel(labelText);
	}

	abstract Node getNode();

	private static Label makeLabel(String text){
		Label label = new Label(text);
		label.setTextAlignment(TextAlignment.RIGHT);
		return label;
	}

	public Label getLabel(){
		return label;
	}

	protected static <T extends Control> T format(T node){
		node.setPrefWidth(CELL_WIDTH);
		return node;
	}
}
