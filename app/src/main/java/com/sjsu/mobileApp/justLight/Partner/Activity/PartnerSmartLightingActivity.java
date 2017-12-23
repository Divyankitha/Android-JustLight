package com.sjsu.mobileApp.justLight.Partner.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.sjsu.mobileApp.justLight.R;
import com.sjsu.mobileApp.justLight.Utility.AlertDialogPartner;
import com.sjsu.mobileApp.justLight.Utility.Constants;
import com.sjsu.mobileApp.justLight.Utility.SessionManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.HashMap;




public class PartnerSmartLightingActivity extends AppCompatActivity
{
    Button smartLight1;
    Button smartLight2;
    Button smartLight3;
    int solutionID;
    String UserID;
    AlertDialogPartner alert = new AlertDialogPartner ();

    SessionManager session;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner_smart_lighting);

        session = new SessionManager(getApplicationContext());
        HashMap<String,String> user = session.getUserDetails();
        UserID =user.get(SessionManager.KEY_ID);


        smartLight1 = (Button) findViewById(R.id.PartnerSmartLighting1);
        smartLight1.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                solutionID = 1;
                Intent i = new Intent(getApplicationContext(), AssociationRequest.class);

                i.putExtra("SolutionID", solutionID);
                i.putExtra("PartnerID", UserID);

                startActivity(i);
                /*AsyncTaskCheckAssociation asyncTaskCheckAssociation = new AsyncTaskCheckAssociation();
                asyncTaskCheckAssociation.execute();*/
            }
        });

        smartLight2 = (Button) findViewById(R.id.PartnerSmartLighting2);
        smartLight2.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                solutionID = 2;
                Intent i = new Intent(getApplicationContext(), AssociationRequest.class);

                i.putExtra("SolutionID", solutionID);
                i.putExtra("PartnerID", UserID);
                startActivity(i);
                /*AsyncTaskCheckAssociation asyncTaskCheckAssociation = new AsyncTaskCheckAssociation();
                asyncTaskCheckAssociation.execute();*/
            }
        });

        smartLight3 = (Button) findViewById(R.id.PartnerSmartLighting3);
        smartLight3.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                solutionID = 3;
                Intent i = new Intent(getApplicationContext(), AssociationRequest.class);

                i.putExtra("SolutionID", solutionID);
                i.putExtra("PartnerID", UserID);
                startActivity(i);
                /*AsyncTaskCheckAssociation asyncTaskCheckAssociation = new AsyncTaskCheckAssociation();
                asyncTaskCheckAssociation.execute();*/
            }
        });
    }


    public class AsyncTaskCheckAssociation extends AsyncTask<Object, Object, String> {

        HttpResponse response;
        String result;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Object... params)
        {

            System.out.println("Inside do in background");
            try
            {
                JSONObject requestBody = new JSONObject();
                requestBody.put("solutionId", String.valueOf(solutionID));
                requestBody.put("partnerId",UserID);

                String request = requestBody.toString();
                StringEntity request_param = new StringEntity(request);

                System.out.println("Body\n" + request);

                String Url= Constants.BASE_PARTNERS_URL+Constants.PartnerAssociationRequest;

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
                        System.out.println("Verification failed 401");
                    }
                    else if(response.getStatusLine().getStatusCode() == 400)
                    {
                        System.out.println("Verification failed 400");
                    }
                    else if(response.getStatusLine().getStatusCode() == 500)
                    {
                        result = "Association Exists";
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
                        result = j.getString("string");

                    }
                }
            }
            catch(Exception x)
            {
                throw new RuntimeException("Error from authenticate customer api",x);
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);

            if(result.equals("Request Succeeded!"))
            {

                alert.showAlertDialog(PartnerSmartLightingActivity.this, "Association Successfull", "Continue to associate with other products!", false, UserID,String.valueOf(solutionID));
            }
            else if(result.equals("Association Exists"))
            {
                alert.showAlertDialog(PartnerSmartLightingActivity.this, "Association Already Exists", "Do you wish to Unassociate?", false, UserID,String.valueOf(solutionID));
            }
        }

    }
}
