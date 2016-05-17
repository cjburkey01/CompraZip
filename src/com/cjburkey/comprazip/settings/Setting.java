package com.cjburkey.comprazip.settings;

import java.io.Serializable;

public class Setting implements Serializable {
	
	private static final long serialVersionUID = -8378250320136465769L;
	
	private String setting;
	private String value;
	
	public Setting(String n, String v) {
		this.setting = n;
		this.value = v;
	}
	
	public String getSetting() {
		return this.setting;
	}
	
	public String getValue() {
		return this.value;
	}
	
	public void setValue(String v) {
		this.value = v;
	}
	
	public boolean same(Setting s) {
		return s.getSetting().equals(this.getSetting());
	}
	
}