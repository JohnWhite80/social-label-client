package com.tute.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class ToList {
	public static List<HashMap<String, Object>> JSONToList(String string) {
		try {
			JSONArray Data_jsonArray = new JSONArray(string);
			if (!Data_jsonArray.isNull(0)) {
				JSONObject jobj = Data_jsonArray.getJSONObject(0);
				
				ArrayList keys = new ArrayList();
				int keys_posi = 0;
				Iterator<String> IT = jobj.keys();
				while (IT.hasNext()) {
					keys.add(keys_posi, IT.next());
					keys_posi++;
				}
				List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
				for (int i = 0; i < Data_jsonArray.length(); i++) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					for (int j = 0; j < keys.size(); j++) {
						map.put(keys.get(j).toString(),
								Data_jsonArray.getJSONObject(i).getString(
										keys.get(j).toString()));
					}
					list.add(map);
				}
				return list;

			}
			return null;

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("json", "json转list出错.");
			return null;
		}
	}
	public static List<HashMap<String, String>> StringToList(String string) {
		try {
			JSONArray Data_jsonArray = new JSONArray(string);
			if (!Data_jsonArray.isNull(0)) {
				JSONObject jobj = Data_jsonArray.getJSONObject(0);
				
				ArrayList keys = new ArrayList();
				int keys_posi = 0;
				Iterator<String> IT = jobj.keys();
				while (IT.hasNext()) {
					keys.add(keys_posi, IT.next());
					keys_posi++;
				}
				List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
				for (int i = 0; i < Data_jsonArray.length(); i++) {
					HashMap<String, String> map = new HashMap<String, String>();
					for (int j = 0; j < keys.size(); j++) {
						map.put(keys.get(j).toString(),
								Data_jsonArray.getJSONObject(i).getString(
										keys.get(j).toString()));
					}
					list.add(map);
				}
				return list;

			}
			return null;

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("json", "String转list出错.");
			return null;
		}
	}
}
