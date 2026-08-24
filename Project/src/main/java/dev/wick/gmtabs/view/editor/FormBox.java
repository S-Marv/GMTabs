package dev.wick.gmtabs.view.editor;

import javafx.beans.property.Property;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.List;

public class FormBox extends VBox {
	private final GridPane grid = new GridPane();

	FormBox(EditorForm form){
		getStyleClass().add("editor-box");
		grid.getStyleClass().add("editor-grid");
		KeybindingSetter keybindingSetter = new KeybindingSetter(form.getKeybinding());
		PathSelector pathSelector = new PathSelector(form.isFilePropertyProperty(), form.getWebPath(), form.getFilePath());
		for(EditorOption option : List.of(pathSelector, keybindingSetter)){
			grid.addRow(grid.getRowCount(), option.getLabel(), option.getNode());
		}
		GraphicPreview graphicPreview = new GraphicPreview(form);
		getChildren().addAll(pathSelector.getSwitch(), grid, graphicPreview);
	}

	private static class GraphicPreview extends TabGraphicNode{
		public GraphicPreview(EditorForm editorForm) {
			super(editorForm.getTabConfig());
			for(Property<?> property : List.of(editorForm.getKeybinding(), editorForm.getGraphicColor(), editorForm.getIconPath())){
				property.addListener((_)->setConfig(editorForm.getTabConfig()));
			}
		}
	}
}
