package com.tool.androidapidemo.app.listviewtutorial;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.tool.androidapidemo.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tool.androidapidemo.bean.*;
import com.tool.androidapidemo.imageview.*;

public class DetailActivity extends Activity {
	private DisplayImageOptions options;
	private ImageLoader imageLoader = ImageLoader.getInstance();// ImageLoader initial;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	public AspectRatioImageView image;
	public FixRatioImageView autherImage;
	public TextView autherName;
	public TextView time;
	public TextView message;
	public TextView effect;
	private int autherImageW;
	private int position;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		//
		DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int moonitorW = displaymetrics.widthPixels;
        autherImageW = moonitorW/5;
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
		//
		UserDetailBean udb = (UserDetailBean)getIntent().getSerializableExtra(FbActivity.SER_KEY);
		UserBean ub = udb.getUserBean();
		autherName = (TextView) findViewById(R.id.AuthorNameText);
		autherName.getPaint().setFakeBoldText(true);
		autherImage = (FixRatioImageView) findViewById(R.id.AuthorImage);
		LayoutParams params = autherImage.getLayoutParams();
		params.width = autherImageW;
		autherImage.setLayoutParams(params);
		time = (TextView) findViewById(R.id.CreateTimeText);
		message = (TextView) findViewById(R.id.ItemText);
		effect = (TextView) findViewById(R.id.ItemDesc);
		image = (AspectRatioImageView) findViewById(R.id.ItemImage);
		//
		imageLoader.displayImage(ub.author_image_url, autherImage, options, animateFirstListener);
		if( !ub.image_url.equals("") )
		{
			image.setVisibility(View.VISIBLE);
			imageLoader.displayImage(ub.image_url, image, options, animateFirstListener);
		}
		else
			image.setVisibility(View.GONE);
		position = udb.getPostion();
		message.setText(ub.message);
		autherName.setText(ub.author_name);
		time.setText(udb.getRelativeTimeString());
		effect.setText(udb.getEffect());
		RelativeLayout rl0 = (RelativeLayout) findViewById(R.id.ItemRelativeLayout);
		LayoutParams rp = rl0.getLayoutParams();
		RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams)rp;
		if( position%2==0 )
		{
			rl0.setBackgroundResource(R.drawable.conversation_bg_left);
			rl.setMargins(0, 0, 20, 0);
		}
		else
		{
			rl0.setBackgroundResource(R.drawable.conversation_bg_right);
			rl.setMargins(20, 0, 0, 0);
		}
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
}
