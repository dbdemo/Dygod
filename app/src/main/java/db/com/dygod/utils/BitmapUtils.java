package db.com.dygod.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapUtils {

	public static Bitmap scaleBitmap(String path, int targetWidth, int targetHeight) {
		Bitmap bit = null;
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		bit = BitmapFactory.decodeFile(path, opts);
		int heightRatio = Math.round((float) opts.outHeight / (float) targetWidth);
		int widthRatio = Math.round((float) opts.outWidth / (float) targetHeight);
		opts.inSampleSize = heightRatio < widthRatio ? widthRatio : heightRatio;
		if (opts.inSampleSize != 0) {
			opts.outHeight = opts.outHeight / opts.inSampleSize;
			opts.outWidth = opts.outWidth / opts.inSampleSize;
		}
		opts.inJustDecodeBounds = false;
		try {
			bit = BitmapFactory.decodeFile(path, opts);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bit;
	}

	public static int getImageHeight(String path) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, opts);
		return opts.outHeight;
	}

	public static int getImageWidth(String path) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, opts);
		return opts.outWidth;
	}
}
