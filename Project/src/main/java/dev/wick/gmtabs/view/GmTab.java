package dev.wick.gmtabs.view;

import javafx.scene.control.Tab;
import javafx.scene.web.WebView;

class GmTab extends Tab {
	GmTab(){
		WebView webView = new WebView();
		webView.getEngine().load("https://google.com");
		setContent(webView);
	}
}
