package dev.wick.gmtabs.view.editor;

import javafx.beans.property.Property;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.List;

public class FormBox extends VBox {

	FormBox(EditorForm form){
		getStyleClass().add("editor-box");
		GridPane grid = new GridPane();
		grid.getStyleClass().add("editor-grid");
		KeybindingSetter keybindingSetter = new KeybindingSetter(form.getKeybinding());
		PathSelector pathSelector = new PathSelector(form.isFilePropertyProperty(), form.getWebPath(), form.getFilePath());
		ColorSelector colorSelector = new ColorSelector(form.getGraphicColor());
		for(EditorOption option : List.of(pathSelector, keybindingSetter, colorSelector)){
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
			format();
		}

		private void format() {
			this.setPadding(new Insets(5));
			setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderStroke.THIN)));
		}
	}
}
