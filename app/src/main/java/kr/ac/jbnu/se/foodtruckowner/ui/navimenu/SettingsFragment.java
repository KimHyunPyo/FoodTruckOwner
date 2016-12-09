package kr.ac.jbnu.se.foodtruckowner.ui.navimenu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.preference.PreferenceFragmentCompat;

import kr.ac.jbnu.se.foodtruckowner.R;
import kr.ac.jbnu.se.foodtruckowner.ui.passwdch.PasswdChangeActivity;
import kr.ac.jbnu.se.foodtruckowner.ui.sign.SigninActivity;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    private SharedPreferences mPref;
    private SharedPreferences.Editor editor;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        mPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Preference prefPw = (Preference) findPreference("passw_set");
        Preference prefLogout = (Preference) findPreference("logout");
        prefLogout.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                editor = mPref.edit();
                editor.putBoolean("auto_login",false);
                editor.commit();
                Intent i = new Intent(getActivity(), SigninActivity.class);
                startActivity(i);
                return false;
            }
        });
        prefPw.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                Intent i = new Intent(getActivity(), PasswdChangeActivity.class);
                startActivity(i);
                return false;
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

    }
    @Override
    public void onPause() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
    }
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        //IT NEVER GETS IN HERE!
        //자동로그인 스위치 리스너
        if (key.equals("auto_login"))
        {

        }
        if (key.equals("review_push"))
        {

        }

    }
}
