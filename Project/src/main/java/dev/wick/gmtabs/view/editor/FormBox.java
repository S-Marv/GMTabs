package dev.wick.gmtabs.view.editor;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class FormBox extends VBox {
	private final GridPane grid = new GridPane();

	FormBox(EditorForm form){
		KeybindingSetter keybindingSetter = new KeybindingSetter(100, form.getKeybinding());
		grid.addRow(grid.getRowCount(), keybindingSetter.getLabel(), keybindingSetter.getNode());
		getChildren().add(grid);
	}
}
