package com.sjsu.mobileApp.justLight.Customer.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.sjsu.mobileApp.justLight.R;
import com.sjsu.mobileApp.justLight.Utility.SessionManager;
import com.sjsu.mobileApp.justLight.Customer.Data.UserSignUpData;
import com.sjsu.mobileApp.justLight.Utility.AlertDialogManager;
import com.sjsu.mobileApp.justLight.Utility.Constants;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;



public class UserSignUpActivity extends Activity
{
    AlertDialogManager alert = new AlertDialogManager();
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usersignup);
    }

    public void signUp(View v)
    {
        EditText un =(EditText)findViewById(R.id.input_username);
        EditText pass = (EditText)findViewById(R.id.input_password);
        EditText cpass =(EditText)findViewById(R.id.input_confirmPassword);

        RadioButton rd1 = (RadioButton) findViewById(R.id.radioPartner);
        RadioButton rd2 = (RadioButton) findViewById(R.id.radioUser);

        String username= un.getText().toString();
        String password= pass.getText().toString();
        String cpassword= cpass.getText().toString();

        UserSignUpData U = new UserSignUpData();
        U.setUsername(username);
        U.setPassword(password);

        if(rd1.isChecked())
            new AsyncTaskSignupPartner().execute(U);

        else
            new AsyncTaskSignupCustomer().execute(U);

    }

    public class AsyncTaskSignupPartner extends AsyncTask<UserSignUpData, String, UserSignUpData> {

        HttpResponse response;
        SessionManager session;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected UserSignUpData doInBackground(UserSignUpData... params)
        {

            try
            {
                JSONObject requestBody = new JSONObject();
                requestBody.put("username", params[0].username);
                requestBody.put("password",params[0].password);
                requestBody.put("usertype","Partner");
                String request = requestBody.toString();
                StringEntity request_param = new StringEntity(request);

                String Url= Constants.BASE_USER_URL+Constants.AddUser;
                System.out.println(Url);
                HttpPost post = new HttpPost(Url);
                post.setHeader("Content-Type","application/json");
                post.setEntity(request_param);
                HttpClient httpClient = new DefaultHttpClient();
                response = httpClient.execute(post);
                System.out.println("Reached after coming back from Backend API");
                System.out.println(response.getStatusLine().getStatusCode());
                if (response.getStatusLine().getStatusCode() != 200)
                {
                    if(response.getStatusLine().getStatusCode() == 401)
                    {
                        System.out.println("add failed");
                    }
                    else if(response.getStatusLine().getStatusCode() == 400)
                    {
                        System.out.println("Verification failed");
                    }
                    else
                        throw new RuntimeException("Failed: HTTP error code :" + response.getStatusLine().getStatusCode());
                }
                else
                {
                    HttpEntity e = response.getEntity();
                    String i = EntityUtils.toString(e);
                    JSONObject j = new JSONObject(i);
                    if(!i.equals("Request Failed"))
                    {
                        //int userID = Integer.parseInt(i);
                        int userID = j.getInt("UserId");
                        params[0].setID(userID);
                    }
                }
            }
            catch(Exception x)
            {
                throw new RuntimeException("Error from authenticate partner api",x);
            }
            return params[0];
        }

        @Override
        protected void onPostExecute(UserSignUpData U)
        {
            super.onPostExecute(U);
            String userID;
            userID = String.valueOf(U.ID);
            if(U.ID != 0)
            {
                session = new SessionManager(getApplicationContext());
                session.createLoginSession(U.username, userID, "Partner");
                Intent i = new Intent(getApplicationContext(), UserSignUpDetails.class);
                i.putExtra("UserID",userID);
                i.putExtra("type", "Partner");
                startActivity(i);
                finish();
            }
            else
            {
                alert.showAlertDialog(UserSignUpActivity.this, "Sign Up failed..", "Fields missing", false);
            }
        }

    }


    public class AsyncTaskSignupCustomer extends AsyncTask<UserSignUpData, String, UserSignUpData> {

        HttpResponse response;
        SessionManager session;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected UserSignUpData doInBackground(UserSignUpData... params)
        {

            try
            {
                JSONObject requestBody = new JSONObject();
                requestBody.put("username", params[0].username);
                requestBody.put("password",params[0].password);
                requestBody.put("usertype","Customer");
                String request = requestBody.toString();
                StringEntity request_param = new StringEntity(request);
                System.out.println(request);
                String Url= Constants.BASE_USER_URL+Constants.AddUser;
                System.out.println(Url);
                HttpPost post = new HttpPost(Url);
                post.setHeader("Content-Type","application/json");
                post.setEntity(request_param);
                HttpClient httpClient = new DefaultHttpClient();
                response = httpClient.execute(post);
                System.out.println("Reached after coming back from Backend API");
                if (response.getStatusLine().getStatusCode() != 200)
                {
                    if(response.getStatusLine().getStatusCode() == 401)
                    {
                        System.out.println("Verification failed");
                    }
                    else if(response.getStatusLine().getStatusCode() == 400)
                    {
                        System.out.println("Verification failed");
                    }
                    else
                        throw new RuntimeException("Failed: HTTP error code :" + response.getStatusLine().getStatusCode());
                }
                else
                {
                    HttpEntity e = response.getEntity();
                    String i = EntityUtils.toString(e);
                    JSONObject j = new JSONObject(i);
                    if(!i.equals("Request Failed"))
                    {
                        //int userID = Integer.parseInt(i);
                        int userID = j.getInt("UserId");
                        params[0].setID(userID);
                    }
                }
            }
            catch(Exception x)
            {
                throw new RuntimeException("Error from authenticate partner api",x);
            }
            return params[0];
        }

        @Override
        protected void onPostExecute(UserSignUpData U)
        {
            super.onPostExecute(U);
            String userID;
            userID = String.valueOf(U.ID);
            if(U.ID != 0)
            {
                session = new SessionManager(getApplicationContext());
                session.createLoginSession(U.username, userID, "Customer");
                Intent i = new Intent(getApplicationContext(), UserSignUpDetails.class);
                i.putExtra("UserID",userID);
                i.putExtra("type", "Customer");
                startActivity(i);
                finish();
            }
            else
            {
                alert.showAlertDialog(UserSignUpActivity.this, "Sign Up failed..", "Fields missing", false);
            }
        }

    }
}

