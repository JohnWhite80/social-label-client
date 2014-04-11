package com.tute.dakexiu2.activity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.dakexiu2.R;
import com.tute.clientHelp.DaKeXiuApplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.PopupMenu;
import android.widget.Toast;

public class MainActivity extends BasicActivity {
	PopupMenu popmenu=null;
	String mStrResult;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		 MenuInflater menuInflater = this.getMenuInflater();  
	     menuInflater.inflate(R.menu.popmenu, menu);  
	     return true;  
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.exit:
			finishApplication();
			break;

		case R.id.cancle:
			reLogin();
			break;
		}

		return super.onOptionsItemSelected(item);
	}
	
	private void reLogin() {
		// TODO Auto-generated method stub
		//应该先断开链接 再进行跳转
		String url="";
		if(checkNetworkState()){
			DisconnectTask task=new DisconnectTask();
			task.execute(url);
		}else{
			Toast.makeText(MainActivity.this, "当前的网络未连接！", Toast.LENGTH_LONG).show();
		}
	}
	private void finishApplication() {
		// TODO Auto-generated method stub
		DaKeXiuApplication app=new DaKeXiuApplication();
		app.exit();
	}
	//该方法实现的是退出的功能
		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			//该方法不会回到桌面（没有home键的效果）
			if (keyCode == KeyEvent.KEYCODE_BACK) {
		        moveTaskToBack(true);
		        return true;
		    }
		    return super.onKeyDown(keyCode, event);		    
		  //实现home键退出
//		    if (keyCode == KeyEvent.KEYCODE_BACK) {
//		        Intent intent = new Intent(Intent.ACTION_MAIN);
//		        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		        intent.addCategory(Intent.CATEGORY_HOME);
//		        startActivity(intent);
//		        return true;
//		    }
//		    return super.onKeyDown(keyCode, event);
		}
		private class DisconnectTask extends AsyncTask<String, Void, String> {

			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				HttpClient hc = new DefaultHttpClient();
				HttpPost hp = new HttpPost(params[0]);
				JSONObject jsonObj = new JSONObject();
				String getResult=null;
				try {
					jsonObj.put("email", params[1]);			
					hp.setHeader("Content-Type", "application/json");
					hp.setEntity(new StringEntity(jsonObj.toString()));				
					HttpResponse response = hc.execute(hp);				
					if (response.getStatusLine().getStatusCode() == 200) {					
						mStrResult = EntityUtils.toString(response.getEntity());					
						JSONObject result = new JSONObject(mStrResult);					
						getResult=result.getString("register");						
					} else {
						System.out.println("连接失败");
					}
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return getResult;
			}
			/* 参数result是doInBackground返回的数值
			 * 并且该方法是在UI线程里执行的
			 */
			@Override
			protected void onPostExecute(String result) {
				if("cancle".equals(result)){
					Intent intent=new Intent(MainActivity.this,LoginActivity.class);
					startActivity(intent);			
				}
			}
		}	

}
