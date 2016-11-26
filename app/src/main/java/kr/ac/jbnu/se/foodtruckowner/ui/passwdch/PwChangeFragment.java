package kr.ac.jbnu.se.foodtruckowner.ui.passwdch;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import kr.ac.jbnu.se.foodtruckowner.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class PwChangeFragment extends Fragment {

    Button bt_pw_ch;

    public PwChangeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_passwd_change, container, false);
        bt_pw_ch = (Button) view.findViewById(R.id.bt_pw_ch);
        bt_pw_ch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"비번바꿔라",Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
