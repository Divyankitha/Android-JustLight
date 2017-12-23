package com.sjsu.mobileApp.justLight.Partner.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.sjsu.mobileApp.justLight.Partner.Activity.PartnerHVACActivity;
import com.sjsu.mobileApp.justLight.Partner.Activity.PartnerSmartLightingActivity;
import com.sjsu.mobileApp.justLight.Partner.Activity.PartnerSolarActivity;
import com.sjsu.mobileApp.justLight.Partner.Activity.PartnerWindowFilmActivity;
import com.sjsu.mobileApp.justLight.R;
import com.sjsu.mobileApp.justLight.Utility.AlertDialogManager;



public class PartnerGreenSolutionFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    AlertDialogManager alert = new AlertDialogManager();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PartnerGreenSolutionFragment() {
        // Required empty public constructor
    }


    public static PartnerGreenSolutionFragment newInstance(String param1, String param2) {
        PartnerGreenSolutionFragment fragment = new PartnerGreenSolutionFragment();
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

        View v = inflater.inflate(R.layout.fragment_partner_green_solution, container, false);
        final EditText search = (EditText) v.findViewById(R.id.partner_input_search);
        ImageButton seachButton = (ImageButton) v.findViewById(R.id.partnerSearchButton);

        Button lighting = (Button)v.findViewById(R.id.P_Smartlighting);
        Button solar=(Button)v.findViewById(R.id.P_solar);
        Button HVAC=(Button)v.findViewById(R.id.P_HVAC);
        Button window_film=(Button)v.findViewById(R.id.P_window_film);
        lighting.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),PartnerSmartLightingActivity.class);
                getActivity().startActivity(i);
            }
        });
        solar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),PartnerSolarActivity.class);
                getActivity().startActivity(i);
            }
        });
        HVAC.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),PartnerHVACActivity.class);
                getActivity().startActivity(i);
            }
        });
        window_film.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), PartnerWindowFilmActivity.class);
                getActivity().startActivity(i);
            }
        });


        seachButton.setOnClickListener(new View.OnClickListener() {
                                           public void onClick(View v)
                                           {

                                               final String searchText = search.getText().toString();
                                               System.out.println(searchText);
                                               //searchText.equalsIgnoreCase()
                                               if(searchText.equalsIgnoreCase("Motion Detection"))
                                               {
                                                   Intent i = new Intent(getActivity(), PartnerSmartLightingActivity.class);
                                                   getActivity().startActivity(i);
                                               }

                                               else if(searchText.equalsIgnoreCase("Occupancy Detection"))
                                               {
                                                   Intent i = new Intent(getActivity(), PartnerSmartLightingActivity.class);
                                                   getActivity().startActivity(i);
                                               }
                                               else if(searchText.equalsIgnoreCase("Daylight Harvest Sensor"))
                                               {
                                                   Intent i = new Intent(getActivity(), PartnerSmartLightingActivity.class);
                                                   getActivity().startActivity(i);
                                               }
                                               else if(searchText.equalsIgnoreCase("Solar"))
                                               {
                                                   Intent i = new Intent(getActivity(), PartnerSolarActivity.class);
                                                   getActivity().startActivity(i);
                                               }
                                               else if(searchText.equalsIgnoreCase("HVAC"))
                                               {
                                                   Intent i = new Intent(getActivity(), PartnerHVACActivity.class);
                                                   getActivity().startActivity(i);
                                               }
                                               else if(searchText.equalsIgnoreCase("Window Film"))
                                               {
                                                   Intent i = new Intent(getActivity(), PartnerWindowFilmActivity.class);
                                                   getActivity().startActivity(i);
                                               }
                                               else
                                               {
                                                   alert.showAlertDialog(getContext(), "Product not found", "Please re-enter the keyword", false);
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
