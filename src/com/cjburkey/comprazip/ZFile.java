package com.cjburkey.comprazip;

public class ZFile {
	
	private String path;
	private boolean dir;

	public ZFile(String pathname, boolean dir) {
		this.path = pathname;
		this.dir = dir;
	}
	
	public boolean isDirectory() {
		return this.dir;
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