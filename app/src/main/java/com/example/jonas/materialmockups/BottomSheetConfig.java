package com.example.jonas.materialmockups;

import com.example.jonas.materialmockups.fragments.bottomsheetfragments.BottomSheetFragment;

/**
 * Contains information for the ExhibitDetailsActivity on how to display the bottom sheet.
 *
 * Created by Jonas on 12.05.2016.
 */
public class BottomSheetConfig {

    /** Describes the action the FAB should perform on click */
    public enum FabAction {
        EXPAND,
        COLLAPSE,
        NEXT
    }

    /** Indicates whether the bottom sheet should be displayed (true) or not (false) */
    public boolean displayBottomSheet = true;

    /** Fragment that is displayed in the bottom sheet */
    public BottomSheetFragment bottomSheetFragment;

    /** The maximum height of the bottom sheet (in dp)*/
    public int maxHeight = 260;

    /** The height of the bottom sheet when it is collapsed (in dp)*/
    public int peekHeight = 105;

    /* The action associated with the FAB */
    public FabAction fabAction = FabAction.EXPAND;


    /** Builder class which eases the creation of BottomSheetConfigs. */
    public static class Builder {

        BottomSheetConfig config = new BottomSheetConfig();

        public Builder displayBottomSheet(boolean display) {
            config.displayBottomSheet = display;
            return this;
        }

        public Builder bottomSheetFragment(BottomSheetFragment fragment) {
            config.bottomSheetFragment = fragment;
            return this;
        }

        public Builder maxHeight(int height) {
            config.maxHeight = height;
            return this;
        }

        public Builder peekHeight(int height) {
            config.peekHeight = height;
            return this;
        }

        public Builder fabAction(FabAction action) {
            config.fabAction = action;
            return this;
        }

        public BottomSheetConfig getBottomSheetConfig() {
            return config;
        }
    }

}
