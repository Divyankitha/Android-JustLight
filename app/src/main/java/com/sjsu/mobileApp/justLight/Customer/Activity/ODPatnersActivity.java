package com.sjsu.mobileApp.justLight.Customer.Activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.sjsu.mobileApp.justLight.Customer.Adapters.PartnerAdapter;
import com.sjsu.mobileApp.justLight.R;
import com.sjsu.mobileApp.justLight.Utility.SessionManager;
import com.sjsu.mobileApp.justLight.Customer.Data.Partners;
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



public class ODPatnersActivity extends Activity {
    ListView listView;
    final String Solution_ID="2";
    String UserID;
    SessionManager sessionManager;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.partner_list_view);

        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String,String> user = sessionManager.getUserDetails();
        UserID =user.get(SessionManager.KEY_ID);

        ODPatnersActivity.AsyncTaskODpatners asyncTaskODpatners = new ODPatnersActivity.AsyncTaskODpatners();
        asyncTaskODpatners.execute();
    }
    public class AsyncTaskODpatners extends AsyncTask<Object, Object, ArrayList<Partners>>{

        private final String LOG_TAG = "Smart Lighting Activity";

        private ArrayList<Partners> getODPatnersDetailsFromJson(String appJsonStr) throws JSONException {
            System.out.println("Inside parse function");
            ArrayList<Partners> partners = new ArrayList<Partners>();
            JSONArray jsonArray = new JSONArray(appJsonStr);
            //System.out.println(jsonArray);

            for(int i=0; i<jsonArray.length();i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Partners temp = new Partners();
                temp.Fullname = jsonObject.getString("fullname");
                temp.contact = jsonObject.getString("contact");
                temp.WorkInfo = jsonObject.getString("workInfo");
                temp.emailid = jsonObject.getString("emailId");
                temp.rating = jsonObject.getInt("rating");
                temp.price = jsonObject.getDouble("price");
                temp.ID = jsonObject.getInt("userId");
                partners.add(temp);
                System.out.println(temp.Fullname);

            }

            return partners;

        }


        @Override
        protected ArrayList<Partners> doInBackground(Object... params) {


            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String ODPatnerInfoJson= null;


            try {
                String baseUrl = Constants.BASE_PATNERS_URL+ Constants.PatnerInfo + Solution_ID;
                //final String PATH_PARAM = Constants. PatnerInfo;

                // Uri patUri = Uri.parse(baseUrl).buildUpon().appendEncodedPath(PATH_PARAM).appendEncodedPath(String.valueOf(Solution_ID)).build();

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

                ODPatnerInfoJson = buffer.toString();
                //System.out.println(MDPatnerInfoJson);
                //Log.v(LOG_TAG, "PatientListStr: " + MDPatnerInfoJson);

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
                return getODPatnersDetailsFromJson(ODPatnerInfoJson);
            }catch (JSONException e){
                Log.e(LOG_TAG,e.getMessage(),e);
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(ArrayList<Partners> result)
        {
            super.onPostExecute(result);
            listView = (ListView) findViewById(R.id.PartnerListView);
            listView.setAdapter(new PartnerAdapter(ODPatnersActivity.this,result,Solution_ID,UserID));

        }


    }
}


