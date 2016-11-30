package kr.ac.jbnu.se.foodtruckowner.ui.navimenu;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import kr.ac.jbnu.se.foodtruckowner.R;
import kr.ac.jbnu.se.foodtruckowner.adapter.MenuAdapter;
import kr.ac.jbnu.se.foodtruckowner.model.MenuModel;
import kr.ac.jbnu.se.foodtruckowner.service.ServiceGenerator;
import kr.ac.jbnu.se.foodtruckowner.ui.modi_dialog_Fragment;
import kr.ac.jbnu.se.foodtruckowner.model.Owner;
import kr.ac.jbnu.se.foodtruckowner.service.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentMenu extends Fragment {

    private static RecyclerView myRecyclerView;
    private MenuAdapter menuAdapter;
    ArrayList<MenuModel> listitems = new ArrayList<>();

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
        Log.d("TAG", "오너 아이디 : " + Owner.getInstance().getId());
        requestTruckMenu(Owner.getInstance().getId());
    }

    public void requestTruckMenu(int owner_id) {

        //onwer_info에서 업주 아이디 가져와서 그 업주 아이디를 가진 푸드트럭의 메뉴를 받아온다.
        ApiService service = ServiceGenerator.createService(ApiService.class);
        Call<ArrayList<MenuModel>> convertedContent = service.truck_menus_owner(owner_id);
        convertedContent.enqueue(new Callback<ArrayList<MenuModel>>() {
            @Override
            public void onResponse(Call<ArrayList<MenuModel>> call, Response<ArrayList<MenuModel>> response) {
                ArrayList<MenuModel> menuList = response.body();

                for (MenuModel menu : menuList) {
                    listitems.add(menu);

                    Log.d("TAG", "메뉴이름" + menu.getTitle());
                }
                showViewList(listitems); //서버에서 받아오면 카드뷰 그려주게하기
            }

            @Override
            public void onFailure(Call<ArrayList<MenuModel>> call, Throwable t) {
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
