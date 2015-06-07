package com.modesteam.pardal;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.modesteam.pardal.highwayStretch.HighwayStretchContent;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import helpers.ListViewSearch;
import models.HighwayStretch;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class HighwayStretchListFragment extends Fragment implements AbsListView.OnItemClickListener, OnReverseListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_HIGHWAY = "highWayStretch";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private HighwayStretch highwayStretch;

    //
    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ArrayAdapter mAdapter;

    // TODO: Rename and change types of parameters
    public static HighwayStretchListFragment newInstance(String param1, String param2) {
        HighwayStretchListFragment fragment = new HighwayStretchListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static HighwayStretchListFragment newInstance(HighwayStretch highwayStretch) {
        HighwayStretchListFragment fragment = new HighwayStretchListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_HIGHWAY, highwayStretch.getId());

        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public HighwayStretchListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            if(getArguments().getInt(ARG_HIGHWAY) == 0){
                mParam1 = getArguments().getString(ARG_PARAM1);
                mParam2 = getArguments().getString(ARG_PARAM2);
            }else{
                try {
                    highwayStretch = HighwayStretch.get(getArguments().getInt(ARG_HIGHWAY));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        // Change DummyContent for HighwayStretch

        List<HighwayStretch> listHighwayStretch = new ArrayList<HighwayStretch>();
        if(highwayStretch != null) {
            for (HighwayStretch highwayStretchItem : HighwayStretchContent.ITEMS) {
                if (highwayStretchItem.getId() != highwayStretch.getId()) {
                    listHighwayStretch.add(highwayStretchItem);
                }
            }
        }else{
            listHighwayStretch = HighwayStretchContent.ITEMS;
        }
        mAdapter = new ArrayAdapter<HighwayStretch>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, listHighwayStretch);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_highwaystretch, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        EditText searchText = (EditText) view.findViewById(R.id.searchEditText);
        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        searchText.addTextChangedListener(ListViewSearch.searchListView(mAdapter));

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
            if(highwayStretch == null){
                HighwayStretch highwayStretchSelected = (HighwayStretch)mAdapter.getItem(position);
                mListener.onFragmentInteraction(position, HighwayStretchDetailFragment.newInstance(highwayStretchSelected));
            }else{
                HighwayStretch brandSelected = (HighwayStretch)mAdapter.getItem(position);
                mListener.onFragmentInteraction(position, CompareFragment.newInstance(highwayStretch,brandSelected,"Rodovia"));
            }
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

    @Override
    public void onReverseClick() {
        ArrayList<HighwayStretch> list = (ArrayList<HighwayStretch>) HighwayStretchContent.ITEMS;
        Collections.reverse(list);
        mAdapter = new ArrayAdapter<HighwayStretch>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, list);
        mListView.setAdapter(mAdapter);
    }
}
