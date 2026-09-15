package dev.wick.gmtabs.view.editor;

import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

import java.io.File;

class PathSelector extends EditorOption{
	private static final String DEFAULT_FILE_BUTTON_TEXT = "Choose File";

	private final TogglePane toggleSwitch;
	private final Button fileSelectButton = formatNode(new Button(DEFAULT_FILE_BUTTON_TEXT));
	private final TextField urlField = formatNode(new TextField());
	private final AnchorPane pathContainer = new AnchorPane(fileSelectButton, urlField);

	PathSelector(BooleanProperty isFileProperty, StringProperty webPath, StringProperty filePath){
		super("Tab Content");
		toggleSwitch = new TogglePane(isFileProperty);
		urlField.textProperty().bindBidirectional(webPath);
		urlField.setPromptText("Website URL");
		setFileButton(filePath);
		toggleSwitch.fileSelectedProperty().addListener((_,_,newValue)-> setContainer(newValue));
	}

	private void setContainer(boolean pathIsFile){
		fileSelectButton.setVisible(pathIsFile);
		urlField.setVisible(!pathIsFile);
	}

	@Override
	public Node getNode() {
		return pathContainer;
	}

	public Node getSwitch(){
		return toggleSwitch;
	}

	private void setFileButton(StringProperty filePath) {
		FileChooser fileChooser = new FileChooser();
		fileSelectButton.setOnAction((_)->{
			fileChooser.setInitialDirectory(getInitialDir(filePath.get()));
			File file = fileChooser.showOpenDialog(null);
			String path; String buttonText;
			if(file==null){
				path= null;
				buttonText=DEFAULT_FILE_BUTTON_TEXT;
			} else {
				path = file.getAbsolutePath();
				buttonText = file.getName();
			}
			filePath.set(path);
			fileSelectButton.setText(buttonText);
		});
	}

	private static final File DEFAULT_DIR = new File(System.getProperty("user.home"));
	private static File getInitialDir(String oldPath){
		if(oldPath == null) return DEFAULT_DIR;
		File oldFile = new File(oldPath);
		return oldFile.exists() ? oldFile.getParentFile() : DEFAULT_DIR;
	}

	private static <T extends Control> T formatNode(T node){
		format(node);
		AnchorPane.setTopAnchor(node, 0.);
		AnchorPane.setBottomAnchor(node, .0);
		AnchorPane.setRightAnchor(node, .0);
		AnchorPane.setLeftAnchor(node, .0);
		return node;
	}


	private static class TogglePane extends HBox{
		ToggleGroup group = new ToggleGroup();
		ToggleButton fileButton = makeToggleButton("File");
		ToggleButton webButton = makeToggleButton("Website");

		private TogglePane(BooleanProperty isFileProperty){
			fileButton.setToggleGroup(group);
			webButton.setToggleGroup(group);
			group.selectToggle(isFileProperty.get() ? fileButton : webButton);
			fileButton.selectedProperty().bindBidirectional(isFileProperty);
			getChildren().addAll(fileButton, webButton);
			group.selectedToggleProperty().addListener(this::onToggle);
			setAlignment(Pos.CENTER);
		}

		private void onToggle(Observable ignored, Toggle old, Toggle newValue) {
			if(newValue==null){
				group.selectToggle(old==fileButton ? webButton : fileButton);
			}
		}

		private ToggleButton makeToggleButton(String text){
			ToggleButton toggleButton = new ToggleButton(text);
			toggleButton.setToggleGroup(group);
			toggleButton.setPrefWidth(100);
			return toggleButton;
		}

		private BooleanProperty fileSelectedProperty(){
			return fileButton.selectedProperty();
		}
	}
}
