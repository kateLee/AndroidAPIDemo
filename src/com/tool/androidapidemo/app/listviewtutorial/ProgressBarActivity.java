package com.tool.androidapidemo.app.listviewtutorial;

import com.tool.androidapidemo.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
//import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
//import android.widget.ProgressBar;
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

import static com.tool.androidapidemo.app.listviewtutorial.CommonUtilities.DATA_URL;
import static com.tool.androidapidemo.app.listviewtutorial.CommonUtilities.SERVER_URL;

public class ProgressBarActivity extends Activity {

	private String dataUrl = null;
	private DisplayImageOptions options;
	private ImageLoader imageLoader = ImageLoader.getInstance();// ImageLoader initial;
	private ArrayList<UserBean> users = null;
	private TextView tv;
	private ListView listview;
	private ItemAdapter listItemAdapter;
	boolean isfinish = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simpilelistview);
		//
		if( SERVER_URL!=null && DATA_URL!=null )
			dataUrl = SERVER_URL+DATA_URL;
		//
		tv = (TextView)findViewById(R.id.ItemText);
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
		listItemAdapter = new ItemAdapter();
	    listview.setAdapter(listItemAdapter);
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
			String response;
			ObjectMapper mapper = new ObjectMapper().setVisibility(JsonMethod.FIELD, Visibility.ANY);
			if( dataUrl!=null )
			{
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
				response = hr.body();
			}
			else
			{
				response = CommonUtilities.JSON_DATA;
				//mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
			}
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
			//setProgressPercent(progress[0]);
			//process(progress[0]);
			super.onProgressUpdate(progress);
	    }
		//@Override
	    protected void onPostExecute(String result) {
			//tv.setVisibility(View.GONE);
			listItemAdapter.notifyDataSetChanged();
			if( root.meta.next==null )//if( root.meta.previous==null )
				isfinish = true;
			else if( SERVER_URL!=null )
				dataUrl = SERVER_URL+root.meta.next;
	    	//wrtieListView();
	    	super.onPostExecute(result);
	    }
	}
	public void process(Integer progress)
	{
		tv.setText(progress+"%");
	}
	public void wrtieListView()
	{
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
					//holder.image = iv;
					holder.type = 1;
					//simple ProgressBar
					//view = new ProgressBar(at);
					view = getLayoutInflater().inflate(R.layout.progressbar, parent, false);
				}
				else
				{
					view = getLayoutInflater().inflate(R.layout.mylistview0, parent, false);
					holder.text = (TextView) view.findViewById(R.id.ItemText);
					holder.image = (ImageView) view.findViewById(R.id.ItemImage);
					holder.button = (Button) view.findViewById(R.id.ItemButton);
					holder.type = 0;
				}
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
				if( !fFoot&&holder.type!=0 )
				{
					holder = new ViewHolder();
					{
						view = getLayoutInflater().inflate(R.layout.mylistview0, parent, false);
						holder.text = (TextView) view.findViewById(R.id.ItemText);
						holder.image = (ImageView) view.findViewById(R.id.ItemImage);
						holder.button = (Button) view.findViewById(R.id.ItemButton);
						holder.type = 0;
					}
					view.setTag(holder);
				}
				else if( fFoot&&holder.type!=1 )
				{
					holder = new ViewHolder();
					holder.type = 1;
					//simple ProgressBar
					//view = new ProgressBar(at);
					view = getLayoutInflater().inflate(R.layout.progressbar, parent, false);
					view.setTag(holder);
				}
			}
			if( !fFoot )
			{
				imageLoader.displayImage(users.get(position).author_image_url, holder.image, options, animateFirstListener);
				holder.text.setText(position+".\n"+users.get(position).message);
			}
			else if( !isfinish )
			{
				new Task().execute();
			}
			return view;
		}
	}
}
