package us.duedue.her;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
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

        try {
            ImageView imageView = (ImageView) rootView.findViewById(R.id.image);

            ImageLoader imageLoader = ImageLoader.getInstance();
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                    .showImageOnFail(R.drawable.ic_launcher)
                    .build();
            imageLoader.displayImage(mUrl, imageView, options);
            return rootView;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("error===");
            return rootView;
        }
    }
}