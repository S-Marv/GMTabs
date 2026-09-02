package dev.wick.gmtabs.view.editor;

import dev.wick.gmtabs.tab.TabConfig;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;

import java.util.Optional;

public class Editor {
	private static final ButtonType ACCEPT = new ButtonType("Save", ButtonBar.ButtonData.APPLY);

	private final Dialog<TabConfig> dialog = new Dialog<>();
	private final EditorForm editorForm;
	private final Node acceptButton;

	public Editor(String stylesheet){
		DialogPane dialogPane = dialog.getDialogPane();
		dialogPane.getStylesheets().add(stylesheet);
		dialogPane.getButtonTypes().addAll(ACCEPT, ButtonType.CANCEL);
		acceptButton = dialogPane.lookupButton(ACCEPT);
		dialog.setResultConverter(this::convert);
		editorForm = new EditorForm(this::refresh, null);
		dialogPane.setContent(new FormBox(editorForm));
	}

	/**
	 * @param baseConfig The config the form should be set to initially. May be {@code null} to have an empty form.
	 * @return The result of the form, which may or may not be present.
	 */
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
