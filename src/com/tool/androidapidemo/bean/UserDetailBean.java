package com.tool.androidapidemo.bean;

import java.io.Serializable;

public class UserDetailBean implements Serializable {
    private static final long serialVersionUID = -7060210544600464481L;
    private UserBean ub;
    private int postion;
    private String effect;
    private String relativeTimeString;
    public void setUserBean(UserBean u)
    {
    	ub = u;
    }
    public UserBean getUserBean()
    {
    	return ub;
    }
    public void setPostion(int p)
    {
    	postion = p;
    }
    public int getPostion()
    {
    	return postion;
    }
    public void setEffect(String e)
    {
    	effect = e;
    }
    public String getEffect()
    {
    	return effect;
    }
    public void setRelativeTimeString(String time)
    {
    	relativeTimeString = time;
    }
    public String getRelativeTimeString()
    {
    	return relativeTimeString;
    }
    
    public UserDetailBean(int p, UserBean u, String e, String time)
    {
    	ub = u;
    	postion = p;
    	effect = e;
    	relativeTimeString = time;
    }
}
