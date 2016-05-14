package com.example.jonas.materialmockups.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.jonas.materialmockups.BottomSheetConfig;
import com.example.jonas.materialmockups.Page;
import com.example.jonas.materialmockups.PixelDpConversion;
import com.example.jonas.materialmockups.R;
import com.example.jonas.materialmockups.fragments.bottomsheetfragments.BottomSheetFragment;
import com.example.jonas.materialmockups.fragments.exhibitpagefragments.ExhibitPageFragmentFactory;
import com.example.jonas.materialmockups.fragments.exhibitpagefragments.ExhibitPageFragment;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;


public class ExhibitDetailsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    /** Stores the pages for the current exhibit */
    private List<Page> exhibitPages = new LinkedList<Page>();

    /** Index of the page in the exhibitPages list that is currently displayed */
    private int currentPageIndex = 0;

    /** Indicates whether audio is currently played (true) or not (false) */
    private boolean isAudioPlaying = false;

    /** Indicates whether the audio toolbar is currently displayed (true) or not (false) */
    private boolean isAudioToolbarHidden = true;

    /** Extras contained in the Intent that started this activity */
    private Bundle extras = null;

    /** Stores the current action associated with the FAB */
    private BottomSheetConfig.FabAction fabAction;

    /** Tag used to identify the current ExhibitPageFragment in the FragmentManager */
    public static final String TAG_CURRENT_FRAGMENT = "CURRENT_FRAGMENT";

    /** Tag used to identify the current BottomSheetFragment in the FragmentManager */
    public static final String TAG_CURRENT_BOTTOMSHEET_FRAGMENT = "CURRENT_BOTTOM_SHEET_FRAGMENT";

    // keys for saving/accessing the state
    public static final String KEY_EXHIBIT_ID = "exhibit-id";
    public static final String KEY_EXHIBIT_PAGES = "exhibitPages";
    public static final String KEY_CURRENT_PAGE_INDEX = "currentPageIndex";
    public static final String KEY_AUDIO_PLAYING = "isAudioPlaying";
    public static final String KEY_AUDIO_TOOLBAR_HIDDEN = "isAudioToolbarHidden";
    public static final String KEY_EXTRAS = "extras";

    // ui elements
    private FloatingActionButton fab;
    private View bottomSheet;
    private BottomSheetBehavior bottomSheetBehavior;
    private LinearLayout mRevealView;
    private ImageButton btnPlayPause;
    private ImageButton btnPreviousPage;
    private ImageButton btnNextPage;


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(KEY_EXHIBIT_PAGES, (Serializable) exhibitPages);
        outState.putInt(KEY_CURRENT_PAGE_INDEX, currentPageIndex);
        outState.putBoolean(KEY_AUDIO_PLAYING, isAudioPlaying);
        outState.putBoolean(KEY_AUDIO_TOOLBAR_HIDDEN, isAudioToolbarHidden);
        outState.putBundle(KEY_EXTRAS, extras);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // generated
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // select first menu item on startup
        navigationView.getMenu().getItem(0).setChecked(true);


        if (savedInstanceState != null) {
            // activity re-creation because of device rotation, instant run, ...

            exhibitPages = (List<Page>) savedInstanceState.getSerializable(KEY_EXHIBIT_PAGES);
            currentPageIndex = savedInstanceState.getInt(KEY_CURRENT_PAGE_INDEX, 0);
            isAudioPlaying = savedInstanceState.getBoolean(KEY_AUDIO_PLAYING, false);
            isAudioToolbarHidden = true;
            extras = savedInstanceState.getBundle(KEY_EXTRAS);

            if (exhibitPages == null)
                throw new NullPointerException("exhibitPages cannot be null!");

        } else {
            // activity creation because of intent
            Intent intent = getIntent();
            extras = intent.getExtras();
            // TODO: extract pages from exhibit contained in intent instead of subsequent init
            exhibitPages.add(new Page(ExhibitPageFragment.Type.APPETIZER));
            for (int noOfPages = 3; noOfPages > 0; noOfPages--)
                exhibitPages.add(new Page(ExhibitPageFragment.Type.IMAGE));
        }

        // set up bottom sheet behavior
        bottomSheet = findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (fabAction != BottomSheetConfig.FabAction.NEXT) {
                    if (newState == BottomSheetBehavior.STATE_EXPANDED)
                        setFabCollapseAction();
                    else if (newState == BottomSheetBehavior.STATE_COLLAPSED)
                        setFabExpandAction();
                    else
                        { /* we don't care about any other state */}
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // intentionally left blank
            }
        });

        // audio toolbar
        mRevealView = (LinearLayout) findViewById(R.id.reveal_items);
        mRevealView.setVisibility(View.INVISIBLE);

        // display audio toolbar on savedInstanceState:
        // if (! isAudioToolbarHidden) showAudioToolbar();
        // does not work because activity creation has not been completed?!

        // set up play / pause toggle
        btnPlayPause = (ImageButton) findViewById(R.id.btnPlayPause);
        btnPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePlayPause();
            }
        });

        btnPreviousPage = (ImageButton) findViewById(R.id.buttonPrevious);
        btnPreviousPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayPreviousExhibitPage();
            }
        });

        btnNextPage = (ImageButton) findViewById(R.id.buttonNext);
        btnNextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayNextExhibitPage();
            }
        });

        fab = (FloatingActionButton) findViewById(R.id.fab);

        displayCurrentExhibitPage();

    }

    /** Displays the current exhibit page */
    public void displayCurrentExhibitPage() {
        if (currentPageIndex >= exhibitPages.size())
            throw new IndexOutOfBoundsException("currentPageIndex >= exhibitPages.size() !");

        // collapse bottom sheet first
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        // set previous & next button
        if (currentPageIndex == 0)
            btnPreviousPage.setVisibility(View.GONE);
        else
            btnPreviousPage.setVisibility(View.VISIBLE);

        if (currentPageIndex >= exhibitPages.size() - 1)
            btnNextPage.setVisibility(View.GONE);
        else
            btnNextPage.setVisibility(View.VISIBLE);


        // get ExhibitPageFragment for Page
        Page page = exhibitPages.get(currentPageIndex);
        ExhibitPageFragment pageFragment = ExhibitPageFragmentFactory.getFragmentForExhibitPage(page);

        if (pageFragment == null)
            throw new NullPointerException("pageFragment is null!");

        pageFragment.setArguments(extras);

        // remove old fragment and display new fragment
        if (findViewById(R.id.content_fragment_container) != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // remove current fragment (if present)
            Fragment currentFragment =
                    getSupportFragmentManager().findFragmentByTag(TAG_CURRENT_FRAGMENT);
            if (currentFragment != null)
                transaction.remove(currentFragment);

            // add new fragment
            transaction.add(R.id.content_fragment_container, pageFragment, TAG_CURRENT_FRAGMENT);
            transaction.commit();
        }

        // configure bottom sheet
        BottomSheetConfig config = pageFragment.getBottomSheetConfig();

        if (config == null)
            throw new RuntimeException("BottomSheetConfig cannot be null!");

        if (config.displayBottomSheet) {
            BottomSheetFragment sheetFragment = config.bottomSheetFragment;

            if (sheetFragment == null)
                throw new NullPointerException("sheetFragment is null!");

            // remove old fragment and display new fragment
            if (findViewById(R.id.bottom_sheet_fragment_container) != null) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                // remove current fragment (if present)
                Fragment currentFragment =
                        getSupportFragmentManager().findFragmentByTag(TAG_CURRENT_BOTTOMSHEET_FRAGMENT);
                if (currentFragment != null)
                    transaction.remove(currentFragment);

                // add new fragment
                transaction.add(R.id.bottom_sheet_fragment_container, sheetFragment,
                        TAG_CURRENT_BOTTOMSHEET_FRAGMENT);
                transaction.commit();
            }

            // configure FAB
            setFabAction(config.fabAction);

            // configure peek height and max height
            int peekHeightInPixels = (int) PixelDpConversion.convertDpToPixel(config.peekHeight);
            bottomSheetBehavior.setPeekHeight(peekHeightInPixels);

            int maxHeightInPixels = (int) PixelDpConversion.convertDpToPixel(config.maxHeight);
            ViewGroup.LayoutParams params = bottomSheet.getLayoutParams();
            params.height = maxHeightInPixels;
            bottomSheet.setLayoutParams(params);


        } else {    // config.displayBottomSheet == false
            bottomSheet.setVisibility(View.GONE);
        }

    }

    /** Displays the next exhibit page */
    public void displayNextExhibitPage() {
        currentPageIndex++;
        displayCurrentExhibitPage();
    }

    /** Displays the previous exhibit page (for currentPageIndex > 0) */
    public void displayPreviousExhibitPage() {
        currentPageIndex--;
        if (currentPageIndex < 0)
            throw new IndexOutOfBoundsException("currentPageIndex < 0");

        displayCurrentExhibitPage();
    }

    /** Sets the action of the FAB */
    public void setFabAction(BottomSheetConfig.FabAction action) {
        switch (action) {
            case NEXT:
                setFabNextAction();
                break;
            case COLLAPSE:
                setFabCollapseAction();
                break;
            case EXPAND:
                setFabExpandAction();
                break;
            default:
                throw new RuntimeException("wut?!");
        }
    }

    private void setFabNextAction() {
        fabAction = BottomSheetConfig.FabAction.NEXT;
        fab.setImageResource(R.drawable.ic_arrow_forward_white_48dp);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayNextExhibitPage();
            }
        });
    }

    private void setFabExpandAction() {
        fabAction = BottomSheetConfig.FabAction.EXPAND;
        fab.setImageResource(R.drawable.ic_expand_less_white_48dp);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });
    }

    private void setFabCollapseAction() {
        fabAction = BottomSheetConfig.FabAction.COLLAPSE;
        fab.setImageResource(R.drawable.ic_expand_more_white_48dp);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
    }

    /**
     * Shows the audio toolbar.
     * @return true if the toolbar has been revealed, false otherwise.
     */
    private boolean showAudioToolbar() {
        // check only if mRevealView != null. If isAudioToolbarHidden == true is also checked,
        // the toolbar cannot be displayed on savedInstanceState
        if (mRevealView != null) {
            int cx = (mRevealView.getLeft() + mRevealView.getRight());
            int cy = mRevealView.getTop();
            int radius = Math.max(mRevealView.getWidth(), mRevealView.getHeight());

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {

                SupportAnimator animator =
                        ViewAnimationUtils.createCircularReveal(mRevealView, cx, cy, 0, radius);
                animator.setInterpolator(new AccelerateDecelerateInterpolator());
                animator.setDuration(800);

                mRevealView.setVisibility(View.VISIBLE);
                animator.start();

            } else {
                Animator anim = android.view.ViewAnimationUtils
                        .createCircularReveal(mRevealView, cx, cy, 0, radius);
                mRevealView.setVisibility(View.VISIBLE);
                anim.start();
            }

            isAudioToolbarHidden = false;
            return true;
        }

        return false;
    }

    /**
     * Hides the audio toolbar.
     * @return true if the audio toolbar was hidden, false otherwise
     */
    private boolean hideAudioToolbar() {
        if (mRevealView != null) {
            int cx = (mRevealView.getLeft() + mRevealView.getRight());
            int cy = mRevealView.getTop();
            int radius = Math.max(mRevealView.getWidth(), mRevealView.getHeight());

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {

                SupportAnimator animator =
                        ViewAnimationUtils.createCircularReveal(mRevealView, cx, cy, 0, radius);
                animator.setInterpolator(new AccelerateDecelerateInterpolator());
                animator.setDuration(800);

                SupportAnimator animator_reverse = animator.reverse();
                animator_reverse.addListener(new SupportAnimator.AnimatorListener() {
                    @Override
                    public void onAnimationStart() {

                    }

                    @Override
                    public void onAnimationEnd() {
                        mRevealView.setVisibility(View.INVISIBLE);
                        isAudioToolbarHidden = true;

                    }

                    @Override
                    public void onAnimationCancel() {

                    }

                    @Override
                    public void onAnimationRepeat() {

                    }
                });
                animator_reverse.start();

            } else {
                Animator anim = android.view.ViewAnimationUtils.createCircularReveal(mRevealView, cx, cy, radius, 0);
                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        mRevealView.setVisibility(View.INVISIBLE);
                        isAudioToolbarHidden = true;
                    }
                });
                anim.start();

            }

            return true;
        }

        return false;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_audio:
                if (isAudioToolbarHidden)
                    showAudioToolbar();
                else
                    hideAudioToolbar();
                return true;

            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_overview) {
            fab.hide();
        } else if (id == R.id.nav_routes) {
            fab.hide();
        } else if (id == R.id.nav_exhibit) {
            fab.show();
        } else if (id == R.id.nav_share) {
            Toast.makeText(getApplicationContext(), "ACTION_SEND Intent with : Checkout HiP @ AppStore ...", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_licenses) {
            startActivity(new Intent(getApplication(), LicensingActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void togglePlayPause() {
        // remove old image first
        btnPlayPause.setImageResource(android.R.color.transparent);

        if (isAudioPlaying)
            btnPlayPause.setImageResource(R.drawable.ic_pause_black_24dp);
        else
            btnPlayPause.setImageResource(R.drawable.ic_play_arrow_black_24dp);

        isAudioPlaying = (!isAudioPlaying);
    }
}
