package com.modesteam.pardal;

import android.app.Activity;
import android.graphics.Typeface;
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
 * Use the {@link StateDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StateDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static State stateDetail = null;
    private static final String ID_STATE = "idState";

    // TODO: Rename and change types of parameters
    private int idState;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @return A new instance of fragment StateDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StateDetailFragment newInstance(State state) {
        StateDetailFragment fragment = new StateDetailFragment();
        Bundle args = new Bundle();
        stateDetail = state;
        args.putInt(ID_STATE, state.getId());
        fragment.setArguments(args);
        return fragment;
    }

    public StateDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idState = getArguments().getInt(ID_STATE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_state_detail, container, false);
        detailState(rootView);
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

    public void detailState(View view) {
        ArrayList<City> listCity = null;
        ArrayList<HighwayStretch> listHighwayStretches = null;
        ArrayList<Tickets> listTickets = null;
        try {
            listCity = stateDetail.getCities();
            listHighwayStretches = new ArrayList<>();
            listTickets = new ArrayList<>();
            if (stateDetail==null){
                stateDetail = State.get(getArguments().getInt(ID_STATE));
            }
        }catch (NullPointerException e){
            GenericAlertDialogException genericAlertDialogException = new GenericAlertDialogException();
            genericAlertDialogException.createAlert(this.getActivity());
        }

        double averageExceded = stateDetail.getAverageExceded();
        double maximumMeasuredVelocity = stateDetail.getMaximumMeasuredVelocity();
        int totalTickets = stateDetail.getTotalTickets();
        int totalCities = listCity.size();
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Quango.otf");

        TextView textViewName = (TextView) view.findViewById(R.id.textViewName);
        textViewName.setText((stateDetail.getName()));
        textViewName.setTypeface(typeface);

        TextView textViewCities = (TextView) view.findViewById(R.id.textViewCities);
        textViewCities.setText(Integer.toString(totalCities));
        textViewCities.setTypeface(typeface);

        TextView textViewTotalTickets = (TextView) view.findViewById(R.id.textViewTotalTickets);
        textViewTotalTickets.setText(Integer.toString(totalTickets));
        textViewTotalTickets.setTypeface(typeface);

        TextView textViewMaximumMeasuredVelocity = (TextView) view.findViewById(R.id.textViewMaximumMeasuredVelocity);
        textViewMaximumMeasuredVelocity.setText(String.format("%.1f", maximumMeasuredVelocity) + " km/h");
        textViewMaximumMeasuredVelocity.setTypeface(typeface);

        TextView textViewAverageExceded = (TextView) view.findViewById(R.id.textViewAverageExceded);
        textViewAverageExceded.setText(String.format("%.1f", averageExceded) + " km/h");
        textViewAverageExceded.setTypeface(typeface);
        TextView textViewCompare = (TextView) view.findViewById(R.id.textViewCompare);
        textViewCompare.setTypeface(typeface);
        ImageButton compareButton = (ImageButton) view.findViewById(R.id.compareButton);
        compareButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mListener.onFragmentInteraction(stateDetail.getId(),StateListFragment.newInstance(stateDetail));
            }
        });
    }
}