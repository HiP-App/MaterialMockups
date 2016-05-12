package com.example.jonas.materialmockups.fragments.exhibitpagefragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jonas.materialmockups.BottomSheetConfig;
import com.example.jonas.materialmockups.R;
import com.example.jonas.materialmockups.fragments.bottomsheetfragments.SimpleBottomSheetFragment;

/**
 * A simple {@link ExhibitPageFragment} subclass.
 */
public class DummyExhibitPageFragment extends ExhibitPageFragment {


    public DummyExhibitPageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dummy_page, container, false);
    }

    @Override
    public BottomSheetConfig getBottomSheetConfig() {
        BottomSheetConfig config = new BottomSheetConfig();
        config.bottomSheetFragment = new SimpleBottomSheetFragment();
        return config;
    }

    @Override
    public Type getType() {
        return Type.IMAGE;
    }
}
