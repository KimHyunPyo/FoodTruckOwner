package kr.ac.jbnu.se.foodtruckowner.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.kimkevin.cachepot.CachePot;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import kr.ac.jbnu.se.foodtruckowner.R;
import kr.ac.jbnu.se.foodtruckowner.adapter.MenuAdapter;
import kr.ac.jbnu.se.foodtruckowner.model.FoodTruckModel;
import kr.ac.jbnu.se.foodtruckowner.model.MenuModel;
import kr.ac.jbnu.se.foodtruckowner.model.Owner;
import kr.ac.jbnu.se.foodtruckowner.service.ApiService;
import kr.ac.jbnu.se.foodtruckowner.ui.navimenu.FragmentMenu;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

import static java.lang.Integer.parseInt;
// TODO: 2016-11-27 메뉴 추가시 중복 처리 해야함
public class modi_dialog_Fragment extends DialogFragment {
    EditText et_menu_name;
    EditText et_menu_price;
    Button bt_done;
    Button bt_select_im;
    static String DialogboxTitle;
    MenuAdapter menuAdapter;


    private DialogInterface.OnDismissListener onDismissListener;



    private FoodTruckModel truckInfo;
    private Boolean menuAddSuccess;

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

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
        Log.d("TAG", "메뉴추가 : 트럭 id " + truckInfo.getFT_ID());

        CachePot.getInstance().push(truckInfo); //다이얼로그 다시 열었을 때 오류 안나게하려고

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

                if (!et_menu_name.getText().toString().matches("")) {
                    if (!et_menu_price.getText().toString().matches("")) {
                        JsonObject addMenuInfo = new JsonObject();
                        addMenuInfo.addProperty("name", et_menu_name.getText().toString());
                        addMenuInfo.addProperty("price", et_menu_price.getText().toString());
                        addMenuInfo.addProperty("image", ""); //이미지 넣어라.
                        addMenuInfo.addProperty("foodtruck_id", truckInfo.getFT_ID());

                        requestAddMenu(addMenuInfo);
                    } else {
                        Toast.makeText(getActivity(), "메뉴 가격을 입력하세요.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "메뉴 이름을 입력하세요.", Toast.LENGTH_LONG).show();
                }

                Log.d("TAG", "텍스트?: " + et_menu_name.getText());

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
        //getDialog().setTitle("");

        return view;
    }

    // TODO: 2016-11-27 Add 후 refresh 해줘야함.
    public void requestAddMenu(JsonObject addMenuInfo) {
        ////서버로 add_menu 요청. 서버 리턴값 성공시 true, 실패시 false
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://server-blackdog11.c9users.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);

        Call<Boolean> convertedContent = service.add_menu(addMenuInfo);

        convertedContent.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Response<Boolean> response, Retrofit retrofit) {
                Log.d("TAG", "메뉴추가 성공?" + response.body().toString());

                menuAddSuccess = response.body();

                if (menuAddSuccess == true) {
                    SweetAlertDialog sd = new SweetAlertDialog(getActivity());
                    sd.setCancelable(true);
                    sd.setCanceledOnTouchOutside(true);
                    sd.setTitleText("완료 되었습니다.");
                    sd.show();
                    //---gets the calling activity
                    //InputNameDialogListener activity = (InputNameDialogListener) getActivity();
                    //activity.onFinishInputDialog(et_menu_name.getText().toString());
                    //---dismiss the alert
                    dismiss();
                } else {
                    return;
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("실패", "onFailure: ");
                Log.d("TAG", t.getMessage());
            }
        });
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if(onDismissListener != null) {
            onDismissListener.onDismiss(dialog);
        }
        
    }
}