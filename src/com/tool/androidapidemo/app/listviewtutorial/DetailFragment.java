package com.tool.androidapidemo.app.listviewtutorial;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.tool.androidapidemo.R;
import com.tool.androidapidemo.app.listviewtutorial.FBFragment.OnChangeEffectListener;
//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.ImageLoader;
//import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
//import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

//import android.app.Activity;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tool.androidapidemo.bean.*;
import com.tool.androidapidemo.imageview.*;

public class DetailFragment extends Fragment implements OnChangeEffectListener {
	//private DisplayImageOptions options;
	//private ImageLoader imageLoader = ImageLoader.getInstance();// ImageLoader initial;
	//private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	public AspectRatioImageView image;
	public FixRatioImageView autherImage;
	public TextView autherName;
	public TextView time;
	public TextView message;
	public TextView effect;
	public Button likeBtn;
	public Button shareBtn;
	private int position;
	private UserDetailBean udb;
	OnChangeEffectListener mListener;
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnChangeEffectListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnChangeEffectListener");
        }
    }
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	if (container == null) {
            // We have different layouts, and in one of them this
            // fragment's containing frame doesn't exist.  The fragment
            // may still be created from its saved state, but there is
            // no reason to try to create its view hierarchy because it
            // won't be displayed.  Note this is not needed -- we could
            // just run the code below, where we would create and return
            // the view hierarchy; it would just never be used.
            return null;
        }
        View v = inflater.inflate(R.layout.activity_detail, container, false);
		//
		udb = (UserDetailBean) getArguments().getSerializable(FbActivity.SER_KEY);
		UserBean ub = udb.getUserBean();
		autherName = (TextView) v.findViewById(R.id.AuthorNameText);
		autherName.getPaint().setFakeBoldText(true);
		autherImage = (FixRatioImageView) v.findViewById(R.id.AuthorImage);
		LayoutParams params = autherImage.getLayoutParams();
		params.width = (int)getArguments().getInt(FbActivity.MONITOR_W)/5;
		autherImage.setLayoutParams(params);
		time = (TextView) v.findViewById(R.id.CreateTimeText);
		message = (TextView) v.findViewById(R.id.ItemText);
		effect = (TextView) v.findViewById(R.id.ItemDesc);
		image = (AspectRatioImageView) v.findViewById(R.id.ItemImage);
		likeBtn = (Button) v.findViewById(R.id.ItemButton1);
		shareBtn = (Button) v.findViewById(R.id.ItemButton2);
		//
		FbActivity.imageLoader.displayImage(ub.author_image_url, autherImage, FbActivity.options, FbActivity.animateFirstListener);
		if( !ub.image_url.equals("") )
		{
			image.setVisibility(View.VISIBLE);
			FbActivity.imageLoader.displayImage(ub.image_url, image, FbActivity.options, FbActivity.animateFirstListener);
		}
		else
			image.setVisibility(View.GONE);
		position = udb.getPostion();
		message.setText(ub.message);
		autherName.setText(ub.author_name);
		time.setText(udb.getRelativeTimeString());
		effect.setText(udb.getEffect());
		shareBtn.setOnClickListener(new View.OnClickListener() {
	
	        @Override
	        public void onClick(View arg0) {
	        	addFbFragment(udb, false, true);
	        }
	
	    });
		likeBtn.setOnClickListener(new View.OnClickListener() {
			
	        @Override
	        public void onClick(View arg0) {
	        	addFbFragment(udb, true, false);
	        }
	
	    });
		RelativeLayout rl0 = (RelativeLayout) v.findViewById(R.id.ItemRelativeLayout);
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
        return v;
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
	public void setEffect(final UserDetailBean u)
	{
		effect.setText(FbActivity.getEffect(u.getUserBean()));
		mListener.setEffect(u);
	}
	public void addFbFragment(UserDetailBean udb, boolean isLikeBtn, boolean isShareBtn)
	{
		//now you can use getParentFragment() in child
		FragmentManager fragmentManager = getChildFragmentManager(); 
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    	FBFragment fb = new FBFragment();
		Bundle mBundle = new Bundle();
		mBundle.putSerializable(FbActivity.SER_KEY, udb);
		mBundle.putBoolean("isLikeBtn", isLikeBtn);
		mBundle.putBoolean("isShareBtn", isShareBtn);
        fb.setArguments(mBundle);
        //fragmentTransaction.addToBackStack(DetailFragment.class.getName());
        //fragmentTransaction.add(R.id.ItemRelativeLayout, fb);
        //fragmentTransaction.commit();
        fb.show(fragmentManager, "FB");//DialogFragment do not need add
	}
}
