package kr.ac.jbnu.se.foodtruckowner.ui.sign;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import kr.ac.jbnu.se.foodtruckowner.R;
import kr.ac.jbnu.se.foodtruckowner.model.FoodTruckModel;

public class TruckInfoActivity extends AppCompatActivity {

    final int REQ_CODE_SELECT_IMAGE = 100;
    private FoodTruckModel model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truck_info);

        Intent truckintent = getIntent();

        Button bt_insert = (Button) findViewById(R.id.bt_insert_truck);
        Button bt_upload = (Button) findViewById(R.id.bt_upload);
        final Spinner city = (Spinner) findViewById(R.id.city);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.city_select, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        city.setAdapter(adapter);


        bt_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent loginIntent = new Intent(TruckInfoActivity.this, SigninActivity.class);
                startActivity(loginIntent);
                finish();
            }
        });

        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        bt_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //버튼 클릭시 처리로직
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);

                File tempFile = new File(Environment.getExternalStorageDirectory() + "/temp.jpg");
                Uri tempUri = Uri.fromFile(tempFile);

                intent.putExtra("crop", "true");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);

                intent.setType("image/*");
            }

            protected void onActivityResult(int requestCode, int resultCode, Intent data) {


                Toast.makeText(getBaseContext(), "resultCode : " + resultCode, Toast.LENGTH_SHORT).show();

                if (requestCode == REQ_CODE_SELECT_IMAGE) {
                    if (resultCode == Activity.RESULT_OK) {
                        try {
                            //Uri에서 이미지 이름을 얻어온다.
                            //String name_Str = getImageNameToUri(data.getData());

                            //이미지 데이터를 비트맵으로 받아온다.
                            Bitmap image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                            ImageView image = (ImageView) findViewById(R.id.imageUpload);

                            //배치해놓은 ImageView에 set
                            image.setImageBitmap(image_bitmap);


                            //Toast.makeText(getBaseContext(), "name_Str : "+name_Str , Toast.LENGTH_SHORT).show();


                        } catch (FileNotFoundException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            public String getImageNameToUri(Uri data) {
                String[] proj = {MediaStore.Images.Media.DATA};
                Cursor cursor = managedQuery(data, proj, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                cursor.moveToFirst();

                String imgPath = cursor.getString(column_index);
                String imgName = imgPath.substring(imgPath.lastIndexOf("/") + 1);

                return imgName;
            }


        });


    }

}










