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
import com.tute.dao.UserDao;
import com.tute.sharedDB.LocalStorage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends BasicActivity implements OnFocusChangeListener{
	private EditText email,pasw,repasw,	nikename;		
	private ImageView im_email,im_pasw,im_repasw;			
	private TextView email_test,pasw_test,pasw_retest,nikeView;					
	private Button reg_finish;	
	String mStrResult;
	String address,password,repassword,name;
	boolean boolEmail=false,boolPasw=false,boolNikeName=false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		init();			
	}
	private void init() {
		// TODO Auto-generated method stub
		email=(EditText)findViewById(R.id.et_email);
		im_email=(ImageView)findViewById(R.id.im_email);
		email_test=(TextView)findViewById(R.id.email_test);
		pasw=(EditText)findViewById(R.id.et_pasw);
		im_pasw=(ImageView)findViewById(R.id.im_pasw);
		pasw_test=(TextView)findViewById(R.id.pasw_test);
		repasw=(EditText)findViewById(R.id.et_repasw);
		im_repasw=(ImageView)findViewById(R.id.im_repasw);
		pasw_retest=(TextView)findViewById(R.id.pasw_retest);
		nikename=(EditText)findViewById(R.id.et_nikename);
		nikeView=(TextView)findViewById(R.id.nikeTest);
		reg_finish=(Button)findViewById(R.id.finishregist);
		//对几个输入进行焦点监听
		pasw.setOnFocusChangeListener(this);
		repasw.setOnFocusChangeListener(this);
		nikename.setOnFocusChangeListener(this);
		email.setOnFocusChangeListener(this);
		
		reg_finish.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finishRegist();
			}
		});
	}	
	//用户提交注册信息
	protected  void finishRegist() {	
		// TODO Auto-generated method stub
		//先判断用户是否按要求填写信息，再进行提交
		address=email.getText().toString().trim();
		password=pasw.getText().toString().trim();
		repassword=repasw.getText().toString().trim();
		name=nikename.getText().toString().trim();
		if(boolEmail&&boolPasw&&boolNikeName){
			if(checkNetworkState()){
				String url = "http://10.0.2.2:8080/server/api/register1";
				RegisterTask task=new RegisterTask();
				task.execute(url,address,password,name);
			}else{
				Toast.makeText(RegisterActivity.this, "您的网络未连接！请先设置网络", Toast.LENGTH_LONG).show();	
			}			
		}else{
			Toast.makeText(RegisterActivity.this, "请按要求填写信息", Toast.LENGTH_SHORT).show();		
		}
	}
	@Override
	public void onFocusChange(View v, boolean change) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.et_email:
			TestEmail(change);
			break;
		case R.id.et_pasw:
			TestPassword(change);
			break;
		case R.id.et_repasw:
			ReTestPassword(change);
			break;
		case R.id.et_nikename:
			TestNikeName(change);
			break;
		}
	}
	//验证用户输入邮箱
	private boolean TestEmail(boolean right) {
		// TODO Auto-generated method stub
				String format = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
				String address=email.getText().toString().trim();
				int count=0;
				if(address!=null){	
					if(!right){
						count++;
							if(!address.matches(format)){
								email_test.setText("邮箱格式不正确");
								im_email.setImageResource(R.drawable.error);
							}else{
								boolEmail=true;
							}
					}else{
						//让图片不可见
						count++;
						email_test.setText("");
						im_email.setVisibility(ImageView.GONE);
						boolEmail=false;
						if(count%2==1){
							boolEmail=true;
					}
				}					
			}
		return boolEmail;
	}
	//验证用户输入密码位数
	private void TestPassword(boolean right) {
		// TODO Auto-generated method stub
		String password=pasw.getText().toString().trim();
		if(password!=null){
			if(!right){
				if(password.length()<6){
					im_pasw.setImageResource(R.drawable.error);
					pasw_test.setText("密码格式不正确");
				}else{
					im_pasw.setImageResource(R.drawable.right);
					pasw_test.setText("");
				}
			}else{
				im_pasw.setVisibility(ImageView.GONE);
				pasw_test.setText("");
			}
		}
	}
	//验证两次输入是否一致
	private boolean ReTestPassword(boolean right) {
		// TODO Auto-generated method stub
		String repassword=repasw.getText().toString().trim();
		int count=0;
		if(repassword!=null){
			if(!right){
				count++;
				if(repassword.equals(pasw.getText().toString())){
					im_repasw.setImageResource(R.drawable.right);
					pasw_retest.setText("");
					boolPasw=true;
				}else{
					boolPasw=false;
					im_repasw.setImageResource(R.drawable.error);
					pasw_retest.setText("密码不一致");
				}			
			}else{
				count++;
				im_repasw. setVisibility(ImageView.GONE);
				pasw_retest.setText("");
				boolPasw=false;
				if(count%2==1){
					boolPasw=true;
			}
		}
	}
		return boolPasw;
	}
	//验证用户昵称有无超出字数
	private boolean TestNikeName(boolean right) {
		// TODO Auto-generated method stub
		String name=nikename.getText().toString().trim();
		int count=0;
		if(right){
			count++;
			if(name.length()>20){
				Toast.makeText(RegisterActivity.this, "您输入的字符过长", 0).show();
			}else{
				boolNikeName=true;
			}
		}else{
			count++;
			nikeView.setText("");
			boolNikeName=false;
			if(count%2==1){
				boolNikeName=true;
			}
		}
		return boolNikeName;
			
	}
	private class RegisterTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			HttpClient hc = new DefaultHttpClient();
			HttpPost hp = new HttpPost(params[0]);
			JSONObject jsonObj = new JSONObject();
			String getResult=null;
			try {
				jsonObj.put("e_mail", params[1]);
				jsonObj.put("password", params[2]);
				jsonObj.put("nikeName", params[3]);				
				
				hp.setEntity(new StringEntity(jsonObj.toString()));				
				HttpResponse response = hc.execute(hp);
				System.out.println(response.getStatusLine().getStatusCode());
				if (response.getStatusLine().getStatusCode() == 200) {					
					mStrResult = EntityUtils.toString(response.getEntity());					
					JSONObject result = new JSONObject(mStrResult);					
					getResult=result.getString("register");	
					System.out.println(getResult);
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
			if("register success".equals(result)){
				//登陆成功后保存用户信息
				LocalStorage.saveString(RegisterActivity.this, "userE_mail", address);				
				LocalStorage.saveBoolean(RegisterActivity.this, "isFirstLogin", false);
				//将用户信息添加到数据库中
				UserDao user=new UserDao(RegisterActivity.this);
				user.addUser(address, password, name);
				
				Intent intent=new Intent(RegisterActivity.this,CompleteActivity.class);
				startActivity(intent);	
				finish();
			}else if("Register Error".equals(result)){
				Toast.makeText(RegisterActivity.this, "邮箱已被注册！", Toast.LENGTH_LONG).show();
			}
		}
	}	
}
