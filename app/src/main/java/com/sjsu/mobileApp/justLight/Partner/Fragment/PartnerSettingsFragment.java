package com.sjsu.mobileApp.justLight.Partner.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.sjsu.mobileApp.justLight.Customer.Activity.ChangeCredentialsActivity;
import com.sjsu.mobileApp.justLight.Customer.Activity.ChangeProfileActivity;
import com.sjsu.mobileApp.justLight.R;



public class PartnerSettingsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static String[] Details = {"Change Credentials", "Change Profile"};

    EditText editText;
    ListView listView;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PartnerSettingsFragment() {
        // Required empty public constructor
    }


    public static PartnerSettingsFragment newInstance(String param1, String param2) {
        PartnerSettingsFragment fragment = new PartnerSettingsFragment();
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
        View v = inflater.inflate(R.layout.fragment_partner_settings, container, false);
        listView = (ListView) v.findViewById(R.id.pdetails);
        final ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.partner_setting_details, R.id.textViewPartner, Details);

        listView.setAdapter(adapter);

        // ListView setOnItemClickListener function .

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                String string = (String) adapter.getItem(position);
                if (string == "Change Credentials") {
                    Intent i = new Intent(getActivity(), ChangeCredentialsActivity.class);
                    getActivity().startActivity(i);

                }
                if (string == "Change Profile") {
                    Intent i = new Intent(getActivity(), ChangeProfileActivity.class);
                    getActivity().startActivity(i);

                }

            }
        });

        return v;
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
