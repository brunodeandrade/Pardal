package com.modesteam.pardal;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.modesteam.pardal.category.CategoryContent;


/**
 * A simple {@link Fragment} subclass.

 * to handle interaction events.
 * Use the {@link CategoryOptionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class CategoryOptionsFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;
    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoryOptionsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryOptionsFragment newInstance(String param1, String param2) {
        CategoryOptionsFragment fragment = new CategoryOptionsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public CategoryOptionsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category_options, container, false);
        this.view = view;
        ImageButton bModel = (ImageButton) view.findViewById(R.id.bModel);
        ImageButton bBrand = (ImageButton) view.findViewById(R.id.bBrand);
        ImageButton bType = (ImageButton) view.findViewById(R.id.bType);
        ImageButton bCity = (ImageButton) view.findViewById(R.id.bCity);
        ImageButton bHighway = (ImageButton) view.findViewById(R.id.bHighway);
        ImageButton bState = (ImageButton) view.findViewById(R.id.bState);
        ImageButton bAbout = (ImageButton) view.findViewById(R.id.bAbout);
        bModel.setOnClickListener(this);
        bType.setOnClickListener(this);
        bBrand.setOnClickListener(this);
        bCity.setOnClickListener(this);
        bState.setOnClickListener(this);
        bHighway.setOnClickListener(this);
        bAbout.setOnClickListener(this);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
 //   public void onButtonPressed(Uri uri) {
   //     if (mListener != null) {
     //       mListener.onFragmentInteraction(uri);
       // }
    //}

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
    
    public void myFuckMethod(){
        System.out.println("FUCK");
    }
    // fragment is attached to one) that an item has been selected.
   //public void onClick() {

        /*switch () {
            case 0:
                mListener.onFragmentInteraction(CategoryContent.ITEMS.get(position).id, StateListFragment.newInstance("", ""));
                break;
            case 1:
                mListener.onFragmentInteraction(CategoryContent.ITEMS.get(position).id, CityListFragment.newInstance("", ""));
                break;
            case 2:
                mListener.onFragmentInteraction(CategoryContent.ITEMS.get(position).id, HighwayStretchListFragment.newInstance("", ""));
                break;
            case 3:
                mListener.onFragmentInteraction(CategoryContent.ITEMS.get(position).id, ModelListFragment.newInstance("", ""));
                break;
            case 4:
                mListener.onFragmentInteraction(CategoryContent.ITEMS.get(position).id, TypeListFragment.newInstance("", ""));
                break;
            case 5:
                mListener.onFragmentInteraction(CategoryContent.ITEMS.get(position).id, BrandListFragment.newInstance("", ""));
                break;
        addItem(new Category(1, "Estado"));
        addItem(new Category(2, "Cidade"));
        addItem(new Category(3, "Rodovia"));
        addItem(new Category(4, "Modelo"));
        addItem(new Category(5, "Tipo"));
        addItem(new Category(6, "Marca"));
        }*/
//    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.bModel:
                mListener.onFragmentInteraction(CategoryContent.ITEM_MAP.get(4).id, RankOrListFragment.newInstance("4", ""));
                break;
            case R.id.bBrand:
                mListener.onFragmentInteraction(CategoryContent.ITEM_MAP.get(6).id, RankOrListFragment.newInstance("6", ""));
                break;
            case R.id.bType:
                mListener.onFragmentInteraction(CategoryContent.ITEM_MAP.get(5).id, RankOrListFragment.newInstance("5", ""));
                break;
            case R.id.bCity:
                mListener.onFragmentInteraction(CategoryContent.ITEM_MAP.get(2).id, RankOrListFragment.newInstance("2", ""));
                break;
            case R.id.bState:
                mListener.onFragmentInteraction(CategoryContent.ITEM_MAP.get(1).id, RankOrListFragment.newInstance("1", ""));
                break;
            case R.id.bHighway:
                mListener.onFragmentInteraction(CategoryContent.ITEM_MAP.get(3).id, RankOrListFragment.newInstance("3", ""));
                break;
            case R.id.bAbout:
                mListener.onFragmentInteraction(1,AboutFragment.newInstance("",""));
                break;

        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
   // public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
   //     public void onFragmentInteraction(Uri uri);
    //}

}
