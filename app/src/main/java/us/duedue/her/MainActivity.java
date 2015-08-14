package us.duedue.her;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.util.LinkedList;
import java.util.List;

import static java.util.Collections.shuffle;

/**
 * Created by jack on 15. 8. 14.
 */
public class MainActivity extends FragmentActivity {

    private ViewPager mPager;

    private Integer index;
    private static Integer LIMIT = 15;
    private static Integer MARGIN = 5;

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
        mPager.setOffscreenPageLimit(5);

        ParseInstallation current = ParseInstallation.getCurrentInstallation();

        if (current.get("index") == null) {
            index = 0;
        } else {
            index = (Integer) current.get("index");
        }

        load();
    }

    private void load() {
        AsyncTask<Void, Void, List<ParseObject>> task = new AsyncTask<Void, Void, List<ParseObject>>() {

            @Override
            protected List<ParseObject> doInBackground(Void... params) {
                ParseQuery<ParseObject> query = ParseQuery.getQuery("images").setSkip(index).setLimit(LIMIT);

                try {
                    return query.find();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(List<ParseObject> list) {

                if (list.isEmpty()){
                    index = 0;
                    load();
                    return;
                } else {
                    index = index + list.size();
                }

                recordIndex();

                super.onPostExecute(list);
                mPagerAdapter.appendList(list);
            }

            private void recordIndex() {
                try {
                    ParseInstallation current = ParseInstallation.getCurrentInstallation();
                    current.put("index", index);
                    current.save();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
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

        public void appendList(List<ParseObject> list) {
//            shuffle(list);
            mList.addAll(list);
            notifyDataSetChanged();
        }

        @Override
        public Fragment getItem(int position) {

            if (mList.size() == position + MARGIN) {
                load();
            }

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
