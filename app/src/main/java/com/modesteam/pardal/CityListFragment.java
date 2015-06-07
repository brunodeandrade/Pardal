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


import com.modesteam.pardal.brand.BrandContent;
import com.modesteam.pardal.city.CityContent;

import helpers.ListViewSearch;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import models.City;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class CityListFragment extends Fragment implements AbsListView.OnItemClickListener, OnReverseListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_CITY = "city";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private City city;

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
    public static CityListFragment newInstance(String param1, String param2) {
        CityListFragment fragment = new CityListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static CityListFragment newInstance(City city) {
        CityListFragment fragment = new CityListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CITY, city.getId());

        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CityListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            if(getArguments().getInt(ARG_CITY) == 0){
                mParam1 = getArguments().getString(ARG_PARAM1);
                mParam2 = getArguments().getString(ARG_PARAM2);
            }else{
                try {
                    city = City.get(getArguments().getInt(ARG_CITY));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        // TODO: Change Adapter to display your content
        List<City> listCity = new ArrayList<City>();
        if(city != null) {
            for (City cityItem : CityContent.ITEMS) {
                if (cityItem.getId() != city.getId()) {
                    listCity.add(cityItem);
                }
            }
        }else{
            listCity = CityContent.ITEMS;
        }
        mAdapter = new ArrayAdapter<City>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, listCity);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_citylist, container, false);

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
            if(city == null){
                City citySelected = (City)mAdapter.getItem(position);
                mListener.onFragmentInteraction(citySelected.getId(),CityDetailFragment.newInstance(citySelected));
            }else{
                City citySelected = (City)mAdapter.getItem(position);
                mListener.onFragmentInteraction(position, CompareFragment.newInstance(city,citySelected,"Cidade"));
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
        ArrayList<City> list = (ArrayList<City>) CityContent.ITEMS;
        Collections.reverse(list);
        mAdapter = new ArrayAdapter<City>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, list);
        mListView.setAdapter(mAdapter);
    }
}
