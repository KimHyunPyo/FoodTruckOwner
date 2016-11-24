package kr.ac.jbnu.se.foodtruckowner.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import kr.ac.jbnu.se.foodtruckowner.R;
import kr.ac.jbnu.se.foodtruckowner.adapter.MenuAdapter;
import kr.ac.jbnu.se.foodtruckowner.model.MenuModel;

public class FragmentMenu extends Fragment {

    private static RecyclerView myRecyclerView;
    private MenuAdapter menuAdapter;

    ArrayList<MenuModel> listitems = new ArrayList<>();

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
        StaggeredGridLayoutManager MyLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        MyLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        myRecyclerView.setHasFixedSize(true);
        myRecyclerView.setLayoutManager(MyLayoutManager);
        menuAdapter = new MenuAdapter(getContext(), listitems, "");
        System.out.println(menuAdapter.getItemCount() + "1111111111");
        myRecyclerView.setAdapter(menuAdapter);// set adapter on recyclerview
        showViewList();
        initMenu();
        btadd.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                showInputNameDialog();
                    menuAdapter.addMenu();
                }
        });
        return view;
    }

    public void initMenu() {
        for (int i = 0; i < TITLES.length; i++) {
            listitems.add(new MenuModel(TITLES[i], PRICES[i], IMAGES[i]));
        }
    }
    // populate the list view by adding data to arraylist
    private void showViewList() {
        //리스트에 아이템 넣기. 수정요망
        menuAdapter.notifyDataSetChanged();// Notify the adapter
        onResume();
    }
    private void showInputNameDialog() {
        FragmentManager fragmentManager = getFragmentManager();
        modi_dialog_Fragment inputNameDialog = new modi_dialog_Fragment();
        inputNameDialog.setCancelable(false);
        inputNameDialog.setDialogTitle("Enter Name");
        inputNameDialog.show(fragmentManager, "Input Dialog");
    }

}
