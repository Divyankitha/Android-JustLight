package com.sjsu.mobileApp.justLight.Partner.Adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.sjsu.mobileApp.justLight.Partner.Data.PartnerAuthRequestData;
import com.sjsu.mobileApp.justLight.R;
import com.sjsu.mobileApp.justLight.Utility.Constants;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;



public class PartnerAuthRequestAdapter extends BaseAdapter
{
    private static ArrayList<PartnerAuthRequestData> AuthRequestArray;
    private LayoutInflater mInflater;
    String requestId, status;

    public PartnerAuthRequestAdapter(Context context, ArrayList<PartnerAuthRequestData> results) {
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
    public View getView(final int position, View view, ViewGroup viewGroup) {

        final ViewHolder holder;
        if (view == null) {
            view = mInflater.inflate(R.layout.partner_auth_request_rows, null);
            holder = new ViewHolder();

            holder.customerName = (TextView) view.findViewById(R.id.custName);
            holder.status = (TextView) view.findViewById(R.id.reqStatus);
            holder.update = (Button) view.findViewById(R.id.UpdateAuthorization) ;

            view.setTag(holder);
        } else
        {
            holder = (ViewHolder) view.getTag();
        }


        holder.customerName.setText ("Customer  : " +AuthRequestArray.get(position).customer_name);
        holder.status.setText       ("Status    : " +AuthRequestArray.get(position).status);

        requestId = String.valueOf(AuthRequestArray.get(position).request_id);
        status = AuthRequestArray.get(position).status;

        holder.update.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(status.equals("Approved"))
                {
                    System.out.println("Already Approved");
                }
                else
                {
                    holder.status.setText("Status    : Approved" );
                    AsyncTaskUpdateRequest asyncTaskRating = new AsyncTaskUpdateRequest();
                    asyncTaskRating.execute();

                }

            }
        });


        return view;
    }

    static class ViewHolder
    {

        TextView customerName;
        TextView status;
        Button update;

    }

    public class AsyncTaskUpdateRequest extends AsyncTask<Object, Object, String>
    {

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
                requestBody.put("requestId", requestId);
                requestBody.put("newStatus","Approved");


                String request = requestBody.toString();
                StringEntity request_param = new StringEntity(request);

                System.out.println("Body\n" + request);

                String Url= Constants.BASE_PARTNERS_URL+Constants.PartnerUpdateAuthorizationRequest;

                System.out.println(Url);
                HttpPut post = new HttpPut(Url);
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
                        result = j.getString("string");

                    }
                }
            }
            catch(Exception x)
            {
                throw new RuntimeException("Error from add rating api",x);
            }
            return result;
        }

        @Override
        protected void onPostExecute(String R)
        {
            super.onPostExecute(R);


        }
    }
}
