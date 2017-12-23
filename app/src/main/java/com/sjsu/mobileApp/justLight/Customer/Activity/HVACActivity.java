package com.sjsu.mobileApp.justLight.Customer.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sjsu.mobileApp.justLight.R;



public class HVACActivity extends Activity {
    Button HVAC;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hvac);
        HVAC = (Button) findViewById(R.id.HVAC_Analysis_Solution);
        HVAC.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(HVACActivity.this,HVACPatnersActivity.class);
                startActivity(i);
            }
        });
    }
}
