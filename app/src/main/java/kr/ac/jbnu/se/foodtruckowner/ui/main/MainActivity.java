package kr.ac.jbnu.se.foodtruckowner.ui.main;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Window;

import com.github.kimkevin.cachepot.CachePot;

import kr.ac.jbnu.se.foodtruckowner.CustomCachePot;
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

        ownerInfo = CustomCachePot.getInstance().pop(Owner.class); //SignIn => MainActivity
        Log.d("TAG", "MainActivity: 유저정보 " +  ownerInfo.getEmail());

        requestMyTruckInfo(ownerInfo.getId());

        //CachePot.getInstance().push(ownerInfo); //MainActivity => FragmentMenu
        Log.d("TAG", "MainActivity: 보낸 유저정보" + ownerInfo.getEmail()); //-> 스택처럼 Pop하면 사라지므로 다시 넣어서 써야함


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

        //onwer_info에서 업주 아이디 가져와서 그 업주 아이디를 가진 푸드트럭의 메뉴를 받아온다.
        ApiService service = ServiceGenerator.createService(ApiService.class);
        Call<FoodTruckModel> convertedContent = service.requestMyTruckInfo(ownerId);
        convertedContent.enqueue(new Callback<FoodTruckModel>() {
            @Override
            public void onResponse(Call<FoodTruckModel> call, Response<FoodTruckModel> response) {
                myTruckInfo = response.body();

                if(myTruckInfo != null) {
                    //푸드트럭 정보가 서버에 저장되어 있으면
                    CachePot.getInstance().push(myTruckInfo);// MainActivity => modi_dialog_Fragment
                }
                else {
                    //푸드트럭 정보가 없으면
                }

                Log.d("TAG", "트럭이름 : " + myTruckInfo.getFtName());

            }

            @Override
            public void onFailure(Call<FoodTruckModel> call, Throwable t) {
                Log.d("실패", "onFailure: ");
                Log.d("TAG", t.getMessage());
            }

        });
    }
}