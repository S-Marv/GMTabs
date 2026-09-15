package dev.wick.gmtabs.view.content;

import dev.wick.gmtabs.tab.TabContent;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.File;

public class WebController {
	private final WebView webView = new WebView();
	private final Label urlLabel = new Label();
	private final ZoomUi zoomUi = new ZoomUi(webView.zoomProperty());
	private final HistoryNavigator historyNavigation = new HistoryNavigator(webView.getEngine());

	private TabContent content;
	private File contentFile;

	WebController(){
		addLogging();
	}

	void setContent(TabContent content) {
		this.content = content;
		if(content.pathIsFile()) contentFile = new File(content.path());
		String url = content.pathIsFile()? "file:///"+contentFile.getAbsolutePath() : content.path();
		webView.getEngine().load(url);
		System.out.println(content);
	}

	private void update(Observable observable) {
		WebEngine engine = webView.getEngine();
		String displayText = content.pathIsFile()? contentFile.getName() : engine.getLocation();
		urlLabel.setText(displayText);
	}

	Node getWebView() {
		return webView;
	}

	Label getUrlLabel() {
		return urlLabel;
	}

	Parent getHistoryNavigation() {
		return historyNavigation;
	}

	Parent getZoomUi() {
		return zoomUi;
	}

	private void addLogging() {
		WebEngine engine = webView.getEngine();
		engine.locationProperty().addListener(this::update);
		engine.setOnError(event->{
			System.out.println(event.toString());
			throw new RuntimeException(event.getException());
		});
		engine.getLoadWorker().messageProperty().addListener(
				(_, _, newString) -> Platform.runLater(()->System.out.println(newString)));
		engine.getLoadWorker().progressProperty().addListener((_,_, d)->
				System.out.println(d.doubleValue()*100 +"%"));
	}
}
