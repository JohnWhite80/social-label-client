package com.tute.dakexiu2.activity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.dakexiu2.R;
import com.tute.adapter.ListViewAdapter;
import com.tute.adapter.RefreshableView;
import com.tute.adapter.RefreshableView.PullToRefreshListener;
import com.tute.util.ToList;

public class MainActivity extends Activity {
	PopupMenu popmenu = null;
	String mStrResult;
	private ListView mListView;
	private RefreshableView refreshableView;
	private ListViewAdapter adapter;
	private LinkedList<ArrayList<HashMap<String, String>>> mList = new LinkedList<ArrayList<HashMap<String, String>>>();
	ArrayList<HashMap<String, String>> mGist1 = new ArrayList<HashMap<String, String>>();
	ArrayList<HashMap<String, String>> mGist3 = new ArrayList<HashMap<String, String>>();
	private EditText searchtext;
	private ImageButton search;
	List<HashMap<String, Object>> AllMessage = new ArrayList<HashMap<String, Object>>();
	HashMap<String, Object> taganduser=new HashMap<String, Object>();
	
	LinkedList<String> tags = new LinkedList<String>();
	HashMap<String, String> oneuser=new HashMap<String, String>();
	
	// Сͼurl
	public static String[] thumbImageUrls = new String[] {
			"http://d.hiphotos.baidu.com/album/w%3D2048/sign=14b0934b78310a55c424d9f4837d42a9/a8014c086e061d95e9fd3e807af40ad163d9cacb.jpg",
			"http://c.hiphotos.bdimg.com/album/s%3D900%3Bq%3D90/sign=b8658f17f3d3572c62e290dcba28121a/5fdf8db1cb134954bb97309a574e9258d0094a47.jpg",
			"http://g.hiphotos.bdimg.com/album/s%3D680%3Bq%3D90/sign=ccd33b46d53f8794d7ff4b26e2207fc9/0d338744ebf81a4c0f993437d62a6059242da6a1.jpg",
			"http://c.hiphotos.bdimg.com/album/s%3D680%3Bq%3D90/sign=cdab1512d000baa1be2c44b3772bc82f/91529822720e0cf3855c96050b46f21fbf09aaa1.jpg",
			"http://g.hiphotos.bdimg.com/album/s%3D680%3Bq%3D90/sign=e58fb67bc8ea15ce45eee301863b4bce/a5c27d1ed21b0ef4fd6140a0dcc451da80cb3e47.jpg",
			"http://f.hiphotos.bdimg.com/album/s%3D680%3Bq%3D90/sign=6b62f61bac6eddc422e7b7f309e0c7c0/6159252dd42a2834510deef55ab5c9ea14cebfa1.jpg", };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mListView = (ListView) findViewById(R.id.main_listview);
		refreshableView = (RefreshableView) findViewById(R.id.refreshable_view);
		initData();
		tags.addFirst("��ҵ");			
		adapter = new ListViewAdapter(this, mList, tags);
		mListView.setAdapter(adapter);
		searchtext = (EditText) findViewById(R.id.search_text);
		search = (ImageButton) findViewById(R.id.search_pic);
		search.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				System.out.println("���");
				// TODO Auto-generated method stub
				final String tagname = searchtext.getText().toString().trim();
				// �Ƚ���������������
				 mList.clear();
				 tags.clear();
				 mGist1.clear();
				// ͨ�Ŵ���ݣ�ȡ���
				new AsyncTask<Void, Void, Void>() {
					protected void onPostExecute(Void result) {
						 adapter.notifyDataSetChanged();
					}
					protected Void doInBackground(Void... params) {
						HttpClient hc = new DefaultHttpClient();
						HttpPost hp = new HttpPost("http://10.0.2.2:8080/server/api/search");
						JSONObject jsonObj = new JSONObject();
						String getResult = null;
						try {
							jsonObj.put("tagname",tagname);
							hp.setEntity(new StringEntity(jsonObj.toString()));
							HttpResponse response = hc.execute(hp);
							if (response.getStatusLine().getStatusCode() == 200) {
								mStrResult = EntityUtils.toString(response
										.getEntity());
								JSONObject json=new JSONObject(mStrResult);
								String tagsandusers=json.getString("searchResult");
								AllMessage = ToList.JSONToList(tagsandusers);
								Iterator<HashMap<String, Object>> it=AllMessage.iterator();								
								while(it.hasNext()){
									taganduser=it.next();
									//���ǩ,���ǩ
									tags.addFirst(taganduser.get("tagname").toString());									
									System.out.println("��ǩ��"+tags.getFirst());
									//���û�
									List<HashMap<String, String>> users=new LinkedList<HashMap<String,String>>();
									ArrayList<HashMap<String, String>> mGist2 = new ArrayList<HashMap<String, String>>();
									String user=taganduser.get("users").toString();									
									users=ToList.StringToList(user);
									System.out.println("ת��֮����û���:"+user);
									Iterator<HashMap<String, String>> usermessage=users.iterator();									
									while(usermessage.hasNext()){
										oneuser=usermessage.next();
										mGist2.add(oneuser);																					
									}
									System.out.println("���һ����ǩ���û�"+mGist2);
									mList.addFirst(mGist2);									
								}						
								System.out.println("���еļ�¼��"+mList);
							} else {
								System.out.println("����ʧ��");
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
						// adapter.SetText(tags);
						return null;
					}
				}.execute();
			}
		});
		refreshableView.setOnRefreshListener(new PullToRefreshListener() {
			@Override
			public void onRefresh() {
				new AsyncTask<Void, Void, Void>() {
					protected void onPostExecute(Void result) {
						adapter.notifyDataSetChanged();
						refreshableView.finishRefreshing();
					}
					protected Void doInBackground(Void... params) {						
						try {
							Thread.sleep(3000);
						} catch (Exception e) {
							e.printStackTrace();
						}
						RefreshData();
						tags.addFirst("ˢ�±�ǩ");
						mList.addFirst(mGist3);
						return null;
					}
				}.execute();
			}
		}, 0);
	}

	public void initData() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("image", thumbImageUrls[1]);
		map.put("nikename", "你");
		map.put("grade", "帅气小伙");
		mGist1.add(map);

		map = new HashMap<String, String>();
		map.put("image", thumbImageUrls[1]);
		map.put("nikename", "我");
		map.put("grade", "IT民工");
		mGist1.add(map);

		map = new HashMap<String, String>();
		map.put("image", thumbImageUrls[1]);
		map.put("nikename", "她");
		map.put("grade", "美丽大方");
		mGist1.add(map);
		map = new HashMap<String, String>();
		map.put("image", thumbImageUrls[1]);
		map.put("nikename", "你们");
		map.put("grade", "算你狠");
		mGist1.add(map);

		map = new HashMap<String, String>();
		map.put("image", thumbImageUrls[1]);
		map.put("nikename", "我们");
		map.put("grade", "很一般");
		mGist1.add(map);

		map = new HashMap<String, String>();
		map.put("image", thumbImageUrls[1]);
		map.put("nikename", "他们");
		map.put("grade", "很牛逼");
		mGist1.add(map);

		mList.addFirst(mGist1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater menuInflater = this.getMenuInflater();
		menuInflater.inflate(R.menu.popmenu, menu);
		return true;
	}
	public void RefreshData() {
		// ArrayList<HashMap<String,Integer>> array =new
		// ArrayList<HashMap<String,Integer>>();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("image", thumbImageUrls[0]);
		map.put("nikename", "你");
		map.put("grade", "帅气小伙");
		mGist3.add(map);

		map = new HashMap<String, String>();
		map.put("image", thumbImageUrls[0]);
		map.put("nikename", "我");
		map.put("grade", "IT民工");
		mGist3.add(map);

		map = new HashMap<String, String>();
		map.put("image", thumbImageUrls[0]);
		map.put("nikename", "她");
		map.put("grade", "美丽大方");
		mGist3.add(map);
		map = new HashMap<String, String>();
		map.put("image", thumbImageUrls[0]);
		map.put("nikename", "你们");
		map.put("grade", "算你狠");
		mGist3.add(map);

		map = new HashMap<String, String>();
		map.put("image", thumbImageUrls[0]);
		map.put("nikename", "我们");
		map.put("grade", "很一般");
		mGist3.add(map);

		map = new HashMap<String, String>();
		map.put("image", thumbImageUrls[0]);
		map.put("nikename", "他们");
		map.put("grade", "很牛逼");
		mGist3.add(map);
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
		
		String url = "";
//		 if(checkNetworkState()){
//			 DisconnectTask task=new DisconnectTask();
//			 task.execute(url);
//			 }else{
//			 Toast.makeText(MainActivity.this, "��ǰ������δ���ӣ�",
//			 Toast.LENGTH_LONG).show();
//		 }
	}

	private void finishApplication() {
		// TODO Auto-generated method stub
		// DaKeXiuApplication app=new DaKeXiuApplication();
		// app.exit();
	}

	// �÷���ʵ�ֵ����˳��Ĺ���
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// �÷�������ص����棨û��home���Ч��
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			moveTaskToBack(true);
			return true;
		}
		return super.onKeyDown(keyCode, event);
		// ʵ��home���˳�
		// if (keyCode == KeyEvent.KEYCODE_BACK) {
		// Intent intent = new Intent(Intent.ACTION_MAIN);
		// intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// intent.addCategory(Intent.CATEGORY_HOME);
		// startActivity(intent);
		// return true;
		// }
		// return super.onKeyDown(keyCode, event);
	}

	private class DisconnectTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			HttpClient hc = new DefaultHttpClient();
			HttpPost hp = new HttpPost(params[0]);
			JSONObject jsonObj = new JSONObject();
			String getResult = null;
			try {
				jsonObj.put("email", params[1]);
				hp.setHeader("Content-Type", "application/json");
				hp.setEntity(new StringEntity(jsonObj.toString()));
				HttpResponse response = hc.execute(hp);
				if (response.getStatusLine().getStatusCode() == 200) {
					mStrResult = EntityUtils.toString(response.getEntity());
					JSONObject result = new JSONObject(mStrResult);
					getResult = result.getString("register");
				} else {
					System.out.println("����ʧ��");
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
		/*
		 * ����result��doInBackground���ص���ֵ ���Ҹ÷�������UI�߳���ִ�е�
		 */
		 @Override
		 protected void onPostExecute(String result) {
		 if("cancle".equals(result)){
//			 Intent intent=new Intent(MainActivity.this,LoginActivity.class);
//			 startActivity(intent);
		 }
		}
	}
}
