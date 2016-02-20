package db.com.thirdpartylibrary.utils;

import java.util.List;

public class CollectionUtils {

	public static <T> void listCopyByObject(List<T> dest, List<T> toCopied) {
		if (null == toCopied || null == dest) {
			return;
		}
		for (T t : toCopied) {
			if (!dest.contains(t)) {
				dest.add(t);
			}
		}
	}
}
