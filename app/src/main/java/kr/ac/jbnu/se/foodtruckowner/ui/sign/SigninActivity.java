package kr.ac.jbnu.se.foodtruckowner.ui.sign;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import kr.ac.jbnu.se.foodtruckowner.R;
import kr.ac.jbnu.se.foodtruckowner.model.FoodTruckModel;
import kr.ac.jbnu.se.foodtruckowner.model.Owner;
import kr.ac.jbnu.se.foodtruckowner.service.ApiService;
import kr.ac.jbnu.se.foodtruckowner.service.ServiceGenerator;
import kr.ac.jbnu.se.foodtruckowner.ui.base.BaseActivity;
import kr.ac.jbnu.se.foodtruckowner.ui.main.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.sql.Types.NULL;

public class SigninActivity extends BaseActivity {
    private SharedPreferences mPref;
    private SharedPreferences prefStoreId;
    private SharedPreferences prefStoredId;
    private SharedPreferences.Editor editor;

    private Toolbar toolbar;
    boolean atuoLoginFlag = false;
    private Switch swStoreId;
    private Switch swLoginAuto;
    private EditText etEmail;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.signin_activity);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        mPref = PreferenceManager.getDefaultSharedPreferences(this);
        atuoLoginFlag = mPref.getBoolean("auto_login", false);
        prefStoreId = getSharedPreferences("id_store", NULL);
        prefStoredId = getSharedPreferences("store_id", NULL);
        etEmail = (EditText) findViewById(R.id.et_signin_email);
        etPassword = (EditText) findViewById(R.id.et_signin_pw);

        swLoginAuto = (Switch) findViewById(R.id.sw_auto_log);
        swStoreId = (Switch) findViewById(R.id.sw_stroe_id);
        setSupportActionBar(toolbar);

        swLoginAuto.setChecked(atuoLoginFlag);
        swStoreId.setChecked(prefStoreId.getBoolean("id_store", false));
        if (prefStoreId.getBoolean("id_store", false)) {
            etEmail.setText(prefStoredId.getString("store_id", ""));
        }
        if (atuoLoginFlag == true) {
            login();
            //자동로그인 일경우 메소드

        }

    }


    public void login() {
        //자동로그인
        if (swLoginAuto.isChecked()) {
            editor = mPref.edit();
            editor.putBoolean("auto_login", true);
            editor.commit();
        }
        //아이디 기억
        if (swStoreId.isChecked()) {
            editor = prefStoreId.edit();
            editor.putBoolean("id_store", true);
            editor.commit();
            editor = prefStoredId.edit();
            editor.putString("store_id", etEmail.getText().toString());
            editor.commit();
        } else {
            //아이디 기억 안하니까 널값으로
            editor = prefStoreId.edit();
            editor.putBoolean("id_store", false);
            editor.commit();
            editor = prefStoredId.edit();
            editor.putString("store_id", "");
            editor.commit();
        }

        ApiService service = ServiceGenerator.createService(ApiService.class);
        Call<Owner> convertedContent = service.request_login(etEmail.getText().toString(), etPassword.getText().toString());
        convertedContent.enqueue(new Callback<Owner>() {
            @Override
            public void onResponse(Call<Owner> call, Response<Owner> response) {
                // if parsing the JSON body failed, `response.body()` returns null
                Owner test = response.body();

                if (test == null) {
                    Log.d("TAG", "아이디비번오류");
                    Toast.makeText(getApplicationContext(), "아이디 또는 비밀번호가 일치하지 않습니다.", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    Owner.uniqueInstance = test;
                }

                if (String.valueOf(Owner.getInstance().getEmail()).equals(etEmail.getText().toString())) {
                    Log.d("TAGG", "로그인성공!");

                    //푸드트럭 정보 요청
                    ApiService service = ServiceGenerator.createService(ApiService.class);
                    Call<FoodTruckModel> callMyTruck = service.requestMyTruckInfo(Owner.getInstance().getId());
                    callMyTruck.enqueue(new Callback<FoodTruckModel>() {
                        @Override
                        public void onResponse(Call<FoodTruckModel> call, Response<FoodTruckModel> response) {
                            FoodTruckModel check_data = response.body();

                            if (check_data == null) {
                                Log.d("TAGG", "푸드트럭 정보 없음");
                                Intent loginIntent = new Intent(SigninActivity.this, TruckInfoActivity.class);
                                startActivity(loginIntent);
                                finish();
                            } else {
                                Log.d("TAGG", "푸드트럭 정보 있음");
                                FoodTruckModel.uniqueInstance = check_data;

                                Toast.makeText(getApplicationContext(), "환영합니다. 푸드트럭", Toast.LENGTH_LONG).show();
                                Intent loginIntent = new Intent(SigninActivity.this, MainActivity.class);
                                startActivity(loginIntent);
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<FoodTruckModel> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "푸드트럭 정보 수신 오류", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    Log.d("TAG", "잘못된 정보");
                }
            }

            @Override
            public void onFailure(Call<Owner> call, Throwable t) {
                Log.d("실패", t.getMessage().toString());
            }
        });
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