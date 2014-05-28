package com.tute.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import com.example.dakexiu2.R;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter {
	int i=1;
	
	private int cWidth = 150;
	
	private int hSpacing = 10;
	private Context mContext;
	private View convert_View;
	private LinkedList<ArrayList<HashMap<String, String>>> mList;
	private ArrayList<HashMap<String,String>> mGist ;
	private LayoutInflater inflater;

	public LinkedList<String> tagname=new LinkedList<String>();
	public static class ViewHolder {
		 public static <T extends View> T get(View view, int id) {
			 SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
			 if(viewHolder==null){
				 viewHolder=new SparseArray<View>();
				 view.setTag(viewHolder);
			 }
			 View childView = viewHolder.get(id);
			 if(childView==null){
				 childView = view.findViewById(id);
				 viewHolder.put(id, childView);
			 }
			 return (T) childView;
		 }		
	}	
	public ListViewAdapter(Context context,
			LinkedList<ArrayList<HashMap<String, String>>> list,LinkedList<String> tagname) {
		this.mContext = context;
		this.mList = list;
		this.tagname=tagname;			
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}
	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.fir_ad_listview,null,false);
		}
		TextView tv=ViewHolder.get(convertView, R.id.tv_tag);
		GridView gv=ViewHolder.get(convertView, R.id.gridview);
		tv.setText(tagname.get(arg0));
		GridViewAdapter ga = new GridViewAdapter(mContext,mList.get(arg0));
		int ii = ga.getCount(); //ii * (48 + 100)
		LayoutParams params = new LayoutParams(6 * (60 + 100),
				LayoutParams.FILL_PARENT);
		gv.setLayoutParams(params);
		gv.setColumnWidth(cWidth);
		gv.setHorizontalSpacing(hSpacing);
		gv.setStretchMode(GridView.NO_STRETCH);
		
		gv.setNumColumns(6);
		gv.setAdapter(ga);		
		return convertView;
	}
}
