package us.duedue.her;

import android.app.Application;
import android.util.Log;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class HerApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		/*
		 * In this tutorial, we'll subclass ParseObject for convenience to
		 * create and modify Meal objects
		 */

		/*
		 * Fill in this section with your Parse credentials
		 */
		Parse.initialize(this, "cavYia03mw7CpyRZ8KJ9z3dnsRlbdaGdY2X4DdAM", "aswkqB7rQCBp0Shb7HxWr6GVT1VPnSBbVuvqQhQQ");
        ParseInstallation.getCurrentInstallation().saveInBackground();

		/*
		 * This app lets an anonymous user create and save photos of meals
		 * they've eaten. An anonymous user is a user that can be created
		 * without a username and password but still has all of the same
		 * capabilities as any other ParseUser.
		 * 
		 * After logging out, an anonymous user is abandoned, and its data is no
		 * longer accessible. In your own app, you can convert anonymous users
		 * to regular users so that data persists.
		 * 
		 * Learn more about the ParseUser class:
		 * https://www.parse.com/docs/android_guide#users
		 */
		ParseUser.enableAutomaticUser();

		/*
		 * For more information on app security and Parse ACL:
		 * https://www.parse.com/docs/android_guide#security-recommendations
		 */
		ParseACL defaultACL = new ParseACL();

		/*
		 * If you would like all objects to be private by default, remove this
		 * line
		 */
		defaultACL.setPublicReadAccess(true);

		ParseACL.setDefaultACL(defaultACL, true);



		//Create image options.
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.cacheInMemory(true)
				.cacheOnDisk(true)
				.build();

		//Create a config with those options.
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
				.defaultDisplayImageOptions(options)
				.build();

		ImageLoader.getInstance().init(config);

		// enable push
		ParsePush.subscribeInBackground("", new SaveCallback() {
			@Override
			public void done(ParseException e) {
				if (e == null) {
					Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
				} else {
					Log.e("com.parse.push", "failed to subscribe for push", e);
				}
			}
		});
	}

}
