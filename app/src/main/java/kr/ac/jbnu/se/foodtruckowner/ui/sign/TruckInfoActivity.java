package kr.ac.jbnu.se.foodtruckowner.ui.sign;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import kr.ac.jbnu.se.foodtruckowner.R;

public class TruckInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truck_info);



        Intent truckintent = getIntent();

        Button truckbtn = (Button) findViewById(R.id.truckbtn);
        Button bt_upload = (Button) findViewById(R.id.bt_upload);
        final Spinner city = (Spinner) findViewById(R.id.city);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.city_select, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        city.setAdapter(adapter);


        truckbtn.setOnClickListener(new View.OnClickListener() {
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
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }







}




