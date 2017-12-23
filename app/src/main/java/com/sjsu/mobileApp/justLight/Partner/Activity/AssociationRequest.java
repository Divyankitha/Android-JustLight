package com.sjsu.mobileApp.justLight.Partner.Activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.sjsu.mobileApp.justLight.R;
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



public class AssociationRequest extends AppCompatActivity
{
    Bundle bundle;
    int solutionID;
    String partnerID;
    AlertDialogManager alert = new AlertDialogManager();

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.partner_add_price);

    }

    @Override
    public void onResume()
    {
        super.onResume();
        bundle = getIntent().getExtras();
        solutionID = bundle.getInt("SolutionID");
        partnerID = bundle.getString("PartnerID");

        System.out.println(solutionID + ":" + partnerID);

    }

    public class AsyncTaskCheckAssociation extends AsyncTask<Double, Object, String> {

        HttpResponse response;
        String result;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Double... params)
        {

            System.out.println("Inside do in background");
            try
            {
                JSONObject requestBody = new JSONObject();
                requestBody.put("solutionId", String.valueOf(solutionID));
                requestBody.put("partnerId",partnerID);
                requestBody.put("price",params[0]);

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

                alert.showAlertDialog(AssociationRequest.this, "Association Successfull", "Continue to associate with other products!", false);
            }
            else if(result.equals("Association Exists"))
            {
                alert.showAlertDialog(AssociationRequest.this, "Association Already Exists", "Do you wish to Unassociate?", false);
            }
        }

    }

    public void addPrice(View v)
    {
        EditText p = (EditText)findViewById(R.id.input_price);
        String priceStr = p.getText().toString();
        double price = Double.parseDouble(priceStr);

        AsyncTaskCheckAssociation asyncTaskCheckAssociation = new AsyncTaskCheckAssociation();
        asyncTaskCheckAssociation.execute(price);
    }
}
