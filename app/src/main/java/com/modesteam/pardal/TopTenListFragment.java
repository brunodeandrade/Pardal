package com.modesteam.pardal;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;


import com.philjay.valuebar.ValueBar;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class TopTenListFragment extends Fragment implements AbsListView.OnItemClickListener, com.philjay.valuebar.ValueBarSelectionListener {


    // TODO: Rename and change types of parameters
    private Object bean;
    private String fieldName;
    private ArrayList<Object> arrayListRankingObject = null;

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
    public static TopTenListFragment newInstance(Object object, String fieldName) {
        TopTenListFragment fragment = new TopTenListFragment(object, fieldName);
        Bundle args = new Bundle();
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

        }

        this.arrayListRankingObject = rankCategory(this.bean, this.fieldName);

       // mAdapter = new ArrayAdapter<Object>(getActivity(),
              //  android.R.layout.simple_list_item_1, android.R.id.text1, this.arrayListRankingObject);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        // Set the adapter
      //  mListView = (AbsListView) view.findViewById(android.R.id.list);
      //  ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
     //   mListView.setOnItemClickListener(this);

        ValueBar bar;
        bar = (ValueBar) view.findViewById(R.id.valueBar);

        bar.setMinMax(0, 1000);
        bar.setInterval(1f); // interval in which can be selected
        bar.setDrawBorder(false);
        bar.setValueTextSize(14f);
        bar.setMinMaxTextSize(14f);
        //bar.setValueTextTypeface(...);
        //bar.setMinMaxTextTypeface(...);
        //bar.setOverlayColor(...);

        // create your custom color formatter by using the BarColorFormatter interface
        //bar.setColorFormatter(new RedToGreenFormatter());

        // add your custom text formatter by using the ValueTextFormatter interface
        //bar.setValueTextFormatter(...);

        bar.setValue(800f); // display a value

        // or animate from a specific value to a specific value
        //bar.animate(from, to, animationDuration);

        bar.setValueBarSelectionListener(this); // add a listener for callbacks when touching
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

    private ArrayList<Object> rankCategory(Object bean, String fieldName){
        RankingCategory rankingCategory = new RankingCategory();
        ArrayList<Object> arrayListRankingObject = null;
        try {
            arrayListRankingObject = rankingCategory.rankCategoryWithField(bean, fieldName);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return arrayListRankingObject;
    }

    @Override
    public void onSelectionUpdate(float v, float v2, float v3, ValueBar valueBar) {

    }

    @Override
    public void onValueSelected(float v, float v2, float v3, ValueBar valueBar) {

    }
}
