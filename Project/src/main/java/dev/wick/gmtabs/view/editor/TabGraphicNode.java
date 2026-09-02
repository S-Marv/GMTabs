package dev.wick.gmtabs.view.editor;

import dev.wick.gmtabs.tab.TabConfig;
import dev.wick.gmtabs.tab.TabGraphic;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class TabGraphicNode extends VBox {
	private final static int LABEL_HEIGHT = 10;
	public final static int TAB_WIDTH = 50;
	public final static int TAB_HEIGHT = TAB_WIDTH + LABEL_HEIGHT;
	public static final Image ICON = new Image(Objects.requireNonNull(TabGraphicNode.class.getResourceAsStream("icon.png")));

	private final ImageView icon = new ImageView();
	private final Label keybinding = makeKeybindDisplay();

	public TabGraphicNode(TabConfig tabConfig) {
		System.out.println("TEST");
		init();
		if(tabConfig!=null) setConfig(tabConfig);
	}

	private void init(){
		getChildren().addAll(keybinding, icon);
		setMaxHeight(TAB_HEIGHT);
		setMaxWidth(TAB_WIDTH);
		setAlignment(Pos.TOP_CENTER);
	}

	private Label makeKeybindDisplay() {
		Label keybinding = new Label();
		keybinding.setFont(Font.font(15));
		keybinding.setMaxWidth(TAB_WIDTH);
		keybinding.setMaxHeight(LABEL_HEIGHT);
		keybinding.setAlignment(Pos.CENTER);
		keybinding.setWrapText(true);
		return keybinding;
	}

	private void formatIcon(String imagePath) {
		if(imagePath!=null){
			File file = new File(imagePath);
			String uri = file.toURI().toString();
			icon.setImage(new Image(uri));
		}
		else{
			icon.setImage(ICON);
		}
		icon.setFitHeight(TAB_WIDTH);
		icon.setFitWidth(TAB_WIDTH);
	}

	public void setConfig(TabConfig config){
		TabGraphic tabGraphic = config.getTabGraphic();
		KeyCodeCombination combination = config.getKeyCombination();
		keybinding.setText(combination==null? "" : combination.getName());
		formatIcon(tabGraphic.iconPath());
		setColors(tabGraphic.color());
	}

	private void setColors(Color background) {
		setBackground(Background.fill(background));
		boolean useDarkText = false;
		if(background.getOpacity() < 0.5) {
			useDarkText = true;
		} else {
			for(double value : new double[]{background.getRed(), background.getBlue(), background.getGreen()}){
				if (value > 0.7) {
					useDarkText = true;
					break;
				}
			}
		}
		Color textColor = useDarkText ? Color.BLACK : Color.WHITE;
		keybinding.setTextFill(textColor);
	}
}
