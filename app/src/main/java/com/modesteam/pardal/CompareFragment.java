package com.modesteam.pardal;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link CompareFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CompareFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_AVERAGE_EXCEDED1 = "averageExceded1";
    private static final String ARG_AVERAGE_EXCEDED2 = "averageExceded2";
    private static final String ARG_TOTAL_TICKETS1 = "totalTickets1";
    private static final String ARG_TOTAL_TICKETS2 = "totalTickets2";
    private static final String ARG_MAXIMUM_MEASURE_VELOCITY1 = "maximumMeasuredVelocity1";
    private static final String ARG_MAXIMUM_MEASURE_VELOCITY2 = "maximumMeasuredVelocity2";
    private static final String ARG_NAME_ITEM1 = "nameItem1";
    private static final String ARG_NAME_ITEM2 = "nameItem2";
    private static final String ARG_NAME_CATEGORY = "nameCategory";

    // TODO: Rename and change types of parameters
    private ComparableCategory mParam1;
    private ComparableCategory mParam2;
    private String mParam3;
    private Double averageExceded1;
    private Double averageExceded2;
    private  Double maximumMeasuredVelocity1;
    private Double maximumMeasuredVelocity2;
    private int totalTickets1;
    private int totalTickets2;
    private String nameItem1;
    private String nameItem2;
    private String nameCategory;

    private OnFragmentInteractionListener mListener;

    // TODO: Rename and change types and number of parameters
    public static CompareFragment newInstance(ComparableCategory param1, ComparableCategory param2 ,String param3) {
        CompareFragment fragment = new CompareFragment();
        Bundle args = new Bundle();

        args.putDouble(ARG_AVERAGE_EXCEDED1, param1.getAverageExceded());
        args.putDouble(ARG_AVERAGE_EXCEDED2, param2.getAverageExceded());
        args.putInt(ARG_TOTAL_TICKETS1, param1.getTotalTickets());
        args.putInt(ARG_TOTAL_TICKETS2, param2.getTotalTickets());
        args.putDouble(ARG_MAXIMUM_MEASURE_VELOCITY1, param1.getMaximumMeasuredVelocity());
        args.putDouble(ARG_MAXIMUM_MEASURE_VELOCITY2, param2.getMaximumMeasuredVelocity());
        args.putString(ARG_NAME_ITEM1, param1.toString());
        args.putString(ARG_NAME_ITEM2, param2.toString());
        args.putString(ARG_NAME_CATEGORY, param3);

        fragment.setArguments(args);
        return fragment;
    }

    public CompareFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            averageExceded1 = getArguments().getDouble(ARG_AVERAGE_EXCEDED1);
            averageExceded2 = getArguments().getDouble(ARG_AVERAGE_EXCEDED2);
            maximumMeasuredVelocity1 = getArguments().getDouble(ARG_MAXIMUM_MEASURE_VELOCITY1);
            maximumMeasuredVelocity2 = getArguments().getDouble(ARG_MAXIMUM_MEASURE_VELOCITY2);
            totalTickets1 = getArguments().getInt(ARG_TOTAL_TICKETS1);
            totalTickets2 = getArguments().getInt(ARG_TOTAL_TICKETS2);
            nameItem1 = getArguments().getString(ARG_NAME_ITEM1);
            nameItem2 = getArguments().getString(ARG_NAME_ITEM2);
            nameCategory = getArguments().getString(ARG_NAME_CATEGORY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_compare, container, false);
        showCompareOfItens(rootView);
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

    public void showCompareOfItens (View view){
        double percentageMaximum = 0.0;
        double percentageTotalTickets = 0.0;

        percentageMaximum = (maximumMeasuredVelocity1-maximumMeasuredVelocity2);
        if(percentageMaximum < 0){
            percentageMaximum *= -1;
        }
        if(maximumMeasuredVelocity1>maximumMeasuredVelocity2){
            percentageMaximum = (percentageMaximum/maximumMeasuredVelocity1)*100;
        }else{
            percentageMaximum = (percentageMaximum/maximumMeasuredVelocity2)*100;
        }

        percentageTotalTickets = (totalTickets1-totalTickets2);
        if(percentageTotalTickets < 0){
            percentageTotalTickets *= -1;
        }
        if(totalTickets1>totalTickets2){
            percentageTotalTickets = (percentageTotalTickets/totalTickets1)*100;
        }else{
            percentageTotalTickets = (percentageTotalTickets/totalTickets2)*100;
        }

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Quango.otf");

        TextView titleTextView = (TextView) view.findViewById(R.id.titleTextView);
        titleTextView.setText(nameCategory);

        TextView itemName1textView = (TextView) view.findViewById(R.id.itemName1textView);
        itemName1textView.setText(nameItem1);
        itemName1textView.setTypeface(typeface);

        TextView itemName2textView = (TextView) view.findViewById(R.id.itemName2textView);
        itemName2textView.setText(nameItem2);
        itemName2textView.setTypeface(typeface);


        TextView averageValue1TextView = (TextView) view.findViewById(R.id.averageValue1TextView);
        TextView averageValue2TextView = (TextView) view.findViewById(R.id.averageValue2TextView);
        if(averageExceded1>averageExceded2){
            averageValue1TextView.setTextColor(Color.RED);
            averageValue2TextView.setTextColor(Color.parseColor("#ff188341"));
        }else{
            averageValue1TextView.setTextColor(Color.parseColor("#ff188341"));
            averageValue2TextView.setTextColor(Color.RED);
        }
        DecimalFormat f = new DecimalFormat("#.##");
        averageValue1TextView.setText(""+f.format(averageExceded1));
        averageValue1TextView.setTypeface(typeface);
        averageValue2TextView.setText(""+f.format(averageExceded2));
        averageValue2TextView.setTypeface(typeface);

        TextView totalTicketsValue1TextView = (TextView) view.findViewById(R.id.totalTicketsValue1TextView);
        TextView totalTicketsValue2TextView = (TextView) view.findViewById(R.id.totalTicketsValue2TextView);
        if(totalTickets1>totalTickets2){
            totalTicketsValue1TextView.setTextColor(Color.RED);
            totalTicketsValue2TextView.setTextColor(Color.parseColor("#ff188341"));
        }else{
            totalTicketsValue1TextView.setTextColor(Color.parseColor("#ff188341"));
            totalTicketsValue2TextView.setTextColor(Color.RED);
        }
        totalTicketsValue1TextView.setText(Integer.toString(totalTickets1));
        totalTicketsValue1TextView.setTypeface(typeface);
        totalTicketsValue2TextView.setText(Integer.toString(totalTickets2));
        totalTicketsValue2TextView.setTypeface(typeface);
        TextView maximumValue1TextView = (TextView) view.findViewById(R.id.maximumValue1TextView);
        TextView maximumValue2TextView = (TextView) view.findViewById(R.id.maximumValue2TextView);
        if(maximumMeasuredVelocity1>maximumMeasuredVelocity2){
            maximumValue1TextView.setTextColor(Color.RED);
            maximumValue2TextView.setTextColor(Color.parseColor("#ff188341"));
        }else{
            maximumValue1TextView.setTextColor(Color.parseColor("#ff188341"));
            maximumValue2TextView.setTextColor(Color.RED);
        }
        maximumValue1TextView.setText(""+f.format(maximumMeasuredVelocity1));
        maximumValue1TextView.setTypeface(typeface);
        maximumValue2TextView.setText(""+f.format(maximumMeasuredVelocity2));
        maximumValue2TextView.setTypeface(typeface);

        TextView percentageTotalTicketsTextView = (TextView) view.findViewById(R.id.percentageTotalTicketsTextView);
        if(maximumMeasuredVelocity1 == 0 && maximumMeasuredVelocity2 == 0){
            percentageTotalTicketsTextView.setText("Não há dados suficiente em ambos os itens");
        }else{
            percentageTotalTicketsTextView.setText(""+f.format(percentageTotalTickets)+" %");
        }
        percentageTotalTicketsTextView.setTypeface(typeface);

        TextView percentageMaximumTextView = (TextView) view.findViewById(R.id.percentageMaximumTextView);
        if(totalTickets1 == 0 && totalTickets2 == 0){
            percentageMaximumTextView.setText("Não há dados suficiente em ambos os itens");
        }else{
            percentageMaximumTextView.setText(""+f.format(percentageMaximum)+" %");
        }
        percentageMaximumTextView.setTypeface(typeface);

    }

}
