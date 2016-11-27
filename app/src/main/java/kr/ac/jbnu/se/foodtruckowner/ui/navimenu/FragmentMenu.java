package kr.ac.jbnu.se.foodtruckowner.ui.navimenu;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.kimkevin.cachepot.CachePot;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import kr.ac.jbnu.se.foodtruckowner.R;
import kr.ac.jbnu.se.foodtruckowner.adapter.MenuAdapter;
import kr.ac.jbnu.se.foodtruckowner.model.MenuModel;
import kr.ac.jbnu.se.foodtruckowner.ui.modi_dialog_Fragment;
import kr.ac.jbnu.se.foodtruckowner.model.Owner;
import kr.ac.jbnu.se.foodtruckowner.service.ApiService;
import kr.ac.jbnu.se.foodtruckowner.ui.sign.SigninActivity;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class FragmentMenu extends Fragment {

    private static RecyclerView myRecyclerView;
    private MenuAdapter menuAdapter;

    ArrayList<MenuModel> listitems = new ArrayList<>();
    private Owner owner_info;

    //String and Integer array for Recycler View Items
    public static final String[] TITLES = {"디저트 ", "피자 3000원", "박도현 0원", "1000원"
            , "2000원"};
    public static final Integer[] IMAGES = {R.drawable.menuitem, R.drawable.menuitem2, R.drawable.menuitem3, R.drawable.menuitem4, R.drawable.menuitem5,
    };
    public static final Integer[] PRICES = {5000, 3000, 0, 1000, 2000};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        Button btadd = (Button) view.findViewById(R.id.bt_menu_add);
        //리사이클뷰(카드뷰)
        myRecyclerView = (RecyclerView) view.findViewById(R.id.menu_view);
        // Here 2 is no. of columns to be displayed
        initMenu();
        btadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputNameDialog();
                //menuAdapter.addMenu();
            }
        });
        return view;
    }

    public void initMenu() {
        listitems.clear();

        Log.d("TAG", "됌?");
        //유저 정보 담은 객체 다른 액티비티에서 담아오는거
        owner_info = CachePot.getInstance().pop(Owner.class); //MainActivity => FragmentMenu
        Log.d("TAG", "오너 아이디 : " + owner_info.getId());

        CachePot.getInstance().push(owner_info); //다시 메뉴버튼 눌렀을 때 오류 안나게하려고

        //오너 아이디를 줘서 그 트럭의 메뉴 받아옴
        requestTruckMenu(owner_info.getId());
    }

    // TODO: 2016-11-27 번호가 중간에 비면 안나옴. 중간 삭제시 id재정렬 해줘야함
    public void requestTruckMenu(int owner_id) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://server-blackdog11.c9users.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);

        //onwer_info에서 업주 아이디 가져와서 그 업주 아이디를 가진 푸드트럭의 메뉴를 받아온다.
        Call<ArrayList<MenuModel>> convertedContent = service.truck_menus_owner(owner_id);

        convertedContent.enqueue(new Callback<ArrayList<MenuModel>>() {
            @Override
            public void onResponse(Response<ArrayList<MenuModel>> response, Retrofit retrofit) {

                ArrayList<MenuModel> menuList = response.body();

                Log.d("TAG", "바디: " + response.body().toString());

                for (MenuModel menu : menuList) {
                    listitems.add(menu);

                    Log.d("TAG", "메뉴이름" + menu.getTitle());
                }
                showViewList(listitems); //서버에서 받아오면 카드뷰 그려주게하기
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("실패", "onFailure: ");
                Log.d("TAG", t.getMessage());
            }

        });
    }
    // populate the list view by adding data to arraylist
    private void showViewList(ArrayList<MenuModel> listitems) {
        StaggeredGridLayoutManager MyLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        MyLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        myRecyclerView.setHasFixedSize(true);
        myRecyclerView.setLayoutManager(MyLayoutManager);
        menuAdapter = new MenuAdapter(getContext(), listitems, "",getActivity().getSupportFragmentManager());
        myRecyclerView.setAdapter(menuAdapter);// set adapter on recyclerview

        menuAdapter.notifyDataSetChanged();// Notify the adapter
        //onResume();
    }
    private void showInputNameDialog() {
        FragmentManager fragmentManager = getFragmentManager();
        modi_dialog_Fragment inputDialog = new modi_dialog_Fragment();
        //inputDialog.setDialogTitle("메뉴 추가");
        inputDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { //다이얼로그에서 메뉴 추가시 새로고침
            @Override
            public void onDismiss(DialogInterface dialog) {
                initMenu();
            }
        });
        inputDialog.show(fragmentManager, "Input Dialog");
    }

}