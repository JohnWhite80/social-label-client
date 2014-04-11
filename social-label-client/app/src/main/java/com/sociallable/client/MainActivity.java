package com.sociallable.client;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sociallable.client.entity.User;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@EActivity(R.layout.activity_main)
public class MainActivity extends ActionBarActivity {

    @ViewById
    Button btnRegister;

    @ViewById
    TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Click({R.id.btnRegister})
    void registerClicked() {
        doSomethingInBackground();
    }
//这是另起一个线程
    @Background
    void doSomethingInBackground() {
        User u = new User();
        u.setLoginName("loginname3");
        u.setName("name3");
        u.setPassword("password3");

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

        HttpEntity<User> requestEntity = new HttpEntity(u);
        ResponseEntity<String> result = restTemplate.postForEntity("http://10.104.151.197:8080/server/api/register", u, String.class);
        System.out.println(result);
        HttpStatus statusCode = result.getStatusCode();
        String code = result.getHeaders().get("x-code").get(0);
        String message = result.getHeaders().get("x-message").get(0);

        updateUI(code, message);
    }

    // Notice that we manipulate the activity ref only from the UI thread
    @UiThread
    void updateUI(String code, String message) {
        txtResult.setText("call result, code:" + code + " message:" + message);
    }
}
