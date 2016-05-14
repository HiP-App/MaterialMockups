package com.example.jonas.materialmockups.fragments.exhibitpagefragments;

import com.example.jonas.materialmockups.Page;

/**
 * Creates an ExhibitPageFragment for the given Page.
 *
 * Created by Jonas on 12.05.2016.
 */
public class ExhibitPageFragmentFactory {
    public static ExhibitPageFragment getFragmentForExhibitPage(Page page) {

        // TODO: update this once actual pages are available
        switch (page.type) {
            case APPETIZER:
                return new AppetizerExhibitPageFragment();
            default:
                return new DummyExhibitPageFragment();
        }

    }
}
