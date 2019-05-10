package com.app_republic.test_app;

import android.animation.Animator;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app_republic.test_app.adapter.CardAdapter;
import com.app_republic.test_app.adapter.PostAdapter;
import com.app_republic.test_app.model.Card;
import com.app_republic.test_app.model.Post;
import com.lcodecore.tkrefreshlayout.IBottomView;
import com.lcodecore.tkrefreshlayout.IHeaderView;
import com.lcodecore.tkrefreshlayout.OnAnimEndListener;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.app_republic.test_app.util.Const.TYPE_EXPLORE;
import static com.app_republic.test_app.util.Const.TYPE_HOME;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    static RecyclerView post_recyclerView, card_recyclerView;
    private ViewPager mViewPager;
    private ImageView more, arrow_back;
    private static TextView title;
    CardView menu_card;
    TabLayout mTabLayout;
    RelativeLayout toolbar;
    View view;

    private void setupWindowAnimations() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getSharedElementEnterTransition().setDuration(200);
            getWindow().getSharedElementReturnTransition().setDuration(200)
                    .setInterpolator(new DecelerateInterpolator());

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupWindowAnimations();
        title = findViewById(R.id.title);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = findViewById(R.id.viewpager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        more = findViewById(R.id.more);
        arrow_back = findViewById(R.id.arrow_back);

        toolbar = findViewById(R.id.toolbar);
        view = findViewById(R.id.view);

        menu_card = findViewById(R.id.menu_card);

        mTabLayout = findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);

        View view1 = getLayoutInflater().inflate(R.layout.tab_layout, null);
        view1.findViewById(R.id.icon).setBackgroundResource(R.drawable.explore);
        mTabLayout.getTabAt(0).setCustomView(view1);

        View view2 = getLayoutInflater().inflate(R.layout.tab_layout, null);
        view2.findViewById(R.id.icon).setBackgroundResource(R.drawable.home);
        mTabLayout.getTabAt(1).setCustomView(view2);
        mTabLayout.setTabRippleColor(null);

        mTabLayout.getTabAt(1).getCustomView().findViewById(R.id.icon).getBackground().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_IN);

        mTabLayout.addOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(mViewPager) {

                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        super.onTabSelected(tab);
                        tab.getCustomView().findViewById(R.id.icon).getBackground().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);

                        if (tab.getPosition() == TYPE_HOME) {
                            runLayoutAnimation(post_recyclerView);
                            title.setText(getString(R.string.title_home));

                        } else {
                            runLayoutAnimation(card_recyclerView);
                            title.setText(getString(R.string.title_explore));

                        }
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                        super.onTabUnselected(tab);
                        tab.getCustomView().findViewById(R.id.icon).getBackground().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_IN);
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                        super.onTabReselected(tab);
                        if (tab.getPosition() == TYPE_HOME) {
                            runLayoutAnimation(post_recyclerView);
                        } else {
                            runLayoutAnimation(card_recyclerView);
                        }
                    }
                }
        );
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showCard();
            }
        });

        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        initAnim();


    }

    @Override
    protected void onStop() {
        super.onStop();
        mTabLayout.clearAnimation();
    }

    public void showCard() {
        Animation anim = new ScaleAnimation(
                0f, 1f, // Start and end values for the X axis scaling
                0f, 1f, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 1f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 0f); // Pivot point of Y scaling
        anim.setDuration(100);
        anim.setFillAfter(true);
        menu_card.startAnimation(anim);
        menu_card.setVisibility(View.VISIBLE);
    }

    public void hideCard() {
        Animation animate = new ScaleAnimation(
                1f, 0f,
                1f, 0f,
                Animation.RELATIVE_TO_SELF, 1f,
                Animation.RELATIVE_TO_SELF, 0f);
        animate.setDuration(100);
        animate.setFillAfter(true);
        menu_card.startAnimation(animate);
        menu_card.setVisibility(View.GONE);
    }

    public void initAnim() {

       /* Animation slide = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                1.0f, Animation.RELATIVE_TO_SELF, -1.0f);

        slide.setDuration(1000);
        slide.setFillAfter(true);
        slide.setFillEnabled(true);
        mTabLayout.startAnimation(slide);
        */
        final int px = getResources().getDimensionPixelSize(R.dimen.padding);
        ViewPropertyAnimator animator = mTabLayout.animate();

        animator.translationY(-1.0f).setStartDelay(500).setDuration(500).start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mTabLayout.getTranslationY() != 0)
                mTabLayout.setTranslationY(0);
            }
        },1100);
        animator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

                mViewPager.setPadding(0, 0, 0, px);

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });


    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Rect viewRect = new Rect();
        menu_card.getGlobalVisibleRect(viewRect);
        if (menu_card.getVisibility() == VISIBLE && !viewRect.contains((int) ev.getRawX(), (int) ev.getRawY())) {

            hideCard();
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        ArrayList<Card> card_list = new ArrayList<>();
        ArrayList<Post> post_list = new ArrayList<>();
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            int section = getArguments().getInt(ARG_SECTION_NUMBER);

            View rootView;


            RecyclerView.Adapter adapter;

            if (section == TYPE_EXPLORE) {

                rootView = inflater.inflate(R.layout.content_explore, container, false);
                // card test data

                insert_data_card();

                adapter = new CardAdapter(getActivity(), card_list);


                final TwinklingRefreshLayout swipeRefreshLayout = rootView.findViewById(R.id.swipe);


                swipeRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
                    @Override
                    public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                refreshLayout.finishRefreshing();
                                runLayoutAnimation(card_recyclerView);
                                insert_data_post();
                            }
                        }, 0);
                    }

                    @Override
                    public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                refreshLayout.finishLoadmore();

                            }
                        }, 500);
                    }
                });

                swipeRefreshLayout.setHeaderView(new Header(getActivity()));
                swipeRefreshLayout.setBottomView(new Footer(getActivity()));


                swipeRefreshLayout.setMaxHeadHeight(200);
                swipeRefreshLayout.setMaxBottomHeight(200);

                card_recyclerView = rootView.findViewById(R.id.recyclerView);
                //ViewCompat.setNestedScrollingEnabled(recyclerView, false);
                ItemTouchHelper itemTouchHelper = new
                        ItemTouchHelper(new SwipeToDeleteCallback(adapter, TYPE_EXPLORE));
                itemTouchHelper.attachToRecyclerView(card_recyclerView);
                setupRecycler(card_recyclerView, adapter);
            } else {

                rootView = inflater.inflate(R.layout.content_home, container, false);
                // post test data

                insert_data_post();

                adapter = new PostAdapter(getActivity(), post_list);

                post_recyclerView = rootView.findViewById(R.id.recyclerView);

                post_recyclerView.setAdapter(adapter);

                post_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                post_recyclerView.setNestedScrollingEnabled(false);
            }


            return rootView;
        }

        void setupRecycler(RecyclerView recyclerView, RecyclerView.Adapter adapter) {
            recyclerView.setAdapter(adapter);

            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            recyclerView.setNestedScrollingEnabled(false);

            int resId = R.anim.layout_animation_slide_right;
            LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getActivity(), resId);
            recyclerView.setLayoutAnimation(animation);

            runLayoutAnimation(recyclerView);
        }


        void insert_data_post() {
            post_list.clear();
            post_list.add(new Post(getString(R.string.user_name), getString(R.string.user_description)
                    , 0, R.drawable.card_blue, true, 123, 450));
            post_list.add(new Post(getString(R.string.user_name), getString(R.string.user_description)
                    , R.drawable.image, R.drawable.card_green, true, 123, 450));
            post_list.add(new Post(getString(R.string.user_name), getString(R.string.user_description)
                    , R.drawable.image, R.drawable.card_blue, true, 123, 450));
            post_list.add(new Post(getString(R.string.user_name), getString(R.string.user_description)
                    , 0, R.drawable.card_red, true, 123, 450));
            post_list.add(new Post(getString(R.string.user_name), getString(R.string.user_description)
                    , 0, R.drawable.card_white, true, 123, 450));
            post_list.add(new Post(getString(R.string.user_name), getString(R.string.user_description)
                    , R.drawable.image, R.drawable.card_orange, true, 123, 450));
            post_list.add(new Post(getString(R.string.user_name), getString(R.string.user_description)
                    , R.drawable.image, R.drawable.card_blue, true, 123, 450));

        }

        void insert_data_card() {
            card_list.clear();
            card_list.add(new Card(getString(R.string.card_name), getString(R.string.card_description),
                    123, 450, true, R.drawable.card_white));
            card_list.add(new Card(getString(R.string.card_name), getString(R.string.card_description),
                    123, 450, true, R.drawable.card_green));
            card_list.add(new Card(getString(R.string.card_name), getString(R.string.card_description),
                    123, 450, true, R.drawable.card_orange));
            card_list.add(new Card(getString(R.string.card_name), getString(R.string.card_description),
                    123, 450, true, R.drawable.card_red));
            card_list.add(new Card(getString(R.string.card_name), getString(R.string.card_description),
                    123, 450, true, R.drawable.card_white));
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }

        @Override
        public int getCount() {
            // Show 2 pages.
            return 2;
        }

    }

    public static class Header implements IHeaderView {

        View rootView = null;
        ImageView refreshArrow;

        public Header(Context context) {
            if (rootView == null) {
                rootView = View.inflate(context, R.layout.refresh_header, null);
                refreshArrow = rootView.findViewById(R.id.iv_arrow);
            }
        }

        @Override
        public View getView() {
            return rootView;
        }

        @Override
        public void onPullingDown(float fraction, float maxHeadHeight, float headHeight) {
            refreshArrow.setRotation(fraction * headHeight / maxHeadHeight * 180);


        }

        @Override
        public void onPullReleasing(float fraction, float maxHeadHeight, float headHeight) {
            refreshArrow.setRotation(fraction * headHeight / maxHeadHeight * 180);
            if (refreshArrow.getVisibility() == GONE) {
                refreshArrow.setVisibility(VISIBLE);
            }

        }

        @Override
        public void startAnim(float maxHeadHeight, float headHeight) {
        }

        @Override
        public void onFinish(OnAnimEndListener listener) {
            listener.onAnimEnd();
        }

        @Override
        public void reset() {

        }
    }

    public static class Footer implements IBottomView {

        View rootView = null;
        TextView textView;

        public Footer(Context context) {
            if (rootView == null) {
                rootView = View.inflate(context, R.layout.refresh_footer, null);
                textView = rootView.findViewById(R.id.tv);
            }
        }

        @Override
        public View getView() {
            return rootView;
        }

        @Override
        public void onPullingUp(float fraction, float maxBottomHeight, float bottomHeight) {

        }

        @Override
        public void onPullReleasing(float fraction, float maxHeadHeight, float headHeight) {

        }

        @Override
        public void onFinish() {

        }

        @Override
        public void startAnim(float maxHeadHeight, float headHeight) {
        }


        @Override
        public void reset() {

        }
    }

    private static void runLayoutAnimation(final RecyclerView recyclerView) {


        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_slide_right);


        recyclerView.setLayoutAnimation(controller);

        recyclerView.scheduleLayoutAnimation();

        recyclerView.getAdapter().notifyDataSetChanged();
    }


}
