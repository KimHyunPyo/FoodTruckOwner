package kr.ac.jbnu.se.foodtruckowner.ui.passwdch;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

import kr.ac.jbnu.se.foodtruckowner.R;
import kr.ac.jbnu.se.foodtruckowner.model.Owner;
import kr.ac.jbnu.se.foodtruckowner.service.ApiService;
import kr.ac.jbnu.se.foodtruckowner.service.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class PwChangeFragment extends Fragment {

    Button btChangePw;

    private EditText etChangePw;
    private EditText etConfirmPw;

    public PwChangeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_passwd_change, container, false);

        etChangePw = (EditText) view.findViewById(R.id.et_pw_2);
        etConfirmPw = (EditText) view.findViewById(R.id.et_pw_3);

        btChangePw = (Button) view.findViewById(R.id.bt_pw_ch);
        btChangePw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check_pw(etChangePw.getText().toString())) {
                    if(check_pw_confirm()) {
                        ApiService service = ServiceGenerator.createService(ApiService.class);
                        Call<Integer> call = service.change_password(Owner.getInstance().getId(), etChangePw.getText().toString(), etConfirmPw.getText().toString());
                        call.enqueue(new Callback<Integer>() {
                            @Override
                            public void onResponse(Call<Integer> call, Response<Integer> response) {
                                int actionCheck = response.body();

                                switch(actionCheck) {
                                    case 1: {
                                        Toast.makeText(getActivity(), "비밀번호 변경 성공", Toast.LENGTH_SHORT).show();
                                        getActivity().finish();
                                        break;
                                    }
                                    case 2:
                                    case 3: {
                                        Toast.makeText(getActivity(), "비밀번호 변경 실패", Toast.LENGTH_SHORT).show();
                                        break;
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<Integer> call, Throwable t) {

                            }
                        });

                    } else {
                        Toast.makeText(getActivity(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "비밀번호 8자리 이상 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private boolean check_pw(String paramString) {
        return Pattern.compile("(([A-Za-z0-9]).{7,20})").matcher(paramString).matches();
    }

    private boolean check_pw_confirm() {
        if (etChangePw.getText().toString().equals(etConfirmPw.getText().toString())) {
            return true;
        } else {
            return false;
        }
    }
}
