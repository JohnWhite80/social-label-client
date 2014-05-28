package com.tute.sharedDB;

import android.content.Context;
import android.content.SharedPreferences;

public class LocalStorage {//SharedPreferences数据共享
	private static SharedPreferences getSharedPreferences(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"userInfo", Context.MODE_WORLD_READABLE);
		return sharedPreferences;
	}
//将数据以key value的形式存储
	public static void saveString(Context context, String key, String value) {
		SharedPreferences sharedPreferences = getSharedPreferences(context);
		sharedPreferences.edit().putString(key, value).commit();

	}
//取得和key相对应的的value
	public static String getString(Context context, String key) {
		return getSharedPreferences(context).getString(key, "");
	}
	//这里存储的是布尔型的值
	public static void saveBoolean(Context context, String key, Boolean value){
		SharedPreferences sharedPreferences = getSharedPreferences(context);
		sharedPreferences.edit().putBoolean(key, value).commit();
	}
	public static boolean getBoolean(Context context, String key){
		//默认是true
		return getSharedPreferences(context).getBoolean(key, true);
	}
}
