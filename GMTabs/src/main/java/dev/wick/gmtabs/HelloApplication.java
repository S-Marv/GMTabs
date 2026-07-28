package dev.wick.gmtabs;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Arrays;

public class HelloApplication extends Application {
	private static boolean telemetry=false;
	@Override
	public void start(Stage stage) {
		Scene scene = new Scene(new Pane(new Label("Test")), 320, 240);
		stage.setTitle("Hello!" + telemetry);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		System.out.println(Arrays.toString(args));
		telemetry = args.length>0;
		launch();
	}
}