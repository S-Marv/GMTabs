package dev.wick.gmtabs.view.editor;

import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

class PathSelector extends EditorOption{

	private final TogglePane toggleSwitch;
	private final Button fileSelectButton;
	private final TextField urlField = formatNode(new TextField());
	private final AnchorPane pathContainer;

	PathSelector(BooleanProperty isFileProperty, StringProperty webPath, StringProperty filePath){
		super("Tab Content");
		toggleSwitch = new TogglePane(isFileProperty);
		urlField.textProperty().bindBidirectional(webPath);
		urlField.setPromptText("Website URL");
		fileSelectButton = formatNode(new FileChooserButton(filePath));
		pathContainer = new AnchorPane(fileSelectButton, urlField);
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
