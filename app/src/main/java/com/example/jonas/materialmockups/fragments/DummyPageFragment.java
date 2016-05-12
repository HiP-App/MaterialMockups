package com.example.jonas.materialmockups.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jonas.materialmockups.R;

/**
 * A simple {@link ExhibitPageFragment} subclass.
 */
public class DummyPageFragment extends ExhibitPageFragment {


    public DummyPageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dummy_page, container, false);
    }

}
