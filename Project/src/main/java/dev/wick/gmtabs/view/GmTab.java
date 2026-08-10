package dev.wick.gmtabs.view;

import dev.wick.gmtabs.tab.TabConfig;
import dev.wick.gmtabs.view.content.TabContentDisplay;
import dev.wick.gmtabs.view.editor.Editor;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Tab;

import java.util.Optional;

class GmTab extends Tab {
	private final ObjectProperty<TabConfig> tabConfig = new SimpleObjectProperty<>();
	private final Editor editor;

	private final TabContentDisplay tabContentDisplay;

	GmTab(TabConfig tabConfig, boolean displayContent, Editor editor){
		this.tabConfig.set(tabConfig);
		this.editor = editor;
		setText(String.valueOf(tabConfig.getTabContent().pathIsFile()));
		ContextMenu contextMenu = new GmTabContextMenu(this, _ -> showEditor());
		setContextMenu(contextMenu);
		tabContentDisplay = displayContent? new TabContentDisplay() : null;
		setContent(tabContentDisplay);
		setConfig(tabConfig);
	}


	public TabConfig getConfig() {
		return tabConfig.get();
	}

	private void showEditor(){
		Optional<TabConfig> result = editor.showAndWait(tabConfig.get());
		result.ifPresent(this::setConfig);
	}

	private void setConfig(TabConfig config) {
		if(tabContentDisplay!=null) tabContentDisplay.setContent(config.getTabContent());
		tabConfig.set(config);
	}

	void addUpdateListener(ChangeListener<TabConfig> listener){
		tabConfig.addListener(listener);
	}
}
