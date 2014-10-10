package com.example.fragmentTest.android.test;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.ContactsContract.Contacts;
import com.example.fragmentTest.R;
import com.example.fragmentTest.android.DB.GtdTable;
import com.example.fragmentTest.android.DB.SmsTable;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.app.ActionBar.Tab;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import android.widget.SearchView.OnQueryTextListener;

public class TestAndroidActivity extends Activity {

	private ActionBar mActionBar;
	private static final TabState DEFAULT_TAB = TabState.GROUPS;
	private TabState mCurrentTab = DEFAULT_TAB;
	private final MyTabListener mTabListener = new MyTabListener();
	private ArraListFragment mArrayFragment;
	private CursorLoaderFragment mCursorFragment;
	private ContextMenuFragment mContextMenuFragment;
	private ViewPager mTabPager;
	private TabPagerAdapter mTabPagerAdapter;
	private final TabPagerListener mTabPagerListener = new TabPagerListener();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_view_pager);
		initActionBar();
		createViewAndFragment();
	}

	private void createViewAndFragment() {
		final FragmentManager fragmentManager = getFragmentManager();
		final FragmentTransaction transaction = fragmentManager
				.beginTransaction();

		mTabPager = getView(R.id.tab_pager);
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
		mContextMenuFragment = (ContextMenuFragment) fragmentManager
				.findFragmentByTag(FAVORITE_TAG);
		mArrayFragment = (ArraListFragment) fragmentManager
				.findFragmentByTag(ALL_TAG);
		mCursorFragment = (CursorLoaderFragment) fragmentManager
				.findFragmentByTag(GROUPS_TAG);

		if (mContextMenuFragment == null) {
			Log.e("HJJ", "ContextMenuFragmentd == null");
			mContextMenuFragment = new ContextMenuFragment();
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

		transaction.commitAllowingStateLoss();
		fragmentManager.executePendingTransactions();
	}

	// tab pager Listen (View pager change)
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
	// ��������ʱ��Ҫ�����¡� ����ActionBar������Fragment,��Ӧ����Tab��
	private void initActionBar() {
		mActionBar = getActionBar();
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		// ȥ��Ĭ�ϱ�����
		mActionBar.setDisplayShowHomeEnabled(false);
		mActionBar.setDisplayShowTitleEnabled(false);
		// Set up tabs
		addTab(TabState.GROUPS, R.drawable.ic_tab_groups);
		addTab(TabState.ALL, R.drawable.ic_tab_all);
		addTab(TabState.FAVORITES, R.drawable.ic_tab_starred);
	}

	
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

		/** Gets called when the number of items changes. */
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

	protected static void showFragment(FragmentTransaction ft, Fragment f) {
		if ((f != null) && f.isHidden())
			ft.show(f);
	}

	protected static void hideFragment(FragmentTransaction ft, Fragment f) {
		if ((f != null) && !f.isHidden())
			ft.hide(f);
	}

	private void addTab(TabState tabState, int icon) {
		final Tab tab = mActionBar.newTab();
		tab.setTag(tabState);
		tab.setTabListener(mTabListener);
		tab.setIcon(icon);
		mActionBar.addTab(tab);
	}

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

	private class MyTabListener implements ActionBar.TabListener {
		/**
		 * If true, it won't call {@link #setCurrentTab} in
		 * {@link #onTabSelected}. This flag is used when we want to
		 * programmatically update the current tab without
		 * {@link #onTabSelected} getting called.
		 */
		public boolean mIgnoreTabSelected;

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
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
		// ʵ���ϰ���Contacts�е���ƣ������Ӧ����Ч�ģ����ڴ�ģ������У����������⣬�����ʱ���˶�ȥ��
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
	
	
	// List fragment ����
	public  static class ArraListFragment extends ListFragment {
		
		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onActivityCreated(savedInstanceState);
			String[] array = new String[] { "C", "C++", "Java" };
			setListAdapter(new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_list_item_1, array));
		
		}

		@Override
		public void onListItemClick(ListView l, View v, int position, long id) {
			// TODO Auto-generated method stub
			super.onListItemClick(l, v, position, id);
		}
	}

	public class CursorLoaderFragment extends ListFragment implements
			OnQueryTextListener, LoaderCallbacks<Cursor> {

		SimpleCursorAdapter mAdapter;
		String mCurFilter;

		final String[] CONTACTS_SUMMARY_PROJECTION = new String[] {
				Contacts._ID, Contacts.DISPLAY_NAME, Contacts.CONTACT_STATUS,
				Contacts.CONTACT_PRESENCE, Contacts.PHOTO_ID,
				Contacts.LOOKUP_KEY, };
		
        GtdTable sTable = new GtdTable(TestAndroidActivity.this);

		// fragment����ʱ����õ���Ϣ
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
		
		// ����ѯ���ı��仯��ʱ��
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

		// �ı����ύ��ʱ��
		@Override
		public boolean onQueryTextSubmit(String query) {
			// TODO Auto-generated method stub
			return true;
		}
		
		// ���List Itemʱ��
		@Override
		public void onListItemClick(ListView l, View v, int position, long id) {
			// TODO Auto-generated method stub
			super.onListItemClick(l, v, position, id);
			Cursor c = (Cursor) mAdapter.getItem(position);
			String name = c.getString(0);
			Toast.makeText(getActivity(), "name:" + name, Toast.LENGTH_SHORT)
					.show();
		}
		// �������
		@Override
		public Loader<Cursor> onCreateLoader(int id, Bundle args) {
			// TODO Auto-generated method stub
			Uri baseUri;
			if (mCurFilter != null) {
				baseUri = Uri.withAppendedPath(Contacts.CONTENT_FILTER_URI,
						Uri.encode(mCurFilter));
			} else {
				baseUri = Contacts.CONTENT_URI;
			}
			String select = "((" + Contacts.DISPLAY_NAME + " NOTNULL) AND ("
					+ Contacts.HAS_PHONE_NUMBER + "=1) AND ("
					+ Contacts.DISPLAY_NAME + " != '' ))";
			return new CursorLoader(getActivity(), baseUri,
					CONTACTS_SUMMARY_PROJECTION, select, null,
					Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC");
		}

		// �������
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
				ContextMenuInfo menuInfo) {
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
	
	public void displayToast(String str){
		Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
	}
	
	public static class smsTable{
		
	}
}
