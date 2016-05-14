package com.example.jonas.materialmockups.fragments.exhibitpagefragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jonas.materialmockups.BottomSheetConfig;
import com.example.jonas.materialmockups.R;
import com.example.jonas.materialmockups.fragments.bottomsheetfragments.SimpleBottomSheetFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class AppetizerExhibitPageFragment extends ExhibitPageFragment {

    /** Title for the appetizer bottom sheet */
    private String appetizerTitle = "default appetizer title";

    /** Appetizer text displayed in the bottom sheet */
    private String appetizerText = "default appetizer text";


    public AppetizerExhibitPageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_appetizer, container, false);
    }

    @Override
    public BottomSheetConfig getBottomSheetConfig() {
        SimpleBottomSheetFragment bsFragment = new SimpleBottomSheetFragment();
        bsFragment.setTitle(appetizerTitle);
        bsFragment.setDescription(appetizerText);

        return new BottomSheetConfig.Builder()
                .maxHeight(210)
                .peekHeight(210)
                .fabAction(BottomSheetConfig.FabAction.NEXT)
                .bottomSheetFragment(bsFragment)
                .getBottomSheetConfig();
    }

    @Override
    public Type getType() { return Type.APPETIZER; }
}
