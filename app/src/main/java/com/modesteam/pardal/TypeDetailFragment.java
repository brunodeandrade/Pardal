package com.modesteam.pardal;

import android.app.Activity;
import android.graphics.Typeface;
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
import models.Model;
import models.Type;


public class TypeDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static Type typeForDetail = null;
    private static final String ID_TYPE = "idType";

    // TODO: Rename and change types of parameters
    private static int idType;

    private OnFragmentInteractionListener mListener;

    // TODO: Rename and change types and number of parameters
    public static TypeDetailFragment newInstance(Type type) {
        TypeDetailFragment fragment = new TypeDetailFragment();
        Bundle args = new Bundle();
        typeForDetail = type;
        args.putInt(ID_TYPE,type.getId());
        fragment.setArguments(args);
        return fragment;
    }

    public TypeDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idType = getArguments().getInt(ID_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_type_detail, container, false);
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

        ArrayList<Model> arrayModelsOfType = null;

        try {
            if (typeForDetail==null){
                typeForDetail = Type.get(getArguments().getInt(ID_TYPE));
            }
            arrayModelsOfType = typeForDetail.getModels();
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

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Quango.otf");
        TextView textViewName, textViewTotalModels,textViewTotalTickets, textViewMaximumMeasuredVelocity, textViewAverageExceded;

        textViewName = (TextView) view.findViewById(R.id.textViewName);
        textViewName.setText(typeForDetail.getName());
        textViewName.setTypeface(typeface);

        textViewTotalModels = (TextView) view.findViewById(R.id.textViewTotalModels);
        textViewTotalModels.setText(""+arrayModelsOfType.size());

        textViewTotalTickets = (TextView) view.findViewById(R.id.textViewTotalTickets);
        textViewTotalTickets.setText(""+typeForDetail.getTotalTickets());

        textViewMaximumMeasuredVelocity = (TextView) view.findViewById(R.id.textViewMaximumMeasuredVelocity);
        textViewMaximumMeasuredVelocity.setText(typeForDetail.getMaximumMeasuredVelocity().toString());

        textViewAverageExceded = (TextView) view.findViewById(R.id.textViewAverageExceded);
        DecimalFormat f = new DecimalFormat("#.##");
        textViewAverageExceded.setText(""+f.format(typeForDetail.getAverageExceded()));

        TextView typeDesc = (TextView) view.findViewById(R.id.textViewDesc);
        typeDesc.setText(typeForDetail.getDescription());

        Button compareButton = (Button) view.findViewById(R.id.compareButton);
        compareButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mListener.onFragmentInteraction(typeForDetail.getId(),TypeListFragment.newInstance(typeForDetail));
            }
        });
    }

}