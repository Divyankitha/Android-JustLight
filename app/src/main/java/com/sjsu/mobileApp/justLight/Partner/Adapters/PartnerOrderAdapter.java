package com.sjsu.mobileApp.justLight.Partner.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sjsu.mobileApp.justLight.Partner.Data.PartnerOrderHistoryData;
import com.sjsu.mobileApp.justLight.R;

import java.util.ArrayList;

/**
 * Created by divyankithaRaghavaUrs on 12/5/17.
 */

public class PartnerOrderAdapter extends BaseAdapter
{
    private static ArrayList<PartnerOrderHistoryData> OrderHistoryArray;
    private LayoutInflater mInflater;

    public PartnerOrderAdapter(Context context, ArrayList<PartnerOrderHistoryData> results) {
        OrderHistoryArray = results;
        mInflater = LayoutInflater.from(context);

    }
    @Override
    public int getCount() {
        return OrderHistoryArray.size();
    }

    @Override
    public Object getItem(int position) {
        return OrderHistoryArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View view, ViewGroup viewGroup)
    {

        ViewHolder holder;
        if (view == null) {
            view = mInflater.inflate(R.layout.partner_order_history_rows, null);
            holder = new ViewHolder();
            holder.status = (TextView) view.findViewById(R.id.Pstatus);
            holder.date = (TextView) view.findViewById(R.id.Pdate);
            holder.solution = (TextView) view.findViewById(R.id.Psolution);
            holder.customer = (TextView) view.findViewById(R.id.Pcustomer);

            view.setTag(holder);
        } else
        {
            holder = (ViewHolder) view.getTag();
        }

        holder.solution.setText("Solution : " + OrderHistoryArray.get(position).solution);
        holder.date.setText    ("Date      : " + OrderHistoryArray.get(position).date);
        holder.status.setText  ("Status    : " + OrderHistoryArray.get(position).status);
        holder.customer.setText("Customer  :"+ OrderHistoryArray.get(position).customer);

        return view;
    }

    static class ViewHolder
    {
        TextView solution;
        TextView date;
        TextView status;
        TextView customer;


    }
}
