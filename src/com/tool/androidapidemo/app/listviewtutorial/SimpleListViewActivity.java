package com.tool.androidapidemo.app.listviewtutorial;

import com.tool.androidapidemo.R;
import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class SimpleListViewActivity extends Activity {
	private static final String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
        "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
        "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
        "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
        "Android", "iPhone", "WindowsMobile" };
	private int images[] = new int[] {R.drawable.alien1, R.drawable.alien2, R.drawable.earth};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simpilelistview);
		//Listview
		ListView listview = (ListView) findViewById(R.id.listview);
		//put data
        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();  
        for(int i=0;i<22;i++)  
        {
        	int imgId = i%3;
            HashMap<String, Object> hm = new HashMap<String, Object>();  
            hm.put("ItemImage", images[imgId]);//local pictute's ID
            hm.put("ItemText", values[i]+values[i]+"\n"+values[i]);  
            listItem.add(hm);  
        }
        SimpleAdapter listItemAdapter = new SimpleAdapter(this, listItem, R.layout.mylistview0,
        		//ImageItem format
                new String[] {"ItemImage","ItemText"},
                new int[] {R.id.ItemImage, R.id.ItemText} );
	    //listview set view
	    listview.setAdapter(listItemAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
