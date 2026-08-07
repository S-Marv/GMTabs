package dev.wick.gmtabs.view.content;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ZoomControllerTest {

	@Test
	void testInit(){
		ChangeTracker tracker = new ChangeTracker();
		new ZoomController(tracker);
		Assertions.assertTrue(tracker.changed);
	}
	
	@Test
	void testZoomIn(){
		runZoomTest(new ZoomController(null), true);
	}

	
	@Test
	void testZoomIn_maxLimit(){
		ZoomController controller = new ZoomController(null);
		runZoomTest(controller, true);
		Assertions.assertFalse(controller.zoom(true));
		Assertions.assertEquals(ZoomController.MAXIMUM, controller.getZoom());
	}

	@Test
	void testZoomOut(){
		runZoomTest(new ZoomController(null), false);
	}

	@Test
	void testZoomOut_minLimit(){
		ZoomController controller = new ZoomController(null);
		runZoomTest(controller, false);
		Assertions.assertFalse(controller.zoom(false));
		Assertions.assertEquals(ZoomController.MINIMUM, controller.getZoom());
	}
	
	void runZoomTest(ZoomController controller, boolean zoomIn){
		int times = zoomIn? 16: 8; //Expected calls needed to reach respective limits
		for (int i = 0; i < times; i++) {
			Assertions.assertTrue(controller.getZoom() > ZoomController.MINIMUM);
			Assertions.assertTrue(controller.getZoom()<ZoomController.MAXIMUM);
			Assertions.assertTrue(controller.zoom(zoomIn));
		}
		int expected = zoomIn? ZoomController.MAXIMUM : ZoomController.MINIMUM;
		Assertions.assertEquals(expected, controller.getZoom());
	}
	
	public static class ChangeTracker implements ChangeListener<Number> {

		boolean changed = false;

		@Override
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
			changed=true;
		}
	}
}