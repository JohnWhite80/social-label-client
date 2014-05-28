package com.tute.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.dakexiu2.R;
import com.tute.util.HandlerUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.sax.StartElementListener;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


@SuppressLint("ResourceAsColor")      //�����Ϊ�����ڴ����иı�ĳ�ؼ�����ɫ��SDK��ʾ��ӵģ��������⡣
public class OwnerListViewAdapter extends BaseAdapter {
	LayoutInflater layoutInflater;
	ArrayList<HashMap<String, String>> list;
	Context mContext;
	private Map<String, HashMap<String, String>> map; 
	
	public static class ViewHolder{
		TextView tv_lineState;       //���߻�����״̬
		TextView tv_tagName;         //��ǩ��
		ImageView iv_messageRemind;  //��Ϣ��ʾ��һ����㣩
		TextView tv_id;            
		TextView bt_onOffBtn;        //�����رհ�ť
		TextView tv_theme;           //��ǩ����
		TextView tv_addFans;         //������˿
		TextView tv_sumFans;         //��˿����
		TextView tv_liveness;        //��Ծ��
		TextView tv_enterChat;       //�������찴ť
	}	
	public OwnerListViewAdapter (Context context,ArrayList<HashMap<String, String>> list, Map<String,HashMap<String, String>> map){
		mContext = context;
		this.list=list;
		this.map = map;
	}	
	@Override
	public int getCount() {		
		return list.size();     //!!!!!!!�������Ҫ��һ��Ҫд������������Ȼ������ݣ�д�����ͳ��������
	}
	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
	@Override
	public long getItemId(int arg0) {
		return arg0;    //!!!!!!!!!!!��Ҳ�ǣ�������������������������1
	}
	@Override
	public View getView(int position, View convertView , ViewGroup parent) {
		// TODO Auto-generated method stub
		
		final ViewHolder holder;
		if(convertView==null){
			holder = new ViewHolder();
			convertView  = LayoutInflater.from(mContext).inflate(R.layout.pm_listview, null);		
			Log.i("bbbbbbbbbb","��ҳ�ɹ�");
			holder.tv_id = (TextView) convertView.findViewById(R.id.tv11_id);
			holder.tv_lineState = (TextView) convertView.findViewById(R.id.tv11_line_state);
			holder.tv_tagName = (TextView) convertView.findViewById(R.id.tv11_tagname);
			holder.iv_messageRemind = (ImageView) convertView.findViewById(R.id.tv11_message_remind);
			holder.bt_onOffBtn = (TextView) convertView.findViewById(R.id.bt11_onoffbtn);
			holder.tv_theme = (TextView) convertView.findViewById(R.id.tv12_theme);
			holder.tv_addFans = (TextView) convertView.findViewById(R.id.tv13_addfans);
			holder.tv_sumFans = (TextView) convertView.findViewById(R.id.tv13_sumfans);
			holder.tv_liveness = (TextView) convertView.findViewById(R.id.tv14_liveness);
			convertView .setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.bt_onOffBtn.setTag(list.get(position).get("id"));
		holder.tv_id.setText(list.get(position).get("id"));
		holder.tv_lineState.setText(list.get(position).get("lineState"));
		holder.tv_lineState.setTextColor(R.color.blue);
		holder.tv_tagName.setText(list.get(position).get("tagName")); 
		holder.iv_messageRemind.setVisibility(View.VISIBLE);  //������Ϣ��ʾ�ɼ� 
		holder.bt_onOffBtn.setText(list.get(position).get("onofftext"));
		holder.tv_theme.setText(list.get(position).get("them")); 
		holder.tv_addFans.setText(list.get(position).get("newfans")); 
		holder.tv_sumFans.setText(list.get(position).get("fans")); 
		holder.tv_liveness.setText(list.get(position).get("livenss"));
		
		//���������ʵд������û��ʲô�ã�������Ҫ��ô������~		
		holder.bt_onOffBtn.setOnClickListener(new  View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String p = holder.tv_id.getText().toString();
				Log.i("ANGER", "����ر�");			
				if(holder.tv_lineState.getText()=="����"){
					HashMap<String, String> m = map.get(p);
					m.put("lineState", "����");
					holder.tv_lineState.setText("����");
					holder.tv_lineState.setTextColor(R.color.gray);
					holder.tv_lineState.refreshDrawableState();
					holder.iv_messageRemind.setVisibility(View.INVISIBLE);  //��Ϣ���Ѳ��ɼ�
					//m.put("", value);
					m.put("onofftext","����");
					holder.bt_onOffBtn.setText("����");
					Message msg=new Message();
					msg.what=1;
					HandlerUtil.handler.sendMessage(msg);
					Log.i("ANGER", "ִ�С�������");
				}else{
					holder.tv_lineState.setText("����");
					holder.tv_lineState.setTextColor(R.color.blue);
		           
					holder.bt_onOffBtn.setText("�ر�");
				}
			}
		});
		holder.tv_enterChat.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("ANGER", "�����������");
				if(holder.tv_lineState.getText()=="����"){							
				}
			}
		});
		return convertView ;
	}

}
