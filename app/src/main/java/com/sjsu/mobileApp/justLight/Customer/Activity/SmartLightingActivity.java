package com.sjsu.mobileApp.justLight.Customer.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sjsu.mobileApp.justLight.R;



public class SmartLightingActivity extends Activity {
    Button smarlighting1,smarlighting2,smarlighting3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_lighting);
        smarlighting1 = (Button) findViewById(R.id.SmartLighting1);
        smarlighting2 = (Button) findViewById(R.id.SmartLighting2);
        smarlighting3 = (Button) findViewById(R.id.SmartLighting3);
        smarlighting3.setText("DayLight Harvest Sensor");
        smarlighting1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(SmartLightingActivity.this,MDPatnersActivity.class);
                startActivity(i);
            }
        });
        smarlighting2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(SmartLightingActivity.this,ODPatnersActivity.class);
                startActivity(i);
            }
        });
        smarlighting3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(SmartLightingActivity.this,DHSPatnersActivity.class);
                startActivity(i);
            }
        });


    }

}




