package com.cjburkey.comprazip.zip;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import com.cjburkey.comprazip.Util;

public class ZipLoader {
	
	public static final ArrayList<ZipEntry> getZipEntries(File zip) throws Exception {
		Util.totalFiles = 0;
		ArrayList<ZipEntry> list = new ArrayList<ZipEntry>();
		ZipFile file = new ZipFile(zip);
		Enumeration<? extends ZipEntry> entries = file.entries();
		while(entries.hasMoreElements()) {
			ZipEntry entry = entries.nextElement();
			list.add(entry);
			System.out.println("Loaded file: " + entry.toString());
			Util.totalFiles ++;
		}
		System.out.println(Util.totalFiles + " files loaded.");
		file.close();
		return list;
	}
	
}