package db.com.thirdpartylibrary.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import android.content.Context;
import android.os.Environment;


public class AssetPropertiesHelper {

	public static interface AssetPropertiesChangedListener {
		public void onChanged(String propertyName);
	}

	Context mContext;
	Properties mPro;

	List<AssetPropertiesChangedListener> mListeners = new ArrayList<AssetPropertiesChangedListener>();
	private String extFileName;

	public AssetPropertiesHelper(Context context) {
		mContext = context;
		mPro = new Properties();
	}

//	public void load(String fileName) {
//		extFileName = Environment.getExternalStorageDirectory()
//				.getAbsolutePath()
//				+ "efan"
//				+ File.separator
//				+ File.separator + fileName;
//		load(fileName, true);
//	}

	public void load(String fileName) {
		InputStream is = null;
		try {
				is = mContext.getAssets().open(fileName);
			mPro.load(is);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getString(String propertyName) {
		return mPro.getProperty(propertyName);
	}

	public int getInt(String propertyName, int defaultValue) {
		return Integer.valueOf(mPro.getProperty(propertyName,
				String.valueOf(defaultValue)));
	}

	public short getShort(String propertyName, short defaultValue) {
		return Short.valueOf(mPro.getProperty(propertyName,
				String.valueOf(defaultValue)));
	}

	public boolean getBoolean(String propertyName, boolean defaultValue) {
		return Boolean.valueOf(mPro.getProperty(propertyName,
				String.valueOf(defaultValue)));
	}

	public void put(String propertyName, String value) {
		mPro.put(propertyName, value);
		System.out.println("propertyName: "+propertyName+"   "+value);
		//mPro.setProperty(propertyName,value);
		onChanged(propertyName);
		saveProperties();
	}

	public void saveProperties() {
		File f=new File(extFileName);
		try {
			OutputStream out = new FileOutputStream(f);
			mPro.store(out,"");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void put(String propertyName, int value) {
		mPro.put(propertyName, String.valueOf(value));
		onChanged(propertyName);
		saveProperties();
	}

	public void put(String propertyName, short value) {
		mPro.put(propertyName, String.valueOf(value));
		onChanged(propertyName);
		saveProperties();
	}

	public void put(String propertyName, boolean value) {
		mPro.put(propertyName, String.valueOf(value));
		mPro.setProperty(propertyName, String.valueOf(value));
		onChanged(propertyName);
		saveProperties();
	}

	private void onChanged(String propertyName) {
		synchronized (mListeners) {
			for (AssetPropertiesChangedListener listener : mListeners) {
				try {
					listener.onChanged(propertyName);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void addListener(AssetPropertiesChangedListener listener) {
		synchronized (mListeners) {
			if (!mListeners.contains(listener)) {
				mListeners.add(listener);
			}
		}
	}

	public void removeListener(AssetPropertiesChangedListener listener) {
		synchronized (mListeners) {
			mListeners.remove(listener);
		}
	}
}
