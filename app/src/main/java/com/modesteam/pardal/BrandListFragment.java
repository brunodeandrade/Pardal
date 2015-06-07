package com.modesteam.pardal;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;


import com.modesteam.pardal.brand.BrandContent;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import helpers.ListViewSearch;
import models.Brand;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class BrandListFragment extends Fragment implements AbsListView.OnItemClickListener , OnReverseListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_BRAND = "brand";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Brand brand;

    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;
    private EditText mSearchText;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ArrayAdapter mAdapter;

    // TODO: Rename and change types of parameters
    public static BrandListFragment newInstance(String param1, String param2) {
        BrandListFragment fragment = new BrandListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static BrandListFragment newInstance(Brand brand) {
        BrandListFragment fragment = new BrandListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_BRAND, brand.getId());

        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BrandListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            if(getArguments().getInt(ARG_BRAND) == 0){
                mParam1 = getArguments().getString(ARG_PARAM1);
                mParam2 = getArguments().getString(ARG_PARAM2);
            }else{
                try {
                    brand = Brand.get(getArguments().getInt(ARG_BRAND));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        // TODO: Change Adapter to display your content
        List<Brand> listBrand = new ArrayList<Brand>();
        if(brand != null) {
            for (Brand brandItem : BrandContent.ITEMS) {
                if (brandItem.getId() != brand.getId()) {
                    listBrand.add(brandItem);
                }
            }
        }else{
            listBrand = BrandContent.ITEMS;
        }
        mAdapter = new ArrayAdapter<Brand>(getActivity(),
                  android.R.layout.simple_list_item_1, android.R.id.text1, listBrand);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_brand_list, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        mSearchText = (EditText) view.findViewById(R.id.searchEditText);
        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        mSearchText.addTextChangedListener(ListViewSearch.searchListView(mAdapter));

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
            if(brand == null){
                Brand brandSelected = (Brand)mAdapter.getItem(position);
                mListener.onFragmentInteraction(position, BrandDetailFragment.newInstance(brandSelected));
            }else{
                Brand brandSelected = (Brand)mAdapter.getItem(position);
                mListener.onFragmentInteraction(position, CompareFragment.newInstance(brand,brandSelected,"Marca"));
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
        ArrayList<Brand> list = (ArrayList<Brand>)BrandContent.ITEMS;
        Collections.reverse(list);
        mAdapter = new ArrayAdapter<Brand>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, list);
        mListView.setAdapter(mAdapter);
        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);
        mSearchText.addTextChangedListener(ListViewSearch.searchListView(mAdapter));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.reverse, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_reverse){
            onReverseClick();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
