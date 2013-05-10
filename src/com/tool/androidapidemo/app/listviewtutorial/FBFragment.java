package com.tool.androidapidemo.app.listviewtutorial;

import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
//import com.facebook.android.Facebook;
import com.facebook.widget.LoginButton;

import com.tool.androidapidemo.R;
import com.tool.androidapidemo.bean.*;
import com.tool.androidapidemo.imageview.*;

public class FBFragment extends DialogFragment {
	private static final String TAG = "FacebookFragment";
	private UiLifecycleHelper uiHelper;
	private Button likeButton, shareButton, cancelButton;
	private EditText mMessageText;
	private TextView mMessageTitle;
	private boolean isLikeBtn = true;
	private boolean isShareBtn = true;
	private static final List<String> PERMISSIONS = Arrays.asList("publish_stream", "user_likes");
	private UserDetailBean udb;
	private UserBean ub;
	private String postId;
	private View v;
	public TextView effect;
	public FbActivity activity;
	OnChangeEffectListener mListener;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	        Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.facebook_login, container, false);
		//
		udb = (UserDetailBean) getArguments().getSerializable(FbActivity.SER_KEY);
		ub = udb.getUserBean();
	    LoginButton authButton = (LoginButton) view
	            .findViewById(R.id.authButton);
	    authButton.setFragment(this);
	    authButton.setPublishPermissions(Arrays.asList("publish_stream"));
	    isLikeBtn = getArguments().getBoolean("isLikeBtn");
	    isShareBtn = getArguments().getBoolean("isShareBtn");
	    int stratIdx = ub.permalink.indexOf("fbid=");
	    if( stratIdx<0 )
	    	FBFragment.this.dismiss();
	    postId = ub.permalink.substring(stratIdx+5, ub.permalink.indexOf("&", stratIdx));
	
	    likeButton = (Button) view.findViewById(R.id.likeButton);
	    shareButton = (Button) view.findViewById(R.id.shareButton);
	    mMessageText = (EditText) view.findViewById(R.id.facebook_post_text);
	    mMessageTitle = (TextView) view.findViewById(R.id.facebook_post_title);
	    cancelButton = (Button) view.findViewById(R.id.cancelButton);
	    cancelButton.setOnClickListener(new View.OnClickListener() {
	
	        @Override
	        public void onClick(View arg0) {
	            FBFragment.this.dismiss();
	
	        }
	
	    });
	    shareButton.setOnClickListener(new View.OnClickListener() {
	
	        @Override
	        public void onClick(View arg0) {
	            Bundle params = new Bundle();
	            JSONObject jsonObject = new JSONObject(); 
	            try {
					jsonObject.put("value", "SELF");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
                //link, message, picture, name, caption, description
	            params.putString("privacy", jsonObject.toString());
	            params.putString("link", ub.permalink);
	            params.putString("name", "連結裡所以呈現的文字");
	            //params.putString("message", mMessageText.getText().toString());
	            params.putString("message", "Test");
	            params.putString("description","在灰色註解區塊想寫些什麼？");
	            Request request = new Request(Session.getActiveSession(), "feed", params, 
	                    HttpMethod.POST);

	        	ub.shares_count += 1;
	            udb.setUserBean(ub);
	        	mListener.setEffect(udb);

	            request.setCallback(new Request.Callback() {

	                @Override
	                public void onCompleted(Response response) {
	                    if (response.getError()==null) {
	                        //Toast.makeText(getActivity(), "Successfully posted photo", Toast.LENGTH_SHORT).show();
	                        //FlurryAgent.logEvent(Globals.EVENT_FACEBOOK);
	                    } else {
	                        //Toast.makeText(getActivity(), response.getError().getErrorMessage(), Toast.LENGTH_SHORT).show();
	                    }
	                    FBFragment.this.dismiss();

	                }
	            });
	            request.executeAsync();
	        }
	
	    });
	    likeButton.setOnClickListener(new View.OnClickListener() {
	
	        @Override
	        public void onClick(View arg0) {
	            Bundle params = new Bundle();
	            ub.likes_count += 1;
	            udb.setUserBean(ub);
	        	mListener.setEffect(udb);
	        	FBFragment.this.dismiss();
	        	Request request = new Request(Session.getActiveSession(), postId+"/likes", params, 
                    HttpMethod.POST);

	            request.setCallback(new Request.Callback() {

	                @Override
	                public void onCompleted(Response response) {
	                    if (response.getError()==null) {
	                        //Toast.makeText(getActivity(), "Successfully posted photo", Toast.LENGTH_SHORT).show();
	                        //FlurryAgent.logEvent(Globals.EVENT_FACEBOOK);
	                    } else {
	                        //Toast.makeText(getActivity(), response.getError().getErrorMessage(), Toast.LENGTH_SHORT).show();
	                    }
	                    FBFragment.this.dismiss();

	                }
	            });
	            request.executeAsync();
	            //publishPhoto();
	        }
	
	    });
	    return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    //
	    uiHelper = new UiLifecycleHelper(getActivity(), callback);
	    uiHelper.onCreate(savedInstanceState);
	    setStyle(DialogFragment.STYLE_NO_TITLE, 0);
	}

	private void onSessionStateChange(Session session, SessionState state,
	        Exception exception) {
	    if (state.isOpened()) {
	        Log.i(TAG, "Logged in...");
	        // Check for reading user_photos permission  
	        if( isLikeBtn )
		        likeButton.setVisibility(View.VISIBLE);
	        if( isShareBtn )
	        {
	        	shareButton.setVisibility(View.VISIBLE);
	        	//mMessageText.setVisibility(View.VISIBLE);
	        	//mMessageTitle.setVisibility(View.VISIBLE);
	        }
	
	
	    } else if (state.isClosed()) {
	        Log.i(TAG, "Logged out...");
	        if( isLikeBtn )
		        likeButton.setVisibility(View.GONE);
	        if( isShareBtn )
	        {
	        	shareButton.setVisibility(View.GONE);
	        	//mMessageText.setVisibility(View.GONE);
	        	//mMessageTitle.setVisibility(View.GONE);
	        }
	    }	
	}

	private Session.StatusCallback callback = new Session.StatusCallback() {
	    @Override
	    public void call(Session session, SessionState state,
	            Exception exception) {
	        onSessionStateChange(session, state, exception);
	
	    }
	};
	
	@Override
	public void onResume() {
	    super.onResume();
	    uiHelper.onResume();
	    // For scenarios where the main activity is launched and user
	    // session is not null, the session state change notification
	    // may not be triggered. Trigger it if it's open/closed.
	    Session session = Session.getActiveSession();
	    if (session != null && (session.isOpened() || session.isClosed())) {
	        onSessionStateChange(session, session.getState(), null);
	    }
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    uiHelper.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	public void onPause() {
	    super.onPause();
	    uiHelper.onPause();
	}
	
	@Override
	public void onDestroy() {
	    super.onDestroy();
	    uiHelper.onDestroy();
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    uiHelper.onSaveInstanceState(outState);
	}
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnChangeEffectListener) activity;
            if( getParentFragment()!=null )
            	mListener = (OnChangeEffectListener) getParentFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnChangeEffectListener");
        }
    }
	// Container Activity must implement this interface
	public interface OnChangeEffectListener {
        public void setEffect(UserDetailBean u);
    }
}
