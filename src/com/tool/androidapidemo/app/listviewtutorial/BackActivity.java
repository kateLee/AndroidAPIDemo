package com.tool.androidapidemo.app.listviewtutorial;

import com.tool.androidapidemo.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class BackActivity extends Activity {
	private Button button01;
	private ImageView img;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_back);
		button01 = (Button)findViewById(R.id.Button01);
		img = (ImageView)findViewById(R.id.imageview);
		img.setImageResource(R.drawable.earth);
		button01.setOnClickListener(new Button.OnClickListener(){ 
			@Override
	        public void onClick(View v) {
	            // TODO Auto-generated method stub
				finish();
				overridePendingTransition(R.anim.fadein2, R.anim.fadeout2);
	        }         

		});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
