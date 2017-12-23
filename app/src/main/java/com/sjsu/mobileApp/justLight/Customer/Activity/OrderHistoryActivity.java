package com.sjsu.mobileApp.justLight.Customer.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sjsu.mobileApp.justLight.Customer.Adapters.OrderHistoryAdapter;
import com.sjsu.mobileApp.justLight.R;
import com.sjsu.mobileApp.justLight.Utility.SessionManager;
import com.sjsu.mobileApp.justLight.Customer.Data.OrderHistoryData;
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



public class OrderHistoryActivity extends Activity
{
    ListView orderHistory;
    private ArrayAdapter<String> mAdapter;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_history_list_view);
    }

    @Override
    public void onResume()
    {
        super.onResume();

        new AsyncTaskOrderHistory().execute();

    }

    public class AsyncTaskOrderHistory extends AsyncTask<Object, Object, ArrayList<OrderHistoryData>> {

        private final String LOG_TAG = "Smart Lighting Activity";
        String statusList ;
        String dataList ;
        String solutionList ;
        String partner;


        private ArrayList<OrderHistoryData> OrderHistoryFromJson(String appJsonStr) throws JSONException {

            System.out.println("Inside parse function");

            ArrayList<OrderHistoryData> OrderHistory = new ArrayList<OrderHistoryData>();
            JSONArray jsonArray = new JSONArray(appJsonStr);
            System.out.println(jsonArray);

            for(int i=0; i<jsonArray.length();i++)
            {

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                statusList = jsonObject.getString("order_status");
                dataList = jsonObject.getString("order_date");
                solutionList = jsonObject.getString("solution");
                partner = jsonObject.getString("partnerName");


                OrderHistoryData temp = new OrderHistoryData();
                temp.status = statusList;
                temp.date = dataList;
                temp.solution = solutionList;
                temp.empty = false;
                temp.partner = partner;
                OrderHistory.add(temp);



            }

            return OrderHistory;


        }


        @Override
        protected ArrayList<OrderHistoryData> doInBackground(Object... params) {


            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String AuthRequestJson= null;

            session = new SessionManager(getApplicationContext());
            HashMap<String, String> user = session.getUserDetails();
            String User_ID = user.get(SessionManager.KEY_ID);

            try {
                String baseUrl = Constants.BASE_CUSTOMER_URL+ Constants.OrderHistoryCustomer + User_ID;
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
                return OrderHistoryFromJson(AuthRequestJson);
            }catch (JSONException e){
                Log.e(LOG_TAG,e.getMessage(),e);
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(ArrayList<OrderHistoryData> result)
        {
            super.onPostExecute(result);
            session = new SessionManager(getApplicationContext());
            HashMap<String, String> user = session.getUserDetails();

            orderHistory = (ListView)findViewById(R.id.PartnerOrderListView);
            orderHistory.setAdapter(new OrderHistoryAdapter(OrderHistoryActivity.this,result));
        }
    }

    public void goBack(View V)
    {
        Intent i = new Intent(getApplicationContext(), CustomerDashboardActivity.class);
        startActivity(i);
    }
}
