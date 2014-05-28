package com.tute.dao;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.tute.sharedDB.MySQLiteHelper;
import com.tute.userbean.User;

public class UserDao {
	private MySQLiteHelper sqlhelper;
	//构造方法，对数据库进行初始化
	public UserDao(Context context){
		sqlhelper=new MySQLiteHelper(context);		
	}
	/**
	 * adduser 方法添加一个用户 参数为要添加用户的信息
	 * 该方法用于注册时使用
	 * @param email
	 * @param password
	 * @param nikename
	 */
	public void addUser(String email,String password,String nikename){
		SQLiteDatabase db=sqlhelper.getWritableDatabase();
		db.execSQL("insert into usermessage(email,password,nikename)values(?,?,?)"
				,new Object[]{email,password,nikename});
		db.close();
	}
	/**
	 * 用来更新  完善信息页面数据
	 * @param email
	 * @param sex
	 * @param birthday
	 * @param city
	 * @return ture表示数据更新成功
	 */
	public boolean updateComplete(String email,String sex,String birthday,String city){
		SQLiteDatabase db=sqlhelper.getWritableDatabase();
		db.execSQL("update usermassege set sex=?,birthday=?,city=? where email=?",
				new String[]{sex,birthday,city,email});
		db.close();
		return true;
	}
	/**
	 * 取得用户对应的密码
	 * @param email
	 * @return
	 */
	public String getPassord(String email){
		SQLiteDatabase db=sqlhelper.getReadableDatabase();
		Cursor cursor=db.rawQuery("select password from usermessage where email=?", new String[]{email});
		String password=null;
		while(cursor.moveToNext()){
			password=cursor.getString(cursor.getColumnIndex("password"));
		}
		return password;
	}
	/**
	 * findone 根据邮箱 查找一个用户信息
	 * @param email 要查找的用户
	 * @return
	 */
	public User findOne(String email){
		SQLiteDatabase db=sqlhelper.getReadableDatabase();
		//查找用户
		Cursor cursor=db.rawQuery("select * from usermessage where email=?", new String[]{email});
		User user = null;
		while(cursor.moveToNext()){
			String password=cursor.getString(cursor.getColumnIndex("password"));
			String nikename=cursor.getString(cursor.getColumnIndex("nikename"));
			String birthday =cursor.getString(cursor.getColumnIndex("birthday"));
			String sex=cursor.getString(cursor.getColumnIndex("sex"));
			String city=cursor.getString(cursor.getColumnIndex("city"));
			String tags=cursor.getString(cursor.getColumnIndex("tags"));
			user=new User(email, password, nikename, birthday, sex, city);
//			user=new User(email, password, nikename, null, null, null);
		}	
		db.close();		
		return user;		
	}
	/**
	 * 根据用户的email,更改用户的昵称
	 * @param email 要更新的用户
	 * @return
	 */
	public boolean updateNikename(String email,String nikeName){
		
	
		return true;
	}
	/**
	 * 根据用户的email，更改性别
	 * @return
	 */
	public boolean updateSex(String email,String sex){
		
		return true;
	}
	/**
	 * 根据用户的email，更改城市
	 * @return
	 */
	public boolean updateCity(String email,String city){
		return true;		
	}
	/**
	 * 根据用户的email，更改生日
	 * @return
	 */
	public boolean updateBirthday(String email,String birthday){
		
		return true;
	}	
	
	//findAll 查找数据库中所有的用户 具体哪些功能会用到这个方法未知
	public List<User> findAll(){
		SQLiteDatabase db=sqlhelper.getReadableDatabase();
		List<User> list=new ArrayList<User>();
		//将查找到的所有用户添加到list集合中
		db.close();				
		return list;
	}

}
