package com.tute.sharedDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper {
	private static final int VERSION=1;
	private static final String DBNAME="UserDB";

	public MySQLiteHelper(Context context) {
		//第三个参数是CursorFactory类型,null表示使用默认
		super(context, DBNAME, null, VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		//创建两张表，分别是用户表和标签表
		db.execSQL("create table tags(id integer primary key autoincrement,email text,tagname text unique,theme text);");
		db.execSQL("create table usermessage(id integer primary key autoincrement,email text,password text,nikename text,birthday text,sex text,city text)");
	    
	    System.out.println("创建两张表");
	    	    
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
