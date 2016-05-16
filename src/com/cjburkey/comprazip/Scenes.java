package com.cjburkey.comprazip;

import java.awt.Toolkit;
import java.io.File;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class Scenes {
	
	private static boolean init = false;
	
	public static Scene homeScene;
	public static Scene viewScene;
	
	public static final int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
	public static final int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
	
	public static final ListView<ZFile> list = new ListView<ZFile>();
	
	public static final void init() {
		if(init) return;
		init = true;
		
		homeScene();
		viewScene();
	}
	
	private static final void homeScene() {
		VBox root = defaultRoot();
		homeScene = new Scene(root);
		Button openZip = new Button("Open File");
		
		root.getChildren().add(openZip);
		
		openZip.setOnAction(e -> {
			showFileChooser();
		});
	}
	
	private static final void viewScene() {
		VBox root = defaultRoot();
		viewScene = new Scene(root);
		HBox buttons = defaultHBox();
		Button upLevel = new Button("^");
		
		buttons.getChildren().addAll(upLevel);
		root.getChildren().addAll(buttons, list);
		
		upLevel.setOnAction(e -> {
			Util.currentDir = new ZFile(Util.currentDir, true).getParent();
			CompraZip.viewScene();
		});
		
		list.setOnMouseClicked(e -> {
			ZFile selected = list.getSelectionModel().getSelectedItem();
			if(e.getClickCount() == 2 && selected != null && selected.isDirectory()) {
				Util.currentDir = selected.toString();
				CompraZip.viewScene();
			}
		});
	}
	
	private static final void showFileChooser() {
		FileChooser ch = new FileChooser();
		ExtensionFilter zipFilter = new ExtensionFilter("Zip Files (*.zip)", "*.zip");
		
		ch.setTitle("Choose Zip");
		ch.setInitialDirectory(new File(System.getProperty("user.home")));
		ch.getExtensionFilters().add(zipFilter);
		File f = ch.showOpenDialog(CompraZip.getStage());
		if(f != null) {
			try {
				Util.zip = f;
				CompraZip.viewScene();
			} catch (Exception e1) {
				CompraZip.error(e1);
			}
		}
	}
	
	private static final VBox defaultRoot() {
		VBox root = new VBox();
		root.setAlignment(Pos.CENTER);
		root.setPadding(new Insets(10f));
		root.setSpacing(10);
		
		return root;
	}
	
	private static final HBox defaultHBox() {
		HBox root = new HBox();
		root.setAlignment(Pos.CENTER);
		root.setPadding(new Insets(10f));
		root.setSpacing(10);
		
		return root;
	}
	
}