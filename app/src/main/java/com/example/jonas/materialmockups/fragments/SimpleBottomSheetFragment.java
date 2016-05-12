package com.example.jonas.materialmockups.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jonas.materialmockups.R;

/**
 * A simple {@link BottomSheetFragment} implementation which provides a fragment displaying
 * a title and a description.
 */
public class SimpleBottomSheetFragment extends BottomSheetFragment {


    public SimpleBottomSheetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bottom_sheet, container, false);
    }


    @Override
    public void onBottomSheetExpand() {
        super.onBottomSheetExpand();

        // TODO:  flash scrollbars of NestedScrollView if possible?
    }
}
