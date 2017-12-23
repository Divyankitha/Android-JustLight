package com.sjsu.mobileApp.justLight.Customer.Activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sjsu.mobileApp.justLight.Customer.Data.CustomerProfile;
import com.sjsu.mobileApp.justLight.Customer.Data.UpdateProfileInfo;
import com.sjsu.mobileApp.justLight.R;
import com.sjsu.mobileApp.justLight.Utility.Constants;
import com.sjsu.mobileApp.justLight.Utility.SessionManager;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;



public class ChangeProfileActivity extends Activity {
    EditText fullname;
    EditText Address;
    EditText pincode;
    EditText work;
    EditText mobile;
    EditText gender;
    EditText Email;
    EditText dob;
    EditText city;
    EditText registrationID;
    EditText joineddate;
    Button Save;
    SessionManager session;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);
        fullname = (EditText) findViewById(R.id.fullname);
        Address = (EditText) findViewById(R.id.address);
        pincode = (EditText) findViewById(R.id.pincode);
        city = (EditText) findViewById(R.id.city);
        work = (EditText) findViewById(R.id.work);
        mobile = (EditText) findViewById(R.id.contact);
        gender = (EditText) findViewById(R.id.gender);
        Email = (EditText) findViewById(R.id.email);
        dob = (EditText) findViewById(R.id.dob);
        registrationID = (EditText) findViewById(R.id.registrationid);
        joineddate = (EditText) findViewById(R.id.joineddate);
        Save = (Button) findViewById(R.id.SaveProduct);
        ChangeProfileActivity.AsyncTaskProfile asyncTaskProfile = new ChangeProfileActivity.AsyncTaskProfile();
        asyncTaskProfile.execute();
    }

    public class AsyncTaskProfile extends AsyncTask<Object, Object, ArrayList<CustomerProfile>> {

        private final String LOG_TAG = "Customer Fragment";

        private ArrayList<CustomerProfile> getCustomerDetailsFromJson(String appJsonStr) throws JSONException {
            ArrayList<CustomerProfile> customerProfiles = new ArrayList<CustomerProfile>();

            JSONObject jsonObject = new JSONObject(appJsonStr);
            CustomerProfile temp = new CustomerProfile();
            temp.fullname = jsonObject.getString("fullname");
            temp.streetAddress = jsonObject.getString("streetAddress");
            temp.contact = jsonObject.getString("contact");
            temp.city = jsonObject.getString("city");
            temp.joinedDate = jsonObject.getString("joinedDate");
            temp.registrationId = jsonObject.getString("registrationId");
            temp.pincode = jsonObject.getString("pincode");
            temp.contact = jsonObject.getString("contact");
            temp.workInfo = jsonObject.getString("workInfo");
            temp.dob = jsonObject.getString("dob");
            temp.emailId = jsonObject.getString("emailId");
            temp.gender = jsonObject.getString("gender");

            customerProfiles.add(temp);

            return customerProfiles;

        }

        @Override
        protected ArrayList<CustomerProfile> doInBackground(Object... params) {


            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String CredentialsJson = null;
            session = new SessionManager(getApplicationContext());
            HashMap<String,String> user = session.getUserDetails();
            String UserID =user.get(SessionManager.KEY_ID);
            String type = user.get(SessionManager.KEY_TYPE);
            String baseUrl;

            try {


                    baseUrl = Constants.BASE_USER_URL + Constants.customerProfile + UserID;
                URL url = null;
                try {
                    url = new URL(baseUrl);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                try {
                    urlConnection = (HttpURLConnection) url.openConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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

                CredentialsJson = buffer.toString();
                Log.v(LOG_TAG, "PatientListStr: " + CredentialsJson);

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
            try {
                return getCustomerDetailsFromJson(CredentialsJson);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(ArrayList<CustomerProfile> result) {
            super.onPostExecute(result);
            Log.d("The value is", "Entered Result");
            for (CustomerProfile data : result) {
                Log.d("The value is", "Entered Loop");
                fullname.setText(data.fullname);
                Address.setText(data.streetAddress);
                pincode.setText(data.pincode);
                work.setText(data.workInfo);
                mobile.setText(data.contact);
                gender.setText(data.gender);
                Email.setText(data.emailId);
                city.setText(data.city);
                joineddate.setText(data.joinedDate);
                registrationID.setText(data.registrationId);
                mobile.setText(data.contact);
                dob.setText(data.dob);
            }
            Save.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String name= fullname.getText().toString();
                    String address= Address.getText().toString();
                    String pin= pincode.getText().toString();
                    String workInfo= work.getText().toString();
                    String contact= mobile.getText().toString();
                    String ge= gender.getText().toString();
                    String email= Email.getText().toString();
                    String date_of_birth= dob.getText().toString();
                    String joined_date=joineddate.getText().toString();
                    String city1=city.getText().toString();
                    String registration=registrationID.getText().toString();

                    UpdateProfileInfo U = new UpdateProfileInfo();
                    U.setFullname(name);
                    U.setAddress(address);
                    U.setPincode(pin);
                    U.setWork(workInfo);
                    U.setDob(date_of_birth);
                    U.setGender(ge);
                    U.setEmail(email);
                    U.setMobile(contact);
                    U.setJoinedDate(joined_date);
                    U.setCity(city1);
                    U.setRegistrationID(registration);

                    Log.d(LOG_TAG,"The details you require are"+U.pincode+U.gender+U.contact+U.dob+U.joinedDate+U.city+U.registrationID);
                    ChangeProfileActivity.AsyncTaskUpdateProfile asyncTaskUpdateProfile = new ChangeProfileActivity.AsyncTaskUpdateProfile();
                    asyncTaskUpdateProfile.execute(U);
                }
            });

        }
    }

        public class AsyncTaskUpdateProfile extends AsyncTask<UpdateProfileInfo, String, UpdateProfileInfo> {
            HttpResponse response;


            protected UpdateProfileInfo doInBackground(UpdateProfileInfo... params) {

                try {
                    JSONObject requestBody = new JSONObject();
                    //Passing the details
                    SessionManager sessionManager =new SessionManager(ChangeProfileActivity.this);
                    HashMap<String,String> user = sessionManager.getUserDetails();
                    String UserID =user.get(SessionManager.KEY_ID);
                    String type = user.get(SessionManager.KEY_TYPE);
                    requestBody.put("userId", UserID);
                    requestBody.put("newFullname", params[0].fullname);
                    if(type.equals("Customer"))
                    {
                        requestBody.put("newUserGroupType", "Customer");
                    }
                    else
                    {
                        requestBody.put("newUserGroupType", "Partner");
                    }

                    requestBody.put("newStreetAddress", params[0].streetAddress);
                    requestBody.put("newCity", params[0].city);
                    requestBody.put("newPincode", params[0].pincode);
                    requestBody.put("newWorkInfo", params[0].workInfo);
                    requestBody.put("newGender", params[0].gender);
                    requestBody.put("newDob", params[0].dob);
                    requestBody.put("newContact", params[0].contact);
                    requestBody.put("newEmailId",params[0].Email);
                    requestBody.put("newRegistrationId",params[0].registrationID);
                    requestBody.put("newJoinedDate",params[0].joinedDate);
                    requestBody.put("newRating","0");

                    String request = requestBody.toString();
                    StringEntity request_param = new StringEntity(request);
                    System.out.println(request);
                    String Url = Constants.BASE_USER_URL + Constants.ProfileUpdate;
                    System.out.println(Url);
                    HttpPut put = new HttpPut(Url);
                    put.setHeader("Content-Type", "application/json");
                    put.setEntity(request_param);
                    HttpClient httpClient = new DefaultHttpClient();
                    response = httpClient.execute(put);
                    System.out.println("Reached after coming back from Backend API");
                    if (response.getStatusLine().getStatusCode() != 200) {
                        if (response.getStatusLine().getStatusCode() == 401) {
                            System.out.println("add failed");
                        } else if (response.getStatusLine().getStatusCode() == 400) {
                            System.out.println("Verification failed");
                        } else
                            throw new RuntimeException("Failed: HTTP error code :" + response.getStatusLine().getStatusCode());
                    } else {
                        HttpEntity e = response.getEntity();
                        String i = EntityUtils.toString(e);
                        JSONObject j = new JSONObject(i);
                    }
                } catch (Exception x) {
                    throw new RuntimeException("Error from authenticate partner api", x);
                }
                return params[0];
            }

            @Override
            protected void onPostExecute(UpdateProfileInfo U) {
                super.onPostExecute(U);
                String fullname;
                fullname = String.valueOf(U.fullname);
                Toast toast = Toast.makeText(ChangeProfileActivity.this, "User Profile Updated" ,Toast.LENGTH_LONG);
                toast.show();


            }
        }

        }







