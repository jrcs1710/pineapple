package br.senai.sp.cfp132.PineappleWS.util;

import org.json.JSONArray;

public class DataHolder {
	private JSONArray array;
	
	public JSONArray getArray() {
		return array;
	}
	
	public void setArray(JSONArray array){
		this.array = array;
	}

	private static final DataHolder holder = new DataHolder();
	public static DataHolder getInstance(){
		return holder;
	}
}
