package com.example.jonas.materialmockups;

import com.example.jonas.materialmockups.fragments.exhibitpagefragments.ExhibitPageFragment;

/**
 * Represents model class / interface.
 *
 * Created by Jonas on 12.05.2016.
 */
public class Page {

    public ExhibitPageFragment.Type type;

    public Page(ExhibitPageFragment.Type type) {
        this.type = type;
    }

}
