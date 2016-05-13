package com.example.jonas.materialmockups.fragments.bottomsheetfragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jonas.materialmockups.R;

/**
 * A simple {@link BottomSheetFragment} implementation which provides a fragment displaying
 * a title and a description.
 */
public class SimpleBottomSheetFragment extends BottomSheetFragment {

    /** Title displayed in the bottom sheet (should be ~30 characters long */
    private String title = "default title";

    /** Description displayed in the bottom sheet */
    private String description = "default description";


    /** Default constructor */
    public SimpleBottomSheetFragment() {
        // Required empty public constructors
    }


    /** Sets the title which is used in onCreateView */
    public void setTitle(String title) {
        this.title = title;
    }

    /** Sets the title which is used in onCreateView  */
    public void setDescription(String desc) {
        this.description = desc;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_bottom_sheet, container, false);

        // set title and description
        TextView tv = (TextView) v.findViewById(R.id.tvTitle);
        tv.setText(title);
        tv = (TextView) v.findViewById(R.id.tvDescription);
        tv.setText(description);

        return v;
    }


    @Override
    public void onBottomSheetExpand() {
        super.onBottomSheetExpand();

        // TODO:  flash scrollbars of NestedScrollView if possible?
    }
}
