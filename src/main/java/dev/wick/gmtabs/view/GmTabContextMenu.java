package dev.wick.gmtabs.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;

public class GmTabContextMenu extends ContextMenu {
	private final Tab parentTab;
	private final MenuItem deleteItem;

	GmTabContextMenu(Tab parent, EventHandler<ActionEvent> onEditButtonPressed){
		this.parentTab = parent;
		MenuItem openItem = makeItem("Open", _->parent.getTabPane().getSelectionModel().select(parentTab));
		//MenuItem addTabItem = makeItem("Add Tab", _->tabPane.showNewTabEditor(parentTab));
		MenuItem editItem = makeItem("Edit", onEditButtonPressed);
		deleteItem = makeItem("Delete", _ -> parent.getTabPane().getTabs().remove(parentTab));
		getItems().addAll(openItem, editItem, deleteItem);
	}

	private MenuItem makeItem(String text, EventHandler<ActionEvent> eventHandler){
		MenuItem item = new MenuItem(text);
		item.setOnAction(eventHandler);
		return item;
	}

	@Override
	protected void show() {
		deleteItem.setDisable(parentTab.getTabPane().getTabs().size()==1);
		super.show();
	}
}
