package com.tute.dakexiu2.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

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

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ForgetPaswActivity extends BasicActivity {
	private TextView prompt;
	private EditText email;
	private Button getpasw;	
	String mStrResult;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forgetpassword);
		prompt=(TextView)findViewById(R.id.tv_addressforget);
		email=(EditText)findViewById(R.id.et_getemail);
		getpasw=(Button)findViewById(R.id.bt_getpasw);
		getpasw.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String address=email.getText().toString().trim();
				String format = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
				if(!address.matches(format)){
					prompt.setText("输入邮箱格式有误");
				}else{
					if(checkNetworkState()){
						String url = "http://10.0.2.2:8080/server/api/";
						ForgetTask task=new ForgetTask();
						task.execute(url,address);
					}else{
						Toast.makeText(ForgetPaswActivity.this,"您的网络未连接！请先设置网络", Toast.LENGTH_LONG).show();
					}					
				}
			}
		});
	}	
	private class ForgetTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			HttpClient hc = new DefaultHttpClient();		
			HttpPost hp = new HttpPost(params[0]);
			JSONObject jsonObj = new JSONObject();
			String getResult=null;
			try {				
				jsonObj.put("e_mail", params[1]);				
				hp.setEntity(new StringEntity(jsonObj.toString()));				
				HttpResponse response = hc.execute(hp);				
				if (response.getStatusLine().getStatusCode() == 200) {					
					mStrResult = EntityUtils.toString(response.getEntity());					
					JSONObject result = new JSONObject(mStrResult);					
					getResult=result.getString("retrievepwd");

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
			if("retrievepwd success".equals(result)){			
				 AlertDialog.Builder builder = new Builder(ForgetPaswActivity.this);				 
				 builder.setMessage("新密码已发送至您的邮箱，请重新登录");
				 builder.setTitle("提示");
				 builder.setPositiveButton("确认", new OnClickListener() {					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				}).show();
			}else{
				Toast.makeText(ForgetPaswActivity.this, "该邮箱未注册！", Toast.LENGTH_LONG).show();
			}
		}
	}
}
