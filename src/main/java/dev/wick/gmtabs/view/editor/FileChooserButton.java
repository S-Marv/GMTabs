package dev.wick.gmtabs.view.editor;

import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;

import java.io.File;

public class FileChooserButton extends Button {
	private static final String DEFAULT_TEXT = "Choose File";
	private static final File DEFAULT_DIR = new File(System.getProperty("user.home"));

	private final FileChooser fileChooser = new FileChooser();

	public FileChooserButton(StringProperty fileProperty){
		displayFilePath(fileProperty.get());
		fileProperty.addListener((_,_,newValue)-> displayFilePath(newValue));
		setOnAction((_)->{
			fileChooser.setInitialDirectory(getInitialDir(fileProperty.get()));
			File file = fileChooser.showOpenDialog(null);
			String path = file==null? null : file.getAbsolutePath();
			fileProperty.set(path);
		});
	}

	private void displayFilePath(String path){
		setText(path==null? DEFAULT_TEXT : path);
	}

	private static File getInitialDir(String oldPath){
		if(oldPath == null) return DEFAULT_DIR;
		File oldFile = new File(oldPath);
		return oldFile.exists() ? oldFile.getParentFile() : DEFAULT_DIR;
	}

}
