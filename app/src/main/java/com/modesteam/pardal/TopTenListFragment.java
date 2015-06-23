package com.modesteam.pardal;

import android.app.Activity;
import android.graphics.AvoidXfermode;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.TextView;


import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Highlight;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import helpers.Category;
import helpers.GenericPersistence;
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

    private static final String NAME_ARRAY = "nameArray";
    private static final String VALUE_ARRAY = "valueArray";
    private static final String ID_ARRAY = "idArray";
    private static final String FIELD_NAME = "fieldName";
    private static final String CATEGORY = "category";



    private ArrayList<String> nameArray;
    private float[] valueArray;
    private ArrayList<Integer> idArray;
    private String fieldName;
    private String category;
    private Typeface typeface;

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
    public static TopTenListFragment newInstance(ComparableCategory bean, String fieldName) {
        TopTenListFragment fragment = new TopTenListFragment();
        Bundle args = new Bundle();
        Category category = bean.getCategory();
        args.putString(CATEGORY, category.name());
        args.putString(FIELD_NAME, fieldName);
        GenericPersistence gP = new GenericPersistence();
        ArrayList<Object> objects = gP.selectAllBeans(bean, 10, true, GenericPersistence.getField(bean, fieldName));
        Collections.reverse(objects);
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<Integer> ids = new ArrayList<Integer>();
        float[] values = new float[objects.size()];
        for (int i = 0; i < objects.size(); i++) {
            ComparableCategory categoryItem = (ComparableCategory)objects.get(i);
            names.add(categoryItem.toString());
            ids.add(categoryItem.getId());
            switch (fieldName){
                case "maximumMeasuredVelocity":
                    values[i] = categoryItem.getMaximumMeasuredVelocity().floatValue();
                    break;
                case "averageExceded":
                    values[i] = categoryItem.getAverageExceded().floatValue();
                    break;
                case "totalTickets":
                    values[i] = (float) categoryItem.getTotalTickets();
                    break;
            }
        }
        args.putStringArrayList(NAME_ARRAY, names);
        args.putIntegerArrayList(ID_ARRAY, ids);
        args.putFloatArray(VALUE_ARRAY, values);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TopTenListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            nameArray = getArguments().getStringArrayList(NAME_ARRAY);
            valueArray = getArguments().getFloatArray(VALUE_ARRAY);
            idArray = getArguments().getIntegerArrayList(ID_ARRAY);
            fieldName = getArguments().getString(FIELD_NAME);
            category = getArguments().getString(CATEGORY);
        }

        //rankCategory(this.bean, this.fieldName);
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

    private void setData(HorizontalBarChart horizontalBarChart) {

        ArrayList<BarEntry> yVals = new ArrayList<BarEntry>();

        for (int i = 0; i < nameArray.size(); i++) {
            yVals.add(new BarEntry(valueArray[i],i));
        }
        BarDataSet set1 = new BarDataSet(yVals, this.fieldName);
        set1.setBarSpacePercent(35f);

        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(set1);

        BarData data = new BarData(nameArray, dataSets);
        data.setValueTextSize(12f);

        horizontalBarChart.setData(data);
    }

    @Override
    public void onValueSelected(Entry entry, int i, Highlight highlight) {
        if (entry == null) {
            return;
        }
        Category category = Category.valueOf(this.category);
        int j = highlight.getXIndex();
        switch(category){
            case STATE:
                State state = State.get(idArray.get(j));
                mListener.onFragmentInteraction(0,StateDetailFragment.newInstance(state));
                break;
            case CITY:
                City city = City.get(idArray.get(j));
                mListener.onFragmentInteraction(0,CityDetailFragment.newInstance(city));
                break;
            case HIGHWAY_STRETCH:
                HighwayStretch highwayStretch = HighwayStretch.get(idArray.get(j));
                mListener.onFragmentInteraction(0,HighwayStretchDetailFragment.newInstance(highwayStretch));
                break;
            case MODEL:
                Model model = Model.get(idArray.get(j));
                mListener.onFragmentInteraction(0,ModelDetailFragment.newInstance(model));
                break;
            case TYPE:
                Type type = Type.get(idArray.get(j));
                mListener.onFragmentInteraction(0,TypeDetailFragment.newInstance(type));
                break;
            case BRAND:
                Brand brand = Brand.get(idArray.get(j));
                mListener.onFragmentInteraction(0,BrandDetailFragment.newInstance(brand));
                break;
        }


    }

    @Override
    public void onNothingSelected() {

    }
}
