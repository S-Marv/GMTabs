package dev.wick.gmtabs.view.editor;

import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import static javafx.scene.paint.Color.TRANSPARENT;

public class ColorSelector extends EditorOption {
	private final ColorPicker colorPicker = new ColorPicker();
	private final HBox buttonBox = new HBox(colorPicker, makeClearButton());

	ColorSelector(ObjectProperty<Color> colorProperty) {
		super("Color (optional)");
		colorPicker.valueProperty().addListener((_,_,newColor)->{
			if(newColor == null
					|| (newColor.getOpacity() == TRANSPARENT.getOpacity() && newColor!= TRANSPARENT)){
				colorProperty.setValue(TRANSPARENT);
			} else {
				colorProperty.set(newColor);
			}
		});
		colorProperty.bindBidirectional(colorPicker.valueProperty());
	}

	@Override
	Node getNode() {
		return buttonBox;
	}

	Color getColor(){
		return colorPicker.getValue();
	}

	private Button makeClearButton(){
		Button button = new Button("×");
		button.setOnAction(_->colorPicker.setValue(TRANSPARENT));
		button.setTooltip(new Tooltip("Set to Transparent"));
		return button;
	}
}
