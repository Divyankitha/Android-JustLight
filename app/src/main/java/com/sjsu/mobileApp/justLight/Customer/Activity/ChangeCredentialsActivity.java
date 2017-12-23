package com.sjsu.mobileApp.justLight.Customer.Activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sjsu.mobileApp.justLight.Customer.Data.CredentialsInfo;
import com.sjsu.mobileApp.justLight.Customer.Data.UpdateCredentialsInfo;
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



public class ChangeCredentialsActivity extends Activity {
    EditText name, pass;
    Button Save;
    SessionManager session;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_credentials);
        name = (EditText) findViewById(R.id.Username);
        pass = (EditText) findViewById(R.id.password);
        Save = (Button) findViewById(R.id.SaveProduct);
        ChangeCredentialsActivity.AsyncTaskProfile asyncTaskProfile = new ChangeCredentialsActivity.AsyncTaskProfile();
         asyncTaskProfile.execute();
    }
        public class AsyncTaskProfile extends AsyncTask<Object, Object, ArrayList<CredentialsInfo>> {

            private final String LOG_TAG = "Customer Fragment";

            private ArrayList<CredentialsInfo> getCustomerDetailsFromJson(String appJsonStr) throws JSONException {
                ArrayList<CredentialsInfo> credentialsInfo = new ArrayList<CredentialsInfo>();

                JSONObject jsonObject = new JSONObject(appJsonStr);
                CredentialsInfo temp = new CredentialsInfo();
                temp.username = jsonObject.getString("username");
                temp.password = jsonObject.getString("password");

                credentialsInfo.add(temp);

                return credentialsInfo;

            }

            @Override
            protected ArrayList<CredentialsInfo> doInBackground(Object... params) {


                HttpURLConnection urlConnection = null;
                BufferedReader reader = null;

                String CredentialsJson = null;
                session = new SessionManager(getApplicationContext());
                HashMap<String,String> user = session.getUserDetails();
                String UserID =user.get(SessionManager.KEY_ID);
                String type = user.get(SessionManager.KEY_TYPE);
                String baseUrl;

                try {
                    if(type.equals("Customer"))
                    {
                        baseUrl = Constants.BASE_USER_URL + Constants.Credentials + UserID + "/Customer";
                    }
                    else
                    {
                        baseUrl = Constants.BASE_USER_URL + Constants.Credentials + UserID + "/Partner";
                    }




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
                    //Log.v(LOG_TAG,"PatientListStr: "+CredentialsJson);

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
            protected void onPostExecute(ArrayList<CredentialsInfo> result) {
                super.onPostExecute(result);
                for(CredentialsInfo data : result) {
                    name.setText(data.username);
                    pass.setText(data.password);
                }
                Save.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        String username= name.getText().toString();
                        String password= pass.getText().toString();
                        UpdateCredentialsInfo U = new UpdateCredentialsInfo();
                        U.setUsername(username);
                        U.setPassword(password);
                        Log.d(LOG_TAG,"The details you require are"+U.username + U.password);
                        ChangeCredentialsActivity.AsyncTaskUpdateCredentials asyncTaskupdateProfile = new ChangeCredentialsActivity.AsyncTaskUpdateCredentials();
                        asyncTaskupdateProfile.execute(U);
                    }
                });

            }
        }

           public class AsyncTaskUpdateCredentials extends AsyncTask<UpdateCredentialsInfo, String, UpdateCredentialsInfo> {
        HttpResponse response;


                protected UpdateCredentialsInfo doInBackground(UpdateCredentialsInfo... params) {

                    try {
                        JSONObject requestBody = new JSONObject();
                        //Passing the details
                        SessionManager sessionManager =new SessionManager(ChangeCredentialsActivity.this);
                        HashMap<String,String> user = sessionManager.getUserDetails();
                        String UserID =user.get(SessionManager.KEY_ID);
                        requestBody.put("userId", UserID);
                        requestBody.put("newUsername", params[0].username);
                        requestBody.put("newPassword", params[0].password);
                        String request = requestBody.toString();
                        StringEntity request_param = new StringEntity(request);
                        System.out.println(request);
                        String Url = Constants.BASE_USER_URL + Constants.CredentialsUpdate;
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
                protected void onPostExecute(UpdateCredentialsInfo U) {
                    super.onPostExecute(U);
                    String userID;
                    userID = String.valueOf(U.username);
                    Toast toast = Toast.makeText(ChangeCredentialsActivity.this, "Credentials Updated" ,Toast.LENGTH_LONG);
                    toast.show();


                }
            }

        }




