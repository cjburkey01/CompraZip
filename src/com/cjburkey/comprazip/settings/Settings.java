package com.cjburkey.comprazip.settings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import com.cjburkey.comprazip.CompraZip;

public final class Settings {
	
	public static final File progDir = new File(System.getProperty("user.home") + File.separator + "comprazip" + File.separator);
	public static final File settingDir =  new File(progDir, File.separator + "settings" + File.separator);
	public static final File settingFile = new File(settingDir, File.separator + "settings.dat");
	
	public static final void set(String setting, String value) {
		ArrayList<Setting> settings = lineToSettings(getFile(settingFile));
		if(settings == null) {
			settingDir.mkdirs();
			settings = new ArrayList<Setting>();
			System.out.println("Creating settings file.");
		} else {
			for(Setting s : settings) {
				if(s.getSetting().equals(setting)) {
					s.setValue(value);
					writeSettings(settingFile, settings);
					System.out.println("Set " + setting + " to " + value);
					return;
				}
			}
		}
		settings.add(new Setting(setting, value));
		writeSettings(settingFile, settings);
	}
	
	public static final String get(String setting, String def) {
		ArrayList<Setting> settings = lineToSettings(getFile(settingFile));
		if(settings != null) {
			for(Setting s : settings) {
				if(s.getSetting().equals(setting)) {
					return s.getValue();
				}
			}
		}
		set(setting, def);
		return def;
	}
	
	private static final ArrayList<Setting> lineToSettings(ArrayList<String> list) {
		ArrayList<Setting> s = new ArrayList<Setting>();
		if(list != null) {
			for(String line : list) {
				String[] split = line.split("\t");
				s.add(new Setting(split[0].trim(), split[1].trim()));
			}
			return s;
		} else {
			return null;
		}
	}
	
	private static final ArrayList<String> getFile(File f) {
		if(f.exists()) {
			try {
				ArrayList<String> lines = new ArrayList<String>();
				BufferedReader r = new BufferedReader(new FileReader(f));
				String l;
				while((l = r.readLine()) != null) {
					lines.add(l.trim());
				}
				r.close();
				return lines;
			} catch(Exception e) {
				CompraZip.error(e);
			}
		}
		return null;
	}
	
	private static final void writeSettings(File f, ArrayList<Setting> settings) {
		try {
			f.delete();
			FileWriter r = new FileWriter(f, true);
			for(Setting s : settings) {
				r.write(s.getSetting() + "\t" + s.getValue() + "\n");
			}
			r.close();
		} catch(Exception e) {
			CompraZip.error(e);
		}
	}
	
}