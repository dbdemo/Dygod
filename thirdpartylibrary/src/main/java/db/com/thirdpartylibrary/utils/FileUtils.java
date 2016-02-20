package db.com.thirdpartylibrary.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;

import android.content.res.AssetManager;

public class FileUtils {

	/**
	 * 
	 * @param file
	 * @param mode
	 */
	public static void chmod(File file, String mode) {
		try {
			Runtime.getRuntime().exec(
					"chmod " + mode + " " + file.getCanonicalPath());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param oldPathWithName
	 * @param newPathWithName
	 * @return
	 */
	public static boolean rename(String oldPathWithName, String newPathWithName) {
		File myOldFile = new File(oldPathWithName);
		File myNewFile = new File(newPathWithName);
		return myOldFile.renameTo(myNewFile);
	}

	/**
	 * 
	 * @param path
	 *            must end with "/" or "/a.jar"
	 * @return true if existed, false else
	 */
	public static boolean mkdirs(String path) {
		String dirs = path.substring(0, path.lastIndexOf("/") + 1);
		File file = new File(dirs);
		if (file.exists()) {
			return true;
		} else {
			return file.mkdirs();
		}
	}

	/**
	 * 
	 * @param inputFile
	 * @param outputFile
	 */
	public static void copyFile(File inputFile, File outputFile) {
		InputStream instrm = null;
		try {
			instrm = new FileInputStream(inputFile);
			saveStreamToFile(instrm, outputFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			closeInputStream(instrm);
		}
	}

	
	public static void saveStreamToFileForDb(InputStream stream, File outputFile){
		OutputStream ops=null;
		byte[] b=new byte[1024];
		int len=0;
		try {
		ops	=new FileOutputStream(outputFile);
			while((len=stream.read(b))!=-1){
				ops.write(b, 0, len);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(ops!=null){
				try {
					ops.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
	/**
	 * 
	 * @param stream
	 * @param outputFile
	 */
	public static void saveStreamToFile(InputStream stream, File outputFile) {
		OutputStream ostrm = null;
		try {
			ostrm = new FileOutputStream(outputFile.getAbsolutePath() + ".tmp");
			saveStreamToFile(stream, ostrm);
			ostrm.flush();
			closeOutputStream(ostrm);

			rename(outputFile.getAbsolutePath() + ".tmp",
					outputFile.getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeOutputStream(ostrm);
		}
	}

	/**
	 * 
	 * @param instream
	 * @param outStream
	 * @return
	 */
	private static boolean saveStreamToFile(InputStream instream,
			OutputStream outStream) {
		try {
			byte[] data = new byte[1024];
			while ((instream.read(data)) != -1) {
				outStream.write(data);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static void closeInputStream(InputStream stream) {
		try {
			if (null != stream) {
				stream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param stream
	 */
	public static void closeOutputStream(OutputStream stream) {
		try {
			if (null != stream) {
				stream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param stream
	 */
	public static void closeOutputStream(RandomAccessFile stream) {
		try {
			if (null != stream) {
				stream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param fname
	 * @param assetManager
	 * @return
	 */
	public static String loadFromAssetsFile(String fname,
			AssetManager assetManager) {
		String result = null;
		try {
			InputStream in = assetManager.open(fname);
			int ch = 0;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while ((ch = in.read()) != -1) {
				baos.write(ch);
			}
			byte[] buff = baos.toByteArray();
			baos.close();
			in.close();
			result = new String(buff, "UTF-8");
			result = result.replaceAll("\\r\\n", "\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
