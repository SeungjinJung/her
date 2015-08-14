package com.parse.her;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.parse.ParseObject;

/**
 * Created by jack on 15. 8. 14.
 */
public class ScreenSlidePageFragment extends Fragment {
    private static final String ARG_URL = "arg_url";
    private String mUrl;

    public static ScreenSlidePageFragment newInstance(String url) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUrl = getArguments().getString(ARG_URL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.page, container, false);

        ImageView imageView = (ImageView) rootView.findViewById(R.id.image);

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(mUrl, imageView);
        return rootView;
    }
}