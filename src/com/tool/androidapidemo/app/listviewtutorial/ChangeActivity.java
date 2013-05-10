package com.tool.androidapidemo.app.listviewtutorial;

import com.tool.androidapidemo.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ChangeActivity extends Activity {
	private Button button01;
	private Button button02;
	private ImageView img;
	private int imageSid;
	public int images[] = new int[] {R.drawable.alien1, R.drawable.alien2};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change2activity);
		button01 = (Button)findViewById(R.id.Button01);
		button02 = (Button)findViewById(R.id.Button02);
		img = (ImageView)findViewById(R.id.imageview);
		imageSid = 0;
		img.setImageResource(images[imageSid]);
		button01.setOnClickListener(new Button.OnClickListener(){ 
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
            	imageSid = 1-imageSid;
                img.setImageResource(images[imageSid]);
            }         

        }); 
		button02.setOnClickListener(new Button.OnClickListener(){ 
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
            	Intent intent = new Intent();
            	intent.setClass(ChangeActivity.this, BackActivity.class);
            	ChangeActivity.this.startActivity(intent);
				overridePendingTransition(R.anim.fadein, R.anim.fadeout);
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
