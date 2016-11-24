package kr.ac.jbnu.se.foodtruckowner.ui.sign;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import kr.ac.jbnu.se.foodtruckowner.R;
import kr.ac.jbnu.se.foodtruckowner.ui.MainActivity;

import static java.sql.Types.NULL;

public class SigninActivity extends AppCompatActivity {
    private SharedPreferences mPref;
    private SharedPreferences pref_id_store;
    private SharedPreferences pref_store_id;
    private SharedPreferences.Editor editor;
    private Toolbar toolbar;
    boolean at_login = false;
    private Switch sw_id_store;
    private Switch sw_auto_login;
    private EditText et_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_activity);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        mPref = PreferenceManager.getDefaultSharedPreferences(this);
        at_login = mPref.getBoolean("auto_login", false);
        pref_id_store = getSharedPreferences("id_store", NULL);
        pref_store_id = getSharedPreferences("store_id", NULL);
        et_id = (EditText) findViewById(R.id.et_signin_email);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("로그인");
        toolbar.setTitleTextColor(Color.WHITE);
        sw_auto_login = (Switch) findViewById(R.id.sw_auto_log);
        sw_id_store = (Switch) findViewById(R.id.sw_stroe_id);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        //actionBar.setHomeAsUpIndicator(R.drawable.button_back); //뒤로가기 버튼을 본인이 만든 아이콘으로 하기 위해 필요
        sw_auto_login.setChecked(at_login);
        sw_id_store.setChecked(pref_id_store.getBoolean("id_store",false));
        if (pref_id_store.getBoolean("id_store",false)) {
            et_id.setText(pref_store_id.getString("store_id", ""));
        }
        if (at_login == true) {
            auto_log();
            System.out.println("자동로긴");
        }

    }



    public void login() {
        if (sw_auto_login.isChecked()) {
            editor = mPref.edit();
            editor.putBoolean("auto_login", true);
            editor.commit();
        }
        if (sw_id_store.isChecked()) {
            editor = pref_id_store.edit();
            editor.putBoolean("id_store", true);
            editor.commit();
            editor = pref_store_id.edit();
            editor.putString("store_id", et_id.getText().toString());
            editor.commit();
        }else {
            editor = pref_id_store.edit();
            editor.putBoolean("id_store", false);
            editor.commit();
            editor = pref_store_id.edit();
            editor.putString("store_id", "");
            editor.commit();
        }
        Toast.makeText(this, "환영합니다. 푸드트럭", Toast.LENGTH_LONG).show();
        Intent loginIntent = new Intent(SigninActivity.this, MainActivity.class);
        startActivity(loginIntent);
        finish();
    }

    public void auto_log() {
        login();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                //verridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }

    public void Onclick_Signin(View v) {
        login();
    }

    public void Onclick_Signup(View view) {
        Intent singnupIntent = new Intent(SigninActivity.this, SignUpActivity.class);
        startActivity(singnupIntent);
        finish();
    }
}