package kr.ac.jbnu.se.foodtruckowner.ui;

import android.os.Bundle;

import kr.ac.jbnu.se.foodtruckowner.R;

public class PasswdChangeActivity extends BaseDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passwd_change);
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView,new PwChangeFragment()).commit();
    }

}
