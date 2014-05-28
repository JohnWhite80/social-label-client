package com.tute.dakexiu2.activity;
import com.example.dakexiu2.R;
import java.io.File;

import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Calendar;

import javax.security.auth.PrivateCredentialPermission;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import kankan.wheel.widget.AddressData;
import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.Tools;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import com.speedven.pickview.widget.NumericWheelAdapter_bir;
import com.speedven.pickview.widget.OnWheelScrollListener_bir;
import com.speedven.pickview.widget.WheelView_bir;
import com.tute.sharedDB.LocalStorage;

public class CompleteActivity extends BasicActivity {
	//服务端返回结果
	String mStrResult;
	Bitmap photo;
	byte[] img;
	//设置头像
	/* 组件 */
	private RelativeLayout switchAvatar;
	private ImageView faceImage;

	private String[] items = new String[] { "选择本地图片","拍照" };
	/* 头像名称 */
	private static final String IMAGE_FILE_NAME = "faceImage.jpg";

	/* 请求码 */
	private static final int IMAGE_REQUEST_CODE = 0;
	private static final int CAMERA_REQUEST_CODE = 1;
	private static final int RESULT_REQUEST_CODE = 2;
	
	//生日
	LinearLayout test_all_layout,ll_birth;
	int width_bir,height_bir;
	private LayoutInflater inflater = null;
	private WheelView_bir year;
	private WheelView_bir month;
	private WheelView_bir day;
	private TextView birthdata ;
	
	//所在地
	private static final String TAG = "MainActivity";
	private Button button_ok;
	private LinearLayout ll_city;
	LinearLayout test_pop_layout;
	int width,height;
	
	private Button complete;
	private TextView tt ;
	String picture;
	private RadioGroup group;
	private RadioButton boy;
	private RadioButton girl;
	String sex;
	String birthday;
	String city;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		setContentView(R.layout.complete);
		class ThreadComplete implements Runnable{
			@Override
			public void run() {
				// TODO Auto-generated method stub
				RestTemplate template = new RestTemplate();
				FormHttpMessageConverter formConverter = new FormHttpMessageConverter();
			    formConverter.setCharset(Charset.forName("UTF8"));
			    template.getMessageConverters().add(formConverter);
			    template.getMessageConverters().add(new GsonHttpMessageConverter());
				template.getMessageConverters().add(new StringHttpMessageConverter());				
				FileOutputStream outputStream;
				File f = null;
				try {
					File file = new File(Environment.getExternalStoragePublicDirectory(
				            Environment.DIRECTORY_PICTURES), "upload");
					if (!file.mkdirs()) {
				        Log.e("upload", "Directory not created");
				    }
					f = new File(file.getAbsoluteFile() + File.separator + "b.jpg");
				  outputStream = new FileOutputStream(f);
				  outputStream.write(img);
				  outputStream.close();
				} catch (Exception e) {
				  e.printStackTrace();
				}	
				String UserID=LocalStorage.getString(CompleteActivity.this, "userE_mail");
				MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
				parts.add("userId", "4");
				parts.add("filename", "b.jpg");
				parts.add("image", new FileSystemResource(f));
				parts.add("birthday", birthday);
				parts.add("city", city);
				parts.add("sex", sex);
				ResponseEntity<String> result = template.postForEntity("http://10.0.2.2:8080/server/api/profile", parts, String.class);
				System.out.println(result);
				
				//传完图片，传其他信息
//				String url="http://10.0.2.2:8080/server/api/uploadinformation";
//				CompleteTask task=new CompleteTask();
//				task.execute(url,UserID,sex,birthday,city);				
			}			
		}
		group=(RadioGroup)findViewById(R.id.radioGroup_sex_id);
		boy=(RadioButton)findViewById(R.id.boy_id);
		girl=(RadioButton)findViewById(R.id.girl_id);
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if(checkedId==R.id.boy_id){
					sex=boy.getText().toString();
				}else if(checkedId==R.id.girl_id){
					sex=girl.getText().toString();
				}
			}
		});		
		complete=(Button)findViewById(R.id.complete);
		complete.setOnClickListener(new View.OnClickListener(){			
			@Override
			public void onClick(View arg0) {	
				birthday=birthdata.getText().toString();
				city=tt.getText().toString();
				photo=faceImage.getDrawingCache();
				img=getBitmapByte(photo);
				if(checkNetworkState()){
					ThreadComplete t=new ThreadComplete();
					Thread t1=new Thread(t);
					t1.start();
				}else{
					Toast.makeText(CompleteActivity.this, "请链接网络", Toast.LENGTH_LONG).show();
				}				
			}
		});		
		/***
		 * 设置头像
		 */
		switchAvatar = (RelativeLayout) findViewById(R.id.switch_face_rl);
		faceImage = (ImageView) findViewById(R.id.face);
		switchAvatar.setOnClickListener(listener);	
		
		/***
		 * 生日
		 */
		// 获取屏幕的高度和宽度
				Display display_bir = this.getWindowManager().getDefaultDisplay();
				width_bir = display_bir.getWidth();
			    height_bir = display_bir.getHeight();
				
			 // 获取弹出的layout
			    test_all_layout = (LinearLayout)findViewById(R.id.ll_perfect_imfor);
			    birthdata = (TextView)findViewById(R.id.tv_register_birth);  
			    
			    ll_birth = (LinearLayout)findViewById(R.id.ll_register_birthday);
				
			    ll_birth.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						PopupWindow popupWindow = makePopupWindow_bir(CompleteActivity.this);
						int[] xy = new int[2];
						test_all_layout.getLocationOnScreen(xy);
						popupWindow.showAtLocation(test_all_layout,Gravity.CENTER|Gravity.BOTTOM, 0, -height_bir); 
					}
				});		
		
				/***
				 * 所在地
				 */	
				// 获取屏幕的高度和宽度
		Display display = this.getWindowManager().getDefaultDisplay();
		width = display.getWidth();
	    height = display.getHeight();
		
	 // 获取弹出的layout
	    test_pop_layout = (LinearLayout)findViewById(R.id.ll_perfect_imfor);
	    tt = (TextView)findViewById(R.id.tv_register_city);  	    
		
		ll_city = (LinearLayout) findViewById(R.id.ll_register_area);
		ll_city.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				PopupWindow popupWindow = makePopupWindow(CompleteActivity.this);
				int[] xy = new int[2];
				test_pop_layout.getLocationOnScreen(xy);
				popupWindow.showAtLocation(test_pop_layout,Gravity.CENTER|Gravity.BOTTOM, 0, -height); 
			}
		});		
	}
//  设置头像	
	private View.OnClickListener listener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			showDialog();
		}
	};
	/**
	 * 显示选择对话框
	 */
	private void showDialog() {

		new AlertDialog.Builder(this)
				.setTitle("设置头像")
				.setItems(items, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:
							Intent intentFromGallery = new Intent();
							intentFromGallery.setType("image/*"); 
							intentFromGallery
									.setAction(Intent.ACTION_GET_CONTENT);
							startActivityForResult(intentFromGallery,
									IMAGE_REQUEST_CODE);
							break;
						case 1:

							Intent intentFromCapture = new Intent(
									MediaStore.ACTION_IMAGE_CAPTURE);
							if (Tools.hasSdcard()) {
								intentFromCapture.putExtra(
										MediaStore.EXTRA_OUTPUT,
										Uri.fromFile(new File(Environment
												.getExternalStorageDirectory(),
												IMAGE_FILE_NAME)));
							}
							startActivityForResult(intentFromCapture,
									CAMERA_REQUEST_CODE);
							break;
						}
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).show();

	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_CANCELED) {
			switch (requestCode) {
			case IMAGE_REQUEST_CODE:
				startPhotoZoom(data.getData());
				break;
			case CAMERA_REQUEST_CODE:
				if (Tools.hasSdcard()) {
					File tempFile = new File(
							Environment.getExternalStorageDirectory()
									+"/"+ IMAGE_FILE_NAME);
					startPhotoZoom(Uri.fromFile(tempFile));
				} else {
					Toast.makeText(CompleteActivity.this,"未找到存储卡，无法存储照片！",
							Toast.LENGTH_LONG).show();
				}
				break;
			case RESULT_REQUEST_CODE:
				if (data != null) {
					Bitmap bt=getImageToView(data);
					//将bitmap转byte数组用于传递数据
					img=getBitmapByte(bt);
				}
				break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	/**
	 * 裁剪图片方法实现
	 * 
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {
		// 裁剪图片意图
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		// 裁剪框的比例，1：1
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// 裁剪后输出图片的尺寸大小
		intent.putExtra("outputX", 250);
		intent.putExtra("outputY", 250);
		// 图片格式
		intent.putExtra("outputFormat", "JPEG");
		intent.putExtra("noFaceDetection", true);// 取消人脸识别
		intent.putExtra("return-data", true);// true:不返回uri，false：返回uri
		startActivityForResult(intent, RESULT_REQUEST_CODE);
	}
	/**
	 * 保存裁剪之后的图片数据
	 * 
	 * @param picdata
	 */
	public Bitmap getImageToView(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			photo = extras.getParcelable("data");
			//将图片显示到ImageView中
			faceImage.setImageBitmap(photo);
		}
		return photo;
	}
	// 将头像转换成byte[]以便能将图片上传到服务器
	public byte[] getBitmapByte(Bitmap bitmap) {
		if (bitmap == null) {  
		     return null;  
		  }  
		  final ByteArrayOutputStream os = new ByteArrayOutputStream();  
		  // 将Bitmap压缩成PNG编码，质量为100%存储  
		  bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);//除了PNG还有很多常见格式，如jpeg等。  
		  return os.toByteArray();
	}

	//  生日	
	private PopupWindow makePopupWindow_bir(Context cx)
	{
		final PopupWindow window;
 		window = new PopupWindow(cx); 
 		
 		View contentView = LayoutInflater.from(this).inflate(R.layout.datapick, null);
        window.setContentView(contentView);
 		 
 		Calendar c = Calendar.getInstance();
		int curYear = c.get(Calendar.YEAR);
		int curMonth = c.get(Calendar.MONTH) + 1;
		int curDate = c.get(Calendar.DATE);		
		// 年
		year = (WheelView_bir) contentView.findViewById(R.id.year);
		year.setAdapter(new NumericWheelAdapter_bir(2000, 2099));
		year.setLabel("年");
		year.setCyclic(true);
		year.addScrollingListener(scrollListener);
		// 月
		month = (WheelView_bir) contentView.findViewById(R.id.month);
		month.setAdapter(new NumericWheelAdapter_bir(1, 12));
		month.setLabel("月");
		month.setCyclic(true);
		month.addScrollingListener(scrollListener);
		// 日
		day = (WheelView_bir) contentView.findViewById(R.id.day);
		initDay(curYear,curMonth);
		day.setLabel("日");
		day.setCyclic(true);

		year.setCurrentItem(curYear - 2000);
		month.setCurrentItem(curMonth - 1);
		day.setCurrentItem(curDate - 1);
		TextView bt = (TextView) contentView.findViewById(R.id.set);
		bt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				birthdata.setText((year.getCurrentItem()+2000) + "年"
						+ month.getCurrentItem() + "月"
						+ day.getCurrentItem() + "日");
				window.dismiss();
			}
		});                
 		window.setWidth(width_bir);
 		window.setHeight(height_bir/2);
        
 		// 设置PopupWindow外部区域是否可触摸
		window.setFocusable(true); //设置PopupWindow可获得焦点
		window.setTouchable(true); //设置PopupWindow可触摸
		window.setOutsideTouchable(true); //设置非PopupWindow区域可触摸
		return window;
	}
	
	/**
	 * 用于监听滑动事件，更改月份天数
	 */
	OnWheelScrollListener_bir scrollListener = new OnWheelScrollListener_bir() {

		@Override
		public void onScrollingStarted(WheelView_bir wheel) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onScrollingFinished(WheelView_bir wheel) {
			// TODO Auto-generated method stub
			int n_year = year.getCurrentItem() + 2000;
			int n_month = month.getCurrentItem() + 1;
			initDay(n_year,n_month);
		}
	};

	/**
	 * 根据年月算出这个月多少天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	private int getDay(int year, int month) {
		int day = 30;
		boolean flag = false;
		switch (year % 4) {
		case 0:
			flag = true;
			break;
		default:
			flag = false;
			break;
		}
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			day = 31;
			break;
		case 2:
			day = flag ? 29 : 28;
			break;
		default:
			day = 30;
			break;
		}
		return day;
	}
	/**
	 * 初始化天数
	 */
	private void initDay(int arg1, int arg2) {
		day.setAdapter(new NumericWheelAdapter_bir(1, getDay(arg1, arg2), "%02d"));
	}
	//  所在地
    private boolean scrolling = false; 
    private TextView tv;
	
	private PopupWindow makePopupWindow(Context cx)
	{
		final PopupWindow window;
 		window = new PopupWindow(cx); 
 		 
        View contentView = LayoutInflater.from(this).inflate(R.layout.cities_layout, null);
        window.setContentView(contentView);
        
        
        tv = (TextView)contentView.findViewById(R.id.tv_cityName);
        
        final WheelView country = (WheelView) contentView.findViewById(R.id.country);
        country.setVisibleItems(3);
        country.setViewAdapter(new CountryAdapter(this));

        final String cities[][] = AddressData.CITIES; 
        final String ccities[][][] = AddressData.COUNTIES;  
        final WheelView city = (WheelView) contentView.findViewById(R.id.city);
        city.setVisibleItems(0);

        country.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
			    if (!scrolling) {
			        updateCities(city, cities, newValue);
			    }
			}
		});
        
        country.addScrollingListener( new OnWheelScrollListener() {
            public void onScrollingStarted(WheelView wheel) {
                scrolling = true;
            }
            public void onScrollingFinished(WheelView wheel) {
                scrolling = false;
                updateCities(city, cities, country.getCurrentItem());
                
                tv.setText( AddressData.PROVINCES[country.getCurrentItem()] ); 
            }
        });
        
        
        final WheelView ccity = (WheelView) contentView.findViewById(R.id.ccity);
        ccity.setVisibleItems(0);

        city.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
			    if (!scrolling) {
			        updatecCities(ccity, ccities, country.getCurrentItem(),newValue); 
			    }
			}
		});
        
        city.addScrollingListener( new OnWheelScrollListener() {
            public void onScrollingStarted(WheelView wheel) {
                scrolling = true;
            }
            public void onScrollingFinished(WheelView wheel) {
                scrolling = false;
                updatecCities(ccity, ccities, country.getCurrentItem(), city.getCurrentItem());
                
                tv.setText(
            		   AddressData.PROVINCES[country.getCurrentItem()] + "-" + 
            		   AddressData.CITIES[country.getCurrentItem()][city.getCurrentItem()]);
               
            }
        }); 
         
        ccity.addScrollingListener( new OnWheelScrollListener() {
            public void onScrollingStarted(WheelView wheel) {
                scrolling = true;
            }
            public void onScrollingFinished(WheelView wheel) {
                scrolling = false; 
                
               tv.setText(
            		   AddressData.PROVINCES[country.getCurrentItem()] + "-" + 
            		   AddressData.CITIES[country.getCurrentItem()][city.getCurrentItem()] + "-" + 
            		   AddressData.COUNTIES[country.getCurrentItem()][city.getCurrentItem()][ccity.getCurrentItem()]);
                
            }
        }); 
         
        country.setCurrentItem(1);
        
        
        button_ok = (Button)contentView.findViewById(R.id.button_ok);
    	button_ok.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{ 
				tt.setText(AddressData.PROVINCES[country.getCurrentItem()] + "-" + 
	            		   AddressData.CITIES[country.getCurrentItem()][city.getCurrentItem()] + "-" + 
	            		   AddressData.COUNTIES[country.getCurrentItem()][city.getCurrentItem()][ccity.getCurrentItem()]);
				window.dismiss(); 
			}
		});        
 		window.setWidth(width);
 		window.setHeight(height/2);
        
 		// 设置PopupWindow外部区域是否可触摸
		window.setFocusable(true); //设置PopupWindow可获得焦点
		window.setTouchable(true); //设置PopupWindow可触摸
		window.setOutsideTouchable(true); //设置非PopupWindow区域可触摸
		return window;
	}
	 /**
     * Updates the city wheel
     */
    private void updateCities(WheelView city, String cities[][], int index) {
        ArrayWheelAdapter<String> adapter =
            new ArrayWheelAdapter<String>(this, cities[index]);
        adapter.setTextSize(18);
        city.setViewAdapter(adapter);
        city.setCurrentItem(cities[index].length / 2);    
    }
    
    /**
     * Updates the ccity wheel
     */
    private void updatecCities(WheelView city, String ccities[][][], int index,int index2) {
        ArrayWheelAdapter<String> adapter =
            new ArrayWheelAdapter<String>(this, ccities[index][index2]);
        adapter.setTextSize(18);
        city.setViewAdapter(adapter);
        city.setCurrentItem(ccities[index][index2].length / 2);     
    }
    
    /**
     * Adapter for countries
     */
    private class CountryAdapter extends AbstractWheelTextAdapter {
        // Countries names
        private String countries[] = AddressData.PROVINCES;  
        /**
         * Constructor
         */
        protected CountryAdapter(Context context) {
            super(context, R.layout.country_layout, NO_RESOURCE);
            
            setItemTextResource(R.id.country_name);
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent); 
            return view;
        }
        
        @Override
        public int getItemsCount() {
            return countries.length;
        }
        
        @Override
        protected CharSequence getItemText(int index) {
            return countries[index];
        }
    }
//    private class CompleteTask extends AsyncTask<String, Void, String> {
//
//		@Override
//		protected String doInBackground(String... params) {
//			// TODO Auto-generated method stub
//			HttpClient hc = new DefaultHttpClient();			
//			HttpPost hp = new HttpPost(params[0]);			
//			JSONObject jsonObj = new JSONObject();
//			String getResult=null;
//			try {				
//				jsonObj.put("email", params[1]);
//				jsonObj.put("sex", params[2]);
//				jsonObj.put("birthday", params[3]);
//				jsonObj.put("city", params[4]);				
//				hp.setEntity(new StringEntity(jsonObj.toString()));			
//				HttpResponse response = hc.execute(hp);				
//				if (response.getStatusLine().getStatusCode() == 200) {					
//					mStrResult = EntityUtils.toString(response.getEntity());					
//					JSONObject result = new JSONObject(mStrResult);					
//					getResult=result.getString("completInformation");					
//				} else {
//					System.out.println("连接失败");
//				}
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (ClientProtocolException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return getResult;
//		}
//		@Override
//		protected void onPostExecute(String result) {
//			if("upload success".equals(result)){
//				Intent intent=new Intent(CompleteActivity.this,SetTagActivity.class);
//				startActivity(intent);
//				finish();
//			}
//
//		}
//    }
}
