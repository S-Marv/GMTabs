package dev.wick.gmtabs.files;

import dev.wick.gmtabs.tab.TabConfig;
import dev.wick.gmtabs.tab.TabContent;
import dev.wick.gmtabs.tab.TabGraphic;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

import static dev.wick.gmtabs.files.XMLNode.*;


public class ConfigXMLReader {
	public List<TabConfig> parse(Document document){
		List<TabConfig> configurations = new ArrayList<>();
		Element root = document.getDocumentElement();
		NodeList nodeList = root.getElementsByTagName(TAB_CONFIGURATION.getNodeName());
		for(int index = 0; index < nodeList.getLength(); index++){

			configurations.add(constructFrom((Element) nodeList.item(index)));
		}
		return configurations;
	}

	private Element configuration;

	private TabConfig constructFrom(Element element){
		configuration = element;
		KeyCodeCombination keyCode = new KeyCodeCombination(
				KeyCode.getKeyCode(readValue(KEY)),
				readModifierValue(SHIFT),
				readModifierValue(CONTROL),
				readModifierValue(ALT),
				readModifierValue(META),
				KeyCombination.ModifierValue.UP
		);
		TabContent content = new TabContent(Boolean.parseBoolean(readValue(CONTENT_TYPE)), readValue(CONTENT_PATH));
		String iconPath = null;
		Color color = Color.TRANSPARENT;
		if(configuration.getElementsByTagName(ICON_PATH.getNodeName()).getLength()!=0) iconPath = readValue(ICON_PATH);
		if(configuration.getElementsByTagName(COLOR.getNodeName()).getLength()!=0) color = Color.valueOf(readValue(COLOR));
		TabGraphic graphic = new TabGraphic(iconPath, color);
		return new TabConfig(graphic, content, keyCode);
	}

	private String readValue(XMLNode xmlNode){
		return configuration.getElementsByTagName(xmlNode.getNodeName()).item(0).getTextContent();
	}

	private KeyCombination.ModifierValue readModifierValue(XMLNode modifierNode){
		String value = readValue(modifierNode);
		return KeyCombination.ModifierValue.valueOf(value);
	}
}
