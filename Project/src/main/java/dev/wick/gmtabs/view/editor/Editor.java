package dev.wick.gmtabs.view.editor;

import dev.wick.gmtabs.tab.TabConfig;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

import java.util.Optional;

public class Editor {
	private static final ButtonType ACCEPT = new ButtonType("Save", ButtonBar.ButtonData.APPLY);

	private final Dialog<TabConfig> dialog = new Dialog<>();
	private final EditorForm editorForm;
	private final Node acceptButton;

	public Editor(){
		dialog.getDialogPane().getButtonTypes().addAll(ACCEPT, ButtonType.CANCEL);
		acceptButton = dialog.getDialogPane().lookupButton(ACCEPT);
		dialog.setResultConverter(this::convert);
		editorForm = new EditorForm(this::refresh, null);
		dialog.getDialogPane().setContent(new FormBox(editorForm));
	}

	public Optional<TabConfig> showAndWait(TabConfig baseConfig){
		editorForm.set(baseConfig);
		return dialog.showAndWait();
	}

	private TabConfig convert(ButtonType buttonType) {
		return switch (buttonType.getButtonData()){
			case ButtonBar.ButtonData.APPLY -> editorForm.constructConfig();
			case null, default -> null;
		};
	}

	private void refresh(boolean isValid) {
		acceptButton.setDisable(!isValid);
	}

}
