package com.tute.dakexiu2.activity;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.dakexiu2.R;
import com.tute.adapter.OwnerListViewAdapter;
import com.tute.util.HandlerUtil;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.widget.ListView;

public class OwnerActicity extends Activity {

	ListView listView;
	OwnerListViewAdapter olAdater;
	ArrayList<HashMap<String, String>> listview=new ArrayList<HashMap<String,String>>();
	Map<String,HashMap<String, String>> mapview=new HashMap<String,HashMap<String, String>>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_owner);
		listView = (ListView) findViewById(R.id.lv_owner);
		init();		
		olAdater = new OwnerListViewAdapter(this,listview,mapview);
		listView.setAdapter(olAdater);
		HandlerUtil.handler=new Handler(){
			public void handleMessage(Message msg) {
				if(msg.what==1){
					olAdater.notifyDataSetChanged();
				}
			};
		};
	}
	private void init() {
		// TODO Auto-generated method stub
		HashMap<String, String> map1 = new HashMap<String, String>();	
		map1.put("id", "0");
		map1.put("onofftext","�ر�");
		map1.put("lineState", "����");
		map1.put("tagName", "��ҵ");
		map1.put("them", "Ȫˮ����");
		map1.put("newfans", "+66");
		map1.put("fans", "9000");
		map1.put("livenss", "̽��С����");
		listview.add(map1);
		mapview.put("0", map1);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("id", "1");
		map.put("onofftext","�ر�");
		map.put("lineState", "����");
		map.put("tagName", "Ȫˮ");
		map.put("them", "Ȫˮ����");
		map.put("newfans", "+66");
		map.put("fans", "9000");
		map.put("livenss", "̽��С����");
		listview.add(map);
		mapview.put("1", map);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.owner, menu);
		return true;
	}

}
