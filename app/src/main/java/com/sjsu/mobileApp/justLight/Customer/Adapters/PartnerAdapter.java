package com.sjsu.mobileApp.justLight.Customer.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.sjsu.mobileApp.justLight.Customer.Activity.PlaceOrder;
import com.sjsu.mobileApp.justLight.Customer.Data.Partners;
import com.sjsu.mobileApp.justLight.R;
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



public class PartnerAdapter extends BaseAdapter
{
    private static ArrayList<Partners> PartnerArray;
    private LayoutInflater mInflater;
    String SolutionID, UserID, partnerID;
    int PartnerID;
    double price;
    AlertDialog.Builder alert;
    Context con;

    public PartnerAdapter(Context context, ArrayList<Partners> results, String solutionID, String userID)
    {
        PartnerArray = results;
        mInflater = LayoutInflater.from(context);
        SolutionID = solutionID;
        UserID = userID;
        con = context;

    }
    @Override
    public int getCount() {
        return PartnerArray.size();
    }

    @Override
    public Object getItem(int position) {
        return PartnerArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {

        ViewHolder holder;
        if (view == null) {
            view = mInflater.inflate(R.layout.partner_detail_rows, null);
            holder = new ViewHolder();
            holder.Fullname = (TextView) view.findViewById(R.id.Pfullname);
            holder.contact = (TextView) view.findViewById(R.id.Pcontact);
            holder.emailid = (TextView) view.findViewById(R.id.PEmail);
            holder.rating = (TextView) view.findViewById(R.id.Prating);
            holder.requestDemo = (Button)view.findViewById(R.id.RequestAuth);
            holder.order = (Button)view.findViewById(R.id.order);
            holder.price = (TextView) view.findViewById(R.id.pprice);
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }

        //view.setBackground(getContext().getDrawable(R.drawable.border));

        holder.Fullname.setText("Name    : " + PartnerArray.get(position).Fullname);
        holder.contact.setText ("Contact : " +PartnerArray.get(position).contact);
        holder.emailid.setText ("Email   : " +PartnerArray.get(position).emailid);
        holder.rating.setText  ("Rating  : " +PartnerArray.get(position).rating);
        holder.price.setText   ("Price   : "+PartnerArray.get(position).price);
        PartnerID = PartnerArray.get(position).ID;
        partnerID = String.valueOf(PartnerID);
        //price = PartnerArray.get(position).price;
        //ystem.out.println("Adapter:" +price);

        holder.requestDemo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {

                alert = new AlertDialog.Builder(v.getContext());

                AsyncTaskRequestDemo asyncTaskRating = new AsyncTaskRequestDemo();
                asyncTaskRating.execute();

            }
        });

        holder.order.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                price = PartnerArray.get(position).price;
                System.out.println("Adapter 2:" +price);
                System.out.println("Adapter 2:" +SolutionID);
                System.out.println("Adapter 2:" +partnerID);
                Intent i = new Intent(con, PlaceOrder.class);
                i.putExtra("partnerID", partnerID);
                i.putExtra("solutionID", SolutionID);
                i.putExtra("customerID", UserID);
                i.putExtra("price", price);

                con.startActivity(i);


            }
        });

        //view.setBackground(getContext().getDrawable(R.drawable.border));
        return view;
    }


    static class ViewHolder
    {

        TextView contact,Fullname,emailid,rating,price;
        Button requestDemo, order;

    }

    public class AsyncTaskRequestDemo extends AsyncTask<Object, Object, Integer>
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
                requestBody.put("customerId", UserID);
                requestBody.put("solutionId",SolutionID);
                requestBody.put("partnerId",String.valueOf(PartnerID));
                requestBody.put("status","Pending");

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

            if(R != 0)
            {
                System.out.println("Auth request sent");
                alert.setMessage("Demo Requested!");
                alert.setTitle("You will hear from the partner soon!");
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }});
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
            }
            else
            {
                System.out.println("Auth request not sent");
                alert.setMessage("Demo Request not sent!");
                alert.setTitle("Please try again!");
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }});
                AlertDialog alertDialog = alert.create();
                alertDialog.show();

            }
        }
    }
}
