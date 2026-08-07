package dev.wick.gmtabs.view;

import dev.wick.gmtabs.tab.TabContent;
import dev.wick.gmtabs.view.content.TabContentDisplay;
import javafx.scene.control.Tab;

class GmTab extends Tab {
	GmTab(){
		TabContent tabContent = new TabContent(false, "https://google.com");
		TabContentDisplay tabContentDisplay = new TabContentDisplay(tabContent);
		setContent(tabContentDisplay);
		setText(String.valueOf(tabContent.pathIsFile()));
	}
}
