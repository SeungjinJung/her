package com.parse.her;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by jack on 15. 8. 14.
 */
public class MainActivity extends FragmentActivity {

    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private ScreenSlidePagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        load();
    }

    private void load() {
        AsyncTask<Void, Void, List<ParseObject>> task = new AsyncTask<Void, Void, List<ParseObject>>() {

            @Override
            protected List<ParseObject> doInBackground(Void... params) {
                ParseQuery<ParseObject> query = ParseQuery.getQuery("images");
                try {
                    return query.find();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(List<ParseObject> list) {
                super.onPostExecute(list);
                mPagerAdapter.setList(list);
            }
        };
        task.execute();
    }


    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        private List<ParseObject> mList = new LinkedList<ParseObject>();

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void setList(List<ParseObject> list) {
            mList = list;
            notifyDataSetChanged();
        }

        @Override
        public Fragment getItem(int position) {
            String url = mList.get(position).getString("url");
            ScreenSlidePageFragment fragment = ScreenSlidePageFragment.newInstance(url);
            return fragment;
        }

        @Override
        public int getCount() {
            return mList.size();
        }
    }
}
