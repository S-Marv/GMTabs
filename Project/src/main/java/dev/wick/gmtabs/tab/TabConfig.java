package dev.wick.gmtabs.tab;

import javafx.scene.input.KeyCodeCombination;

import java.util.Objects;

public class TabConfig {

	private final TabGraphic tabGraphic;
	private final TabContent tabContent;
	private final KeyCodeCombination keyCode;

	public TabConfig(TabGraphic tabGraphic, TabContent tabContent, KeyCodeCombination keyCode){
		this.tabGraphic = tabGraphic;
		this.tabContent = tabContent;
		this.keyCode = keyCode;
	}

	public TabConfig(TabContent tabContent, KeyCodeCombination keyCode){
		this.tabGraphic = new TabGraphic(null, null);
		this.tabContent = tabContent;
		this.keyCode = keyCode;
	}

	public TabGraphic getTabGraphic() {
		return tabGraphic;
	}

	public String getContentPath() {
		return tabContent.path();
	}

	public KeyCodeCombination getKeyCombination() {
		return keyCode;
	}

	public TabContent getTabContent() {
		return tabContent;
	}

	public boolean isContentAFile() {
		return tabContent.pathIsFile();
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		TabConfig that = (TabConfig) o;
		return Objects.equals(tabGraphic, that.tabGraphic) && Objects.equals(tabContent, that.tabContent) && keyCode.equals(that.keyCode);
	}

	@Override
	public String toString() {
		return "TabConfig{" +
				"tabGraphic='" + tabGraphic + '\'' +
				", tabContent=" + tabContent +
				", keyCode=" + keyCode +
				'}';
	}

	@Override
	public int hashCode() {
		return Objects.hash(tabGraphic, tabContent, keyCode);
	}
}
