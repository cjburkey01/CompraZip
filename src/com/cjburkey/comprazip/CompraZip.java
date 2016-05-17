package com.cjburkey.comprazip;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.zip.ZipEntry;
import com.cjburkey.comprazip.settings.Settings;
import com.cjburkey.comprazip.zip.ZipLoader;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CompraZip extends Application {
	
	private static Stage s;
	
	public void start(Stage stage) {
		s = stage;
		
		stage.setTitle("CompraZip");
		setScene(Scenes.homeScene);
		//setScene(Scenes.viewScene);
		stage.sizeToScene();
		stage.setResizable(false);
		stage.centerOnScreen();
		stage.show();
		stage.setWidth(Scenes.screenWidth / 2);
		stage.setHeight(Scenes.screenHeight / 2);
		stage.centerOnScreen();
	}
	
	public static void main(String[] args) {
		Thread.setDefaultUncaughtExceptionHandler((t, e) -> error(e) );
		
		Scenes.init();
		launch(args);
	}
	
	public static final void setScene(Scene scene) {
		if(!Boolean.parseBoolean(Settings.get("useDarkTheme", "false"))) {
			scene.getStylesheets().add("css/JMetroLightTheme.css");
		} else {
			scene.getStylesheets().add("css/JMetroDarkTheme.css");
		}
		s.setScene(scene);
	}
	
	public static final void viewScene() {
		try {
			if(Util.files == null) {
				Util.files = new ArrayList<ZFile>();
				for(ZipEntry e : ZipLoader.getZipEntries(Util.zip)) {
					Util.files.add(new ZFile(e.toString(), e.isDirectory(), e));
				}
			}
			
			ObservableList<ZFile> file = FXCollections.observableArrayList();
			Comparator<? super ZFile> comparator = new Comparator<ZFile>() { public int compare(ZFile in, ZFile in2) { return in.toString().compareTo(in2.toString()); } };
			for(ZFile f : Util.files) {
				if(f.getParent().trim().equals(Util.currentDir.trim())) file.add(f);
			}
			SortedList<ZFile> sort = new SortedList<ZFile>(file);
			sort.setComparator(comparator);
			Scenes.list.setItems(sort);
			Scenes.upLevel.setDisable(Util.currentDir == Util.getCurrentParent());
			setScene(Scenes.viewScene);
		} catch (Exception e) {
			error(e);
		}
	}
	
	public static final void error(Throwable e) {
		System.err.println("AN ERROR OCCURRED!");
		System.err.println("--[BEGIN ERR]--");
		e.printStackTrace();
		System.err.println("--[END ERR]--");
		
		Platform.exit();
	}
	
	public static final Stage getStage() { return s; }
	
}