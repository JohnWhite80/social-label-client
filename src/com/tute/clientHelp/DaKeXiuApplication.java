package com.tute.clientHelp;

import java.util.LinkedList;
import java.util.List;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Intent;

public class DaKeXiuApplication extends Application {

	private List<Activity> activityList = new LinkedList<Activity>();

	
	public void addActivity(Activity activity) {
		activityList.add(activity);
	}

	
	public void exit() {
		
		Intent intent = new Intent(Intent.ACTION_MAIN);  
        intent.addCategory(Intent.CATEGORY_HOME);  
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  
        startActivity(intent);  
        android.os.Process.killProcess(android.os.Process.myPid());		
//		for (Activity activity : activityList) {
//			activity.finish();
//		}
	}
}
