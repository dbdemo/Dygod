package db.com.dyhome.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class UiAdapterUtils {

	public static final int[] QS_THUMBNAIL_WIDTH_ARRAY = new int[] { 160, 240,
			320, 400, 480, 560 };

	private static final String UI_ADAPTER_INFO_RECORD_PREFS = "ui_adaptive_infos";

	public static void init(Context context, WindowManager windowManager) {
		DisplayMetrics outMetrics = new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(outMetrics);

		context.getSharedPreferences(UI_ADAPTER_INFO_RECORD_PREFS, 0).edit()
				.putFloat("density", outMetrics.density)
				.putFloat("scaledDensity", outMetrics.scaledDensity)
				.putFloat("xdpi", outMetrics.xdpi)
				.putFloat("ydpi", outMetrics.ydpi)
				.putInt("widthPixels", outMetrics.widthPixels)
				.putInt("heightPixels", outMetrics.heightPixels).commit();
	}

	public static int getWidthPixel(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(
				UI_ADAPTER_INFO_RECORD_PREFS, 0);
		return prefs.getInt("widthPixels", 0);
	}

	public static int getHeightPixel(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(
				UI_ADAPTER_INFO_RECORD_PREFS, 0);
		return prefs.getInt("heightPixels", 0);
	}

	public static float getWidthInch(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(
				UI_ADAPTER_INFO_RECORD_PREFS, 0);
		return prefs.getInt("widthPixels", 0) / prefs.getFloat("xdpi", 1);
	}

	public static float getHeightInch(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(
				UI_ADAPTER_INFO_RECORD_PREFS, 0);
		return prefs.getInt("heightPixels", 0) / prefs.getFloat("ydpi", 1);
	}

	public static float getScreenInch(Context context) {
		return (float) Math.sqrt(getWidthInch(context) * getWidthInch(context)
				+ getHeightInch(context) * getHeightInch(context));
	}

	public static int getPixByDip(Context context, float dip) {
		SharedPreferences prefs = context.getSharedPreferences(
				UI_ADAPTER_INFO_RECORD_PREFS, 0);
		return (int) (prefs.getFloat("density", 1.0f) * dip);
	}

	public static float getSpByPix(Context context, float pix) {
		SharedPreferences prefs = context.getSharedPreferences(
				UI_ADAPTER_INFO_RECORD_PREFS, 0);
		return (float) (pix / prefs.getFloat("scaledDensity", 1.0f));
	}
}
