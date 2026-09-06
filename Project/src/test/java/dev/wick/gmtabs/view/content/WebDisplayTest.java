package dev.wick.gmtabs.view.content;

import dev.wick.gmtabs.tab.TabContent;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;

public class WebDisplayTest {
	public static void main(String[] args) throws InterruptedException {
		CountDownLatch latch = new CountDownLatch(1);
		Platform.startup(()-> viewImageDisplay(latch));
		latch.await();
	}


	private static void viewImageDisplay(CountDownLatch latch){
		String path;
		try{
			path = Objects.requireNonNull(WebDisplayTest.class.getResource("Image_Test.html")).getPath();
		} catch (NullPointerException _){
			System.out.println("File not found");
			latch.countDown();
			return;
		}
		WebController controller = new WebController();
		controller.setContent(new TabContent(true,path));
		Stage stage = new Stage();
		Parent parent = new VBox(controller.getWebView(), controller.getHistoryNavigation());
		stage.setScene(new Scene(parent, 600, 600));
		System.out.println(controller.getWebView());
		stage.showAndWait();
		latch.countDown();
	}
}
