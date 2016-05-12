package com.example.jonas.materialmockups.fragments;

import android.support.v4.app.Fragment;

/**
 * Abstract class for fragments that are included in the bottom sheet of
 * {@link ExhibitDetailsActivity}.
 *
 * Created by Jonas on 12.05.2016.
 */
public abstract class BottomSheetFragment extends Fragment {

    /**
     * Called by ExhibitDetailsActivity when the bottom sheet has been expanded.
     */
    public void onBottomSheetExpand() {}

    /**
     * Called by ExhibitDetailsActivity when the bottom sheet has been collapsed.
     */
    public void onBottomSheetCollapse() {}

}
