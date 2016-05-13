package com.example.jonas.materialmockups.fragments.exhibitpagefragments;

import android.net.Uri;
import android.support.v4.app.Fragment;

import com.example.jonas.materialmockups.BottomSheetConfig;

/**
 * Abstract superclass for Fragments that are displayed in the ExhibitDetailsActivity.
 *
 * Created by Jonas on 12.05.2016.
 */
public abstract class ExhibitPageFragment extends Fragment {

    /** Available page types */
    public enum Type {
        APPETIZER,
        IMAGE,
        SLIDER,
        TEXT
    }

    /** Uri pointing to the audio file that should be played */
    private Uri audio = null;

    public Uri getAudio() { return audio; }
    public void setAudio(Uri audio) { this.audio = audio; }


    /** Returns the BottomSheetConfig for the PageFragment */
    public abstract BottomSheetConfig getBottomSheetConfig();

    /** Returns the type of page */
    public abstract Type getType();

}
