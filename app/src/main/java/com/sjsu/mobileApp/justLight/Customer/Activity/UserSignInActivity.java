package com.sjsu.mobileApp.justLight.Customer.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.sjsu.mobileApp.justLight.Partner.Activity.PartnerDashboardActivity;
import com.sjsu.mobileApp.justLight.R;
import com.sjsu.mobileApp.justLight.Utility.SessionManager;
import com.sjsu.mobileApp.justLight.Customer.Data.LoginInfo;
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



public class UserSignInActivity extends Activity {

    EditText txtUsername, txtPassword;
    Button btnLogin;

    AlertDialogManager alert = new AlertDialogManager();
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlogin);

        session = new SessionManager(getApplicationContext());

        txtUsername = (EditText) findViewById(R.id.input_username);
        txtPassword = (EditText) findViewById(R.id.input_password);

        btnLogin = (Button) findViewById(R.id.btn_signin);
    }


    /*public void signIn(View view) {
            Intent intent = new Intent(UserSignInActivity.this, CustomerDashboardActivity.class);
            startActivity(intent);

    }*/

          public void login(View v)
            {
                System.out.println("Inside login button");
                String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();
                Bundle bundle = getIntent().getExtras();
                RadioButton rd1 = (RadioButton) findViewById(R.id.radioUser);
                RadioButton rd2 = (RadioButton) findViewById(R.id.radioPartner);


                LoginInfo L = new LoginInfo();

                if (username.trim().length() > 0 && password.trim().length() > 0)
                {
                    L.setUsername(username);
                    L.setPassword(password);
                    System.out.println(L.Username + L.Password);
                    if (rd1.isChecked())
                        new AsyncTaskLoginUser().execute(L);
                    else if (rd2.isChecked())
                        new AsyncTaskLoginPartner().execute(L);
                } else {

                    alert.showAlertDialog(UserSignInActivity.this, "Login failed..", "Please enter username and password", false);
                }

                /*HashMap<String, String>user = session.getUserDetails();
                System.out.println("Id from session:" +user.get(session.KEY_ID));*/

            }





    public class AsyncTaskLoginUser extends AsyncTask<LoginInfo, String, LoginInfo> {

        HttpResponse response;
        SessionManager session;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected LoginInfo doInBackground(LoginInfo... params)
        {

            System.out.println("Inside do in background");
            try
            {
                JSONObject requestBody = new JSONObject();
                requestBody.put("username", params[0].Username);
                requestBody.put("password",params[0].Password);
                requestBody.put("usertype","Customer");
                String request = requestBody.toString();
                StringEntity request_param = new StringEntity(request);

                System.out.println("Body\n" + request);

                String Url= Constants.BASE_USER_URL+Constants.Authenticate;

                System.out.println(Url);
                HttpPost post = new HttpPost(Url);
                post.setHeader("Content-Type","application/json");
                post.setEntity(request_param);
                HttpClient httpClient = new DefaultHttpClient();
                response = httpClient.execute(post);

                System.out.println("Reached after coming back from Backend API");

                System.out.println("response" +response);
                System.out.println("Response complete");

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
                    if(!i.equals("Verification Failed"))
                    {
                        //int userID = Integer.parseInt(i);
                        System.out.println(i ); //+ j.getInt("Username and password entered is correct for userId:"));
                        int userID = j.getInt("Username and password entered is correct for userId: ");
                        params[0].setID(userID);
                    }
                }
            }
            catch(Exception x)
            {
                throw new RuntimeException("Error from authenticate customer api",x);
            }
            return params[0];
        }

        @Override
        protected void onPostExecute(LoginInfo L)
        {
            super.onPostExecute(L);
            String userID;
            userID = String.valueOf(L.ID);
            if(L.ID != 0)
            {
                session = new SessionManager(getApplicationContext());
                session.createLoginSession(L.Username, userID, "Customer");
                Intent i = new Intent(getApplicationContext(), CustomerDashboardActivity.class);
                startActivity(i);
                finish();
            }
            else
            {
                alert.showAlertDialog(UserSignInActivity.this, "Login failed..", "Username/Password is incorrect", false);
            }
        }

    }


    public class AsyncTaskLoginPartner extends AsyncTask<LoginInfo, String, LoginInfo> {

        HttpResponse response;
        SessionManager session;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected LoginInfo doInBackground(LoginInfo... params)
        {

            try
            {
                JSONObject requestBody = new JSONObject();
                requestBody.put("username", params[0].Username);
                requestBody.put("password",params[0].Password);
                requestBody.put("usertype","Partner");
                String request = requestBody.toString();
                StringEntity request_param = new StringEntity(request);
                System.out.println(request);
                String Url= Constants.BASE_USER_URL+Constants.Authenticate;
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
                    if(!i.equals("Verification Failed"))
                    {
                        //int userID = Integer.parseInt(i);
                        int userID = j.getInt("Username and password entered is correct for userId: ");
                        params[0].setID(userID);
                    }
                }
            }
            catch(Exception x)
            {
                throw new RuntimeException("Error from authenticate doc api",x);
            }
            return params[0];
        }

        @Override
        protected void onPostExecute(LoginInfo L)
        {
            super.onPostExecute(L);
            String userID;
            userID = String.valueOf(L.ID);
            if(L.ID != 0)
            {
                session = new SessionManager(getApplicationContext());
                session.createLoginSession(L.Username, userID, "Partner");
                Intent i = new Intent(getApplicationContext(), PartnerDashboardActivity.class); //ToDo partner dashboard
                startActivity(i);
                finish();
            }
            else
            {
                alert.showAlertDialog(UserSignInActivity.this, "Login failed..", "Username/Password is incorrect", false);
            }
        }

    }

}
