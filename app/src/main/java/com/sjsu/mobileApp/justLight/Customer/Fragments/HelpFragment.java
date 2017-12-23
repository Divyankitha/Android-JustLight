package com.sjsu.mobileApp.justLight.Customer.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sjsu.mobileApp.justLight.Customer.Activity.ContactDetailsActivity;
import com.sjsu.mobileApp.justLight.R;
import com.sjsu.mobileApp.justLight.Customer.Activity.FaqActivity;


public class HelpFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HelpFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static HelpFragment newInstance(String param1, String param2) {
        HelpFragment fragment = new HelpFragment();
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

        View v = inflater.inflate(R.layout.fragment_help, container, false);
        Button btn_contactUs = (Button)v.findViewById(R.id.btn_contactUs);

        btn_contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent sendIntent = new Intent();
                Intent sendIntent = new Intent(getActivity(),ContactDetailsActivity.class);
                /*sendIntent.setAction(Intent.ACTION_SEND);
                //sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send-Sharana.");
                sendIntent.putExtra(Intent.EXTRA_EMAIL, "contactus@justlight.com");
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Promotion of Just Light Technologies");
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey! Empower your business with Green Building Energy Solution.");
                */
                //getActivity().startActivity(sendIntent);
                getActivity().startActivity(sendIntent);
            }
        });

        Button btn_faq = (Button)v.findViewById(R.id.btn_faq);

        btn_faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send-Sharana.");
                sendIntent.setType("text/plain");
                getActivity().startActivity(sendIntent);
                */
                /*Intent intent = new Intent(getActivity(), FaqActivity.class); 
                getActivity().startActivity(intent); 
                */

                //View v = inflater.inflate(R.layout.fragment_green_solution, container, false);
                Button lighting = (Button)v.findViewById(R.id.btn_faq);
                lighting.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent i = new Intent(getActivity(),FaqActivity.class);
                        getActivity().startActivity(i);
                    }
                });
            }
        });

        // Inflate the layout for this fragment
        return v;
        //inflater.inflate(R.layout.fragment_help, container, false);
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
