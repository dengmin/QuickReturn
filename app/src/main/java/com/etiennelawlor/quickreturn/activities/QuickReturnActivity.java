package com.etiennelawlor.quickreturn.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.etiennelawlor.quickreturn.R;
import com.etiennelawlor.quickreturn.fragments.QuickReturnFooterListFragment;
import com.etiennelawlor.quickreturn.fragments.QuickReturnFragment;
import com.etiennelawlor.quickreturn.fragments.QuickReturnHeaderListFragment;


public class QuickReturnActivity extends Activity implements ActionBar.TabListener {

    // region Member Variables
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    // endregion

    // region Lifecycle Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quickreturn);

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }
    // endregion

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.quick_return, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_github) {
            openWebPage("https://github.com/lawloretienne/QuickReturn");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // region ActionBar.TabListener Methods
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }
    // endregion

    // region Helper Methods
    public void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    // endregion

    // region Inner Classes

    /**
     * A {@link android.support.v13.app.FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            switch (position) {
                case 0:
                    bundle.putString(getString(R.string.quick_return_type),
                            QuickReturnFragment.QuickReturnType.HEADER.name());
                    return QuickReturnFragment.newInstance(bundle);
                case 1:
                    bundle.putString(getString(R.string.quick_return_type),
                            QuickReturnFragment.QuickReturnType.FOOTER.name());
                    return QuickReturnFragment.newInstance(bundle);
                case 2:
                    bundle.putString(getString(R.string.quick_return_type),
                            QuickReturnFragment.QuickReturnType.BOTH.name());
                    return QuickReturnFragment.newInstance(bundle);
                case 3:
                    return QuickReturnHeaderListFragment.newInstance();
                case 4:
                    return QuickReturnFooterListFragment.newInstance();
                default:
                    bundle.putString(getString(R.string.quick_return_type),
                            QuickReturnFragment.QuickReturnType.HEADER.name());
                    return QuickReturnFragment.newInstance(bundle);
            }
        }



        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.header);
                case 1:
                    return getString(R.string.footer);
                case 2:
                    return getString(R.string.header_footer);
                case 3:
                    return "Header List";
                case 4:
                    return "Footer List";
            }
            return null;
        }
    }

    // endregion

}
