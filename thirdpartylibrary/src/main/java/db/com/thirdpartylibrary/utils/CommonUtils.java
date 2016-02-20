package db.com.thirdpartylibrary.utils;

import android.content.Context;

public class CommonUtils {
	public static int dp2px(Context context,int dpVaule){
		float density = context.getResources().getDisplayMetrics().density;
		int px = (int) (dpVaule * density + 0.5f);
		return px;
	}
}
