package com.tool.androidapidemo.app.parser.gson;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tool.androidapidemo.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: tacowu
 * Date: 5/20/13
 * Time: 1:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class GsonDemoActivity extends Activity {
    private TextView tv_gson_demo_json;
    private TextView tv_gson_demo_object;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_gson_activity);
        tv_gson_demo_json  = (TextView)findViewById(R.id.tv_gson_demo_json);
        tv_gson_demo_object = (TextView)findViewById(R.id.tv_gson_demo_object);

        String StrJson = "[{\"a\":\"i am sam\",\"b\":2},{\"a\":\"sam i am\",\"b\":3}]";

        Type type = new TypeToken<Collection<ITEM>>(){}.getType();
        Gson gson = new Gson();
        ArrayList<ITEM> itemAL;
        try{
            itemAL = (ArrayList<ITEM>)gson.fromJson(StrJson, type);
        }catch (Exception e){
            itemAL = new ArrayList<ITEM>();
        }

        tv_gson_demo_json.setText(StrJson);
        tv_gson_demo_object.setText(itemAL.get(0).toString()+itemAL.get(1).toString());
    }
    class ITEM {
        String a;
        int b;
        // field with transient would be ignore

        transient int index;

        @Override
        public String toString() {
            return String.format("a: %s\n" +
                                 "b: %d\n",a,b);
        }
    }
}