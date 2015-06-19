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
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;

import exception.GenericAlertDialogException;
import models.Model;
import models.Tickets;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link //ModelDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ModelDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ModelDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static Model modelDetail = null;
    private static final String ID_MODEL = "idModel";

    // TODO: Rename and change types of parameters
    private int idModel;

    private OnFragmentInteractionListener mListener;

    // TODO: Rename and change types and number of parameters
    public static ModelDetailFragment newInstance(Model model) {
        ModelDetailFragment fragment = new ModelDetailFragment();
        Bundle args = new Bundle();
        modelDetail = model;
        args.putInt(ID_MODEL, model.getId());
        fragment.setArguments(args);
        return fragment;
    }

    public ModelDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idModel = getArguments().getInt(ID_MODEL);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_model_detail, container, false);
        detailModel(rootView);
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        //if (mListener != null) {
        //  mListener.onFragmentInteraction(uri);
        //}
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
    public void detailModel(View view){

        ArrayList<Tickets> tickets = null;
        try {
            if (modelDetail==null){
                modelDetail = Model.get(getArguments().getInt(ID_MODEL));
            }

        }catch (NullPointerException e){
            GenericAlertDialogException genericAlertDialogException = new GenericAlertDialogException();
            genericAlertDialogException.createAlert(this.getActivity());
        }
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Quango.otf");
        TextView textViewName = (TextView) view.findViewById(R.id.textViewName);
        textViewName.setText(modelDetail.getName());
        textViewName.setTypeface(typeface);
        TextView textViewTotalTickets = (TextView) view.findViewById(R.id.textViewTotalTickets);
        textViewTotalTickets.setText(Integer.toString(modelDetail.getTotalTickets()));
        textViewTotalTickets.setTypeface(typeface);
        TextView textViewMaximumMeasuredVelocity = (TextView) view.findViewById(R.id.textViewMaximumMeasuredVelocity);
        textViewMaximumMeasuredVelocity.setText(Double.toString(modelDetail.getMaximumMeasuredVelocity()));
        textViewMaximumMeasuredVelocity.setTypeface(typeface);
        TextView textViewAverageExceded = (TextView) view.findViewById(R.id.textViewAverageExceded);
        textViewAverageExceded.setText(String.format("%.2fKM/h", modelDetail.getAverageExceded()));
        textViewAverageExceded.setTypeface(typeface);
        Button compareButton = (Button) view.findViewById(R.id.compareButton);
        compareButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mListener.onFragmentInteraction(modelDetail.getId(),ModelListFragment.newInstance(modelDetail));
            }
        });
    }

}