package kr.ac.jbnu.se.foodtruckowner.ui.sign;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import kr.ac.jbnu.se.foodtruckowner.R;
import kr.ac.jbnu.se.foodtruckowner.ui.MainActivity;

public class TruckInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truck_info);

        Intent truckintent = getIntent();

        Button truckbtn = (Button) findViewById(R.id.truckbtn);

        truckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(TruckInfoActivity.this, SigninActivity.class);
                startActivity(loginIntent);
                finish();
            }
        });



    }
}
