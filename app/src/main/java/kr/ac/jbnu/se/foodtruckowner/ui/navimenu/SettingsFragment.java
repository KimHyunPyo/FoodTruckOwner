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
        Preference pwPref = (Preference) findPreference("passw_set");
        Preference logout_Pref = (Preference) findPreference("logout");
        logout_Pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                editor = mPref.edit();
                editor.putBoolean("auto_login",false);
                editor.commit();
                Intent i = new Intent(getActivity(), SigninActivity.class);
                startActivity(i);
                return false;
            }
        });
        pwPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
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
        if (key.equals("auto_login"))
        {
            System.out.println("자동로긴 온");
        }
        if (key.equals("review_push"))
        {
            System.out.println("푸쉬 온");
        }

    }
}
