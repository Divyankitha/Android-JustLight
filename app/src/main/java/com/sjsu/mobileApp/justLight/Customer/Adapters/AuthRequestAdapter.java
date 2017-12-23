package com.sjsu.mobileApp.justLight.Customer.Adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.sjsu.mobileApp.justLight.R;
import com.sjsu.mobileApp.justLight.Customer.Data.AuthRequestCustomer;
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

import java.util.ArrayList;


public class AuthRequestAdapter extends BaseAdapter
{
    private static ArrayList<AuthRequestCustomer> AuthRequestArray;
    private LayoutInflater mInflater;
    int requestID;

    public AuthRequestAdapter(Context context, ArrayList<AuthRequestCustomer> results) {
        AuthRequestArray = results;
        mInflater = LayoutInflater.from(context);

    }
    @Override
    public int getCount() {
        return AuthRequestArray.size();
    }

    @Override
    public Object getItem(int position) {
        return AuthRequestArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        final ViewHolder holder;
        if (view == null) {
            view = mInflater.inflate(R.layout.auth_request_list_customer, null);
            holder = new ViewHolder();
            holder.status = (TextView) view.findViewById(R.id.ReqSolutionTitle);
            holder.partner = (TextView) view.findViewById(R.id.ReqPartnerTitle);
            holder.solution = (TextView) view.findViewById(R.id.ReqTitle);
            holder.delete = (Button) view.findViewById(R.id.DeleteRequestAuthorizationCustomer);
            requestID = AuthRequestArray.get(position).requestid;

            holder.delete.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    AsyncTaskDeleteRequest asyncTaskRating = new AsyncTaskDeleteRequest();
                    asyncTaskRating.execute();

                    notifyDataSetChanged();


                }
            });

            view.setTag(holder);
        } else
            {
            holder = (ViewHolder) view.getTag();
        }

        holder.solution.setText("Solution : " + AuthRequestArray.get(position).solution);
        holder.partner.setText ("Partner  : " +AuthRequestArray.get(position).partner);
        holder.status.setText  ("Status    : " +AuthRequestArray.get(position).status);

        return view;
    }

    static class ViewHolder
    {
        TextView solution;
        TextView partner;
        TextView status;
        Button delete;
    }


    public class AsyncTaskDeleteRequest extends AsyncTask<Object, Object, Integer>
    {

        HttpResponse response;
        SessionManager session;
        int AuthID = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Object... params)
        {

            System.out.println("Inside do in background");
            try
            {
                JSONObject requestBody = new JSONObject();


                String request = requestBody.toString();
                StringEntity request_param = new StringEntity(request);

                System.out.println("Body\n" + request);

                String Url= Constants.BASE_CUSTOMER_URL+Constants.DemoRequest;

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
                        System.out.println("Verification failed 401");
                    }
                    else if(response.getStatusLine().getStatusCode() == 400)
                    {
                        System.out.println("Verification failed 400");
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
                        AuthID = j.getInt("AuthorizationRequestId");

                    }
                }
            }
            catch(Exception x)
            {
                throw new RuntimeException("Error from add rating api",x);
            }
            return AuthID;
        }

        @Override
        protected void onPostExecute(Integer R)
        {
            super.onPostExecute(R);

            if(AuthID != 0)
            {
                System.out.println("Auth request sent");
                //alert.showAlertDialog(getContext(), "Success", "Thank you for your feedback!", false);
            }
            else
            {
                System.out.println("Auth request not sent");
                //alert.showAlertDialog(getActivity(), "Unsucessful", "Feedback not Inserted!", false);
            }
        }
    }

    }

