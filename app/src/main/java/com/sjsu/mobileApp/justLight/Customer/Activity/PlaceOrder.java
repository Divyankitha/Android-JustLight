package com.sjsu.mobileApp.justLight.Customer.Activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sjsu.mobileApp.justLight.R;
import com.sjsu.mobileApp.justLight.Utility.AlertDialogManager;
import com.sjsu.mobileApp.justLight.Utility.Constants;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;



public class PlaceOrder extends AppCompatActivity
{
    Bundle bundle;
    int solutionID;
    String solution;
    String partnerID;
    String customerID;
    double price = 1.00;
    int quantity;
    int orderID =0;
    AlertDialogManager alert = new AlertDialogManager();


    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_form_customer);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        bundle = getIntent().getExtras();
        solutionID = Integer.parseInt(bundle.getString("solutionID"));
        partnerID = bundle.getString("partnerID");
        customerID = bundle.getString("customerID");
        price = bundle.getDouble("price");
        System.out.println("price:" +price);

        switch (solutionID)
        {
            case 1: solution = "Motion Detection";
                    break;
            case 2: solution = "Occupancy Detection";
                break;
            case 3: solution = "DayLight Harvest Sensor";
                break;
            case 4: solution = "Solar Solution";
                break;
            case 5: solution = "HVAC Analysis and Solution";
                break;
            case 6: solution = "Windows Film";
                break;

        }


    }

    public void getQuote(View v)
    {
        EditText q = (EditText) findViewById(R.id.quantityOrder);
        TextView t = (TextView) findViewById(R.id.totalPrice);
        String quantityStr = q.getText().toString();
        quantity = Integer.parseInt(quantityStr);
        double total = quantity * price;
        String totalStr = String.valueOf(total);

        t.setText(totalStr);
    }

    public void confirmOrder(View v)
    {
        TextView t = (TextView) findViewById(R.id.totalPrice);
        double total = Double.parseDouble(t.getText().toString());
        AsyncTaskPlaceOrder asyncTaskPlaceOrder = new AsyncTaskPlaceOrder();
        asyncTaskPlaceOrder.execute(total);


    }

    public class AsyncTaskPlaceOrder extends AsyncTask<Double, Object, Integer> {

        HttpResponse response;
        String result;

        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c.getTime());


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Double... params)
        {

            System.out.println("Inside do in background");
            try
            {
                JSONObject requestBody = new JSONObject();
                requestBody.put("solution", solution);
                requestBody.put("partnerId",partnerID);
                requestBody.put("recipientId",customerID);
                requestBody.put("totalPrice",params[0]);
                requestBody.put("quantity",quantity);
                requestBody.put("orderStatus","In progress");
                requestBody.put("orderDate",formattedDate);

                String request = requestBody.toString();
                StringEntity request_param = new StringEntity(request);

                System.out.println("Body\n" + request);

                String Url= Constants.BASE_CUSTOMER_URL+Constants.NewOrder;

                System.out.println(Url);
                HttpPost post = new HttpPost(Url);
                post.setHeader("Content-Type","application/json");
                post.setEntity(request_param);
                HttpClient httpClient = new DefaultHttpClient();
                response = httpClient.execute(post);

                System.out.println("Reached after coming back from Backend API");

                System.out.println("response" +response);
                System.out.println("Response complete");

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
                    else if(response.getStatusLine().getStatusCode() == 500)
                    {
                        result = "Association Exists";
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
                        orderID = j.getInt("OrderId");

                    }
                }
            }
            catch(Exception x)
            {
                throw new RuntimeException("Error from authenticate customer api",x);
            }
            return orderID;
        }

        @Override
        protected void onPostExecute(Integer result)
        {
            super.onPostExecute(result);

            if(result != 0)
            {

                alert.showAlertDialog(PlaceOrder.this, "Success,Your order has been placed!", "Thank you for choosing JustLight", false);
            }
            else
            {
                alert.showAlertDialog(PlaceOrder.this, "Failure,Your order has not been placed!", "Kindly Retry, Thank you for your patience", false);
            }
        }

    }
}
