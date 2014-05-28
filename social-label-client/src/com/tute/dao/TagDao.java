package com.tute.dao;

import java.util.ArrayList;
import java.util.List;

import com.tute.sharedDB.MySQLiteHelper;
import com.tute.userbean.Tag;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class TagDao {
	private MySQLiteHelper sqlhelper;
	public TagDao(Context context){
		sqlhelper=new MySQLiteHelper(context);		
	}
	/**
	 * 添加用户对应的标签，每次添加一条标签
	 * @param email
	 * @param tagname
	 */
	public void addTag(String email,String tagname){
		SQLiteDatabase db=sqlhelper.getWritableDatabase();
		db.execSQL("insert into tags(email,tagname)values(?,?)",new Object[]{email,tagname});
		db.close();
	}
	/**
	 * 更改标签对应的主题
	 * @param tagname 标签名
	 * @param theme	标签对饮的主题名
	 */
	public void updateTagTheme(String tagname,String theme){
		SQLiteDatabase db=sqlhelper.getWritableDatabase();	
		
		db.execSQL("update tags set theme=? where tagname=?", new Object[]{theme,tagname});
		System.out.println("插入数据");
		db.close();
	}
	/**
	 * 查询对应email所有的标签
	 * @param email
	 * @return
	 */
	public List<Tag> findAlltag(String email){
		SQLiteDatabase db=sqlhelper.getReadableDatabase();
		Cursor cursor=db.rawQuery("select tagname from tags where email=?",new String[]{email} );
		List<Tag> tags=new ArrayList<Tag>();
		while(cursor.moveToNext()){
			Tag t=new Tag();
			t.setTagname(cursor.getString(cursor.getColumnIndex("tagename")));
			tags.add(t);
		}		
		db.close();
		return tags;
	}
}
