package com.sjsu.mobileApp.justLight.Partner.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sjsu.mobileApp.justLight.Partner.Data.PartnerProfile;
import com.sjsu.mobileApp.justLight.R;
import com.sjsu.mobileApp.justLight.Utility.Constants;
import com.sjsu.mobileApp.justLight.Utility.SessionManager;

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


public class PartnerProfileFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    TextView Address;
    TextView pincode;
    TextView work;
    TextView mobile;
    TextView gender;
    TextView registration_id;
    TextView date_of_birth;
    TextView Email;
    TextView joined_date;

    TextView fullname;
    TextView business;
    TextView city;

    String UserID;

    SessionManager sessionManager;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PartnerProfileFragment() {
        // Required empty public constructor
    }


    public static PartnerProfileFragment newInstance(String param1, String param2) {
        PartnerProfileFragment fragment = new PartnerProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final String TAG = "Partner Fragment";

        View v = inflater.inflate(R.layout.fragment_partner_profile, container, false);

        fullname= (TextView)v.findViewById(R.id.partner_name);
        business= (TextView)v.findViewById(R.id.partner_designation);
        city= (TextView)v.findViewById(R.id.partner_location);

        Address= (TextView)v.findViewById(R.id.pblood_group);
        pincode= (TextView)v.findViewById(R.id.partner_education);
        work= (TextView)v.findViewById(R.id.poccupation);
        mobile= (TextView)v.findViewById(R.id.pmobileNumber);
        gender= (TextView)v.findViewById(R.id.pgender);
        registration_id= (TextView)v.findViewById(R.id.pmarriage);
        date_of_birth= (TextView)v.findViewById(R.id.pdob);
        Email= (TextView)v.findViewById(R.id.pemail);
        joined_date= (TextView)v.findViewById(R.id.approved_by);
        //Address.setText("101E San Fernando Street,San Jose");

        sessionManager =new SessionManager(getActivity());
        HashMap<String,String> user = sessionManager.getUserDetails();
        UserID =user.get(SessionManager.KEY_ID);
        AsyncTaskProfile asyncTaskProfile = new AsyncTaskProfile();
        asyncTaskProfile.execute();

        return v;
    }


    public class AsyncTaskProfile extends AsyncTask<Object, Object, ArrayList<PartnerProfile>> {

        private final String LOG_TAG = "Customer Fragment";

        private ArrayList<PartnerProfile> getCustomerDetailsFromJson(String appJsonStr) throws JSONException {

            System.out.println("Inside parse function");
            ArrayList<PartnerProfile> customerProfiles = new ArrayList<PartnerProfile>();

            JSONObject jsonObject = new JSONObject(appJsonStr);
            PartnerProfile temp = new PartnerProfile();
            temp.streetAddress = jsonObject.getString("streetAddress");
            temp.contact = jsonObject.getString("contact");
            temp.pincode = jsonObject.getString("pincode");
            temp.workInfo = jsonObject.getString("workInfo");
            temp.fullname = jsonObject.getString("fullname");
            temp.dob =jsonObject.getString("dob");
            temp.emailId =jsonObject.getString("emailId");
            temp.gender =jsonObject.getString("gender");
            temp.city = jsonObject.getString("city");
            temp.joinedDate  =jsonObject.getString("joinedDate");
            temp.registrationId = jsonObject.getString("registrationId");

            customerProfiles.add(temp);

            return customerProfiles;

        }

        @Override
        protected ArrayList<PartnerProfile> doInBackground(Object... params) {


            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String custprofileJson = null;

            try {

                String baseUrl = Constants.BASE_USER_URL+ Constants.customerProfile + UserID;

                // Uri patUri = Uri.parse(baseUrl).buildUpon().appendEncodedPath(PATH_PARAM).appendEncodedPath(String.valueOf(Cus_ID)).build();

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

                custprofileJson = buffer.toString();
                Log.v(LOG_TAG,"PatientListStr: "+custprofileJson);

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
                return getCustomerDetailsFromJson(custprofileJson);
            }catch (JSONException e){
                Log.e(LOG_TAG,e.getMessage(),e);
                e.printStackTrace();
            }

            return null ;
        }


        @Override
        protected void onPostExecute(ArrayList<PartnerProfile> result) {
            super.onPostExecute(result);
            for(PartnerProfile customer : result)
            {

                Log.d("the result is", "Profile data is  " + customer.streetAddress);
                Address.setText(customer.streetAddress);
                pincode.setText(customer.pincode);
                work.setText(customer.workInfo);
                mobile.setText(customer.contact);
                gender.setText(customer.gender);
                registration_id.setText(customer.registrationId);
                date_of_birth.setText(customer.dob);
                Email.setText(customer.emailId);
                joined_date.setText(customer.joinedDate);

                fullname.setText(customer.fullname);
                city.setText(customer.city);
                business.setText("Partner");
            }

        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}
