package com.sjsu.mobileApp.justLight.Customer.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sjsu.mobileApp.justLight.R;


public class ActivityMainPage extends AppCompatActivity
{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void signUpButton(View v)
    {
        Intent intent = new Intent(ActivityMainPage.this, UserSignUpActivity.class);
        startActivity(intent);
    }


    public void signInButton(View v)
    {
        Intent intent = new Intent(ActivityMainPage.this, UserSignInActivity.class);
        startActivity(intent);
    }
}
