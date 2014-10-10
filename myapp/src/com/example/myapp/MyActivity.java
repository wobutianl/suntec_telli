package com.example.myapp;

import android.app.ActionBar;
import android.app.Activity;
import android.app.*;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.gesture.GestureOverlayView;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.example.myapp.DB.GtdTable;
import com.example.myapp.DataParse.IK;
import com.example.myapp.Module.gtdContent;
import com.example.myapp.View.Fragment2;
import android.widget.AdapterView.OnItemClickListener;
import com.example.myapp.View.allListAdapter;

import java.io.IOException;
import java.net.FileNameMap;
import java.util.*;

/**
 *
 */
public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    private ActionBar mActionBar;     // Action bar Upon the Fragment
    private static final TabState DEFAULT_TAB = TabState.TODAY;  // Tab
    private TabState mCurrentTab = DEFAULT_TAB;
    private final MyTabListener mTabListener = new MyTabListener();  // listen to tab event
    private ArraListFragment mFinishFragment;    // arrayFragment
    private ArraListFragment mTodayFragment;    // arrayFragment
    private ArraListFragment mTomFragment;    // arrayFragment
    private ArraListFragment mAllFragment;    // arrayFragment
    private Fragment2 mAddFragment ;  // menu Fragment (need menu)
    private ViewPager mTabPager;                   // Tab Pager
    private TabPagerAdapter mTabPagerAdapter;       // Adabpter for TabPager
    private final TabPagerListener mTabPagerListener = new TabPagerListener();       // listener

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Log.d("fragment", "tab view");
        initActionBar();      // ActionBar
        Log.d("fragment", "tab view2");
        createViewAndFragment();     // Fragment
    }

    /**
     * Create View and Fragment
     * use FragmentManager to create fragments
     * FragmentTransaction to add, hide and so on fragments
     */
    private void createViewAndFragment() {
        final FragmentManager fragmentManager = getFragmentManager(); //FragmentManager
        final FragmentTransaction transaction = fragmentManager
                .beginTransaction();    // FragmentTransaction
        Log.d("fragment", "tab view");
        mTabPager = getView(R.id.tab_pager);  // Tab pager see XML
        mTabPagerAdapter = new TabPagerAdapter();
        mTabPager.setAdapter(mTabPagerAdapter);
        mTabPager.setOnPageChangeListener(mTabPagerListener);

        final String TODAY_TAG = "tab-today";
        final String ALL_TAG = "tab-all";
        final String FINISH_TAG = "tab-finish";
        final String TOM_TAG = "tab-tom";
        final String ADD_TAG = "tab-add";

        // Create the fragments and add as children of the view pager.
        // The pager adapter will only change the visibility; it'll never
        // create/destroy
        // fragments.
        // However, if it's after screen rotation, the fragments have been
        // re-created by
        // the fragment manager, so first see if there're already the target
        // fragments
        // existing.
        mAddFragment = (Fragment2) fragmentManager
                .findFragmentByTag(ADD_TAG);  // Find fragment by Tag ??
        mFinishFragment = (ArraListFragment) fragmentManager
                .findFragmentByTag(FINISH_TAG);
        mTodayFragment = (ArraListFragment) fragmentManager
                .findFragmentByTag(TODAY_TAG);
        mTomFragment = (ArraListFragment) fragmentManager
                .findFragmentByTag(TOM_TAG);
        mAllFragment = (ArraListFragment) fragmentManager
                .findFragmentByTag(ALL_TAG);

        Log.e("HJJ", "ContextMenuFragmentd == null");
//        mAddFragment = new Fragment2(MyActivity.this);
//        mFinishFragment = new ArraListFragment();
//        mTodayFragment = new ArraListFragment();
//        mTomFragment = new ArraListFragment();
//        mAllFragment = new ArraListFragment();
//
//        transaction.add(R.id.tab_pager, mAddFragment, ADD_TAG);
//        transaction.add(R.id.tab_pager, mFinishFragment, FINISH_TAG);
//        transaction.add(R.id.tab_pager, mTodayFragment, TODAY_TAG);
//        transaction.add(R.id.tab_pager, mTomFragment, TOM_TAG);
//        transaction.add(R.id.tab_pager, mAllFragment, ALL_TAG);

        if (mAddFragment == null) {
            Log.e("HJJ", "ContextMenuFragmentd == null");
            mAddFragment = new Fragment2(MyActivity.this);
            mFinishFragment = new ArraListFragment("finish");
            mTodayFragment = new ArraListFragment("today");
            mTomFragment = new ArraListFragment("tomorrow");
            mAllFragment = new ArraListFragment("all");

            transaction.add(R.id.tab_pager, mAddFragment, ADD_TAG);
            transaction.add(R.id.tab_pager, mFinishFragment, FINISH_TAG);
            transaction.add(R.id.tab_pager, mTodayFragment, TODAY_TAG);
            transaction.add(R.id.tab_pager, mTomFragment, TOM_TAG);
            transaction.add(R.id.tab_pager, mAllFragment, ALL_TAG);
        }



        // Hide all fragments for now. We adjust visibility when we get
        // onSelectedTabChanged()
        // from ActionBarAdapter.
        transaction.hide(mAddFragment);
        transaction.hide(mFinishFragment);
        transaction.hide(mTodayFragment);
        transaction.hide(mTomFragment);
        transaction.hide(mAllFragment);

//        transaction.hide(mCursorFragment);

        transaction.commitAllowingStateLoss();  // ??
        fragmentManager.executePendingTransactions();   // ??
    }

    /**
     * get tab state
     */
    public enum TabState {
        ADD,  FINISH, TODAY, TOM, ALL,  ;

        public static TabState fromInt(int value) {
            if (TODAY.ordinal() == value) {
                return TODAY;
            }
            if (TOM.ordinal() == value) {
                return TOM;
            }
            if (ALL.ordinal() == value) {
                return ALL;
            }
            if (FINISH.ordinal() == value) {
                return FINISH;
            }
            if (ADD.ordinal() == value) {
                return ADD;
            }
            throw new IllegalArgumentException("Invalid value: " + value);
        }
    }
    /**
     * tab pager Listen (View pager change)
     * Page Scroll
     * page select
     *
     */
    private class TabPagerListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int state) {
        }

        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            // Make sure not in the search mode, in which case position !=
            // TabState.ordinal().
            TabState selectedTab = TabState.fromInt(position);
            setCurrentTab(selectedTab, false);
            invalidateOptionsMenu();
        }
    }

    /**
     * init action bar each page with a bar
     * the list must the same as enum(tabstate)
     */
    private void initActionBar() {
        mActionBar = getActionBar();
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        //
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        // Set up tabs
        addTab(TabState.ADD, R.drawable.ic_tab_all);
        addTab(TabState.FINISH, R.drawable.ic_tab_groups);
        addTab(TabState.TODAY, R.drawable.ic_tab_starred);
        addTab(TabState.TOM, R.drawable.ic_tab_all);
        addTab(TabState.ALL, R.drawable.ic_tab_starred);
    }

    /**
     * Tab pager adapter
     * count , get Item position ,
     */
    private class TabPagerAdapter extends PagerAdapter {
        private final FragmentManager mFragmentManager;
        private FragmentTransaction mCurTransaction = null;

        private boolean mTabPagerAdapterSearchMode;

        private Fragment mCurrentPrimaryItem;

        public TabPagerAdapter() {
            mFragmentManager = getFragmentManager();
        }

        @Override
        public int getCount() {
            return mTabPagerAdapterSearchMode ? 1 : TabState.values().length;
        }

        /**
         * Gets called when the number of items changes.
         */
        @Override
        public int getItemPosition(Object object) {
            if (mTabPagerAdapterSearchMode) {
                if (object == mFinishFragment) {
                    return 0; // Only 1 page in search mode
                }
            } else {
                if (object == mAllFragment) {
                    return TabState.ALL.ordinal();
                }
                if (object == mFinishFragment) {
                    return TabState.FINISH.ordinal();
                }
                if (object == mTodayFragment) {
                    return TabState.TODAY.ordinal();
                }
                if (object == mTomFragment) {
                    return TabState.TOM.ordinal();
                }
                if (object == mAddFragment) {
                    return TabState.ADD.ordinal();
                }
            }
            return POSITION_NONE;
        }

        @Override
        public void startUpdate(View container) {
        }

        private Fragment getFragment(int position) {
            if (position == TabState.TODAY.ordinal()) {
                return mTodayFragment;
            } else if (position == TabState.ALL.ordinal()) {
                return mAllFragment;
            }
            else if (position == TabState.TOM.ordinal()) {
                return mTomFragment;
            }else if (position == TabState.FINISH.ordinal()) {
                return mFinishFragment;
            }
            else if (position == TabState.ADD.ordinal()) {
                return mAddFragment;
            }
            throw new IllegalArgumentException("position: " + position);
        }

        @Override
        public Object instantiateItem(View container, int position) {
            if (mCurTransaction == null) {
                mCurTransaction = mFragmentManager.beginTransaction();
            }
            Fragment f = getFragment(position);
            mCurTransaction.show(f);

            // Non primary pages are not visible.
            // f.setUserVisibleHint(f == mCurrentPrimaryItem);
            return f;
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            if (mCurTransaction == null) {
                mCurTransaction = mFragmentManager.beginTransaction();
            }
            mCurTransaction.hide((Fragment) object);
        }

        @Override
        public void finishUpdate(View container) {
            if (mCurTransaction != null) {
                mCurTransaction.commitAllowingStateLoss();
                mCurTransaction = null;
                mFragmentManager.executePendingTransactions();
            }
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return ((Fragment) object).getView() == view;
        }

        @SuppressWarnings("unused")
        public void setPrimaryItem(View container, int position, Object object) {
            Fragment fragment = (Fragment) object;
            if (mCurrentPrimaryItem != fragment) {
                if (mCurrentPrimaryItem != null) {
                    // mCurrentPrimaryItem.setUserVisibleHint(false);
                }
                if (fragment != null) {
                    // fragment.setUserVisibleHint(true);
                }
                mCurrentPrimaryItem = fragment;
            }
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int id) {
        T result = (T) findViewById(id);
        if (result == null) {
            throw new IllegalArgumentException("view 0x"
                    + Integer.toHexString(id) + " doesn't exist");
        }
        return result;
    }

    /**
     * Show fragment
     * @param ft FragmentTransaction
     * @param f fragment
     */
    protected static void showFragment(FragmentTransaction ft, Fragment f) {
        if ((f != null) && f.isHidden())
            ft.show(f);
    }

    /**
     * hide fragment
     * @param ft
     * @param f
     */
    protected static void hideFragment(FragmentTransaction ft, Fragment f) {
        if ((f != null) && !f.isHidden())
            ft.hide(f);
    }

    /**
     * add Tab
     * @param tabState
     * @param icon
     */
    private void addTab(TabState tabState, int icon) {
        Log.d("add tab", " tab ");
        final ActionBar.Tab tab = mActionBar.newTab();
        tab.setTag(tabState);
        tab.setTabListener(mTabListener);
        tab.setIcon(icon);
        Log.d("add tab", " tab ");
        mActionBar.addTab(tab);
    }

    /**
     * Tab listener
     * Tab reselect, unselect , selected
     */
    private class MyTabListener implements ActionBar.TabListener {
        /**
         * If true, it won't call {@link #setCurrentTab} in
         * {@link #onTabSelected}. This flag is used when we want to
         * programmatically update the current tab without
         * {@link #onTabSelected} getting called.
         */
        public boolean mIgnoreTabSelected;

        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
        }

        @Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        }

        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
            Log.e("HJJ", "onTabSelected..tag:" + tab.getTag());
            if (!mIgnoreTabSelected) {
                setCurrentTab((TabState) tab.getTag());
            }
        }
    }

    /**
     * Change the current tab, and notify the listener.
     */
    public void setCurrentTab(TabState tab) {
        setCurrentTab(tab, true);
    }

    /**
     * Change the current tab
     */
    public void setCurrentTab(TabState tab, boolean notifyListener) {
        if (tab == null)
            throw new NullPointerException();

        if (tab == mCurrentTab) {
        return;
        }
        mCurrentTab = tab;

        int index = mCurrentTab.ordinal();
        if ((mActionBar.getNavigationMode() == ActionBar.NAVIGATION_MODE_TABS)
                && (index != mActionBar.getSelectedNavigationIndex())) {
            mActionBar.setSelectedNavigationItem(index);
        }

        if (notifyListener)
            onSelectedTabChanged(tab);
    }

    /**
     * selected Tab changed
     * show current Tab
     * hide other Tabs
     * @param tab
     */
    private void onSelectedTabChanged(TabState tab) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        int tabIndex = tab.ordinal();
        Log.e("HJJ", "tabIndex:" + tabIndex);
        switch (tab) {
            case TODAY:
                if (mTodayFragment != null) {
                    mTabPager.setCurrentItem(tabIndex);
                }
                showFragment(ft, mTodayFragment);
                hideFragment(ft, mFinishFragment);
                hideFragment(ft, mTomFragment);
                hideFragment(ft, mAllFragment);
                hideFragment(ft, mAddFragment);
                break;
            case TOM:
                if (mTomFragment != null) {
                    mTabPager.setCurrentItem(tabIndex);
                }
                hideFragment(ft, mTodayFragment);
                hideFragment(ft, mFinishFragment);
                showFragment(ft, mTomFragment);
                hideFragment(ft, mAllFragment);
                hideFragment(ft, mAddFragment);
                break;
            case ALL:
                if (mAllFragment != null) {
                    mTabPager.setCurrentItem(tabIndex);
                }
                hideFragment(ft, mTodayFragment);
                hideFragment(ft, mFinishFragment);
                hideFragment(ft, mTomFragment);
                showFragment(ft, mAllFragment);
                hideFragment(ft, mAddFragment);
                break;
            case FINISH:
                if (mFinishFragment != null) {
                    mTabPager.setCurrentItem(tabIndex);
                }
                hideFragment(ft, mTodayFragment);
                showFragment(ft, mFinishFragment);
                hideFragment(ft, mTomFragment);
                hideFragment(ft, mAllFragment);
                hideFragment(ft, mAddFragment);
                break;
            case ADD:
                if (mAddFragment != null) {
                    mTabPager.setCurrentItem(tabIndex);
                }
                hideFragment(ft, mTodayFragment);
                hideFragment(ft, mFinishFragment);
                hideFragment(ft, mTomFragment);
                hideFragment(ft, mAllFragment);
                showFragment(ft, mAddFragment);
                break;
        }
        if (!ft.isEmpty()) {
            Log.e("HJJ", "not ft isEmpty");
            ft.commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
            // When switching tabs, we need to invalidate options menu, but
            // executing a
            // fragment transaction does it implicitly. We don't have to call
            // invalidateOptionsMenu
            // manually.
        }
    }


    /**
     * List fragment adapter
     */
    public class ArraListFragment extends ListFragment {
        private GtdTable table ;
        private allListAdapter lva;
        private List<gtdContent> list ;
        private String tab;

        public ArraListFragment(String tab){
            this.tab = tab;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            table = new GtdTable(getActivity());
            if(tab == "today"){
                list = table.retrieveTime();
            }else if(tab == "tomorrow"){
                list = table.retrieveTime();
            }else if(tab == "all"){
                list = table.retrieveStatus("todo");
            }else if(tab == "finish"){
                list = table.retrieveStatus("finish");
            }
            list = table.retrieveStatus("todo");
            lva = new allListAdapter(getActivity(), list);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.content_list, null);
            setListAdapter(lva);
            return view;
        }

        @Override
        public void onListItemClick(final ListView l, View v, final int position, long id)  {
            // TODO Auto-generated method stub
            super.onListItemClick(l, v, position, id);
            final CharSequence[] items = {"完成", "推迟", "取消"};
            AlertDialog.Builder builder = new AlertDialog.Builder(MyActivity.this);
            builder.setTitle("修改任务状态");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    if(items[item] == "完成"){
                        // 获取数据id
                        gtdContent om = (gtdContent) l.getAdapter().getItem(position);
                        int keyId = om.getSmsID();
                        table.updateStatus(keyId,"finish");

                        list.clear();
                        list= table.retrieveStatus("todo");
                        lva = new allListAdapter(getActivity(), list);
                        ((allListAdapter) l.getAdapter()).notifyDataSetChanged();
                    }
                    if(items[item]== "推迟"){
                        IK ik = new IK();
                        String str = ik.participle("IK Analyzer是一个结合词典分词和文法分词的中文分词开源工具包。它使用了全新的正向迭代最细粒度切分算法。");
                        System.out.println(str);
                    }
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    public void displayToast(String str) {
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
    }
}
