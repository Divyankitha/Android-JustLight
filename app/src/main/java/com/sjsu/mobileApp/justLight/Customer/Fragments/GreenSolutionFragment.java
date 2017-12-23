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
import android.widget.EditText;
import android.widget.ImageButton;

import com.sjsu.mobileApp.justLight.Customer.Activity.DHSPatnersActivity;
import com.sjsu.mobileApp.justLight.Customer.Activity.HVACActivity;
import com.sjsu.mobileApp.justLight.Customer.Activity.HVACPatnersActivity;
import com.sjsu.mobileApp.justLight.Customer.Activity.MDPatnersActivity;
import com.sjsu.mobileApp.justLight.Customer.Activity.ODPatnersActivity;
import com.sjsu.mobileApp.justLight.Customer.Activity.SmartLightingActivity;
import com.sjsu.mobileApp.justLight.Customer.Activity.SolarActivity;
import com.sjsu.mobileApp.justLight.Customer.Activity.SolarPatnersActivity;
import com.sjsu.mobileApp.justLight.Customer.Activity.WindowFilmActivity;
import com.sjsu.mobileApp.justLight.R;
import com.sjsu.mobileApp.justLight.Utility.AlertDialogManager;


public class GreenSolutionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    AlertDialogManager alert = new AlertDialogManager();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public GreenSolutionFragment() {
        // Required empty public constructor
    }


    public static GreenSolutionFragment newInstance(String param1, String param2) {
        GreenSolutionFragment fragment = new GreenSolutionFragment();
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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_green_solution, container, false);

        final EditText search = (EditText) v.findViewById(R.id.input_search);
        ImageButton seachButton = (ImageButton) v.findViewById(R.id.customerSearchButton);
        Button lighting = (Button)v.findViewById(R.id.Smartlighting);
        Button solar=(Button)v.findViewById(R.id.solar);
        Button HVAC=(Button)v.findViewById(R.id.HVAC);
        Button window_film=(Button)v.findViewById(R.id.window_film);
        lighting.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),SmartLightingActivity.class);
                getActivity().startActivity(i);
            }
        });
        solar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),SolarActivity.class);
                getActivity().startActivity(i);
            }
        });
        HVAC.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),HVACActivity.class);
                getActivity().startActivity(i);
            }
        });
        window_film.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), WindowFilmActivity.class);
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
                    Intent i = new Intent(getActivity(), MDPatnersActivity.class);
                    getActivity().startActivity(i);
                }

                else if(searchText.equalsIgnoreCase("Occupancy Detection"))
                {
                    Intent i = new Intent(getActivity(), ODPatnersActivity.class);
                    getActivity().startActivity(i);
                }
                else if(searchText.equalsIgnoreCase("Daylight Harvest Sensor"))
                {
                    Intent i = new Intent(getActivity(), DHSPatnersActivity.class);
                    getActivity().startActivity(i);
                }
                else if(searchText.equalsIgnoreCase("Solar"))
                {
                    Intent i = new Intent(getActivity(), SolarPatnersActivity.class);
                    getActivity().startActivity(i);
                }
                else if(searchText.equalsIgnoreCase("HVAC"))
                {
                    Intent i = new Intent(getActivity(), HVACPatnersActivity.class);
                    getActivity().startActivity(i);
                }
                else if(searchText.equalsIgnoreCase("Window Film"))
                {
                    Intent i = new Intent(getActivity(), WindowFilmActivity.class);
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