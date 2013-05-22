package com.tool.androidapidemo.app.parser.gson;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tool.androidapidemo.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: tacowu
 * Date: 5/22/13
 * Time: 10:19 AM
 * To change this template use File | Settings | File Templates.
 */
public class JsonParseDemoActivity extends Activity {
    private TextView tv_demo_json;
    private TextView tv_demo_object;
    private ArrayList<ITEM> itemAL = new ArrayList<ITEM>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.json_parse_demo_activity);
        tv_demo_json  = (TextView)findViewById(R.id.tv_demo_json);
        tv_demo_object = (TextView)findViewById(R.id.tv_demo_object);

        String StrJson = "[{\"a\":\"i am sam\",\"b\":2},{\"a\":\"sam i am\",\"b\":3}]";

        try {
            JSONArray tmpJA= new JSONArray(StrJson);
            for(int i=0; i<tmpJA.length();i++){
                JSONObject tmpJO = (JSONObject) tmpJA.getJSONObject(i);
                ITEM tmpItem = new ITEM();
                tmpItem.a = tmpJO.getString("a");
                tmpItem.b = tmpJO.getInt("b");
                itemAL.add(tmpItem);
            }
        } catch (JSONException e) {


        }

        tv_demo_json.setText(StrJson);
        StringBuffer sb= new StringBuffer("");
        for(ITEM item : itemAL){
            sb.append(item.toString());
        }
        tv_demo_object.setText(sb.toString());
    }
    class ITEM {
        String a;
        int b;
        @Override
        public String toString() {
            return String.format("a: %s\n" +
                    "b: %d\n",a,b);
        }
    }
}