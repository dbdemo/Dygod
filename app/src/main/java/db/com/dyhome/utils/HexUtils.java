package db.com.dyhome.utils;

import android.content.Context;
import android.content.pm.PackageInfo;

import java.util.List;
import java.util.Locale;

public class HexUtils {

    private static final char[] hexArray = {48, 49, 50, 51, 52, 53, 54, 55,
            56, 57, 97, 98, 99, 100, 101, 102};

    private static int getCharIndex(char ch) {
        for (int i = 0; i < hexArray.length; i++) {
            if (ch == hexArray[i]) {
                return i;
            }
        }
        return -1;
    }

    public static short getShort(byte[] bytesArray, int offset) {
        return (short) (((bytesArray[offset] & 0x00FF) << 8) | (bytesArray[offset + 1] & 0x00FF));
    }

    public static int getInt(byte[] bytesArray, int offset) {
        return (int) (((bytesArray[offset] & 0x00FF) << 24)
                | ((bytesArray[offset] & 0x00FF) << 16)
                | ((bytesArray[offset] & 0x00FF) << 8) | (bytesArray[offset + 1] & 0x00FF));

    }

    public static String getString(byte[] bytesArray) {
        return getString(bytesArray, 0, bytesArray.length);
    }

    public static String getString(byte[] bytesArray, int offset, int length) {
        if (null == bytesArray) {
            return "";
        }
        char[] ret = new char[length * 2];
        int retIndex = 0;
        for (int i = 0; i < length; i++) {
            retIndex = 2 * i;
            ret[retIndex] = hexArray[(bytesArray[offset + i] & 0x00FF) >> 4];
            ret[retIndex + 1] = hexArray[bytesArray[offset + i] & 0x000F];
        }
        return new String(ret);
    }

    public static boolean equals(byte[] array1, byte[] array2) {
        if (null == array1 && null == array2) {
            return true;
        }
        if (null == array1 && null != array2) {
            return false;
        }
        if (null != array1 && null == array2) {
            return false;
        }

        if (array1.length != array2.length) {
            return false;
        }

        for (int i = 0; i < array1.length; i++) {
            if (array1[i] != array2[i]) {
                return false;
            }
        }
        return true;
    }

    public static byte[] getBytes(String szInput) {
        if (null == szInput || "".equals(szInput)) {
            return null;
        }
        String stdszInput = szInput.toLowerCase(Locale.getDefault());
        byte[] rets = new byte[stdszInput.length() / 2];
        for (int i = 0; i < stdszInput.length(); i += 2) {

            // hight 4 bits
            char nchar = stdszInput.charAt(i);
            int index = getCharIndex(nchar);
            if (index < 0) {
                return null;
            }
            rets[i / 2] = (byte) ((index << 4) & 0x00F0);

            // low 4 bits
            nchar = stdszInput.charAt(i + 1);
            index = getCharIndex(nchar);
            if (index < 0) {
                return null;
            }
            rets[i / 2] |= (byte) ((index << 0) & 0x000F);
        }

        return rets;
    }

    public static String getUUIDByBytes(byte[] bytesUUID) {
        String szProfileUUID = getString(bytesUUID);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(szProfileUUID.substring(0, 8));
        stringBuilder.append("-");
        stringBuilder.append(szProfileUUID.substring(8, 12));
        stringBuilder.append("-");
        stringBuilder.append(szProfileUUID.substring(12, 16));
        stringBuilder.append("-");
        stringBuilder.append(szProfileUUID.substring(16, 20));
        stringBuilder.append("-");
        stringBuilder.append(szProfileUUID.substring(20, 32));
        return stringBuilder.toString();
    }

    public static void getBytes(byte[] src, int offset, int value) {
        src[offset + 3] = (byte) (0x000000ff & value);
        src[offset + 2] = (byte) ((0x0000ff00 & value) >> 8);
        src[offset + 1] = (byte) ((0x0000ff00 & value) >> 16);
        src[offset + 0] = (byte) ((0x0000ff00 & value) >> 24);
        return;
    }

    public static void getBytes(byte[] src, int offset, short value) {
        src[offset + 1] = (byte) (0x000000ff & value);
        src[offset + 0] = (byte) ((0x0000ff00 & value) >> 8);
        return;
    }

    /***
     * 查看设备是否安装了迅雷
     *
     * @return
     */
    public static boolean getHaveXunLei(Context context) {

        List<PackageInfo> packages = context.getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packages.size(); i++) {
            PackageInfo packageInfo = packages.get(i);
            if("com.xunlei.downloadprovider".equals(packageInfo.packageName)){
                return true;
            }
        }
        return false;
    }
}
