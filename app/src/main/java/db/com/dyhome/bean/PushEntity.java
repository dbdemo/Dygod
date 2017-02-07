package db.com.dyhome.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zdb on 2017/2/7.
 *
 * {"action":"1","url":"https://www.baidu.com/","content":"推送内容","title":"推送标题","versionCode":"2"}
 * {"action":"2","url":"http://www.bttt.la/subject/28963.html","content":"来找我电影","title":"请您欣赏"}
 */

public class PushEntity implements Parcelable{
    private String title;
    private int action;//1，推送升级，2.推送一步电影
    private String url;
    private String content;//推送内容
    private int  versionCode;


    protected PushEntity(Parcel in) {
        title = in.readString();
        action = in.readInt();
        url = in.readString();
        content=in.readString();
        versionCode=in.readInt();
    }
    public PushEntity(){

    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static final Creator<PushEntity> CREATOR = new Creator<PushEntity>() {
        @Override
        public PushEntity createFromParcel(Parcel in) {
            return new PushEntity(in);
        }

        @Override
        public PushEntity[] newArray(int size) {
            return new PushEntity[size];
        }
    };

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeInt(action);
        dest.writeString(url);
        dest.writeString(content);
        dest.writeInt(versionCode);
    }

    public static PushEntity parse(String json) {
        PushEntity entity=new PushEntity();
        try {
            JSONObject object=new JSONObject(json);
            entity.setUrl(object.optString("url"));
            entity.setAction(object.optInt("action"));
            entity.setTitle(object.optString("title"));
            entity.setContent(object.optString("content"));
            entity.setVersionCode( object.optInt("versionCode"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return entity;
    }
}
