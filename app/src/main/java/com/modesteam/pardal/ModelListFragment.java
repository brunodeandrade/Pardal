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
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.modesteam.pardal.brand.BrandContent;
import com.modesteam.pardal.model.ModelContent;

import helpers.ListViewSearch;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import models.Model;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class ModelListFragment extends Fragment implements AbsListView.OnItemClickListener, OnReverseListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_MODEL = "model";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Model model;
    private boolean changeButtonReverse = false;

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
    public static ModelListFragment newInstance(String param1, String param2) {
        ModelListFragment fragment = new ModelListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static ModelListFragment newInstance(Model model) {
        ModelListFragment fragment = new ModelListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_MODEL, model.getId());

        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ModelListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            if(getArguments().getInt(ARG_MODEL) == 0){
                mParam1 = getArguments().getString(ARG_PARAM1);
                mParam2 = getArguments().getString(ARG_PARAM2);
            }else{
                try {
                    model = Model.get(getArguments().getInt(ARG_MODEL));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        /* TODO: Change Adapter to display your content */
        List<Model> listModel = new ArrayList<Model>();
        if(model != null) {
            for (Model modelItem : ModelContent.ITEMS) {
                if (modelItem.getId() != model.getId()) {
                    listModel.add(modelItem);
                }
            }
        }else{
            listModel = ModelContent.ITEMS;
        }
        mAdapter = new ArrayAdapter<Model>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, listModel);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_model, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        mSearchText = (EditText) view.findViewById(R.id.searchEditText);

        mSearchText.addTextChangedListener(ListViewSearch.searchListView(mAdapter));


        final ImageButton ordenateButton = (ImageButton) view.findViewById(R.id.bOrdenate);
        ordenateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                onReverseClick();
                if (changeButtonReverse == false) {
                    ordenateButton.setImageResource(R.drawable.arrow_up_float);
                    changeButtonReverse = true;
                } else {
                    ordenateButton.setImageResource(R.drawable.arrow_down_float);
                    changeButtonReverse = false;
                }
            }
        });


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
            if(model == null){
                Model modelSelected = (Model) mAdapter.getItem(position);
                mListener.onFragmentInteraction(modelSelected.getId(), ModelDetailFragment.newInstance(modelSelected));
            }else{
                Model modelSelected = (Model)mAdapter.getItem(position);
                mListener.onFragmentInteraction(position, CompareFragment.newInstance(model,modelSelected,"Modelo"));
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
        ArrayList<Model> list = (ArrayList<Model>) ModelContent.ITEMS;
        Collections.reverse(list);
        mAdapter = new ArrayAdapter<Model>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, list);
        mListView.setAdapter(mAdapter);
        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);
        mSearchText.addTextChangedListener(ListViewSearch.searchListView(mAdapter));
    }
}