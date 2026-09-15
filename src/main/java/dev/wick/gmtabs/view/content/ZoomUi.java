package dev.wick.gmtabs.view.content;

import javafx.beans.property.DoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;


public class ZoomUi extends HBox {
	private final Label zoomLabel = new Label();
	private final Button zoomInButton = makeZoomButton(true);
	private final Button zoomOutButton = makeZoomButton(false);
	private final ZoomController zoomController;

	public ZoomUi(DoubleProperty zoomProperty){
		setAlignment(Pos.CENTER_RIGHT);
		zoomController = makeZoomController(zoomProperty);
		setAlignment(Pos.CENTER);
		zoomLabel.setMinWidth(35);
		zoomLabel.setAlignment(Pos.CENTER);
		getChildren().addAll(
				zoomInButton,
				zoomLabel,
				zoomOutButton);
		setMinWidth(zoomLabel.getMinWidth() + 2*zoomInButton.getMinWidth());
	}

	private Button makeZoomButton(boolean zoomIn){
		Button button = new Button(zoomIn? "+" : "-");
		button.setOnAction(_ -> zoomController.zoom(zoomIn));
		button.setMinWidth(25);
		return button;
	}

	private ZoomController makeZoomController(DoubleProperty externalZoomProperty) {
		return new ZoomController((_, _, newNumber)->{
			int newValue = newNumber.intValue();
			externalZoomProperty.setValue(newValue/100d);
			zoomLabel.setText(newValue+"%");
			zoomInButton.setDisable(newValue>=ZoomController.MAXIMUM);
			zoomOutButton.setDisable(newValue<=ZoomController.MINIMUM);
		});
	}
}
