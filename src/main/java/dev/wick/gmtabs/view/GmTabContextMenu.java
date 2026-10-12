package dev.wick.gmtabs.view;

import dev.wick.gmtabs.tab.TabConfig;
import dev.wick.gmtabs.view.editor.Editor;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;

import java.util.Optional;

public class GmTabContextMenu extends ContextMenu {
	private final Tab parentTab;
	private final Editor editor;
	private final MenuItem deleteItem;

	GmTabContextMenu(Tab parent, EventHandler<ActionEvent> onEditButtonPressed, Editor editor){
		this.parentTab = parent;
		this.editor = editor;
		MenuItem openItem = makeItem("Open", _->parent.getTabPane().getSelectionModel().select(parentTab));
		MenuItem addTabItem = makeItem("Add Tab", _->showEmptyEditor());
		MenuItem editItem = makeItem("Edit", onEditButtonPressed);
		deleteItem = makeItem("Delete", _ -> parent.getTabPane().getTabs().remove(parentTab));
		getItems().addAll(openItem, addTabItem, editItem, deleteItem);
	}

	private MenuItem makeItem(String text, EventHandler<ActionEvent> eventHandler){
		MenuItem item = new MenuItem(text);
		item.setOnAction(eventHandler);
		return item;
	}

	private void showEmptyEditor(){
		Optional<TabConfig> result = editor.showAndWait(null);
		result.ifPresent(config -> {
			GmTab newTab = new GmTab(config, true, editor);
			parentTab.getTabPane().getTabs().add(newTab);
		});
	}

	@Override
	protected void show() {
		deleteItem.setDisable(parentTab.getTabPane().getTabs().size()==1);
		super.show();
	}
}
