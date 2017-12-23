package com.sjsu.mobileApp.justLight.Customer.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.sjsu.mobileApp.justLight.Partner.Activity.PartnerDashboardActivity;
import com.sjsu.mobileApp.justLight.R;
import com.sjsu.mobileApp.justLight.Utility.SessionManager;
import com.sjsu.mobileApp.justLight.Customer.Data.UserSignUpDetailsData;
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



public class UserSignUpDetails extends Activity
{
    AlertDialogManager alert = new AlertDialogManager();
    Bundle bundle;
    String UserID, UserType, WorkInfo, joinedDate, dob, RegistrationID;
    int rating;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completesetup);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        bundle = getIntent().getExtras();
        UserID = bundle.getString("UserID");
        UserType = bundle.getString("type");
        if(UserType.equals("Customer"))
        {
           WorkInfo = "Individual Business";
            dob = "1986-10-04";
            RegistrationID = "01198";
            rating = 0;

        }
        else {
            WorkInfo = "JustLight Associate";
            dob = "1980-11-14";
            RegistrationID = "02298";
            rating = 4;
        }

        joinedDate = "2017-12-2";

    }

    public void Save(View v)
    {
        EditText fn =(EditText)findViewById(R.id.input_fullname);
        EditText cn = (EditText)findViewById(R.id.input_contactNo);
        EditText eid =(EditText)findViewById(R.id.input_emailId);
        EditText add = (EditText)findViewById(R.id.input_address);

        String fullname = fn.getText().toString();
        String contact = cn.getText().toString();
        String emailId = eid.getText().toString();
        String address = add.getText().toString();
        String [] addressStr = address.split(" ");

        UserSignUpDetailsData S = new UserSignUpDetailsData();
        S.fullname = fullname;
        S.contact = contact;
        S.email = emailId;
        S.registerID = "1105";
        S.address = addressStr[0];
        S.city = addressStr[1];
        S.pincode = addressStr[2];

        new AsyncTaskSignupDetails().execute(S);



    }

    public class AsyncTaskSignupDetails extends AsyncTask<UserSignUpDetailsData, String, UserSignUpDetailsData> {

        HttpResponse response;
        SessionManager session;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected UserSignUpDetailsData doInBackground(UserSignUpDetailsData... params)
        {

            try
            {
                JSONObject requestBody = new JSONObject();
                requestBody.put("userId", UserID);
                requestBody.put("fullname",params[0].fullname);
                requestBody.put("userGroupType", UserType );
                requestBody.put("streetAddress",params[0].address);
                requestBody.put("city",params[0].city);
                requestBody.put("pincode",params[0].pincode);
                requestBody.put("workInfo",WorkInfo);
                requestBody.put("gender","Female");
                requestBody.put("dob",dob);
                requestBody.put("contact",params[0].contact);
                requestBody.put("emailId",params[0].email);
                requestBody.put("registrationId",RegistrationID);
                requestBody.put("joinedDate",joinedDate);
                requestBody.put("rating",rating);

                String request = requestBody.toString();
                StringEntity request_param = new StringEntity(request);
                System.out.println(request);
                String Url= Constants.BASE_USER_URL+Constants.AddUserProfile;
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
                    /*if(i.equals("User details inserted succesfully!!"))
                    {*/
                        //int userID = Integer.parseInt(i);
                        //int userID = j.getInt("UserId"); //TODO check the result
                        params[0].setUserid(1);


                    //}
                }
            }
            catch(Exception x)
            {
                throw new RuntimeException("Error from authenticate partner api",x);
            }
            return params[0];
        }

        @Override
        protected void onPostExecute(UserSignUpDetailsData U)
        {
            super.onPostExecute(U);
            String userID;
            userID = String.valueOf(U.userid);
            if(U.userid != 0)
            {
                /*session = new SessionManager(getApplicationContext());
                session.createLoginSession(U.fullname, userID, "Partner");*/
                if(UserType.equals("Customer")) {
                    Intent i = new Intent(getApplicationContext(), CustomerDashboardActivity.class);
                    startActivity(i);
                    finish();
                }
                else if(UserType.equals("Partner"))
                {
                    Intent i = new Intent(getApplicationContext(), PartnerDashboardActivity.class); //TODO partner dashboard
                    startActivity(i);
                    finish();
                }
            }
            else
            {
                alert.showAlertDialog(UserSignUpDetails.this, "Sign Up failed..", "Fields missing", false);
            }
        }

    }
}
