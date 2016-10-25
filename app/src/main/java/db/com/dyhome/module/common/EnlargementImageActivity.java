package db.com.dyhome.module.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;

import java.util.ArrayList;

import db.com.dyhome.R;

/**
*  from zdb  create at 2016/3/26  14:57
*   查看大图
**/
public class EnlargementImageActivity extends Activity implements ViewPager.OnPageChangeListener{

    private ViewPager mViewPager;
    private ArrayList<String> mArrayImageUrl=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_enlargement);

        String url = getIntent().getStringExtra("url");
        ArrayList<String> urlArray=getIntent().getStringArrayListExtra("urlArray");
        if(!TextUtils.isEmpty(url)){
            mArrayImageUrl.add(url);
        }

        if(urlArray!=null&&urlArray.size()>0){
            mArrayImageUrl.addAll(urlArray);
        }
        init();
    }

    public static void start(Context context, String url){
        if("".equals(url)){
           // ToastUtil.showMsg("没有找到该图片");
            return;
        }
        Intent intent = new Intent(context, EnlargementImageActivity.class);
        intent.putExtra("url", url);
        //InvokeUtil.startActivityEnterRightToLeft(context, intent);
    }

    public static void start(Context context, ArrayList<String> arrayUrl){
        if(arrayUrl==null||arrayUrl.size()==0){
            //ToastUtil.showMsg("没有找到该图片");
            return;
        }
        Intent intent = new Intent(context, EnlargementImageActivity.class);
        intent.putStringArrayListExtra("urlArray", arrayUrl);
        //InvokeUtil.startActivityEnterRightToLeft(context, intent);
    }

    private void init() {
        mViewPager= (ViewPager) findViewById(R.id.paintings_view_pager);
        mViewPager.setAdapter(new EnlargementPagerAdapter(this, mArrayImageUrl));
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.view_pager_margin));
        onPageSelected(0);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
