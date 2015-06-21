package com.modesteam.pardal;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.modesteam.pardal.category.CategoryContent;

import java.sql.SQLException;

import exception.GenericAlertDialogException;
import models.Brand;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link BrandDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BrandDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static Brand brandDetail = null;
    private static final String ID_BRAND = "idBrand";

    // TODO: Rename and change types of parameters
    private int idBrand;


    private OnFragmentInteractionListener mListener;

    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @return A new instance of fragment BrandDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BrandDetailFragment newInstance(Brand brand) {
        BrandDetailFragment fragment = new BrandDetailFragment();
        Bundle args = new Bundle();
        brandDetail = brand;
        args.putInt(ID_BRAND, brand.getId());
        fragment.setArguments(args);
        return fragment;
    }

    public BrandDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idBrand = getArguments().getInt(ID_BRAND);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_brand_detail, container, false);
        detailBrand(rootView);
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

    public void detailBrand(View view) {
        try {
            if (brandDetail==null){
                brandDetail = Brand.get(getArguments().getInt(ID_BRAND));
            }

        } catch (NullPointerException e){
            GenericAlertDialogException genericAlertDialogException = new GenericAlertDialogException();
            genericAlertDialogException.createAlert(this.getActivity());
        }catch (ClassNotFoundException e) {
            GenericAlertDialogException genericAlertDialogException = new GenericAlertDialogException();
            genericAlertDialogException.createAlert(this.getActivity());
        } catch (SQLException e) {
            GenericAlertDialogException genericAlertDialogException = new GenericAlertDialogException();
            genericAlertDialogException.createAlert(this.getActivity());
        }

        double averageExceded = brandDetail.getAverageExceded(), maximumMeasuredVelocity=brandDetail.getMaximumMeasuredVelocity();
        int  totalTickets = brandDetail.getTotalTickets();
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Quango.otf");

        TextView textViewTotalTickets = (TextView) view.findViewById(R.id.textViewTotalTickets);
        textViewTotalTickets.setText(Integer.toString(totalTickets));
        textViewTotalTickets.setTypeface(typeface);

        TextView textViewCompare = (TextView) view.findViewById(R.id.textViewCompare);
        textViewCompare.setTypeface(typeface);

        TextView textViewName = (TextView) view.findViewById(R.id.textViewName);
        textViewName.setText((brandDetail.getName()));
        textViewName.setTypeface(typeface);

        TextView textViewAverageExceded = (TextView) view.findViewById(R.id.textViewAverageExceded);
        textViewAverageExceded.setText((String.format("%.1f", averageExceded) + " km/h"));
        textViewAverageExceded.setTypeface(typeface);

        TextView textViewMaximumMeasuredVelocity = (TextView) view.findViewById(R.id.textViewMaximumMeasuredVelocity);
        textViewMaximumMeasuredVelocity.setText((Double.toString(maximumMeasuredVelocity )+ " km/h"));
        textViewMaximumMeasuredVelocity.setTypeface(typeface);

        ImageButton compareButton = (ImageButton) view.findViewById(R.id.compareButton);
        compareButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mListener.onFragmentInteraction(brandDetail.getId(),BrandListFragment.newInstance(brandDetail));
            }
        });
    }


}
