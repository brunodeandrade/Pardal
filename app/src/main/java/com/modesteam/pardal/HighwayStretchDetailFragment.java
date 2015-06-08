package com.modesteam.pardal;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;

import exception.GenericAlertDialogException;
import models.City;
import models.HighwayStretch;
import models.State;
import models.Tickets;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link HighwayStretchDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HighwayStretchDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static HighwayStretch highwayStretchDetail = null;
    private static final String ID_HIGHWAY_STRETCH = "idHighwayStretch";


    // TODO: Rename and change types of parameters
    private int idHighwayStretch;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @return A new instance of fragment HighwayStretchDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HighwayStretchDetailFragment newInstance(HighwayStretch highwayStretch) {
        HighwayStretchDetailFragment fragment = new HighwayStretchDetailFragment();
        Bundle args = new Bundle();
        highwayStretchDetail = highwayStretch;
        args.putInt(ID_HIGHWAY_STRETCH, highwayStretch.getId());
        fragment.setArguments(args);
        return fragment;
    }

    public HighwayStretchDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idHighwayStretch = getArguments().getInt(ID_HIGHWAY_STRETCH);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_highway_stretch_detail, container, false);
        detailHighwayStretch(rootView);
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(0, HighwayStretchListFragment.newInstance("",""));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void detailHighwayStretch(View view) {

        City cityOfHighwayStretch = null;
        State stateOfHighwayStretch = null;
        ArrayList<Tickets> tickets = null;
        double velocityLimitOfHighwayStretch = 0.0;


        try {
            if (highwayStretchDetail==null){
                highwayStretchDetail = HighwayStretch.get(getArguments().getInt(ID_HIGHWAY_STRETCH));
            }
            cityOfHighwayStretch = highwayStretchDetail.getCity();
            stateOfHighwayStretch = cityOfHighwayStretch.getState();

            tickets = highwayStretchDetail.getTickets();

            if (tickets.size()>0) {
                velocityLimitOfHighwayStretch = tickets.get(0).getVelocityLimit();
            }

        }catch(ClassNotFoundException e){
            GenericAlertDialogException genericAlertDialogException = new GenericAlertDialogException();
            genericAlertDialogException.createAlert(this.getActivity());
        }catch(SQLException e){
            GenericAlertDialogException genericAlertDialogException = new GenericAlertDialogException();
            genericAlertDialogException.createAlert(this.getActivity());
        }catch (NullPointerException e){
            GenericAlertDialogException genericAlertDialogException = new GenericAlertDialogException();
            genericAlertDialogException.createAlert(this.getActivity());
        }


        //Imprime total ticktes
        TextView totalTickets = (TextView) view.findViewById(R.id.totalTickets);
        totalTickets.setText(Integer.toString(highwayStretchDetail.getTotalTickets()));


        //Imprime velocidade limite dos carros na rodovia
        TextView velocityLimit = (TextView) view.findViewById(R.id.velocityLimit);
        velocityLimit.setText(Double.toString(velocityLimitOfHighwayStretch) + " km/h");

        //Imprime a media de velocidade excedida
        TextView averageExceded = (TextView) view.findViewById(R.id.averageExceded);
        averageExceded.setText(Double.toString(velocityLimitOfHighwayStretch) + " km/h");

        //Imprime a maxima velocidade registrada
        TextView maximumMeasuredVelocity = (TextView) view.findViewById(R.id.maximumMeasuredVelocity);
        maximumMeasuredVelocity.setText(Double.toString(highwayStretchDetail.getMaximumMeasuredVelocity()) + " km/h");

        TextView name = (TextView) view.findViewById(R.id.textViewName);
        name.setText("BR "+(highwayStretchDetail.getNumber())+" KM "+(highwayStretchDetail.getKilometer()));

        TextView cityState = (TextView) view.findViewById(R.id.textViewCityState);
        cityState.setText(""+(cityOfHighwayStretch.getName())+"/"+(stateOfHighwayStretch.getName()));

        Button compareButton = (Button) view.findViewById(R.id.compareButton);
        compareButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mListener.onFragmentInteraction(highwayStretchDetail.getId(),HighwayStretchListFragment.newInstance(highwayStretchDetail));
            }
        });
    }
}
