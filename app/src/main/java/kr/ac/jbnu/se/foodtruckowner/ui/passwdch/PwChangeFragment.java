package kr.ac.jbnu.se.foodtruckowner.ui.passwdch;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

import kr.ac.jbnu.se.foodtruckowner.R;
import kr.ac.jbnu.se.foodtruckowner.model.FoodTruckModel;
import kr.ac.jbnu.se.foodtruckowner.model.Owner;
import kr.ac.jbnu.se.foodtruckowner.service.ApiService;
import kr.ac.jbnu.se.foodtruckowner.service.ServiceGenerator;
import kr.ac.jbnu.se.foodtruckowner.ui.main.TabFragment;
import kr.ac.jbnu.se.foodtruckowner.ui.navimenu.SettingsFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class PwChangeFragment extends Fragment {

    Button bt_pw_ch;

    private EditText et_change_pw;
    private EditText et_pw_confirm;

    public PwChangeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_passwd_change, container, false);

        et_change_pw = (EditText) view.findViewById(R.id.et_pw_2);
        et_pw_confirm = (EditText) view.findViewById(R.id.et_pw_3);

        bt_pw_ch = (Button) view.findViewById(R.id.bt_pw_ch);
        bt_pw_ch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check_pw(et_change_pw.getText().toString())) {
                    if(check_pw_confirm()) {
                        ApiService service = ServiceGenerator.createService(ApiService.class);
                        Call<Integer> call = service.change_password(Owner.getInstance().getId(), et_change_pw.getText().toString(), et_pw_confirm.getText().toString());
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
        if (et_change_pw.getText().toString().equals(et_pw_confirm.getText().toString())) {
            return true;
        } else {
            return false;
        }
    }
}
