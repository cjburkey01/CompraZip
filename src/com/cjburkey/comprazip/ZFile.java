package com.cjburkey.comprazip;

import java.util.zip.ZipEntry;

public class ZFile {
	
	private String path;
	private boolean dir;
	private ZipEntry entry;

	public ZFile(String pathname, boolean dir, ZipEntry entry) {
		this.path = pathname;
		this.dir = dir;
		this.entry = entry;
	}
	
	public boolean isDirectory() {
		return this.dir;
	}
	
	public ZipEntry getEntry() {
		return this.entry;
	}
	
	public String getParent() {
		String temp = ((path.endsWith("/")) ? path.substring(0, path.length() - 1) : path);
		int endIndex = temp.lastIndexOf("/");
		if(endIndex != -1) {
			String fin = temp.substring(0, endIndex) + "/";
			return fin;
		}
		return "";
	}
	
	public String toString() {
		return this.path;
	}
	
}