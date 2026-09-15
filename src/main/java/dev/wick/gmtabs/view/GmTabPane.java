package dev.wick.gmtabs.view;

import dev.wick.gmtabs.files.ConfigFile;
import dev.wick.gmtabs.tab.TabConfig;
import dev.wick.gmtabs.tab.TabContent;
import dev.wick.gmtabs.tab.TabGraphic;
import dev.wick.gmtabs.view.editor.Editor;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.geometry.Side;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GmTabPane extends TabPane {
	static final TabConfig DEFAULT_TAB = new TabConfig(
			TabGraphic.EMPTY_GRAPHIC,
			new TabContent(false, "https://google.com"), new KeyCodeCombination(KeyCode.F2));

	private static final String STYLESHEET = Objects.requireNonNull(GmTabPane.class.getResource("stylesheet.css")).toExternalForm();

	private final ConfigFile configFile;
	private final boolean loadContent;
	private final Editor editor = new Editor(STYLESHEET);

	/**
	 * @param file File to read tab configs from. If the file does not exit, it will create it.
	 * @param loadContent Web viewer will not be loaded if {@code false}. Mainly for testing.
	 */
	public GmTabPane(File file, boolean loadContent){
		this.configFile = new ConfigFile(file);
		this.loadContent = loadContent;
		load();
		getTabs().addListener(this::processListChange);
		format();
	}

	/**
	 * @return all TabConfigs from each of the pane's GmTabs.
	 */
	List<TabConfig> getTabConfigs() {
		List<TabConfig> tabConfigs = new ArrayList<>();
		for (Tab tab : getTabs()){
			if(tab instanceof GmTab gmTab) tabConfigs.add(gmTab.getConfig());
		}
		return tabConfigs;
	}


	/**
	 * Adds tabs to the pane's tab list, prioritizing data from the ConfigFile. If that file doesn't exist, it uses the default TabConfig
	 */
	private void load(){
		List<TabConfig> configs;
		try {
			configs = configFile.load();
		} catch (FileNotFoundException e) {
			configs = List.of(DEFAULT_TAB);
		}
		for (TabConfig tabConfig: configs){
			GmTab tab = new GmTab(tabConfig, loadContent, editor);
			tab.addUpdateListener(tabListener);
			getTabs().add(tab);
		}
	}


	private void format(){
		getStylesheets().add(STYLESHEET);
		setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		setTabMaxHeight(Double.MAX_VALUE);
		setSide(Side.BOTTOM);
	}


	private final ChangeListener<TabConfig> tabListener = (_, _, _) -> save();

	private void processListChange(ListChangeListener.Change<? extends Tab> change){
		boolean saveEventOccurred = false;
		while (change.next()) {
			if (change.wasRemoved() || change.wasAdded() || change.wasPermutated()) {
				saveEventOccurred = true;
				break;
			}
		}
		if (saveEventOccurred) save();
	}

	private void save(){
		configFile.save(getTabConfigs());
	}
}
