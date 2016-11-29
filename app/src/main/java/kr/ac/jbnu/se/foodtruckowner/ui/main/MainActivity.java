package kr.ac.jbnu.se.foodtruckowner.ui.main;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Window;

import kr.ac.jbnu.se.foodtruckowner.R;
import kr.ac.jbnu.se.foodtruckowner.model.FoodTruckModel;
import kr.ac.jbnu.se.foodtruckowner.model.Owner;
import kr.ac.jbnu.se.foodtruckowner.service.ApiService;
import kr.ac.jbnu.se.foodtruckowner.service.ServiceGenerator;
import kr.ac.jbnu.se.foodtruckowner.ui.base.BaseDrawerActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseDrawerActivity {
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

    private FoodTruckModel myTruckInfo;
    private Owner ownerInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        Log.d("TAG", "MainActivity: 유저정보 " +  Owner.getInstance().getEmail());
        Log.d("TAG", "MainActivity: 보낸 유저정보" + Owner.getInstance().getEmail());

//        ReviewFragment reviewFragment = (ReviewFragment) getSupportFragmentManager().findFragmentById(R.id.reviewListFragment);
//        reviewFragment.addItem(ContextCompat.getDrawable(this, R.drawable.profle),
//                "New Box", "New Account Box Black 36dp");
//        @Override
//        protected void onCreate (Bundle savedInstanceState){
//            ReviewFragment reviewFragment = (ReviewFragment) getSupportFragmentManager().findFragmentById(R.id.reviewListFragment);
//            reviewFragment.addItem(ContextCompat.getDrawable(this, R.drawable.profle),
//                    "New Box", "New Account Box Black 36dp");
//        }


        /**
         *Setup the DrawerLayout and NavigationView
         */

        /**
         * Lets inflate the very first fragment
         * Here , we are inflating the TabFragment as the first Fragment
         */
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView,new TabFragment()).commit();
        /**
         * Setup click events on the Navigation View Items.
         */
        /**
         * Setup Drawer Toggle of the Toolbar
         */
    }
}