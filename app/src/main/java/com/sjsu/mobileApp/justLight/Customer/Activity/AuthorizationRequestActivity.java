package com.sjsu.mobileApp.justLight.Customer.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sjsu.mobileApp.justLight.R;
import com.sjsu.mobileApp.justLight.Utility.SessionManager;
import com.sjsu.mobileApp.justLight.Customer.Data.AuthRequestCustomer;
import com.sjsu.mobileApp.justLight.Customer.Adapters.AuthRequestAdapter;
import com.sjsu.mobileApp.justLight.Utility.Constants;

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


public class AuthorizationRequestActivity extends AppCompatActivity
{
    ListView AuthRequestList;
    private ArrayAdapter<String> mAdapter;
    SessionManager session;
    AlertDialog.Builder alert;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorization_request_activity);


    }

    @Override
    public void onResume()
    {
        super.onResume();
        AuthRequestCustomer A = new AuthRequestCustomer();
        new AsyncTaskAuthRequest().execute(A);

    }


    public class AsyncTaskAuthRequest extends AsyncTask<AuthRequestCustomer, Object, ArrayList<AuthRequestCustomer>> {

            private final String LOG_TAG = "Smart Lighting Activity";
            ArrayList<String> statusList = new ArrayList<>();
            ArrayList<String> partnerList = new ArrayList<>();
            ArrayList<String> solutionList = new ArrayList<>();
            ArrayList<Integer> requestList = new ArrayList<>();


            private ArrayList<AuthRequestCustomer> AuthRequestDetailsFromJson(String appJsonStr) throws JSONException {

                System.out.println("Inside parse function");

                ArrayList<AuthRequestCustomer> AuthRequests = new ArrayList<AuthRequestCustomer>();
                JSONArray jsonArray = new JSONArray(appJsonStr);
                System.out.println(jsonArray);

                for(int i=0; i<jsonArray.length();i++)
                {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String status = jsonObject.getString("status");
                    String partner = jsonObject.getString("partnerName");
                    String solution = jsonObject.getString("solutionName");
                    int request = jsonObject.getInt("request_id");

                    AuthRequestCustomer temp = new AuthRequestCustomer();
                    temp.empty = false;
                    temp.status = status;
                    temp.partner = partner;
                    temp.solution = solution;
                    temp.requestid = request;

                    AuthRequests.add(temp);

                }

                return AuthRequests;

            }


            @Override
            protected ArrayList<AuthRequestCustomer> doInBackground(AuthRequestCustomer... params) {


                HttpURLConnection urlConnection = null;
                BufferedReader reader = null;

                String AuthRequestJson= null;

                session = new SessionManager(getApplicationContext());
                HashMap<String, String> user = session.getUserDetails();
                String userID = user.get(SessionManager.KEY_ID);

                try {
                    String baseUrl = Constants.BASE_CUSTOMER_URL+ Constants.AuthorizationRequest + userID;
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
            protected void onPostExecute(ArrayList<AuthRequestCustomer> result)
            {
                super.onPostExecute(result);
                session = new SessionManager(getApplicationContext());
                HashMap<String, String> user = session.getUserDetails();

                AuthRequestList = (ListView)findViewById(R.id.AuthReqList);
                AuthRequestList.setAdapter(new AuthRequestAdapter(AuthorizationRequestActivity.this,result));

            }
        }

    public void goBack(View V)
    {
        Intent i = new Intent(getApplicationContext(), CustomerDashboardActivity.class);
        startActivity(i);
    }

}
