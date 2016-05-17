package com.cjburkey.comprazip;

import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;
import com.cjburkey.comprazip.settings.Settings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class Scenes {
	
	private static boolean init = false;
	
	public static Scene homeScene;
	public static Scene viewScene;
	public static Scene settingsScene;
	
	public static final int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
	public static final int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
	
	public static final ListView<ZFile> list = new ListView<ZFile>();
	public static final Button upLevel = new Button("^");
	
	public static final void init() {
		if(init) return;
		init = true;
		
		homeScene();
		viewScene();
		settingsScene();
	}
	
	private static final void homeScene() {
		VBox root = defaultRoot();
		homeScene = new Scene(root);
		Button openZip = new Button("Open File");
		Button settings = new Button("Settings");
		
		root.getChildren().addAll(openZip, settings);
		
		settings.setOnAction(e -> {
			CompraZip.setScene(settingsScene);
		});
		
		openZip.setOnAction(e -> {
			showFileChooser();
		});
	}
	
	private static final void settingsScene() {
		VBox root = defaultRoot();
		settingsScene = new Scene(root);
		Button back = new Button("Back");
		
		CheckBox useDarkTheme = new CheckBox("Use Dark theme");
		boolean theme = Boolean.parseBoolean(Settings.get("useDarkTheme", "false"));
		useDarkTheme.setSelected(theme);
		
		root.getChildren().addAll(useDarkTheme, back);
		
		back.setOnAction(e -> { CompraZip.setScene(homeScene); });
		
		useDarkTheme.setOnAction(e -> { Settings.set("useDarkTheme", "" + useDarkTheme.isSelected()); });
	}
	
	private static final void viewScene() {
		VBox root = defaultRoot();
		viewScene = new Scene(root);
		HBox buttons = defaultHBox();
		Button newZip = new Button("Open File");
		Button home = new Button("Close File");
		
		buttons.getChildren().addAll(upLevel, newZip, home);
		root.getChildren().addAll(buttons, list);
		
		newZip.setOnAction(e -> {
			showFileChooser();
		});
		
		home.setOnAction(e -> {
			CompraZip.setScene(homeScene);
		});
		
		upLevel.setOnAction(e -> {
			Util.currentDir = Util.getCurrentParent();
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
		ExtensionFilter zipFilter = new ExtensionFilter("Compressed Files (*.zip, *.jar, *.rar)", new ArrayList<String>(){
			private static final long serialVersionUID = -1252262948127098519L;
			{
				add("*.zip");
				add("*.jar");
				add("*.rar");
		}});
		
		ch.setTitle("Choose Zip");
		ch.setInitialDirectory(new File(System.getProperty("user.home")));
		ch.getExtensionFilters().add(zipFilter);
		File f = ch.showOpenDialog(CompraZip.getStage());
		if(f != null) {
			try {				
				Util.files = null;
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