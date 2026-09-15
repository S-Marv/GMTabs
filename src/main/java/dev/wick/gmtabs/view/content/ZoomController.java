package dev.wick.gmtabs.view.content;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.IntegerPropertyBase;
import javafx.beans.value.ChangeListener;

class ZoomController {
	static final int MINIMUM = 20;
	static final int BASE = 100;
	static final int MAXIMUM = 500;

	// set as -1 to make sure call to set value in constructor activates listener
	private final IntegerProperty zoomValue = new IntegerPropertyBase(-1) {
		@Override
		public Object getBean() {
			return null;
		}

		@Override
		public String getName() {
			return "Zoom Value";
		}
	};

	ZoomController(ChangeListener<Number> listener){
		if(listener!=null) zoomValue.addListener(listener);
		zoomValue.setValue(BASE);
	}

	boolean zoom(boolean zoomIn){
		int newValue = zoomValue.getValue() + getDelta(zoomIn);
		if(newValue < MINIMUM || newValue > MAXIMUM) return false;
		zoomValue.setValue(newValue);
		return true;
	}

	private int getDelta(boolean zoomIn){
		int modifier = (zoomIn? 1:-1);
		int magnitudeValue = zoomValue.getValue() + modifier;
		int delta = (magnitudeValue < 200)? 10
				: (magnitudeValue < 300)? 25
				: 100;
		return delta*modifier;
	}

	public int getZoom() {
		return zoomValue.get();
	}
}
