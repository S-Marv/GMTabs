package dev.wick.gmtabs.view.content;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;

public class HistoryNavigator extends HBox {
	private final WebHistory history;
	private final Button backButton = makeButton("⮜", -1);
	private final Button forwardButton = makeButton("⮞", 1);

	public HistoryNavigator(WebEngine engine) {
		this.history = engine.getHistory();
		engine.getLoadWorker().stateProperty().addListener(_ -> refresh());
		getChildren().addAll(backButton, forwardButton);
		setMinWidth(55);
		refresh();
	}

	private Button makeButton(String text, int delta) {
		Button button = new Button(text);
		button.setOnAction(_ -> {
			int newIndex = history.getCurrentIndex()+delta;
			if (newIndex >= 0 && newIndex <history.getEntries().size()){
				history.go(delta);
			}
		});
		return button;
	}

	public void refresh(){
		backButton.setDisable(history.getCurrentIndex()<=0);
		forwardButton.setDisable(history.getCurrentIndex()>=history.getEntries().size()-1);
	}
}
