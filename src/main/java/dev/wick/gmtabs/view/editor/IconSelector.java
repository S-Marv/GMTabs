package dev.wick.gmtabs.view.editor;

import javafx.beans.property.StringProperty;
import javafx.scene.Node;

public class IconSelector extends EditorOption{
	private final FileChooserButton button;

	public IconSelector(StringProperty iconPath) {
		super("Icon (Optional)");
		button = new FileChooserButton(iconPath);
	}

	@Override
	Node getNode() {
		return button;
	}
}
