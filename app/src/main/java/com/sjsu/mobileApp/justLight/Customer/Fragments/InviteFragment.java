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

import com.sjsu.mobileApp.justLight.R;


public class InviteFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    Button share_btn;
    View emailView;


    public InviteFragment() {
        // Required empty public constructor
    }

    public static InviteFragment newInstance(String param1, String param2) {
        InviteFragment fragment = new InviteFragment();
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
                             Bundle savedInstanceState)
    {

        //new button_start
        View v = inflater.inflate(R.layout.fragment_invite, container, false);

        /*
        Button lighting = (Button)v.findViewById(R.id.btn_gmail);

        lighting.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send-Sharana.");
                sendIntent.setType("text/plain");
                getActivity().startActivity(sendIntent);
            }
        });
        */


        Button btn_email = (Button)v.findViewById(R.id.btn_gmail);

        btn_email.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                //sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send-Sharana.");
                sendIntent.putExtra(Intent.EXTRA_EMAIL, "contactus@justlight.com");
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Promotion of Just Light Technologies");
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey! Empower your business with Green Building Energy Solution.");
                sendIntent.setType("*/*");
                //getActivity().startActivity(sendIntent);
                getActivity().startActivity(sendIntent);

            }
        });

        Button btn_message = (Button)v.findViewById(R.id.btn_msg);

        btn_message.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey! Empower your business with Green Building Energy Solution.");
                sendIntent.setType("text/plain");
                getActivity().startActivity(sendIntent);
            }
        });

        Button btn_tweet = (Button)v.findViewById(R.id.btn_twitter);

        btn_tweet.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey! Empower your business with Green Building Energy Solution.");
                sendIntent.setType("text/plain");
                getActivity().startActivity(sendIntent);


                /*Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send-Sharana.");
                sendIntent.setType("text/plain");
                getActivity().startActivity(sendIntent);
                */
            }
        });

        Button btn_facebook = (Button)v.findViewById(R.id.btn_facebook);

        btn_facebook.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey! Empower your business with Green Building Energy Solution.");
                sendIntent.setType("text/plain");
                getActivity().startActivity(sendIntent);
            }
        });


        return v;

        //new button_end
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_invite, container, false);
    }

    public void inviteGmail(View v)
    {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey! Empower your business with Green Building Energy Solution.");
        sendIntent.setType("text/plain");
        getActivity().startActivity(sendIntent);
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
