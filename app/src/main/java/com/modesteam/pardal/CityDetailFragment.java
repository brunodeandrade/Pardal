package com.modesteam.pardal;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import exception.GenericAlertDialogException;
import models.City;
import models.HighwayStretch;
import models.State;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link CityDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CityDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static City cityForDetail = null;
    private static final String ID_CITY = "idCity";

    private OnFragmentInteractionListener mListener;

    private int idCity;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment CityDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CityDetailFragment newInstance(City city) {
        CityDetailFragment fragment = new CityDetailFragment();
        Bundle args = new Bundle();
        cityForDetail = city;
        args.putInt(ID_CITY,city.getId());
        fragment.setArguments(args);
        return fragment;
    }

    public CityDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idCity = getArguments().getInt(ID_CITY);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_city_detail, container, false);
        detailType(rootView);

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

    public void detailType(View view) {

        ArrayList<HighwayStretch> arrayHighwayStretchesOfCity = null;
        State stateOfCity = null;
        try {
            if (cityForDetail==null){
                cityForDetail = City.get(getArguments().getInt(ID_CITY));
            }
            arrayHighwayStretchesOfCity = cityForDetail.getHighwayStretches();
            stateOfCity = cityForDetail.getState();


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

        TextView nameCity, totalHighwayStretches,totalTickets,maxVelocity, averageExceded;

        nameCity = (TextView) view.findViewById(R.id.textViewName);
        nameCity.setText(cityForDetail.getName()+" - "+stateOfCity.getName());

        totalHighwayStretches = (TextView) view.findViewById(R.id.textViewHighwayStretches);
        totalHighwayStretches.setText("" + arrayHighwayStretchesOfCity.size());

        totalTickets = (TextView) view.findViewById(R.id.textViewTickets);
        totalTickets.setText(""+cityForDetail.getTotalTickets());

        maxVelocity = (TextView) view.findViewById(R.id.textViewMaximumMeasuredVelocity);
        maxVelocity.setText(cityForDetail.getMaximumMeasuredVelocity().toString());

        averageExceded = (TextView) view.findViewById(R.id.textViewAverageExceded);
        DecimalFormat f = new DecimalFormat("#.##");
        averageExceded.setText(""+f.format(cityForDetail.getAverageExceded()));

        Button compareButton = (Button) view.findViewById(R.id.compareButton);
        compareButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mListener.onFragmentInteraction(cityForDetail.getId(), CityListFragment.newInstance(cityForDetail));
            }
        });

    }

}
