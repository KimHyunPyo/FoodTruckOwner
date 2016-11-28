package kr.ac.jbnu.se.foodtruckowner.ui.sign;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Pattern;

import kr.ac.jbnu.se.foodtruckowner.R;
import kr.ac.jbnu.se.foodtruckowner.service.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// TODO: 2016-11-26 핸드폰인증, 사업자 등록번호 인증 추가필요
public class SignUpActivity extends AppCompatActivity implements ProgressGenerator.OnCompleteListener {

    private Toolbar toolbar;
    private EditText et_signup_email;
    private EditText et_signup_pw;
    private EditText et_signup_pwconfirm;
    private EditText et_signup_business_num;
    private EditText et_signup_Ph_num;
    private static final String EXTRAS_ENDLESS_MODE = "EXTRAS_ENDLESS_MODE";
    private int signupStatus = 2; //1은 성공, 2는 실패, 3은 중복

    //사진사진

    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_GALLERY = 2;
    ImageView upload2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("회원가입");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        //actionBar.setHomeAsUpIndicator(R.drawable.button_back); //뒤로가기 버튼을 본인이 만든 아이콘으로 하기 위해 필요

        et_signup_email = ((EditText) findViewById(R.id.et_signup_email));
        et_signup_pw = ((EditText) findViewById(R.id.et_signup_pw));
        et_signup_pwconfirm = ((EditText) findViewById(R.id.et_signup_pwconfirm));
        et_signup_business_num = (EditText) findViewById(R.id.et_signup_business_num);
        et_signup_Ph_num = (EditText) findViewById(R.id.et_signup_Ph_num);
        upload2 = (ImageView) findViewById(R.id.upload2);

        Button iu = (Button) findViewById(R.id.img_up);


        final ActionProcessButton bt_singup_fragment_login = (ActionProcessButton) findViewById(R.id.bt_singup_login);
        final ProgressGenerator progressGenerator = new ProgressGenerator(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.getBoolean(EXTRAS_ENDLESS_MODE))
            bt_singup_fragment_login.setMode(ActionProcessButton.Mode.ENDLESS);
        else
            bt_singup_fragment_login.setMode(ActionProcessButton.Mode.PROGRESS);

        bt_singup_fragment_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StartSingUp()) {
                    progressGenerator.start(bt_singup_fragment_login);
                    //bt_singup_fragment_login.setEnabled(false);
                    et_signup_email.setEnabled(false);
                    et_signup_pw.setEnabled(false);
                }
            }
        });

        iu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //버튼 클릭시 처리로직
                Intent intent = new Intent();
                //갤러리호출
                //intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
               // intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                intent.putExtra("crop", "true");
                intent.putExtra("aspectX", 0);
                intent.putExtra("aspectY", 0);
                intent.putExtra("outputX", 200);
                intent.putExtra("outputY", 150);

                try {
                    intent.putExtra("return-data", true);
                    startActivityForResult(Intent.createChooser(intent,
                            "Complete action using"), PICK_FROM_GALLERY);
                    Log.d("카메라", "1");
                } catch (ActivityNotFoundException e) {
                    Log.d("카메라", "실패");
                }
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d("카메라", "2");
        if (requestCode == PICK_FROM_GALLERY) {
            Log.d("카메라", "3");
            Bundle extras2 = data.getExtras();
            if (extras2 != null) {
                Log.d("카메라", "4");
                Bitmap photo = extras2.getParcelable("data");
                upload2.setImageBitmap(photo);
            }
        }
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
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
    }

    private boolean check_email(String paramString) {
        return Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$").matcher(paramString).matches();
    }

//    private boolean check_id(String paramString) {
//        return Pattern.compile("([A-Za-z0-9].{2,10})").matcher(paramString).matches();
//    }

    private boolean check_pw(String paramString) {
        return Pattern.compile("(([A-Za-z0-9]).{7,20})").matcher(paramString).matches();
    }

    private boolean check_pw_confirm() {
        if (et_signup_pw.getText().toString().equals(et_signup_pwconfirm.getText().toString())) {
            return true;
        } else {
            return false;
        }
    }

    private Boolean StartSingUp() {
//        if (pwconfrim) {
//            if (check_id(this.et_signup_email.getText().toString())) {
//                if (check_pw(this.et_signup_pw.getText().toString())) {
//                    getSignUpRequest();
//                    return true;
//                }
//                Toast.makeText(SignUpActivity.this, "비밀번호를 8자이상 입력해주세요", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//            Toast.makeText(SignUpActivity.this, "올바른 아이디 형식이 아닙니다", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        Toast.makeText(SignUpActivity.this, "비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show();
//        return false;

        if (check_email(this.et_signup_email.getText().toString())) {
            if (check_pw(this.et_signup_pw.getText().toString())) {
                if (check_pw_confirm()) {
                    getSignUpRequest();
                    return true;
                }
                Toast.makeText(this, "비밀번호와 비밀번호 확인이 서로 다릅니다.", Toast.LENGTH_LONG).show();
            }
            Toast.makeText(SignUpActivity.this, "비밀번호를 8자이상 입력해주세요", Toast.LENGTH_SHORT).show();
            return false;
        }
        Toast.makeText(SignUpActivity.this, "올바른 이메일 형식이 아닙니다", Toast.LENGTH_SHORT).show();
        return false;
    }


    private void getSignUpRequest() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://server-blackdog11.c9users.io")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);

        Call<Integer> convertedContent = service.owner_join(et_signup_email.getText().toString(), et_signup_pw.getText().toString(),
                et_signup_Ph_num.getText().toString(), et_signup_business_num.getText().toString());

        convertedContent.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.body().toString() == "1") {
                    signupStatus = 1;
                } else if (response.body().toString() == "2") {
                    signupStatus = 2;
                } else {
                    signupStatus = 3;
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.d("실패", t.getMessage().toString());
            }
        });
    }


    @Override
    public void onComplete() {
        if (signupStatus == 1) {
            Toast.makeText(this, "회원가입이 완료되었습니다.", Toast.LENGTH_LONG).show();
            Intent loginIntent = new Intent(SignUpActivity.this, SigninActivity.class);
            SignUpActivity.this.startActivity(loginIntent);
            SignUpActivity.this.finish();
        } else if (signupStatus == 2) {
            Toast.makeText(this, "회원가입에 실패하였습니다.\n잠시 후 다시 시도해주세요.", Toast.LENGTH_LONG).show();
            return;
        } else {
            Toast.makeText(this, "중복된 이메일이 있습니다.", Toast.LENGTH_LONG).show();
        }
    }
}