package com.tute.clientHelp;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.Charset;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import android.os.Environment;
import android.util.Log;


public class ThreadComplete implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
	
//				RestTemplate template = new RestTemplate();
//				FormHttpMessageConverter formConverter = new FormHttpMessageConverter();
//			    formConverter.setCharset(Charset.forName("UTF8"));
//			    template.getMessageConverters().add(formConverter);
//			    template.getMessageConverters().add(new GsonHttpMessageConverter());
//				template.getMessageConverters().add(new StringHttpMessageConverter());
//				
//				
//				FileOutputStream outputStream;
//				File f = null;
//				try {
//					File file = new File(Environment.getExternalStoragePublicDirectory(
//				            Environment.DIRECTORY_PICTURES), "upload");
//					if (!file.mkdirs()) {
//				        Log.e("upload", "Directory not created");
//				    }
//					f = new File(file.getAbsoluteFile() + File.separator + "b.jpg");
//				  outputStream = new FileOutputStream(f);
//				  outputStream.write(img);
//				  outputStream.close();
//				} catch (Exception e) {
//				  e.printStackTrace();
//				}
//				
//				MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
//				parts.add("userId", "2");
//				parts.add("filename", "b.jpg");
//				parts.add("image", new FileSystemResource(f));
//
//				ResponseEntity<String> result = template.postForEntity("http://10.247.64.239:8080/server/api/profile", parts, String.class);
//				String url="http://10.247.64.239:8080/server/api/profile";
//				CompleteTask task=new CompleteTask();
//				task.execute(url,sex,birthday,city);
//				
//				System.out.println(result);
			}

}
