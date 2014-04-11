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
import com.tute.sharedDB.LocalStorage;

import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends BasicActivity implements OnClickListener{
	private TextView tv_err;//提示用户输入
	private EditText et_userName;//用户名
	private EditText et_pasw;//用户密码
	private Button bt_login;//登陆
	private TextView tv_register;//注册
	private TextView tv_forgetpasw;//忘记密码	
	private String mStrResult;//服务端返回结果
	String username,password;//用来传递参数
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        init();//初始化控件 
        String name=LocalStorage.getString(LoginActivity.this, "userE_mail");
        String pwd=LocalStorage.getString(LoginActivity.this,"userPassword");
        //实现自动登陆，链接服务端验证
        if(checkNetworkState()){
        	ConnecteServer(name, pwd);
        }else{
        	et_userName.setText(name);
        	et_pasw.setText(pwd);
        }        
    }
    private void init() {
		// TODO Auto-generated method stub
		tv_err=(TextView)findViewById(R.id.tv_loginErr);
		et_userName=(EditText)findViewById(R.id.et_loginName);
		et_pasw=(EditText)findViewById(R.id.et_loginPasw);
		bt_login=(Button)findViewById(R.id.bt_login);
		bt_login.setOnClickListener(this);
		tv_register=(TextView)findViewById(R.id.register);
		tv_register.setOnClickListener(this);
		tv_forgetpasw=(TextView)findViewById(R.id.forgetpasw);
		tv_forgetpasw.setOnClickListener(this);
	}   
    @Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
    	switch(v.getId()){
    	case R.id.bt_login:
    		login();			//登录方法
    		break;
    	case R.id.register:
    		Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
    		startActivity(intent);
    		finish();
    		break;
    	case R.id.forgetpasw:
    		forgetPasw();		//忘记密码
    		break;
    	}	
	}
	private void forgetPasw() {
		// TODO Auto-generated method stub
		Intent intent=new Intent(LoginActivity.this,ForgetPaswActivity.class);
		startActivity(intent);		
	}
	private void login() {
		// TODO Auto-generated method stub
		username=et_userName.getText().toString().trim();
		password=et_pasw.getText().toString().trim();
		//先检测网络状态，在执行相应的操作
		if(checkNetworkState()){
			if(username==null||password==null){
				return;
			}else{			
				ConnecteServer(username,password);
			}	
		}else{
			Toast.makeText(LoginActivity.this, "您的网络未连接！请先设置网络", Toast.LENGTH_LONG).show();			
		}		
	}
	private void ConnecteServer(String name,String pwd) {
		// TODO Auto-generated method stub
		String url = "http://10.0.2.2:8080/server/api/logging";
		LoginTask task = new LoginTask();
		task.execute(url,name,pwd);
	}
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }
	private class LoginTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			HttpClient hc = new DefaultHttpClient();
			HttpPost hp = new HttpPost(params[0]);
			JSONObject jsonObj = new JSONObject();
			String getResult=null;
			try {
				jsonObj.put("name", params[1]);
				jsonObj.put("password", params[2]);
				hp.setEntity(new StringEntity(jsonObj.toString()));
				HttpResponse response = hc.execute(hp);				
				if (response.getStatusLine().getStatusCode() == 200) {					
					mStrResult = EntityUtils.toString(response.getEntity());					
					JSONObject result = new JSONObject(mStrResult);			
					getResult=result.getString("login");
					System.out.print(getResult);
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
			if("login success".equals(result)){
				LocalStorage.saveString(LoginActivity.this, "userE_mail", username);
				LocalStorage.saveString(LoginActivity.this, "userPassword", password);
				
				Intent intent=new Intent(LoginActivity.this,MainActivity.class);
				startActivity(intent);
			}else{
				tv_err.setText("用户名或密码错误！");
			}
		}
	}
}
