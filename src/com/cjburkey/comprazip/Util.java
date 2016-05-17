package com.cjburkey.comprazip;

import java.io.File;
import java.util.ArrayList;

public class Util {
	
	public static String currentDir = "";
	public static File zip = null;
	public static int totalFiles = 0;
	public static ArrayList<ZFile> files;
	
	public static final String getCurrentParent() {
		return new ZFile(Util.currentDir, true, null).getParent();
	}
	
}