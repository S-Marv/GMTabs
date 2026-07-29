package dev.wick.gmtabs.view;

import javafx.scene.control.TabPane;

public class GmTabPane extends TabPane {
	public GmTabPane(){
		getTabs().add(new GmTab());
	}
}
