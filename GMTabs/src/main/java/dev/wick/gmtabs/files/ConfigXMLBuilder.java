package dev.wick.gmtabs.files;

import dev.wick.gmtabs.tab.TabConfig;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.paint.Color;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import java.util.List;

import static dev.wick.gmtabs.files.XMLNode.*;


public class ConfigXMLBuilder {
	private final Document document;
	private final Element root;

	public ConfigXMLBuilder(DocumentBuilder documentBuilder){
		document = documentBuilder.newDocument();
		root = document.createElement(ROOT.getNodeName());
		document.appendChild(root);
	}

	public Document build(List<TabConfig> tabConfigs){
		for(TabConfig tabConfig : tabConfigs) {
			Element currentElement = document.createElement(TAB_CONFIGURATION.getNodeName());
			root.appendChild(currentElement);
			addChildWithValue(currentElement, CONTENT_PATH, tabConfig.getContentPath());
			if(tabConfig.getTabGraphic().iconPath()!=null) {
				addChildWithValue(currentElement, ICON_PATH, tabConfig.getTabGraphic().iconPath());
			}
			if(tabConfig.getTabGraphic().color()!=Color.TRANSPARENT && tabConfig.getTabGraphic().color()!=null){
				addChildWithValue(currentElement, COLOR, tabConfig.getTabGraphic().color().toString());
			}
			addChildWithValue(currentElement, CONTENT_TYPE, String.valueOf(tabConfig.isContentAFile()));
			currentElement.appendChild(makeKeyCombinationElement(tabConfig.getKeyCombination()));
		}
		return document;
	}

	private Element makeKeyCombinationElement(KeyCodeCombination keyCombination) {
		Element element = document.createElement(KEY_COMBINATION.getNodeName());
		addChildWithValue(element, CONTROL, keyCombination.getControl().name());
		addChildWithValue(element, ALT, keyCombination.getAlt().name());
		addChildWithValue(element, META, keyCombination.getMeta().name());
		addChildWithValue(element, SHIFT, keyCombination.getShift().name());
		addChildWithValue(element, KEY, keyCombination.getCode().name());
		return element;
	}

	private void addChildWithValue(Element targetElement, XMLNode childNode, String childValue){
		targetElement.appendChild(document.createElement(childNode.getNodeName())).appendChild(document.createTextNode(childValue));
	}
}
