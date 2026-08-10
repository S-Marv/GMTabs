package dev.wick.gmtabs;

import dev.wick.gmtabs.view.GmTabPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HelloApplication extends Application {
	private final static String TELEMETRY_ADDENDUM = " (Beta Build)";
	private static boolean telemetry = false;
	private static String title = "GMTabs v";

	@Override
	public void start(Stage stage) {
		Scene scene = new Scene(new GmTabPane(new File("./tabs.xml"), true), 1000, 720);
		if(telemetry) title += TELEMETRY_ADDENDUM;
		stage.setTitle(title);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		telemetry = args.length > 1;
		title+= getVersion(args[0]);
		launch();
	}

	public static String getVersion(String versionString){
		versionString = versionString.strip();
		Matcher matcher = Pattern.compile("^\\d+(\\.\\d)+$").matcher(versionString);
		if(!matcher.find()) throw new RuntimeException("Version # invalid");
		return versionString;
	}
}