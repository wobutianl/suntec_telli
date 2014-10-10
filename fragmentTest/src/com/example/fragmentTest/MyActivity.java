package com.example.fragmentTest;

import android.app.*;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.example.fragmentTest.android.DB.GtdTable;
import com.example.fragmentTest.android.DB.SmsContent;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MyActivity extends Activity {

    private ActionBar mActionBar;     // Action bar Upon the Fragment
    private static final TabState DEFAULT_TAB = TabState.GROUPS;  // Tab
    private TabState mCurrentTab = DEFAULT_TAB;
    private final MyTabListener mTabListener = new MyTabListener();  // listen to tab event
    private ArraListFragment mArrayFragment;    // arrayFragment
    private CursorLoaderFragment mCursorFragment;     // Cursor Fragemnt need DB
    private ArraListFragment mContextMenuFragment;  // menu Fragment (need menu)
    private ViewPager mTabPager;                   // Tab Pager
    private TabPagerAdapter mTabPagerAdapter;       // Adabpter for TabPager
    private final TabPagerListener mTabPagerListener = new TabPagerListener();       // listener

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_view_pager);
        initActionBar();      // ActionBar
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

        mTabPager = getView(R.id.tab_pager);  // Tab pager see XML
        mTabPagerAdapter = new TabPagerAdapter();
        mTabPager.setAdapter(mTabPagerAdapter);
        mTabPager.setOnPageChangeListener(mTabPagerListener);

        final String FAVORITE_TAG = "tab-pager-favorite";
        final String ALL_TAG = "tab-pager-all";
        final String GROUPS_TAG = "tab-pager-groups";
        final String Cursor_TAG = "tab-cursorAdapter";
        final String Base_TAG = "tab-baseAdapter";

        // Create the fragments and add as children of the view pager.
        // The pager adapter will only change the visibility; it'll never
        // create/destroy
        // fragments.
        // However, if it's after screen rotation, the fragments have been
        // re-created by
        // the fragment manager, so first see if there're already the target
        // fragments
        // existing.
        mContextMenuFragment = (ArraListFragment) fragmentManager
                .findFragmentByTag(FAVORITE_TAG);  // Find fragment by Tag ??
        mArrayFragment = (ArraListFragment) fragmentManager
                .findFragmentByTag(ALL_TAG);
        mCursorFragment = (CursorLoaderFragment) fragmentManager
                .findFragmentByTag(GROUPS_TAG);

        if (mContextMenuFragment == null) {
            Log.e("HJJ", "ContextMenuFragmentd == null");
            mContextMenuFragment = new ArraListFragment();
            mArrayFragment = new ArraListFragment();
            mCursorFragment = new CursorLoaderFragment();

            transaction.add(R.id.tab_pager, mContextMenuFragment, FAVORITE_TAG);
            transaction.add(R.id.tab_pager, mArrayFragment, ALL_TAG);
            transaction.add(R.id.tab_pager, mCursorFragment, GROUPS_TAG);
        }

        // Hide all fragments for now. We adjust visibility when we get
        // onSelectedTabChanged()
        // from ActionBarAdapter.
        transaction.hide(mContextMenuFragment);
        transaction.hide(mArrayFragment);
        transaction.hide(mCursorFragment);

        transaction.commitAllowingStateLoss();  // ??
        fragmentManager.executePendingTransactions();   // ??
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
      */
    private void initActionBar() {
        mActionBar = getActionBar();
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        //
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        // Set up tabs
        addTab(TabState.GROUPS, R.drawable.ic_tab_groups);
        addTab(TabState.ALL, R.drawable.ic_tab_all);
        addTab(TabState.FAVORITES, R.drawable.ic_tab_starred);
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
                if (object == mArrayFragment) {
                    return 0; // Only 1 page in search mode
                }
            } else {
                if (object == mContextMenuFragment) {
                    return TabState.FAVORITES.ordinal();
                }
                if (object == mArrayFragment) {
                    return TabState.ALL.ordinal();
                }
                if (object == mCursorFragment) {
                    return TabState.GROUPS.ordinal();
                }
            }
            return POSITION_NONE;
        }

        @Override
        public void startUpdate(View container) {
        }

        private Fragment getFragment(int position) {
            if (position == TabState.FAVORITES.ordinal()) {
                return mContextMenuFragment;
            } else if (position == TabState.ALL.ordinal()) {
                return mArrayFragment;
            } else if (position == TabState.GROUPS.ordinal()) {
                return mCursorFragment;
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
        final ActionBar.Tab tab = mActionBar.newTab();
        tab.setTag(tabState);
        tab.setTabListener(mTabListener);
        tab.setIcon(icon);
        mActionBar.addTab(tab);
    }

    /**
     * get tab state
     */
    public enum TabState {
        GROUPS, ALL, FAVORITES;

        public static TabState fromInt(int value) {
            if (GROUPS.ordinal() == value) {
                return GROUPS;
            }
            if (ALL.ordinal() == value) {
                return ALL;
            }
            if (FAVORITES.ordinal() == value) {
                return FAVORITES;
            }
            throw new IllegalArgumentException("Invalid value: " + value);
        }
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

        // if (tab == mCurrentTab) {
        // return;
        // }
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
            case FAVORITES:
                if (mContextMenuFragment != null) {
                    mTabPager.setCurrentItem(tabIndex);
                }
                showFragment(ft, mContextMenuFragment);
                hideFragment(ft, mArrayFragment);
                hideFragment(ft, mCursorFragment);
                break;
            case ALL:
                if (mArrayFragment != null) {
                    mTabPager.setCurrentItem(tabIndex);
                }
                hideFragment(ft, mContextMenuFragment);
                hideFragment(ft, mCursorFragment);
                showFragment(ft, mArrayFragment);
                break;
            case GROUPS:
                if (mCursorFragment != null) {
                    mTabPager.setCurrentItem(tabIndex);
                }
                hideFragment(ft, mContextMenuFragment);
                hideFragment(ft, mArrayFragment);
                showFragment(ft, mCursorFragment);
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
    public static class ArraListFragment extends ListFragment {

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            // TODO Auto-generated method stub
            super.onActivityCreated(savedInstanceState);
            String[] array = new String[]{"C", "C++", "Java"};
            setListAdapter(new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, array));

        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            // TODO Auto-generated method stub
            super.onListItemClick(l, v, position, id);
        }
    }

    /**
     * List fragment adapter
     */
    public class SmsListFragment extends ListFragment {

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            // TODO Auto-generated method stub
            super.onActivityCreated(savedInstanceState);
            List<Map<String, Object>> mData = null; //
            SmsContentListAdapter adapter = new SmsContentListAdapter(MyActivity.this, mData);
            String[] array = new String[]{"C", "C++", "Java"};
            setListAdapter(adapter);

            //adapter.
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            // TODO Auto-generated method stub
            super.onListItemClick(l, v, position, id);
        }
    }

    public class SmsContentListAdapter extends BaseAdapter {

        private LayoutInflater mInflater;
        private List<Map<String, Object>> mData = null;
        private Context ctx;

        public SmsContentListAdapter(Context context, List<Map<String, Object>> om) {
            this.ctx = context;
            this.mData = om;
            this.mInflater = LayoutInflater.from(this.ctx);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mData.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return mData.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return arg0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.sms_content_list, null);
                holder.content = (EditText) convertView.findViewById(R.id.sms_content_edit); // item title ( sms content)
                holder.address = (EditText) convertView.findViewById(R.id.sms_address_edit);
                holder.viewBtn = (Button) convertView.findViewById(R.id.sms_btn);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

//
//        holder.content.setBackgroundResource((Integer) mData.get(position).get("img"));
//        holder.address.setText((String) mData.get(position).get("title"));
//        holder.viewBtn.setText((String) mData.get(position).get("info"));
//
            holder.viewBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    showInfo();
                }
            });
            return convertView;
        }

        private void showInfo() {
            AlertDialog.Builder bdr = new AlertDialog.Builder(MyActivity.this);
            bdr.setTitle("确定要记录这个事项?");
            bdr.setPositiveButton("记录", new View.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SmsContent recd = new SmsContent();
                    GtdTable es =new GtdTable(MyActivity.this);

                    HashMap<String, Object> om = new HashMap<String, Object>();
                    om.put(MyDayRecordTable.ITEM_NAME,
                            ((ObjectMap<String, Object>) arg0.getAdapter()
                                    .getItem(arg2))
                                    .getStringValue(CommandsTable.KEY_NAME));
                    om.put(MyDayRecordTable.ITEM_WRITTEN_MILLSECECOND,
                            System.currentTimeMillis());
                    es.addData(om);
                }
            }).setNegativeButton("撤销", new View.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).show();
        }

        public final class ViewHolder{
            public EditText content;
            public EditText address;
            public Button viewBtn;
        }
    }
    /**
     * Cursor Fragment extends from listFragment
     * SimpleCursorAdapter
     */
    public class CursorLoaderFragment extends ListFragment implements
            SearchView.OnQueryTextListener, LoaderManager.LoaderCallbacks<Cursor> {

        SimpleCursorAdapter mAdapter;
        String mCurFilter;

        final String[] CONTACTS_SUMMARY_PROJECTION = new String[]{
                ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.Contacts.CONTACT_STATUS,
                ContactsContract.Contacts.CONTACT_PRESENCE, ContactsContract.Contacts.PHOTO_ID,
                ContactsContract.Contacts.LOOKUP_KEY,};

        GtdTable sTable = new GtdTable(MyActivity.this);

        // fragment
        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            // TODO Auto-generated method stub
            super.onActivityCreated(savedInstanceState);

            setEmptyText("No sms ");
            setHasOptionsMenu(true);
            displayToast(" no sms ");

//	        Cursor c = sTable.find(findSql, obj)
//	        displayToast(" no query ");
//			mAdapter = new SimpleCursorAdapter(getActivity(),
//					android.R.layout.simple_list_item_2, c, new String[] {
//							sTable.getContent(), sTable.getType() },
//					new int[] { android.R.id.text1, android.R.id.text2 }, 0);
//			setListAdapter(mAdapter);
//			setListShown(false);
//
//			getLoaderManager().initLoader(0, null, this);
//			onCreateLoader(0, savedInstanceState);
        }

        // @Override
        // public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // // TODO Auto-generated method stub
        // MenuItem item = menu.add("Search");
        // item.setIcon(android.R.drawable.ic_menu_search);
        // item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM
        // | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        // SearchView sv = new SearchView(getActivity());
        // sv.setOnQueryTextListener(this);
        // item.setActionView(sv);
        // }

        // query changed then restart loader
        @Override
        public boolean onQueryTextChange(String newText) {
            // TODO Auto-generated method stub
            String newFilter = !TextUtils.isEmpty(newText) ? newText : null;
            if (mCurFilter == null && newFilter == null) {
                return true;
            }
            if (mCurFilter != null && mCurFilter.equals(newFilter)) {
                return true;
            }

            mCurFilter = newFilter;
            getLoaderManager().restartLoader(0, null, this);
            return true;
        }

        //
        @Override
        public boolean onQueryTextSubmit(String query) {
            // TODO Auto-generated method stub
            return true;
        }

        // list item click event
        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            // TODO Auto-generated method stub
            super.onListItemClick(l, v, position, id);
            Cursor c = (Cursor) mAdapter.getItem(position);
            String name = c.getString(0);
            Toast.makeText(getActivity(), "name:" + name, Toast.LENGTH_SHORT)
                    .show();
        }

        // create loader
        // loader file from Bundle
        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            // TODO Auto-generated method stub
            Uri baseUri;
            if (mCurFilter != null) {
                baseUri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_FILTER_URI,
                        Uri.encode(mCurFilter));
            } else {
                baseUri = ContactsContract.Contacts.CONTENT_URI;
            }
            String select = "((" + ContactsContract.Contacts.DISPLAY_NAME + " NOTNULL) AND ("
                    + ContactsContract.Contacts.HAS_PHONE_NUMBER + "=1) AND ("
                    + ContactsContract.Contacts.DISPLAY_NAME + " != '' ))";
            return new CursorLoader(getActivity(), baseUri,
                    CONTACTS_SUMMARY_PROJECTION, select, null,
                    ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC");
        }

        // load finish event
        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            // TODO Auto-generated method stub
            mAdapter.swapCursor(data);
            if (isResumed()) {
                setListShown(true);
            } else {
                setListShownNoAnimation(true);
            }
        }
        // load reset
        // adapter swapCursor
        @Override
        public void onLoaderReset(Loader<Cursor> arg0) {
            // TODO Auto-generated method stub
            mAdapter.swapCursor(null);
        }
    }

    public static class ContextMenuFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // TODO Auto-generated method stub
            View root = inflater.inflate(R.layout.fragment_context_menu,
                    container, false);
            registerForContextMenu(root.findViewById(R.id.long_press));
            return root;
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v,
                                        ContextMenu.ContextMenuInfo menuInfo) {
            // TODO Auto-generated method stub
            super.onCreateContextMenu(menu, v, menuInfo);
            menu.add(Menu.NONE, R.id.a_item, Menu.NONE, "Menu A");
            menu.add(Menu.NONE, R.id.b_item, Menu.NONE, "Menu B");
        }

        @Override
        public boolean onContextItemSelected(MenuItem item) {
            // TODO Auto-generated method stub
            switch (item.getItemId()) {
                case R.id.a_item:
                    Toast.makeText(getActivity(), "a_item...", Toast.LENGTH_SHORT)
                            .show();
                    break;
                case R.id.b_item:
                    Toast.makeText(getActivity(), "b_item...", Toast.LENGTH_SHORT)
                            .show();
                    break;
                default:
                    break;
            }
            return super.onContextItemSelected(item);
        }
    }

    public void displayToast(String str) {
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
    }

    public static class smsTable {

    }

    /**
     * send message to Chat ListView notify list Data
     * @param str	:message
     */
    private void sendToList(String str) { //
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("TextView_SmsBody", str);
//        mdata.add(0, map);  // insert data to front
//        adapter.notifyDataSetChanged(); // refresh data
//		gList.setSelection(mdata.size() - 1);
    }
}
