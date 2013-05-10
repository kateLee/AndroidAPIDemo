package com.tool.androidapidemo.app.listviewtutorial;

import com.tool.androidapidemo.R;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
//import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class WebImageListViewActivity extends Activity {

	DisplayImageOptions options;
	ImageLoader imageLoader = ImageLoader.getInstance();// ImageLoader initial;
	private static final String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
        "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
        "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
        "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
        "Android", "iPhone", "WindowsMobile" };
	private String[] imageUrls = new String[] {
		// 大圖片們
		"https://lh6.googleusercontent.com/-jZgveEqb6pg/T3R4kXScycI/AAAAAAAAAE0/xQ7CvpfXDzc/s1024/sample_image_01.jpg",
		"https://lh4.googleusercontent.com/-K2FMuOozxU0/T3R4lRAiBTI/AAAAAAAAAE8/a3Eh9JvnnzI/s1024/sample_image_02.jpg",
		"https://lh5.googleusercontent.com/-SCS5C646rxM/T3R4l7QB6xI/AAAAAAAAAFE/xLcuVv3CUyA/s1024/sample_image_03.jpg",
		"https://lh6.googleusercontent.com/-f0NJR6-_Thg/T3R4mNex2wI/AAAAAAAAAFI/45oug4VE8MI/s1024/sample_image_04.jpg",
		"https://lh3.googleusercontent.com/-n-xcJmiI0pg/T3R4mkSchHI/AAAAAAAAAFU/EoiNNb7kk3A/s1024/sample_image_05.jpg",
		"https://lh3.googleusercontent.com/-X43vAudm7f4/T3R4nGSChJI/AAAAAAAAAFk/3bna6D-2EE8/s1024/sample_image_06.jpg",
		// 小圖片們
		"http://tabletpcssource.com/wp-content/uploads/2011/05/android-logo.png",
		"http://simpozia.com/pages/images/stories/windows-icon.png",
		"https://si0.twimg.com/profile_images/1135218951/gmail_profile_icon3_normal.png",
		"http://www.krify.net/wp-content/uploads/2011/09/Macromedia_Flash_dock_icon.png",
		// 特殊情況們
		"file:///sdcard/UniversalImageLoader.png", // Image from SD card
		"assets://LivingThings.jpg", // Image from assets
		"drawable://" + R.drawable.ic_launcher, // Image from drawables
		"http://upload.wikimedia.org/wikipedia/ru/b/b6/?訄郕_郕郋?_?_邾??訄邾邽_赲郋迮赲訄郅.png", // Link with UTF-8
		"https://www.eff.org/sites/default/files/chrome150_0.jpg", // Image from HTTPS
		"http://bit.ly/soBiXr", // Redirect link
		"", // Empty link
		"http://wrong.site.com/corruptedLink", // Wrong link
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simpilelistview);
		//
        options = new DisplayImageOptions.Builder()
        .showStubImage(R.drawable.ic_stub)
        .showImageForEmptyUri(R.drawable.ic_empty)
        //.showImageOnFail(R.drawable.ic_error)
        .cacheInMemory()
        .cacheOnDisc()
		//.displayer(new RoundedBitmapDisplayer(20))
        .build();
       
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
        //.defaultDisplayImageOptions(options)
        .build();
		imageLoader.init(config);
		//ListView
		ListView listview = (ListView) findViewById(R.id.listview);
		ItemAdapter listItemAdapter = new ItemAdapter();
	    listview.setAdapter(listItemAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	//set picture as first loading
		private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

			static final List displayedImages = Collections.synchronizedList(new LinkedList());

			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				if (loadedImage != null) {
					ImageView imageView = (ImageView) view;
					boolean firstDisplay = !displayedImages.contains(imageUri);
					if (firstDisplay) {
						FadeInBitmapDisplayer.animate(imageView, 500);
						displayedImages.add(imageUri);
					} else {
						imageView.setImageBitmap(loadedImage);//?
					}
				}
			}
		}
		
		class ItemAdapter extends BaseAdapter {
			private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
			private class ViewHolder {
				public ImageView image;
				public TextView text;
				public Button button;
			}
			@Override
			public int getCount() {
				return imageUrls.length;
			}
			@Override
			public Object getItem(int position) {
				return position;
			}

			@Override
			public long getItemId(int position) {
				return position;
			}
			@Override
			public View getView(final int position, View convertView, ViewGroup parent) {
				View view = convertView;
				final ViewHolder holder;
				if (convertView == null) {
					view = getLayoutInflater().inflate(R.layout.mylistview0, parent, false);
					holder = new ViewHolder();
					holder.text = (TextView) view.findViewById(R.id.ItemText);
					holder.image = (ImageView) view.findViewById(R.id.ItemImage);
					holder.button = (Button) view.findViewById(R.id.ItemButton);
					view.setTag(holder);
				} else {
					holder = (ViewHolder) view.getTag();
				}
				imageLoader.displayImage(imageUrls[position], holder.image, options, animateFirstListener);
				holder.text.setText(values[position]+values[position]+"\n"+values[position]);
				return view;
			}
		}
}
