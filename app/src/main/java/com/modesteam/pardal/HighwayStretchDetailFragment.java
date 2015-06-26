package com.modesteam.pardal;

import android.app.Activity;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
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

        }catch (NullPointerException e){
            GenericAlertDialogException genericAlertDialogException = new GenericAlertDialogException();
            genericAlertDialogException.createAlert(this.getActivity());
        }
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Quango.otf");

        //Imprime total ticktes
        TextView textViewTotalTickets = (TextView) view.findViewById(R.id.textViewTotalTickets);
        textViewTotalTickets.setText(Integer.toString(highwayStretchDetail.getTotalTickets()));
        textViewTotalTickets.setTypeface(typeface);

        //Imprime velocidade limite dos carros na rodovia
        TextView textViewVelocityLimit = (TextView) view.findViewById(R.id.velocityLimit);
        textViewVelocityLimit.setText(Double.toString(velocityLimitOfHighwayStretch) + " km/h");
        textViewVelocityLimit.setTypeface(typeface);

        //Imprime a media de velocidade excedida
        TextView textViewAverageExceded = (TextView) view.findViewById(R.id.textViewAverageExceded);
        textViewAverageExceded.setText(Double.toString(velocityLimitOfHighwayStretch) + " km/h");
        textViewAverageExceded.setTypeface(typeface);

        //Imprime a maxima velocidade registrada
        TextView textViewMaximumMeasuredVelocity = (TextView) view.findViewById(R.id.textViewMaximumMeasuredVelocity);
        textViewMaximumMeasuredVelocity.setText(Double.toString(highwayStretchDetail.getMaximumMeasuredVelocity()) + " km/h");
        textViewMaximumMeasuredVelocity.setTypeface(typeface);

        TextView textViewName = (TextView) view.findViewById(R.id.textViewName);
        textViewName.setText("BR "+(highwayStretchDetail.getNumber())+" KM "+(highwayStretchDetail.getKilometer()));
        textViewName.setTypeface(typeface);

        TextView textViewcityState = (TextView) view.findViewById(R.id.textViewCityState);
        textViewcityState.setText(""+(cityOfHighwayStretch.getName())+"/"+(stateOfHighwayStretch.getName()));
        textViewcityState.setTypeface(typeface);

        TextView textViewCompare = (TextView) view.findViewById(R.id.textViewCompare);
        textViewCompare.setTypeface(typeface);

        ImageButton compareButton = (ImageButton) view.findViewById(R.id.compareButton);
        compareButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mListener.onFragmentInteraction(highwayStretchDetail.getId(),HighwayStretchListFragment.newInstance(highwayStretchDetail));
            }
        });
    }
}
