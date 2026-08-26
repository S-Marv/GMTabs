package dev.wick.gmtabs.view.editor;

import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

import static javafx.scene.paint.Color.TRANSPARENT;

public class ColorSelector extends EditorOption {
	private final ColorPicker colorPicker = new ColorPicker();

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
		colorPicker.valueProperty().bind(colorProperty);
	}

	@Override
	Node getNode() {
		return colorPicker;
	}

	Color getColor(){
		return colorPicker.getValue();
	}
}
