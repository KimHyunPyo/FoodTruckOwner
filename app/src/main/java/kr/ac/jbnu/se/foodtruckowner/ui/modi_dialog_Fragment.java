package kr.ac.jbnu.se.foodtruckowner.ui;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;

import com.github.kimkevin.cachepot.CachePot;
import com.google.gson.JsonObject;

import cn.pedant.SweetAlert.SweetAlertDialog;
import kr.ac.jbnu.se.foodtruckowner.R;
import kr.ac.jbnu.se.foodtruckowner.adapter.MenuAdapter;
import kr.ac.jbnu.se.foodtruckowner.model.FoodTruckModel;
import kr.ac.jbnu.se.foodtruckowner.model.MenuModel;
import kr.ac.jbnu.se.foodtruckowner.model.Owner;

import static java.lang.Integer.parseInt;

public class modi_dialog_Fragment extends DialogFragment {
    EditText et_menu_name;
    EditText et_menu_price;
    Button bt_done;
    Button bt_select_im;
    static String DialogboxTitle;
    MenuAdapter menuAdapter;

    private FoodTruckModel truckInfo;

    public interface InputNameDialogListener {
        void onFinishInputDialog(String inputText);
    }

    //---empty constructor required
    public modi_dialog_Fragment() {

    }

    //---set the title of the dialog window
    public void setDialogTitle(String title) {
        DialogboxTitle = title;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {

        truckInfo = CachePot.getInstance().pop(FoodTruckModel.class); //MainActivity => modi_dialog_Fragment
        Log.d("TAG", "이름이맞냐? : " + truckInfo.getFtName());

        View view = inflater.inflate(
                R.layout.fragment_modi_dialog_, container);

        //---get the EditText and Button views
        et_menu_name = (EditText) view.findViewById(R.id.et_menu_Name);
        et_menu_price = (EditText) view.findViewById(R.id.et_menu_price);
        bt_done = (Button) view.findViewById(R.id.bt_done);
        bt_select_im = (Button) view.findViewById(R.id.bt_select_im);
        //---event handler for the button
        bt_done.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                JsonObject addMenuInfo = new JsonObject();
                addMenuInfo.addProperty("name", et_menu_name.getText().toString());
                addMenuInfo.addProperty("price", et_menu_price.getText().toString());
                addMenuInfo.addProperty("image", ""); //이미지 넣어라.
//                addMenuInfo.addProperty("foodtruck_id", );

                System.out.println("추가됨");
                SweetAlertDialog sd = new SweetAlertDialog(getActivity());
                sd.setCancelable(true);
                sd.setCanceledOnTouchOutside(true);
                sd.show();
                //---gets the calling activity
                //InputNameDialogListener activity = (InputNameDialogListener) getActivity();
                //activity.onFinishInputDialog(et_menu_name.getText().toString());
                //---dismiss the alert
                dismiss();

            }
        });

        bt_select_im.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

            }
        });

        //---show the keyboard automatically
        et_menu_name.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        //---set the title for the dialog
        getDialog().setTitle(DialogboxTitle);

        return view;
    }
}