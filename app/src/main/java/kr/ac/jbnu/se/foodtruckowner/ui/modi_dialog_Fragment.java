package kr.ac.jbnu.se.foodtruckowner.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import cn.pedant.SweetAlert.SweetAlertDialog;
import kr.ac.jbnu.se.foodtruckowner.R;
import kr.ac.jbnu.se.foodtruckowner.adapter.MenuAdapter;
import kr.ac.jbnu.se.foodtruckowner.model.FoodTruckModel;
import kr.ac.jbnu.se.foodtruckowner.service.ApiService;
import kr.ac.jbnu.se.foodtruckowner.service.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;

// TODO: 2016-11-27 메뉴 추가시 중복 처리 해야함
public class modi_dialog_Fragment extends DialogFragment {
    EditText et_menu_name;
    EditText et_menu_price;
    Button bt_done;
    Button bt_select_im;
    static String DialogboxTitle;
    MenuAdapter menuAdapter;

    private ImageView Iv_menu;
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_iMAGE = 2;
    private Uri mImageCaptureUri;
    private String absoultePath;

    private DialogInterface.OnDismissListener onDismissListener;

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

        Log.d("TAG", "메뉴추가 : 트럭 id " + FoodTruckModel.getInstance().getFT_ID());

        View view = inflater.inflate(
                R.layout.fragment_modi_dialog_, container);

        //---get the EditText and Button views
        et_menu_name = (EditText) view.findViewById(R.id.et_menu_Name);
        et_menu_price = (EditText) view.findViewById(R.id.et_menu_price);
        bt_done = (Button) view.findViewById(R.id.bt_done);
        bt_select_im = (Button) view.findViewById(R.id.bt_select_im);
        Iv_menu = (ImageView) view.findViewById(R.id.Iv_menu);
        //---event handler for the button
        bt_done.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!et_menu_name.getText().toString().matches("")) {
                    if (!et_menu_price.getText().toString().matches("")) {
                        JsonObject addMenuInfo = new JsonObject();
                        addMenuInfo.addProperty("name", et_menu_name.getText().toString());
                        addMenuInfo.addProperty("price", et_menu_price.getText().toString());
                        addMenuInfo.addProperty("image", ""); //이미지 넣어라.
                        addMenuInfo.addProperty("foodtruck_id", FoodTruckModel.getInstance().getFT_ID());
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

                DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doTakePhotoAction();
                    }
                };
                DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doTakeAlbumAction();
                    }
                };

                DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                };
                new AlertDialog.Builder(getContext())
                        .setTitle("업로드할 이미지 선택")
                        .setPositiveButton("사진촬영", cameraListener)
                        .setNeutralButton("앨범선택", albumListener)
                        .setNegativeButton("취소", cancelListener)
                        .show();

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

    public void doTakePhotoAction() // 카메라 촬영 후 이미지 가져오기
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // 임시로 사용할 파일의 경로를 생성
        String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
        mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));

        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        startActivityForResult(intent, PICK_FROM_CAMERA);
    }
    public void doTakeAlbumAction() // 앨범에서 이미지 가져오기
    {
        // 앨범 호출
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if(resultCode != RESULT_OK)
            return;

        switch(requestCode)
        {
            case PICK_FROM_ALBUM:
            {
                // 이후의 처리가 카메라와 같으므로 일단  break없이 진행합니다.
                // 실제 코드에서는 좀더 합리적인 방법을 선택하시기 바랍니다.
                mImageCaptureUri = data.getData();
                Log.d("SmartWheel",mImageCaptureUri.getPath().toString());
            }

            case PICK_FROM_CAMERA:
            {
                // 이미지를 가져온 이후의 리사이즈할 이미지 크기를 결정합니다.
                // 이후에 이미지 크롭 어플리케이션을 호출하게 됩니다.
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri, "image/*");

                // CROP할 이미지를 200*200 크기로 저장
                intent.putExtra("outputX", 300); // CROP한 이미지의 x축 크기
                intent.putExtra("outputY", 300); // CROP한 이미지의 y축 크기
                intent.putExtra("aspectX", 1); // CROP 박스의 X축 비율
                intent.putExtra("aspectY", 1); // CROP 박스의 Y축 비율
                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, CROP_FROM_iMAGE); // CROP_FROM_CAMERA case문 이동
                break;
            }
            case CROP_FROM_iMAGE:
            {
                // 크롭이 된 이후의 이미지를 넘겨 받습니다.
                // 이미지뷰에 이미지를 보여준다거나 부가적인 작업 이후에
                // 임시 파일을 삭제합니다.
                if(resultCode != RESULT_OK) {
                    return;
                }

                final Bundle extras = data.getExtras();

                // CROP된 이미지를 저장하기 위한 FILE 경로
                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+
                        "/SmartWheel/"+System.currentTimeMillis()+".jpg";

                if(extras != null)
                {
                    //uproadPhoto.bringToFront();
                    Bitmap photo = extras.getParcelable("data"); // CROP된 BITMAP
                   Iv_menu.setImageBitmap(photo); // 레이아웃의 이미지칸에 CROP된 BITMAP을 보여줌
                    Iv_menu.bringToFront();
                    storeCropImage(photo, filePath); // CROP된 이미지를 외부저장소, 앨범에 저장한다.
                    absoultePath = filePath;
                    break;

                }
                // 임시 파일 삭제
                File f = new File(mImageCaptureUri.getPath());
                if(f.exists())
                {
                    f.delete();
                }
            }
        }
    }

    private void storeCropImage(Bitmap bitmap, String filePath) {
        // SmartWheel 폴더를 생성하여 이미지를 저장하는 방식이다.
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/SmartWheel";
        File directory_SmartWheel = new File(dirPath);

        if(!directory_SmartWheel.exists()) // SmartWheel 디렉터리에 폴더가 없다면 (새로 이미지를 저장할 경우에 속한다.)
            directory_SmartWheel.mkdir();

        File copyFile = new File(filePath);
        BufferedOutputStream out = null;

        try {

            copyFile.createNewFile();
            out = new BufferedOutputStream(new FileOutputStream(copyFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

            // sendBroadcast를 통해 Crop된 사진을 앨범에 보이도록 갱신한다.
            getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.fromFile(copyFile)));
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void requestAddMenu(JsonObject addMenuInfo) {

        ApiService service = ServiceGenerator.createService(ApiService.class);
        Call<Boolean> convertedContent = service.add_menu(addMenuInfo);
        convertedContent.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Log.d("TAG", "메뉴추가 성공?" + response.body().toString());

                Boolean menuAddSuccess = response.body();

                if (menuAddSuccess == true) {
                    SweetAlertDialog sd = new SweetAlertDialog(getActivity());
                    sd.setCancelable(true);
                    sd.setCanceledOnTouchOutside(true);
                    sd.setTitleText("완료 되었습니다.");
                    sd.show();
                    dismiss();
                } else {
                    return;
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
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