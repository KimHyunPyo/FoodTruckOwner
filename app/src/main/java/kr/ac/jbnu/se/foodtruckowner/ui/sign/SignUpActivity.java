package kr.ac.jbnu.se.foodtruckowner.ui.sign;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;

import java.util.regex.Pattern;

import kr.ac.jbnu.se.foodtruckowner.R;
import kr.ac.jbnu.se.foodtruckowner.service.ApiService;
import kr.ac.jbnu.se.foodtruckowner.service.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// TODO: 2016-11-26 핸드폰인증, 사업자 등록번호 인증 추가필요
public class SignUpActivity extends AppCompatActivity implements ProgressGenerator.OnCompleteListener {

    private Toolbar toolbar;
    private EditText etSignupEmail;
    private EditText etSignupPw;
    private EditText etSignupConfirmPw;
    private EditText etSignupBusinessNum;
    private EditText etSignupPhnum;
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

        etSignupEmail = ((EditText) findViewById(R.id.et_signup_email));
        etSignupPw = ((EditText) findViewById(R.id.et_signup_pw));
        etSignupConfirmPw = ((EditText) findViewById(R.id.et_signup_pwconfirm));
        etSignupBusinessNum = (EditText) findViewById(R.id.et_signup_business_num);
        etSignupPhnum = (EditText) findViewById(R.id.et_signup_Ph_num);


        final ActionProcessButton btSingupFragmentLogin = (ActionProcessButton) findViewById(R.id.bt_singup_login);
        final ProgressGenerator progressGenerator = new ProgressGenerator(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.getBoolean(EXTRAS_ENDLESS_MODE))
            btSingupFragmentLogin.setMode(ActionProcessButton.Mode.ENDLESS);
        else
            btSingupFragmentLogin.setMode(ActionProcessButton.Mode.PROGRESS);

        btSingupFragmentLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startSingUp()) {
                    progressGenerator.start(btSingupFragmentLogin);
                    //bt_singup_fragment_login.setEnabled(false);
                    etSignupEmail.setEnabled(false);
                    etSignupPw.setEnabled(false);
                }
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
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
    }

    private boolean checkEmail(String paramString) {
        return Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$").matcher(paramString).matches();
    }

    private boolean checkPw(String paramString) {
        return Pattern.compile("(([A-Za-z0-9]).{7,20})").matcher(paramString).matches();
    }

    private boolean checkPwConfirm() {
        if (etSignupPw.getText().toString().equals(etSignupConfirmPw.getText().toString())) {
            return true;
        } else {
            return false;
        }
    }

    private Boolean startSingUp() {
        if (checkEmail(this.etSignupEmail.getText().toString())) {
            if (checkPw(this.etSignupPw.getText().toString())) {
                if (checkPwConfirm()) {
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

        ApiService service = ServiceGenerator.createService(ApiService.class);
        Call<Integer> convertedContent = service.owner_join(etSignupEmail.getText().toString(), etSignupPw.getText().toString(),
                etSignupPhnum.getText().toString(), etSignupBusinessNum.getText().toString());

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