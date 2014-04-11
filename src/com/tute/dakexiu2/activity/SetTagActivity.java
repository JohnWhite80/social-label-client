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

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dakexiu2.R;
import com.tute.sharedDB.LocalStorage;
public class SetTagActivity extends BasicActivity {
	private EditText tag1,tag2,tag3,tag4,tag5;
	private Button login;
	 String mStrResult;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_set_tag);
		init();
		}

	private void init() {
		// TODO Auto-generated method stub
		tag1=(EditText)findViewById(R.id.tag_1);
		tag2=(EditText)findViewById(R.id.tag_2);
		tag3=(EditText)findViewById(R.id.tag_3);
		tag4=(EditText)findViewById(R.id.tag_4);
		tag5=(EditText)findViewById(R.id.tag_5);
		
		login=(Button)findViewById(R.id.set_tag_login);
		login.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String t1=tag1.getText().toString().trim();
				String t2=tag2.getText().toString().trim();
				String t3=tag3.getText().toString().trim();
				String t4=tag4.getText().toString().trim();
				String t5=tag5.getText().toString().trim();
				String userID=LocalStorage.getString(SetTagActivity.this, "userE_mail");
				if(t1!=null&&t2!=null){
					if(checkNetworkState()){
						//将所有数据提交到服务端，服务端过滤是否为空
						String url="http://10.0.2.2:8080/server/api/addtag";
						SetTagTask task=new SetTagTask();
						task.execute(url,userID,t1,t2,t3,t4,t5);
					}else{
						Toast.makeText(SetTagActivity.this, "您的网络为链接！请设置网络", Toast.LENGTH_LONG).show();
					}			
									
				}else{
					AlertDialog.Builder builder = new Builder(SetTagActivity.this);				 
					 builder.setMessage("亲~标签还未填完哦！");
					 builder.setTitle("提示ʾ");
					 builder.setPositiveButton("确认", new OnClickListener() {						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					}).show();
				}
			}
		});
	}
	private class SetTagTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			HttpClient hc = new DefaultHttpClient();
			HttpPost hp = new HttpPost(params[0]);
			JSONObject jsonObj = new JSONObject();
			String getResult=null;
			try {
				jsonObj.put("email", params[1]);
				jsonObj.put("tag1", params[2]);
				jsonObj.put("tag2", params[3]);
				jsonObj.put("tag3", params[4]);
				jsonObj.put("tag4", params[5]);
				jsonObj.put("tag5", params[6]);				
				hp.setEntity(new StringEntity(jsonObj.toString()));
				HttpResponse response = hc.execute(hp);				
				if (response.getStatusLine().getStatusCode() == 200) {					
					mStrResult = EntityUtils.toString(response.getEntity());					
					JSONObject result = new JSONObject(mStrResult);			
					getResult=result.getString("addtag");
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
		@Override
		protected void onPostExecute(String result) {
			if("add success".equals(result)){
				Intent intent=new Intent(SetTagActivity.this,MainActivity.class);
				startActivity(intent);
			}					
		}
	}
}
