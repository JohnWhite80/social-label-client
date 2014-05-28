package com.tute.dakexiu2.activity;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class BasicActivity extends Activity {
	private ConnectivityManager manager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}
	/**
	 * 检测网络是否连接
	 * @return
	 */
	public boolean checkNetworkState() {
		boolean flag = false;		
		manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);		
		if (manager.getActiveNetworkInfo() != null) {
			flag = manager.getActiveNetworkInfo().isAvailable();
		}
		return flag;
	}

}
