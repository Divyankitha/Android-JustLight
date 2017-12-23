package com.sjsu.mobileApp.justLight.Customer.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sjsu.mobileApp.justLight.R;
import com.sjsu.mobileApp.justLight.Customer.Data.OrderHistoryData;

import java.util.ArrayList;



public class OrderHistoryAdapter extends BaseAdapter
{
    private static ArrayList<OrderHistoryData> OrderHistoryArray;
    private LayoutInflater mInflater;

    public OrderHistoryAdapter(Context context, ArrayList<OrderHistoryData> results) {
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
    public View getView(int position, View view, ViewGroup viewGroup) {

        ViewHolder holder;
        if (view == null) {
            view = mInflater.inflate(R.layout.order_history_customer_rows, null);
            holder = new ViewHolder();
            holder.status = (TextView) view.findViewById(R.id.Cstatus);
            holder.date = (TextView) view.findViewById(R.id.Cdate);
            holder.solution = (TextView) view.findViewById(R.id.Csolution);
            holder.partner = (TextView) view.findViewById(R.id.cpartner);

            view.setTag(holder);
        } else
        {
            holder = (ViewHolder) view.getTag();
        }

        holder.solution.setText("Solution : " + OrderHistoryArray.get(position).solution);
        holder.date.setText    ("Date       : " + OrderHistoryArray.get(position).date);
        holder.status.setText  ("Status    : " + OrderHistoryArray.get(position).status);
        holder.partner.setText ("Partner   :" +OrderHistoryArray.get(position).partner);

        return view;
    }

    static class ViewHolder
    {
        TextView solution;
        TextView date;
        TextView status;
        TextView partner;

    }
}
