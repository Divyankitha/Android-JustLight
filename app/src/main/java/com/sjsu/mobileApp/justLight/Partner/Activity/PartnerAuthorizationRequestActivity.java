package com.sjsu.mobileApp.justLight.Partner.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sjsu.mobileApp.justLight.Partner.Adapters.PartnerAuthRequestAdapter;
import com.sjsu.mobileApp.justLight.Partner.Data.PartnerAuthRequestData;
import com.sjsu.mobileApp.justLight.R;
import com.sjsu.mobileApp.justLight.Utility.Constants;
import com.sjsu.mobileApp.justLight.Utility.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;



public class PartnerAuthorizationRequestActivity extends AppCompatActivity
{
    ListView AuthRequestList;
    private ArrayAdapter<String> mAdapter;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.partner_auth_request_list);


    }

    @Override
    public void onResume()
    {
        super.onResume();
        //PartnerAuthRequestData A = new PartnerAuthRequestData ();
        new AsyncTaskAuthRequest().execute();

    }


    public class AsyncTaskAuthRequest extends AsyncTask<Object, Object, ArrayList<PartnerAuthRequestData>> {

        private final String LOG_TAG = "Smart Lighting Activity";



        private ArrayList<PartnerAuthRequestData> AuthRequestDetailsFromJson(String appJsonStr) throws JSONException {

            System.out.println("Inside parse function");

            ArrayList<PartnerAuthRequestData> AuthRequests = new ArrayList<PartnerAuthRequestData>();
            JSONArray jsonArray = new JSONArray(appJsonStr);
            System.out.println(jsonArray);

            for(int i=0; i<jsonArray.length();i++)
            {

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String status = jsonObject.getString("status");
                int solutionID = jsonObject.getInt("solution_id");
                int RequestID = jsonObject.getInt("request_id");
                int customerID = jsonObject.getInt("customer_id");
                String customer = jsonObject.getString("customerName");

                PartnerAuthRequestData temp = new PartnerAuthRequestData();
                temp.empty = false;
                temp.status = status;
                temp.customer_id = customerID;
                temp.solution_id = solutionID;
                temp.customer_id = customerID;
                temp.request_id = RequestID;
                temp.customer_name = customer;

                AuthRequests.add(temp);

            }

            return AuthRequests;

        }


        @Override
        protected ArrayList<PartnerAuthRequestData> doInBackground(Object... params) {


            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String AuthRequestJson= null;

            session = new SessionManager(getApplicationContext());
            HashMap<String, String> user = session.getUserDetails();
            String user_ID = user.get(SessionManager.KEY_ID);

            try {
                String baseUrl = Constants.BASE_PARTNERS_URL+ Constants.PartnerAuthorizationRequest + user_ID;
                System.out.println(baseUrl);

                URL url = new URL(baseUrl);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();


                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;

                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }

                AuthRequestJson = buffer.toString();


            } catch (IOException e) {

                Log.e(LOG_TAG, e.getMessage(), e);
                return null;

            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }

            }

            try{
                return AuthRequestDetailsFromJson(AuthRequestJson);
            }catch (JSONException e){
                Log.e(LOG_TAG,e.getMessage(),e);
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(ArrayList<PartnerAuthRequestData> result)
        {
            super.onPostExecute(result);


            AuthRequestList = (ListView)findViewById(R.id.PartnerAuthReqList);
            AuthRequestList.setAdapter(new PartnerAuthRequestAdapter(PartnerAuthorizationRequestActivity.this,result));

        }
    }

    public void goBackToPartnerDashboard(View V)
    {
        Intent i = new Intent(getApplicationContext(), PartnerDashboardActivity.class);
        startActivity(i);
    }
}
