package dev.wick.gmtabs.view.editor;

import dev.wick.gmtabs.tab.TabConfig;
import dev.wick.gmtabs.tab.TabContent;
import dev.wick.gmtabs.tab.TabGraphic;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.function.Consumer;

class EditorForm {
	private static final TabConfig EMPTY_CONFIG = new TabConfig(new TabContent(false, ""), null);

	private final StringProperty webPath = new SimpleStringProperty();
	private final StringProperty filePath = new SimpleStringProperty();

	private final BooleanProperty isFileProperty = new SimpleBooleanProperty(false);

	private final StringProperty iconPath = new SimpleStringProperty();
	private final ObjectProperty<Color> graphicColor = new SimpleObjectProperty<>();

	private final ObjectProperty<KeyCodeCombination> keybinding = new SimpleObjectProperty<>();


	EditorForm(Consumer<Boolean> onUpdate, TabConfig oldConfig){
		if(oldConfig!=null) set(oldConfig);
		Consumer<Boolean> finalOnUpdate = onUpdate==null? _->{} : onUpdate ;
		ChangeListener<? super Object> listener = (_, _, _)-> finalOnUpdate.accept(isValid());
		for(ObservableValue<?> observable : List.of(
				webPath.isEmpty(), isFileProperty, keybinding)){
			observable.addListener(listener);
		}
		finalOnUpdate.accept(isValid());
	}

	void set(TabConfig config){
		if(config==null) config=EMPTY_CONFIG;
		isFileProperty.set(config.isContentAFile());
		getSelectedPath().set(config.getContentPath());
		TabGraphic graphic = config.getTabGraphic();
		iconPath.set(graphic.iconPath());
		graphicColor.set(graphic.color());
		keybinding.set(config.getKeyCombination());
	}

	/**
	 * @return {@code true} if the keybinding and whichever path defined by {@code getSelectedPath} are both defined. False otherwise.
	 */
	boolean isValid(){
		return !getSelectedPath().isEmpty().get() && keybinding.get()!=null;
	}

	StringProperty getSelectedPath(){
		return isFileProperty.get() ? filePath : webPath;
	}

	/**
	 * @return exact same as getTabConfig, but checks if the form is valid before returning.
	 */
	TabConfig constructConfig(){
		if(!isValid()) throw IncompleteFormException.create(this);
		return getTabConfig();
	}

	/**
	 * @return the TabConfig from the form.
	 */
	TabConfig getTabConfig() {
		boolean isFile = isFileProperty.get();
		TabContent content = new TabContent(isFile, getSelectedPath().get());
		TabGraphic graphic = new TabGraphic(iconPath.get(), graphicColor.get());
		return new TabConfig(graphic, content, keybinding.get());
	}

	public ObjectProperty<Color> getGraphicColor() {
		return graphicColor;
	}

	public ObjectProperty<KeyCodeCombination> getKeybinding() {
		return keybinding;
	}

	public StringProperty getFilePath() {
		return filePath;
	}

	public StringProperty getIconPath() {
		return iconPath;
	}

	public StringProperty getWebPath() {
		return webPath;
	}

	public BooleanProperty isFilePropertyProperty() {
		return isFileProperty;
	}
}
