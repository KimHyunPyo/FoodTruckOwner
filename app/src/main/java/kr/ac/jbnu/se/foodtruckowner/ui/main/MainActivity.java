package kr.ac.jbnu.se.foodtruckowner.ui.main;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Window;

import com.github.kimkevin.cachepot.CachePot;

import java.util.ArrayList;

import kr.ac.jbnu.se.foodtruckowner.R;
import kr.ac.jbnu.se.foodtruckowner.model.FoodTruckModel;
import kr.ac.jbnu.se.foodtruckowner.model.MenuModel;
import kr.ac.jbnu.se.foodtruckowner.model.Owner;
import kr.ac.jbnu.se.foodtruckowner.service.ApiService;
import kr.ac.jbnu.se.foodtruckowner.ui.base.BaseDrawerActivity;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

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

        ownerInfo = CachePot.getInstance().pop(Owner.class); //SignIn => MainActivity
        Log.d("TAG", "MainActivity: 유저정보 " +  ownerInfo.getEmail());

        requestMyTruckInfo(ownerInfo.getId());

        //CachePot.getInstance().push(ownerInfo); //MainActivity => FragmentMenu
        //Log.d("TAG", "MainActivity: 보낸 유저정보" + ownerInfo.getEmail()); -> 이거는 Pop해서 안되는줄 알고 써놨던거 잠시만 남겨두삼


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

    public void requestMyTruckInfo(int ownerId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://server-blackdog11.c9users.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);

        //onwer_info에서 업주 아이디 가져와서 그 업주 아이디를 가진 푸드트럭의 메뉴를 받아온다.
        Call<FoodTruckModel> convertedContent = service.requestMyTruckInfo(ownerId);

        convertedContent.enqueue(new Callback<FoodTruckModel>() {
            @Override
            public void onResponse(Response<FoodTruckModel> response, Retrofit retrofit) {

                 myTruckInfo = response.body();
                CachePot.getInstance().push(myTruckInfo);// MainActivity => modi_dialog_Fragment

                Log.d("TAG", "트럭이름 : " + myTruckInfo.getFtName());

            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("실패", "onFailure: ");
                Log.d("TAG", t.getMessage());
            }

        });
    }
}