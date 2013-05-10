package com.tool.androidapidemo.app.listviewtutorial;

import com.tool.androidapidemo.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.format.DateUtils;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
//import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.github.kevinsawicki.http.HttpRequest;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.tool.androidapidemo.bean.*;
import com.tool.androidapidemo.imageview.*;

import static com.tool.androidapidemo.app.listviewtutorial.CommonUtilities.DATA_URL;
import static com.tool.androidapidemo.app.listviewtutorial.CommonUtilities.SERVER_URL;

public class NinePatchActivity extends Activity {

	private String dataUrl = SERVER_URL+DATA_URL;;
	private DisplayImageOptions options;
	private ImageLoader imageLoader = ImageLoader.getInstance();// ImageLoader initial;
	private ArrayList<UserBean> users = null;
	private ListView listview;
	private ItemAdapter listItemAdapter;
	private boolean isfinish = false;
	private int moonitorW;
	private int autherImageW;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simpilelistview);
		//
		DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        moonitorW = displaymetrics.widthPixels;
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
		//ListView
		listview = (ListView) findViewById(R.id.listview);
	    View v = new View(this);
	    listview.addHeaderView(v);
		listItemAdapter = new ItemAdapter();
	    listview.setAdapter(listItemAdapter);
	    listview.setCacheColorHint(Color.TRANSPARENT);
		new Task().execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	class Task extends AsyncTask<String, Integer, String> {
		private RootBean root;
		@Override
		protected void onPreExecute(){
			super.onPreExecute();  
		}
		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			HttpRequest hr;
			try
			{
				hr = HttpRequest.get(dataUrl);
				if( !hr.ok() )
				{
					return null;
				}
			} catch (Exception e){
			// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			String response = hr.body();
			ObjectMapper mapper = new ObjectMapper().setVisibility(JsonMethod.FIELD, Visibility.ANY);
		    mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false); // can reuse, share globally
			try {
				root = mapper.readValue(response, RootBean.class);
				if( root==null )
					return null;
				if( users==null )
					users = root.objects;
				else
					users.addAll(root.objects);
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		@Override
	    protected void onProgressUpdate(Integer... progress) {
			super.onProgressUpdate(progress);
	    }
		//@Override
	    protected void onPostExecute(String result) {
			listItemAdapter.notifyDataSetChanged();
			if( users==null||root.meta.next==null )//if( root.meta.previous==null )
				isfinish = true;
			else
				dataUrl = SERVER_URL+root.meta.next;
	    	super.onPostExecute(result);
	    }
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
			public FixRatioImageView image;
			public FixRatioImageView autherImage;
			public TextView autherName;
			public TextView time;
			public TextView message;
			public TextView desc;
			//public Button button1;
			//public Button button2;
			public int type;
		}
		@Override
		public int getCount() {
			if( users==null )
				return 0;
			if( isfinish )
				return users.size();
			return users.size()+1;
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
			ViewHolder holder;
			boolean fFoot = position==users.size();
			if (convertView == null) {
				holder = new ViewHolder();
				if( fFoot )
				{
					holder.type = 1;
					view = getLayoutInflater().inflate(R.layout.progressbar, parent, false);
				}
				else
				{
					view = getLayoutInflater().inflate(R.layout.mylistview5, parent, false);
					holder.autherName = (TextView) view.findViewById(R.id.AuthorNameText);
					holder.autherName.getPaint().setFakeBoldText(true);
					holder.autherImage = (FixRatioImageView) view.findViewById(R.id.AuthorImage);
					LayoutParams params = holder.autherImage.getLayoutParams();
					params.width = autherImageW;
					holder.autherImage.setLayoutParams(params);
					holder.time = (TextView) view.findViewById(R.id.CreateTimeText);
					holder.message = (TextView) view.findViewById(R.id.ItemText);
					holder.desc = (TextView) view.findViewById(R.id.ItemDesc);
					holder.image = (FixRatioImageView) view.findViewById(R.id.ItemImage);
					holder.image.ratio = (float)0.618;
					holder.type = 0;
					RelativeLayout rl0 = (RelativeLayout) view.findViewById(R.id.ItemRelativeLayout);
					LayoutParams rp = rl0.getLayoutParams();
					RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams)rp;
					if( position%2==0 )
					{
						rl0.setBackgroundResource(R.drawable.conversation_bg_left);
						rl.setMargins(0, 0, 50, 0);
					}
					else
					{
						rl0.setBackgroundResource(R.drawable.conversation_bg_right);
						rl.setMargins(50, 0, 0, 0);
					}
					rl0.setLayoutParams(rl);
				}
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
				if( !fFoot )
				{
					if( holder.type!=0 )
					{
						holder = new ViewHolder();
						view = getLayoutInflater().inflate(R.layout.mylistview5, parent, false);
						holder.autherName = (TextView) view.findViewById(R.id.AuthorNameText);
						holder.autherName.getPaint().setFakeBoldText(true);
						holder.autherImage = (FixRatioImageView) view.findViewById(R.id.AuthorImage);
						LayoutParams params = holder.autherImage.getLayoutParams();
						params.width = autherImageW;
						holder.autherImage.setLayoutParams(params);
						holder.time = (TextView) view.findViewById(R.id.CreateTimeText);
						holder.message = (TextView) view.findViewById(R.id.ItemText);
						holder.desc = (TextView) view.findViewById(R.id.ItemDesc);
						holder.image = (FixRatioImageView) view.findViewById(R.id.ItemImage);
						holder.image.ratio = (float)0.618;
						holder.type = 0;
						view.setTag(holder);
					}
					RelativeLayout rl0 = (RelativeLayout) view.findViewById(R.id.ItemRelativeLayout);
					LayoutParams rp = rl0.getLayoutParams();
					RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams)rp;
					if( position%2==0 )
					{
						rl0.setBackgroundResource(R.drawable.conversation_bg_left);
						rl.setMargins(0, 0, 50, 0);
					}
					else
					{
						rl0.setBackgroundResource(R.drawable.conversation_bg_right);
						rl.setMargins(50, 0, 0, 0);
					}
					rl0.setLayoutParams(rl);
				}
				else if( holder.type!=1 )
				{
					holder = new ViewHolder();
					holder.type = 1;
					view = getLayoutInflater().inflate(R.layout.progressbar, parent, false);
					view.setTag(holder);
				}
			}
			if( !fFoot )
			{
				imageLoader.displayImage(users.get(position).author_image_url, holder.autherImage, options, animateFirstListener);
				if( !users.get(position).image_url.equals("") )
				{
					holder.image.setVisibility(View.VISIBLE);
					imageLoader.displayImage(users.get(position).image_url, holder.image, options, animateFirstListener);
				}
				else
					holder.image.setVisibility(View.GONE);
				holder.message.setText(users.get(position).message);
				holder.autherName.setText(users.get(position).author_name);
				long relativeMillis=0;
				try 
				{
				    relativeMillis = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
				        .parse(users.get(position).created_time)
				        .getTime();
				} catch (Exception e) {
				    e.printStackTrace();
				}
				String relativeTimeString=(String) DateUtils.getRelativeTimeSpanString(relativeMillis);
				holder.time.setText(relativeTimeString);
				String desc = "";
				if( users.get(position).likes_count!=0 )
					desc = users.get(position).likes_count+"­ÓÆg";
				if( users.get(position).shares_count!=0 )
				{
					if( !desc.equals("") )
						desc += "¡E";
					desc += users.get(position).shares_count+"¦¸¤À¨É";
				}
				holder.desc.setText(desc);
			}
			else if( !isfinish )
			{
				new Task().execute();
			}
			return view;
		}
	}
}
