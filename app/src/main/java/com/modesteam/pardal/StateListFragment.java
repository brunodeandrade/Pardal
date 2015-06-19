package com.modesteam.pardal;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.EditText;

import com.modesteam.pardal.state.StateContent;

import java.sql.SQLException;
import java.util.ArrayList;
import helpers.ListViewSearch;
import java.util.Collections;
import java.util.List;

import models.State;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class StateListFragment extends Fragment implements AbsListView.OnItemClickListener, OnReverseListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_STATE = "state";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private State state;
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
    public static StateListFragment newInstance(String param1, String param2) {
        StateListFragment fragment = new StateListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static StateListFragment newInstance(State state) {
        StateListFragment fragment = new StateListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_STATE, state.getId());

        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StateListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            if(getArguments().getInt(ARG_STATE) == 0){
                mParam1 = getArguments().getString(ARG_PARAM1);
                mParam2 = getArguments().getString(ARG_PARAM2);
            }else{
                state = State.get(getArguments().getInt(ARG_STATE));
            }

        }

        // TODO: Change Adapter to display your content
        List<State> listState = new ArrayList<State>();
        if(state != null) {
            for (State stateItem : StateContent.ITEMS) {
                if (stateItem.getId() != state.getId()) {
                    listState.add(stateItem);
                }
            }
        }else{
            listState = StateContent.ITEMS;
        }
        mAdapter = new ArrayAdapter<State>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, listState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statelist, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);
        mSearchText = (EditText) view.findViewById(R.id.searchEditText);
        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

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
            if(state == null){
                State stateSelected = (State)mAdapter.getItem(position);
                mListener.onFragmentInteraction(position, StateDetailFragment.newInstance(stateSelected));
            }else {
                State stateSelected = (State) mAdapter.getItem(position);
                mListener.onFragmentInteraction(position, CompareFragment.newInstance(state, stateSelected, "Estado"));
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
        ArrayList<State> list = (ArrayList<State>) StateContent.ITEMS;
        Collections.reverse(list);
        mAdapter = new ArrayAdapter<State>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, list);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        mSearchText.addTextChangedListener(ListViewSearch.searchListView(mAdapter));
    }
}