package dev.wick.gmtabs.tab;

import javafx.scene.paint.Color;

import java.io.Serializable;

public record TabGraphic(String iconPath, Color color) implements Serializable {
	public static final TabGraphic EMPTY_GRAPHIC = new TabGraphic(null, Color.TRANSPARENT);

	public TabGraphic(String iconPath, Color color) {
		this.iconPath = iconPath;
		this.color = color != null && color.getOpacity()==Color.TRANSPARENT.getOpacity()? Color.TRANSPARENT : color;
	}

	@Override
	public String toString() {
		return "TabGraphic{" +
				"iconPath='" + iconPath + '\'' +
				", color=" + color +
				'}';
	}
}