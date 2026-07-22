package dev.wick.gmtabs.files;

enum XMLNode {
	ROOT("Configurations"),
	TAB_CONFIGURATION("TabConfig"),
	ICON_PATH("iconPath"),
	COLOR("color"),
	CONTENT_TYPE("isFile"),
	CONTENT_PATH("contentPath"),

	KEY_COMBINATION("KeyCombination"),
	ALT("alt"),
	META("meta"),
	SHIFT("shift"),
	CONTROL("control"),
	KEY("key");

	private final String nodeName;


	XMLNode(String nodeName){
		this.nodeName = nodeName;
	}


	public String getNodeName() {
		return nodeName;
	}
}
