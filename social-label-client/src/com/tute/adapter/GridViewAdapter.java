package com.tute.adapter;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import com.example.dakexiu2.BuildConfig;
import com.example.dakexiu2.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.tute.adapter.ListViewAdapter.ViewHolder;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridViewAdapter extends BaseAdapter {
	int j=1;
	private DisplayImageOptions options;
	private ImageLoader imageLoader;	
	private Context mContext;
	private ArrayList<HashMap<String,String>> mGist ;
	public GridViewAdapter(Context context,ArrayList<HashMap<String,String>> mGist){	
		this.mContext = context;		
		this.mGist=mGist;
				ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(
						context).threadPriority(Thread.NORM_PRIORITY - 2)
						.denyCacheImageMultipleSizesInMemory()
						.discCacheFileNameGenerator(new Md5FileNameGenerator())
						.tasksProcessingOrder(QueueProcessingType.LIFO);
				if (BuildConfig.DEBUG) {
					builder.writeDebugLogs();
				}
				ImageLoader.getInstance().init(builder.build());

				options = new DisplayImageOptions.Builder()
						.showStubImage(R.drawable.empty_photo)
						.showImageForEmptyUri(R.drawable.empty_photo)
						.showImageOnFail(R.drawable.empty_photo).cacheInMemory(true)
						.cacheOnDisc(true).bitmapConfig(Bitmap.Config.ARGB_8888)
						.build();
				imageLoader = ImageLoader.getInstance();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mGist.size();
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mGist.get(position);
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub		
		return mGist.size();
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView ==null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.fir_ad_gridview, null, false);
		}
		ImageView giv=ViewHolder.get(convertView, R.id.main_pic);
		TextView tv1=ViewHolder.get(convertView, R.id.main_text1);
		TextView tv2=ViewHolder.get(convertView, R.id.main_text2);
		imageLoader.displayImage(mGist.get(position).get("image"), giv, options);
		tv1.setText(mGist.get(position).get("nikename"));
		tv2.setText(mGist.get(position).get("grade"));
		
		return convertView;
	}
	public static class GViewHolder{		
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
}
