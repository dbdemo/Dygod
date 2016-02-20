package db.com.dygod;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import db.com.thirdpartylibrary.utils.CommonUtils;
import db.com.thirdpartylibrary.utils.MD5Encoder;

public class Welcome extends Activity {

    private ViewPager welcome_viewPager;
    private List<ImageView> mImageList;
    private LinearLayout welcome_dot_layout;
    private View redDot;
    private Button welcome_comeOn;
    private int[] images;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initView();
        initImage();
        welcome_viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
    }


    /**
     * 加载ViewPager 的图片
     */
    private void initImage() {
        mImageList = new ArrayList<ImageView>();
        images = new int[]{R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3};
        for (int i = 0; i < images.length; i++) {
            ImageView image = new ImageView(Welcome.this);
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
                Intent intent=new Intent(Welcome.this,LoadData_Activity.class);
                startActivity(intent);
                Welcome.this.finish();
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
