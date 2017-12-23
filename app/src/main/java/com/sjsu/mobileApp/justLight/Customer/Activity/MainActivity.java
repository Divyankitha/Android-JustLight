package com.sjsu.mobileApp.justLight.Customer.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sjsu.mobileApp.justLight.Partner.Activity.PartnerDashboardActivity;
import com.sjsu.mobileApp.justLight.R;
import com.sjsu.mobileApp.justLight.Utility.SessionManager;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity
{
    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        session = new SessionManager(getApplicationContext());
        session.checkLogin();

        HashMap<String, String> user = session.getUserDetails();
        String Username = user.get(SessionManager.KEY_NAME);

        System.out.println("Session ID: "+SessionManager.KEY_ID);
        String userID = user.get(SessionManager.KEY_ID);
        int ID = 0;
        ID = Integer.parseInt(userID);


        String type;
        type = user.get(SessionManager.KEY_TYPE);


        System.out.println("username:" +Username);
        System.out.println("userID string:" +userID);
        System.out.println("userID int:" +ID);
        System.out.println("Type:" +type);

        if(type != null && type.equals("Customer"))
        {
            //ToDo goto customer dashboard
            System.out.println("Goto customer dashboard");
            Intent intent=new Intent(MainActivity.this,CustomerDashboardActivity.class);
            startActivity(intent);
        }
        else if (type != null && type.equals("Partner"))
        {
            //ToDo goto partner dashboard
            System.out.println("Goto partner dashboard");
            Intent intent=new Intent(MainActivity.this,PartnerDashboardActivity.class); //TODO partner dashboard
            startActivity(intent);
        }
        else
        {
            setContentView(R.layout.activity_main);
        }

    }

    public void signUpButton(View v)
    {
        Intent intent = new Intent(MainActivity.this, UserSignUpActivity.class);
        startActivity(intent);
    }


    public void signInButton(View v)
    {
        Intent intent = new Intent(MainActivity.this, UserSignInActivity.class);
        startActivity(intent);
    }

}
