package db.com.dyhome.module.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import db.com.dyhome.R;
import db.com.dyhome.base.BaseActivity;
import db.com.dyhome.define.SpHelper;
import db.com.dyhome.utils.CommonUtils;

public class SplashActivity extends BaseActivity {

    private ViewPager welcome_viewPager;
    private List<ImageView> mImageList;
    private LinearLayout welcome_dot_layout;
    private View redDot;
    private Button welcome_comeOn;
    private int[] images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTintColor("#00000000");
        setToolbarvisibility(View.GONE);
        initView();
        initImage();
        welcome_viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
    }

    @Override
    protected int setBodyView() {
        return R.layout.activity_welcome;
    }

    /**
     * 加载ViewPager 的图片
     */
    private void initImage() {
        mImageList = new ArrayList<ImageView>();
        images = new int[]{R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3};
        for (int i = 0; i < images.length; i++) {
            ImageView image = new ImageView(SplashActivity.this);
            image.setScaleType(ImageView.ScaleType.CENTER_CROP);
            image.setImageResource(images[i]);
            mImageList.add(image);
            //加黑点
            View viewDot=new View(this);
            viewDot.setBackgroundResource(R.drawable.welcome_dot_normal);
            int dp2px = CommonUtils.dp2px(getApplicationContext(), 10);
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(dp2px,dp2px);
            if(i!=0){
                layoutParams.leftMargin=dp2px;
            }
            viewDot.setLayoutParams(layoutParams);
            welcome_dot_layout.addView(viewDot);
        }
        welcome_viewPager.setAdapter(new WelcomePageAdapter());
    }

    /**
     * 初始化view
     */
    private void initView() {
        welcome_comeOn=(Button)findViewById(R.id.welcome_comeOn);
        redDot=(View)findViewById(R.id.welcome_redPoint);
        welcome_viewPager = (ViewPager) findViewById(R.id.welcome_ViewPager);
        welcome_dot_layout =(LinearLayout) findViewById(R.id.welcome_dot_layout);
        welcome_comeOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpHelper.setIsFistOpenApp(false);
                Intent intent=new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        });
    }


    class  MyOnPageChangeListener implements ViewPager.OnPageChangeListener{
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            float redMoveX=(float) (position+positionOffset)*CommonUtils.dp2px(getApplicationContext(),20);
            RelativeLayout.LayoutParams redParams=(RelativeLayout.LayoutParams) redDot.getLayoutParams();
            redParams.leftMargin=(int) redMoveX;
            redDot.setLayoutParams(redParams);
        }

        @Override
        public void onPageSelected(int position) {

            if(position==images.length-1){
                welcome_comeOn.setVisibility(View.VISIBLE);
            }else{
                welcome_comeOn.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }



    class WelcomePageAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mImageList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==(View) object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView image=mImageList.get(position);
            container.addView(image);
            return  image;
        }
    }
}
