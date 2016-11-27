package kr.ac.jbnu.se.foodtruckowner.ui.sign;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.github.kimkevin.cachepot.CachePot;

import java.io.IOException;
import java.io.Serializable;

import kr.ac.jbnu.se.foodtruckowner.CustomCachePot;
import kr.ac.jbnu.se.foodtruckowner.R;
import kr.ac.jbnu.se.foodtruckowner.model.Owner;
import kr.ac.jbnu.se.foodtruckowner.service.ApiService;
import kr.ac.jbnu.se.foodtruckowner.ui.main.MainActivity;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

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
    private EditText et_email;
    private EditText et_password;

    private Owner owner_info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_activity);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        mPref = PreferenceManager.getDefaultSharedPreferences(this);
        at_login = mPref.getBoolean("auto_login", false);
        pref_id_store = getSharedPreferences("id_store", NULL);
        pref_store_id = getSharedPreferences("store_id", NULL);
        et_email = (EditText) findViewById(R.id.et_signin_email);
        et_password = (EditText) findViewById(R.id.et_signin_pw);

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
        sw_id_store.setChecked(pref_id_store.getBoolean("id_store", false));
        if (pref_id_store.getBoolean("id_store", false)) {
            et_email.setText(pref_store_id.getString("store_id", ""));
        }
        if (at_login == true) {
            auto_log();
            System.out.println("자동로긴");
        }

    }


    public void login() {
        //자동로그인
        if (sw_auto_login.isChecked()) {
            editor = mPref.edit();
            editor.putBoolean("auto_login", true);
            editor.commit();
        }
        //아이디 기억
        if (sw_id_store.isChecked()) {
            editor = pref_id_store.edit();
            editor.putBoolean("id_store", true);
            editor.commit();
            editor = pref_store_id.edit();
            editor.putString("store_id", et_email.getText().toString());
            editor.commit();
        } else {
            //아이디 기억 안하니까 널값으로
            editor = pref_id_store.edit();
            editor.putBoolean("id_store", false);
            editor.commit();
            editor = pref_store_id.edit();
            editor.putString("store_id", "");
            editor.commit();
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://server-blackdog11.c9users.io")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);

        Call<Owner> convertedContent = service.request_login(et_email.getText().toString(), et_password.getText().toString());

        convertedContent.enqueue(new Callback<Owner>() {
            @Override
            public void onResponse(Response<Owner> response, Retrofit retrofit) {
                Log.d("Response status code: ", String.valueOf(response.code()));

                // isSuccess is true if response code => 200 and <= 300
                if (!response.isSuccess()) {
                    // print response body if unsuccessful
                    try {
                        Log.d("response unsuccessful: ", response.errorBody().string());
                    } catch (IOException e) {
                        // do nothing
                    }
                    return;
                }
                // if parsing the JSON body failed, `response.body()` returns null
                Owner decodedResponse = response.body();
                owner_info = response.body();
                CustomCachePot.getInstance().push(owner_info); //메뉴프레그먼트에서 쓸 수 있게 객체 정보 push Signin => MainActivity
                Log.d("로그인", "업주아이디 : "+ String.valueOf(decodedResponse.getId()));

                if (decodedResponse == null) {
                    Log.d("TAG", "아이디비번오류");
                    Toast.makeText(getApplicationContext(), "아이디 또는 비밀번호가 일치하지 않습니다.", Toast.LENGTH_LONG).show();
                    return;
                }

                if (String.valueOf(decodedResponse.getEmail()).equals(et_email.getText().toString())) {
                    Log.d("TAGG", "로그인성공!");
                    Toast.makeText(getApplicationContext(), "환영합니다. 푸드트럭", Toast.LENGTH_LONG).show();
                    Intent loginIntent = new Intent(SigninActivity.this, MainActivity.class);
//                    loginIntent.putExtra("owner_info", owner_info);
                    startActivity(loginIntent);
                    finish();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("실패", t.getMessage().toString());
            }
        });
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
        Intent signupIntent = new Intent(SigninActivity.this, SignUpActivity.class);
        startActivity(signupIntent);
        finish();
    }
}