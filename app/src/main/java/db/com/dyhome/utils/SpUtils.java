package db.com.dyhome.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import db.com.dyhome.bean.LocalVideoEntity;

import static u.aly.av.T;
import static u.aly.av.a;


/**
 * Created by zdb on 2016/11/24 0024.
 */

public class SpUtils {
    private static final String mSpName = "dyhome";
    public static final String mLocalVideoName = "LocalVideoName";

    public static void saveLocalVideo(Context context, List<LocalVideoEntity> entities) {
        SharedPreferences preferences = context.getSharedPreferences(mSpName, Context.MODE_PRIVATE);
        JSONObject jsonResult=new JSONObject();
        JSONArray jsonArray=new JSONArray();
        try {
        for(int i=0;i<entities.size();i++){
            LocalVideoEntity entity=entities.get(i);
            JSONObject object=new JSONObject();
                object.put("title",entity.getTitle());
                object.put("path",entity.getPath());
                object.put("size",entity.getSize());
                jsonArray.put(object);
        }
            jsonResult.put("result",jsonArray);
            preferences.edit().putString(mLocalVideoName,jsonResult.toString()).commit();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从sp中读取对象
     *
     * @param context
     * @return
     */
    public static List<LocalVideoEntity>  getLocalVideo(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(mSpName, Context.MODE_PRIVATE);
        String jsonStr=preferences.getString(mLocalVideoName, "");
        if(TextUtils.isEmpty(jsonStr)){
            return null;
        }
        List<LocalVideoEntity> entities=new ArrayList<>();
        try {
            JSONObject resultObj=new JSONObject(jsonStr);
            JSONArray array=resultObj.getJSONArray("result");
            for(int i=0;i<array.length();i++){
                JSONObject obj=array.getJSONObject(i);
                String title=obj.optString("title");
                String path=obj.optString("path");
                Long size=obj.optLong("size");
                LocalVideoEntity entity=new LocalVideoEntity(title,path,size,FileUtils.getVideoThumbnail(path));
                entities.add(entity);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return entities;
    }
}
