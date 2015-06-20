package com.modesteam.pardal;

import android.app.Activity;
import android.content.Context;
import android.graphics.AvoidXfermode;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;


import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Highlight;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.DuplicateFormatFlagsException;
import java.util.Objects;

import models.Brand;
import models.City;
import models.HighwayStretch;
import models.Model;
import models.State;
import models.Type;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class TopTenListFragment extends Fragment implements AbsListView.OnItemClickListener, OnChartValueSelectedListener {


    // TODO: Rename and change types of parameters

    private static final String ARG_PARAM1 = "param1";

    private String mParam1;

    private Object bean;
    private String fieldName;
    private ArrayList<Object> arrayListRankingObject = null;
    private float[] arrayValuesListRankingObject;
    private Typeface typeface;
    private int[] arrayIndexChart = new int[10];

    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;

    // TODO: Rename and change types of parameters
    public static TopTenListFragment newInstance(Object object, String fieldName, String param1) {
        TopTenListFragment fragment = new TopTenListFragment(object, fieldName);
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TopTenListFragment(Object object, String fieldName) {
        this.bean = object;
        this.fieldName = fieldName;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }

        rankCategory(this.bean, this.fieldName);
        this.typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Quango.otf");

//        mAdapter = new ArrayAdapter<Object>(getActivity(),
//                android.R.layout.simple_list_item_1, android.R.id.text1, this.arrayListRankingObject);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        // Set the adapter
//        mListView = (AbsListView) view.findViewById(android.R.id.list);
//        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);
//        mListView.setOnItemClickListener(this);

        HorizontalBarChart chart = (HorizontalBarChart) view.findViewById(R.id.chartRanking);

        chart.setOnChartValueSelectedListener(this);
        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);
        chart.setDescription("");
        chart.setPinchZoom(false);
        chart.setDrawGridBackground(false);
        chart.setScaleEnabled(false);
        // mChart.setHighlightEnabled(false);
        // mChart.setDrawXLabels(false);
        // mChart.setDrawYLabels(false);

        XAxis xl = chart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(true);
        xl.setGridLineWidth(0.3f);
        xl.setTextSize(12f);
        xl.setTextColor(Color.BLACK);
        //xl.setTypeface(typeface);

        YAxis yl = chart.getAxisLeft();
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(true);
        yl.setGridLineWidth(0.3f);
        //yl.setTypeface(typeface);
        //yl.setTextSize();
        //yl.setTextColor(Color.GREEN);

        YAxis yr = chart.getAxisRight();
        yr.setDrawAxisLine(true);
        yr.setDrawGridLines(false);
        //yr.setTypeface(typeface);
        //yr.setTextSize();
       // yr.setTextColor(Color.RED);

        setData(chart);
        chart.animateY(2500);

        // setting data
        Legend l = chart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setFormSize(8f);
        l.setXEntrySpace(4f);
        //l.setTypeface(typeface);
        // mChart.setDrawLegend(false);

        return view;
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


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    private void rankCategory(Object bean, String fieldName){
        RankingCategory rankingCategory = new RankingCategory();
        ArrayList<Object> arrayListRankingObject = null;
        float[] arrayValuesListRankingObject = null;
        try {
            arrayListRankingObject = rankingCategory.rankCategoryWithField(bean, fieldName);
            arrayValuesListRankingObject = rankingCategory.getValuesRankCategoryWithField(arrayListRankingObject, fieldName, bean);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        this.arrayListRankingObject = arrayListRankingObject;
        this.arrayValuesListRankingObject = arrayValuesListRankingObject;
    }

    private void setData(HorizontalBarChart horizontalBarChart) {

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i=this.arrayListRankingObject.size()-1; i>=0; i--){
            xVals.add(this.arrayListRankingObject.get(i).toString());
        }


        ArrayList<BarEntry> yVals = new ArrayList<BarEntry>();

        int indexChart = this.arrayListRankingObject.size()-1;
        for (int i = 0; i<this.arrayListRankingObject.size() ; i++) {
            BarEntry v1e1 = new BarEntry(this.arrayValuesListRankingObject[i], indexChart);
            yVals.add(v1e1);
            this.arrayIndexChart[i] = indexChart;
            indexChart--;
        }


        BarDataSet set1 = new BarDataSet(yVals, this.fieldName);
        set1.setBarSpacePercent(35f);

        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(set1);

        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(12f);
        //data.setValueTypeface(typeface);

        horizontalBarChart.setData(data);
    }

    @Override
    public void onValueSelected(Entry entry, int i, Highlight highlight) {
        if (entry == null) {
            return;
        }
        switch(mParam1){
            case "1":
                State state = (State)(this.arrayListRankingObject.get(this.arrayIndexChart[highlight.getXIndex()]));
                mListener.onFragmentInteraction(0,StateDetailFragment.newInstance(state));
                break;
            case "2":
                City city = (City)(this.arrayListRankingObject.get(this.arrayIndexChart[highlight.getXIndex()]));
                mListener.onFragmentInteraction(0,CityDetailFragment.newInstance(city));
                break;
            case "3":
                HighwayStretch highwayStretch = (HighwayStretch)(this.arrayListRankingObject.get(this.arrayIndexChart[highlight.getXIndex()]));
                mListener.onFragmentInteraction(0,HighwayStretchDetailFragment.newInstance(highwayStretch));
                break;
            case "4":
                Model model = (Model)(this.arrayListRankingObject.get(this.arrayIndexChart[highlight.getXIndex()]));
                mListener.onFragmentInteraction(0,ModelDetailFragment.newInstance(model));
                break;
            case "5":
                Type type = (Type)(this.arrayListRankingObject.get(this.arrayIndexChart[highlight.getXIndex()]));
                mListener.onFragmentInteraction(0,TypeDetailFragment.newInstance(type));
                break;
            case "6":
                Brand brand = (Brand)(this.arrayListRankingObject.get(this.arrayIndexChart[highlight.getXIndex()]));
                mListener.onFragmentInteraction(0,BrandDetailFragment.newInstance(brand));
                break;
        }


    }

    @Override
    public void onNothingSelected() {

    }
}
