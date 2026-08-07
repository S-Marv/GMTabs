package dev.wick.gmtabs.view.content;

import dev.wick.gmtabs.tab.TabContent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TabContentDisplay extends VBox {
	private final WebController webController = new WebController();

	private final Label urlLabel = webController.getUrlLabel();
	private final Node historyNavigation = webController.getHistoryNavigation();
	private final Node zoomUi = webController.getZoomUi();
	private final Tooltip tooltip = new Tooltip();

	public TabContentDisplay(TabContent content){
		webController.setContent(content);
		setAnchors(webController.getWebView());
		urlLabel.setAlignment(Pos.CENTER);
		urlLabel.setWrapText(false);
		urlLabel.setMaxHeight(1);
		getChildren().addAll(makeBar(), webController.getWebView());
	}

	private Parent makeBar() {
		urlLabel.setWrapText(true);
		HBox hBox = new HBox(historyNavigation, urlLabel, zoomUi);
		urlLabel.setPrefWidth(Integer.MAX_VALUE);
		hBox.setAlignment(Pos.CENTER);
		return hBox;
	}

	private void setAnchors(Node node){
		AnchorPane.setRightAnchor(node, 0.0);
		AnchorPane.setLeftAnchor(node, 0.0);
		AnchorPane.setTopAnchor(node, 0.0);
		AnchorPane.setBottomAnchor(node, 0.0);
	}

	public Tooltip getTooltip() {
		return tooltip;
	}
}
