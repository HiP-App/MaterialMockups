package com.example.jonas.materialmockups;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.View;

import com.gordonwong.materialsheetfab.AnimatedFab;

/**
 * see https://android-arsenal.com/details/1/2178
 *
 * Created by Jonas on 30.04.2016.
 */
public class MyFab extends FloatingActionButton implements AnimatedFab {

    // constructors provided by FAB
    public MyFab(Context context) {
        super(context);
    }

    public MyFab(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyFab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * Shows the FAB.
     */
    @Override
    public void show() {
        show(0, 0);
    }

    /**
     * Shows the FAB and sets the FAB's translation.
     *
     * @param translationX translation X value
     * @param translationY translation Y value
     */
    @Override
    public void show(float translationX, float translationY) {
        // NOTE: Using the parameters is only needed if you want
        // to support moving the FAB around the screen.
        // NOTE: This immediately hides the FAB. An animation can
        // be used instead - see the sample app.
        setVisibility(View.VISIBLE);
    }

    /**
     * Hides the FAB.
     */
    @Override
    public void hide() {
        // NOTE: This immediately hides the FAB. An animation can
        // be used instead - see the sample app.
        setVisibility(View.INVISIBLE);
    }

}
